
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import webSocket.wsHandler;

/**
 *
 * Esto es una clase de notas, porque asi lo cree mas facil
 */
public class NOTAS {
    /**
     * Usar webSocket para enlazar con las notificaciones (la campanita) 
     *      que nos indique el numero
     *      (response encode)
     *      DemoWeb01 > demo_servlet > calculaSuma
     * 
     * La session tiene que tener la mierda esa de si no aceptas las cookies
     * 
     * Fachadas -> para que enlace con el entity
     *
     * JSPF
     * <jsp:include page="__" /> -- esto en el jsp que usaremos
     * Lo de jspf lo metremos en WEB-INF/jspf/*
     *      Esto hara que no pueda ser visible desde le navegador.
     *
     *  
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
         //      wsHandler.broadcastMessage("Hello World");
}
    
}
