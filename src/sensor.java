import java.sql.*;

public class sensor {
	public static void main(String args[]) {
		Connection conexion = null;
	    Statement stmt = null;
	    String sql;
	    
	    int radomNforT;
	    int radomNforH;   
	    int sensorID;
	    
	      try {
	    	 
	         //Insertar 50 registros a la BD 
	         for (int i=0; i<50; i++) {

	         	 //Valores aleatorios para registros
	      	   	 sensorID = radomNumber("sensor");
	        	 radomNforT = radomNumber("temperatura");
	        	 radomNforH = radomNumber("humedad");
	        	 
	        	 //Abrir conexion a DB
		         Class.forName("org.postgresql.Driver");
		         conexion = DriverManager
		            .getConnection("jdbc:postgresql://localhost:5432/sensordb",
		            "postgres", "MrPopo13");
		         conexion.setAutoCommit(false);
		        //System.out.println("Base de datos abierta");
		         stmt = conexion.createStatement();
	      	   	
	      	   	 //Insertar valores a DB. La fecha es insertada automáticamente por la base de datos
		         sql = "INSERT INTO measurements (sensorid, temperature, humidity) "
		              + "VALUES (" + sensorID + "," + radomNforT + "," + radomNforH + ");";
		         stmt.executeUpdate(sql);
		         //System.out.println("Registro " + (i+1) + " insertado");
		        
		         //Cerrar conexion
		         stmt.close();
		         conexion.commit();
		         conexion.close();
		         //System.out.println("Base de datos cerrada");
		        
		         //Pausar por 1 segundo
		         Thread.sleep(1000);
	         }//Fin for
	    
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	      }
	      
	      System.out.println("Todos los registros insertados");
	   }//Fin Main
	
	//Método para generar números aleatorios para temperatura, humedad y sensorID con rangos específicos
	public static int radomNumber(String elemento) {
			int minimo = 0;
			int maximo = 0;
			
			if (elemento == "temperatura") {
				minimo = 5;
				maximo = 42;
			}
			else if (elemento == "humedad") {
				minimo = 20;
				maximo = 70;
			}
			else if (elemento == "sensor") {
				minimo = 0;
				maximo = 3;
			}
			int rn = minimo + (int)(Math.random()*(maximo - minimo) + 1); 
			return rn; 
		}
	
	}//Fin Class sensor
	


