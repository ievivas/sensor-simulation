import java.sql.*;

public class monitor {
	public static void main(String args[]) {
		Connection conexion = null;
	    Statement stmt = null;
	    ResultSet consulta;

	    try {
	    
	    	//Consultar último registro, 5 veces 
	    	for (int i=0; i<5; i++) {
	    		int sensorID;
	    		int temperatura = 0;
	    		int humedad = 0;
	    		Timestamp fecha;
       	 
       	 		//Abrir conexion a DB
	    		Class.forName("org.postgresql.Driver");
	    		conexion = DriverManager
	    				.getConnection("jdbc:postgresql://localhost:5432/sensordb",
	    						"postgres", "MrPopo13");
	    		conexion.setAutoCommit(false);
	    		//System.out.println("Base de datos abierta");
	    		stmt = conexion.createStatement();
     	   	
		         //Consultar último registro insertado 
		         consulta = stmt.executeQuery( "SELECT * FROM measurements ORDER BY date DESC LIMIT 1;" );
		         while ( consulta.next() ) {
		        	 sensorID = consulta.getInt("sensorid");
		        	 temperatura = consulta.getInt("temperature");
		        	 humedad = consulta.getInt("humidity");
		        	 fecha = consulta.getTimestamp("date");
		           // System.out.println( "sensorid = " + sensorID );
		           // System.out.println( "Temperatura = " + temperatura );
		           // System.out.println( "Humedad = " + humedad );
		           // System.out.println( "Fecha = " + fecha );
		           // System.out.println("-----------------------------");
		         }
	         
		         //Cerrar conexion
		         consulta.close();
		         stmt.close();
		         conexion.commit();
		         conexion.close();
		         //System.out.println("Base de datos cerrada");
		         
		         //Umbrales
		         if ((temperatura < 40)||(temperatura > 45)) {
		        	 System.out.println( "ALARMA: Temperatura = " + temperatura );
		         }
		         if ((humedad < 60)||(humedad > 80)) {
		        	 System.out.println( "ALARMA: Humedad = " + humedad );
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

}
