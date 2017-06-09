package bd.servlet;


//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;








/**
 *
 * @author Borja
 */
public class EnviarNotificacion implements parametrosConexion{



    public static void enviarNotificacionPush(String titulo, String mensaje, String token) throws Exception {


        URL url = new URL(urlFB);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

        conexion.setUseCaches(false);
        conexion.setDoInput(true);
        conexion.setDoOutput(true);
        
        /*Creamos la cabecera de la petici�n*/

        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json");
        conexion.setRequestProperty("Authorization", "key="+claveAutenticacion);
        
     
        /*Se crean 2 JSON, 1 para el cuerpo del mensaje y otro para la llamada a google*/
        
        JSONObject JsonInfo = new JSONObject();
        JsonInfo.put("to", token.trim());
        
        JSONObject infoNotificacion = new JSONObject();
        infoNotificacion.put("title", titulo); // Notification title
        infoNotificacion.put("body", mensaje); // Notification body
        JsonInfo.put("notification", infoNotificacion);
       

        OutputStreamWriter wr = new OutputStreamWriter(conexion.getOutputStream());
        wr.write(JsonInfo.toString());
        wr.flush();
        wr.close();
        
        
        /*Si el c�digo es 200 todo es correcto*/
        System.out.println("Respuesta : " + conexion.getResponseCode());

      

        /*BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        System.out.println("Respuesta : " + response);
        
        in.close();*/

    }


}

