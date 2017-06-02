package bd.servlet;

import bd.Usuario;
import bd.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Iulian
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    @PersistenceContext(unitName = "Web-DBPU")
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
        
        HttpSession sesion = request.getSession();
        
        String user = request.getParameter("user");
        String password = request.getParameter("pass");
        
        Usuario p = new Usuario();
        p.setId(user);
        p.setPass(password);
        
        UsuarioJpaController jpa = new UsuarioJpaController(utx, em.getEntityManagerFactory());
        
        int respuesta = jpa.validar(p);
        System.out.println(respuesta);
        if((respuesta >= 2) && (respuesta < 5)) {
           sesion.setAttribute("Rol", respuesta);
           sesion.setAttribute("user", user);
           
           String UCI = jpa.getUCI(p);
           sesion.setAttribute("UCI", UCI);
           response.sendRedirect(response.encodeRedirectURL("menu.jsp"));
        }
        else{
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        }
        
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet loginServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet loginServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }
}
