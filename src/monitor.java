import java.sql.*;
import java.io.*;
import java.net.*;



public class monitor {
	public static void main(String args[]) {
		Connection conexion = null;
	    Statement stmt = null;
	    ResultSet consulta;
	    
	    int sensorID = 0;
		int temperatura = 0;
		int humedad = 0;
		Timestamp fecha;
		
		String sub_url1 = "https://api.telegram.org/";
		String botToken = "143736528:AAExJNxaLVdjDwvyyorcknZiDf8r65azj8I";
		String chat_id = "-38984406"; //Chat al grupo SensoresSimGrupo
		String mensajeT;
		String mensajeH;
	    
		try {
	    
	    	//Consultar �ltimo registro, 5 veces 
	    	for (int i=0; i<10; i++) {
	    		
       	 		//Abrir conexion a DB
	    		Class.forName("org.postgresql.Driver");
	    		conexion = DriverManager
	    				.getConnection("jdbc:postgresql://localhost:5432/sensordb",
	    						"postgres", "MrPopo13");
	    		conexion.setAutoCommit(false);
	    		//System.out.println("Base de datos abierta");
	    		stmt = conexion.createStatement();
     	   	
		         //Consultar �ltimo registro insertado 
		         consulta = stmt.executeQuery( "SELECT * FROM measurements ORDER BY date DESC LIMIT 1;" );
		         while ( consulta.next() ) {
		        	 sensorID = consulta.getInt("sensorid");
		        	 temperatura = consulta.getInt("temperature");
		        	 humedad = consulta.getInt("humidity");
		        	 fecha = consulta.getTimestamp("date");
		           // System.out.println( "sensorid = " + sensorID );
		           // System.out.println( "Temperatura = " + temperatura );
		           // System.out.println( "Humedad = " + humedad );
		            System.out.println( "Fecha = " + fecha );
		           // System.out.println("-----------------------------");
		         }
	         
		         //Cerrar conexion a DB
		         consulta.close();
		         stmt.close();
		         conexion.commit();
		         conexion.close();
		         //System.out.println("Base de datos cerrada");
		         
		         //ALARMAS
		         if ((temperatura < 40)||(temperatura > 45)) {
		        	//System.out.println( "ALARMA: Temperatura = " + temperatura );
		        	 mensajeT = "ALARMA: " + 
		        			 	"Medicion fuera del rango normal. " +
		        			 	"Temperatura = " + temperatura + " gC," +
		        			 	" en el sensor " + sensorID;
		        	 enviarMensajeBot(sub_url1, botToken, chat_id, mensajeT);
		         }
		         if ((humedad < 60)||(humedad > 80)) {
		        	 //System.out.println( "ALARMA: Humedad = " + humedad );
		        	 mensajeH = "ALARMA: " + 
		        			 	"Medicion fuera del rango normal. " +
		        			 	"%20Humedad = " + humedad + " %20%," +
		        			 	" en el sensor " + sensorID;
		        	 enviarMensajeBot(sub_url1, botToken, chat_id, mensajeH);
		         }
	        
		         //Pausa por 10 segundos
		         Thread.sleep(10000);
	    	}//Fin for
   
	    } catch (Exception e) {
	    	System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	    	System.exit(0);
	    }
     
	    //System.out.println("Registros consultados con exito");

	}//Fin Main
	
	public static String enviarMensajeBot(String sub_url1, String botToken, String chat_id, String mensaje) throws Exception {
		String urlString = sub_url1 + "bot" + botToken + "/sendMessage?" + "chat_id=" + chat_id + "&text=" + mensaje;
		
		URL url = new URL(urlString);
   	 	URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }
        in.close();
		return urlString; 
	}

}//Fin Class Monitor
