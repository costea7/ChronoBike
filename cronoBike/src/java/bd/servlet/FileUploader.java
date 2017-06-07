/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.servlet;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import utilidades.LectorCSV;

/**
 *
 * @author fserna
 */
@WebServlet(name = "FileUploader", urlPatterns = {"/FileUploader"})
public class FileUploader extends HttpServlet {

    @Resource(mappedName = "jms/queue01")
    private Queue queue01;

    @Resource(mappedName = "jms/queueFactory")
    private QueueConnectionFactory queueFactory;

    private int n = 0;

    @PersistenceContext(unitName = "DemoFileUploadPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            
            throws ServletException, IOException {
        try {
            

           
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            // Create a new file upload handler
            System.out.println();
            System.out.println();
            System.out.println("content-length: " + request.getContentLength());
            System.out.println("method: " + request.getMethod());
            System.out.println("character encoding: " + request.getCharacterEncoding());

            if (isMultipart) {
                DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
                dfiFactory.setSizeThreshold(500 * 1024);

                ServletFileUpload upload = new ServletFileUpload(dfiFactory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> it = items.iterator();

                while (it.hasNext()) {
                    FileItem item = it.next();

                    if (item.isFormField()) {
                        System.out.println("4. Form field " + item.getFieldName() + " with value '"
                                + item.getString() + "' detected.");
                    } else {
                        System.out.println("4. File field " + item.getFieldName() + " with file name '"
                                + item.getName() + "' detected.");

                        System.out.println("5. Name: " + item.getName());
                        System.out.println("6. MIME: " + item.getContentType());

                        String fileName = FilenameUtils.getName(item.getName());

                        String path = getServletContext().getRealPath("/");
                        File directorio = new File(path + "/uploads");
                        if (!directorio.exists()) {
                            directorio.mkdirs();
                        }

                        File uploadedFile = new File(directorio + "/" + fileName);
                        System.out.println("El directorio del fichero es: " + directorio);
                        System.out.println("7. Fichero recibido: " + uploadedFile.getName());
                        item.write(uploadedFile);

                        String msg = LectorCSV.procesarFichero(uploadedFile);
                        System.out.println("He llegado aquí");

                        System.out.println("Mensaje vale " + msg);

                        sendJMSMessageToQueue01(msg);

                    
                    }
                }
            } else {
                // NO es multipart
                response.sendRedirect(response.encodeRedirectURL("error.jsp"));
            }

            response.sendRedirect(response.encodeRedirectURL("ok.jsp"));

        } catch (FileUploadException ex) {
            System.out.println(ex.toString());
            response.sendRedirect(response.encodeRedirectURL("error.jsp"));
        } catch (Exception e) {
            System.out.println(e.toString());
            response.sendRedirect(response.encodeRedirectURL("error.jsp"));
        }
    }

    private void sendJMSMessageToQueue01(String _txt) {

        Connection connection = null;
        Session session = null;
        try {
            connection = queueFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue01);
            TextMessage txtMsg = session.createTextMessage(_txt);
            txtMsg.setIntProperty("id", n++);
            messageProducer.send(txtMsg);

        } catch (JMSException ex) {
            Logger.getLogger(SendToQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(SendToQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(response.encodeRedirectURL("error.jsp"));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
            throw new RuntimeException(e);
        }
    }*/
}
