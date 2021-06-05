package es.studium.Cluedo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Modelo
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/cluedo?serverTimezone=UTC";
	String login = "root";
	String password = "Studium2020;";
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	Random rnd = new Random();
	Random p = new Random();
	
	public Connection conectar()
	{
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return connection;
	}
	
	public void desconectar(Connection con)
	{
		try
		{
			con.close();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int tirada()
	{
		return(rnd.nextInt(6)+1);
	}
	
	public int nuevaArma()
	{
		int numero = 0;
		numero = p.nextInt(6);
		return numero;
			
	}	
		
	}

