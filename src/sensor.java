import java.sql.*;

public class sensor {
	public static void main(String args[]) {
		//System.out.print("Hola Mundo");
		Connection conexion = null;
	    Statement stmt = null;
	    
	    int radomNforT;
	    int radomNforH;   
	    int sensorID;
	    
	      try {
	    	 
	         //Insertar 50 registros a la BD 
	         for (int i=0; i<50; i++) {
	        	 radomNforT = radomNumber("temperatura");
	        	 radomNforH = radomNumber("humedad");
	        	 //System.out.print(radomNforT + "\n");
	        	 
	        	 //Abrir conexion a DB
		         Class.forName("org.postgresql.Driver");
		         conexion = DriverManager
		            .getConnection("jdbc:postgresql://localhost:5432/sensordb",
		            "postgres", "MrPopo13");
		         conexion.setAutoCommit(false);
		         System.out.println("Base de datos abierta");
		         stmt = conexion.createStatement();
	        	
	      	   	 //Sensor ID
	      	   	 sensorID = radomNumber("sensor");
	      	   	
	      	   	 //Insertar valores a DB. La fecha es insertada automáticamente por la base de datos
		         String sql = "INSERT INTO measurements (sensorid, temperature, humidity) "
		              + "VALUES (" + sensorID + "," + radomNforT + "," + radomNforH + ");";
		         stmt.executeUpdate(sql);
		         System.out.println("Registro " + (i+1) + " insertado");
		        
		         //Cerrar conexion
		         stmt.close();
		         conexion.commit();
		         conexion.close();
		         System.out.println("Base de datos cerrada");
		        
		         //Pausa por 2 segundos
		         Thread.sleep(2000);
	         }
	         
	         
	         
	        
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
	


