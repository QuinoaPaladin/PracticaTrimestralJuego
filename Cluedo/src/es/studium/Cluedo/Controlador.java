package es.studium.Cluedo;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;







public class Controlador implements ActionListener, WindowListener,  MouseListener, KeyListener
{
	
	VistaMenu vista = null;
	Modelo modelo = null;
	VistaTablero tablero = null;
	VistaNuevaPartida nuevapartida = null;
	VistaAyuda ayuda = null;
	VistaOpciones opciones = null;
	VistaResolver resolver = null;
	VistaHipotesis hipotesis = null;
	VistaRanking ranking = null;

	Modelo bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	// COORDENADAS DE JUGADORES : jugador1 = 32,300  jugador2 = 149,416 jugador3 = 407,337 jugador4 = 408,123
	
	int casillas[][] = {
			
		    {73,81},  {135,90},{220,96}, 		   		     {300,83}, {366,75},
			{73,144},{132,151},{169,161},{226,165},{292,161},{310,132},{373,131},{408,123},
				      {89,225},{172,204},		   {291,211},{365,189},
			                   {170,251},		   {289,243},
   {32,300},{65,301},{115,300},{163,292},{226,298},{291,279},{361,285},
					 {81,375}, {152,334},{219,365},{290,333},{331,342},{375,345},{407,337},
							   {154,384},		   {285,383},{358,396},
							   {149,416}};
	
	
	int casillassalida[][] = {{32,300},{149,416},{407,337},{408,123}};
	
	int casillaposicion[] = {0,1,2,3,4,
							5,6,7,8,9,10,11,12,
							13,14,15,16,
							17,18,
							19,20,21,22,23,24,25,
							26,27,28,29,30,31,32,
							33,34,35,
							36};
	
	int movimientos[] = {1,1,7,13,3,
						4,14,15,12,15,11,3,3,
						1,6,6,3,
						6,6,
						2,2,15,15,13,15,3,
						0,6,0,11,8,3,3,
						0,0,0,
						0};
	
		//QUÉ SIGNIFICA CADA NÚMERO (POSIBILIDADES PARA MOVERSE)
		// 0 = arriba6          1 = abajo3        2 = derecha2           3 = izquierda7
		// 4=arriba derecha1    5=arriba izquierda       6=arriba abajo5
		// 7=abajo derecha1      8=abajo izquierda1        
		// 10=arriba izquierda abajo      11=arriba derecha abajo2     12=derecha izquierda1   13=izquierda derecha abajo2
		// 14 = izquierda derecha arriba1  15= las 4 5
	
	
	int posicionJugador1 = casillaposicion[19];
	int posicionJugador2 = casillaposicion[36];
	int posicionJugador3 = casillaposicion[32];
	int posicionJugador4 = casillaposicion[12];
		
	
	String nombreJugador1 = "";
	String nombreJugador2 = "";
	String nombreJugador3 = "";
	String nombreJugador4 = "";
	
	String nombreJugadorActual = "";
	
	Random r = new Random();
	Random p = new Random();
	
	int resultado =0;
	
	
	String sospechosos[] = {"Señora Escarlata", "Coronel Rubio", "Señora Blanco", "Padre Prado", "Señora Celeste", "Profesor Mora"};
	String armas[] = {"Candelabro","Daga","Tubería","Revolver","Soga","Llave inglesa"};
	String habitaciones[] = {"Cocina","Sala de música","Invernadero","Comedor","Sala de billar","Salón","Estudio","Biblioteca","Vestíbulo"};
	
	List<String> listaarmas = new ArrayList<String>(Arrays.asList(armas));
	List<String> listasus = new ArrayList<String>(Arrays.asList(sospechosos));
	List<String> listahabitaciones = new ArrayList<String>(Arrays.asList(habitaciones));
	
	
	String inventarioJugador1[] = {};
	String inventarioJugador2[] = {};
	String inventarioJugador3[] = {};
	String inventarioJugador4[] = {};
	
	List<String> listaInventarioJugador1 = new ArrayList<String>(Arrays.asList(inventarioJugador1));
	List<String> listaInventarioJugador2 = new ArrayList<String>(Arrays.asList(inventarioJugador2));
	List<String> listaInventarioJugador3 = new ArrayList<String>(Arrays.asList(inventarioJugador3));
	List<String> listaInventarioJugador4 = new ArrayList<String>(Arrays.asList(inventarioJugador4));
	
	String solucion[]= {};
	List<String> listasolucion = new ArrayList<String>(Arrays.asList(solucion));

	
	
	int turno = 0; // 0 Jugador 1, 1 Jugador 2, 2 Jugador 3, 3 Jugador 4
	int tirada = 0;
	
	int armasolucion = 0;
	int susolucion = 0;
	int habitacionsolucion = 0;
	Random a = new Random();
	Random s = new Random();
	Random h = new Random();
	
	int turnosJugador1 = 0;
	int turnosJugador2 = 0;
	int turnosJugador3 = 0;
	int turnosJugador4 = 0;
	
	
	
	public Controlador(VistaMenu vista, Modelo modelo, VistaTablero tablero, VistaNuevaPartida nuevapartida, VistaAyuda ayuda, VistaOpciones opciones, VistaResolver resolver, VistaHipotesis hipotesis, VistaRanking ranking)
	{
		
		//AÑADIR LISTENERS
		
		this.vista = vista;
		this.modelo = modelo;
		this.tablero = tablero;
		this.nuevapartida = nuevapartida;
		this.ayuda = ayuda;
		this.opciones = opciones;
		this.resolver = resolver;
		this.hipotesis = hipotesis;
		this.ranking = ranking;
		
		vista.btnNuevaPartida.addActionListener(this);
		vista.btnAyuda.addActionListener(this);
		vista.btnRanking.addActionListener(this);
		vista.btnSalir.addActionListener(this);
		
		opciones.ventanaCluedo.addWindowListener(this);
		opciones.dlgOpciones.addWindowListener(this);	
		opciones.btnArma.addActionListener(this);
		opciones.btnSospechoso.addActionListener(this);
		opciones.btnHabitacion.addActionListener(this);
		
		
		resolver.frmResolver.addWindowListener(this);
		resolver.dlgResolver.addWindowListener(this);
		resolver.dlgGanaste.addWindowListener(this);
		resolver.btnResolver.addActionListener(this);
		resolver.btnGanaste.addActionListener(this);
		
		hipotesis.frmHipotesis.addWindowListener(this);
		hipotesis.dlgHipotesis.addWindowListener(this);
		hipotesis.btnResolverHipotesis.addActionListener(this);
		
		
		nuevapartida.btnAceptarNuevaPartida.addActionListener(this);
		
		ranking.ventanaRanking.addWindowListener(this);
		ranking.btnAceptarRanking.addActionListener(this);
			
		
		ayuda.btnAceptarAyuda.addActionListener(this);
		
		nuevapartida.ventanaNuevaPartida.addWindowListener(this);
		vista.ventanaMenu.addWindowListener(this);
		ayuda.ventanaAyuda.addWindowListener(this);
		vista.addWindowListener(this);
		
		tablero.addKeyListener(this);
		tablero.addMouseListener(this);
		tablero.addWindowListener(this);
		
	}
	

	
public void windowActivated(WindowEvent arg0){}

public void windowClosed(WindowEvent arg0){}

public void windowDeactivated(WindowEvent arg0){}

public void windowDeiconified(WindowEvent arg0){}

public void windowIconified(WindowEvent arg0){}

public void windowOpened(WindowEvent arg0){}

public void actionPerformed(ActionEvent evento)
{
	
	if (evento.getSource().equals(vista.btnNuevaPartida))
	{
		nuevapartida.ventanaNuevaPartida.setLayout(new FlowLayout());
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.lblJugador1);
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.txfJugador1);
		
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.lblJugador2);
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.txfJugador2);
		
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.lblJugador3);
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.txfJugador3);
		
		
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.lblJugador4);
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.txfJugador4);
		
		nuevapartida.ventanaNuevaPartida.add(nuevapartida.btnAceptarNuevaPartida);
		nuevapartida.ventanaNuevaPartida.setSize(170, 300);
		nuevapartida.ventanaNuevaPartida.setVisible(true);
		
		
	}
	
	if (evento.getSource().equals(nuevapartida.btnAceptarNuevaPartida))
	{
		
		opciones.ventanaCluedo.setVisible(false);
		vista.ventanaMenu.setVisible(false);
		nuevapartida.ventanaNuevaPartida.setVisible(false);
		tablero.setVisible(true);
		
		
		armasolucion = a.nextInt(armas.length);
		susolucion = s.nextInt(sospechosos.length);
		habitacionsolucion = h.nextInt(habitaciones.length);
		
		listasolucion.add(listaarmas.get(armasolucion));
		listasolucion.add(listasus.get(susolucion));
		listasolucion.add(listahabitaciones.get(habitacionsolucion));
			
		
		listaarmas.remove(listasolucion.get(0));
		listasus.remove(listasolucion.get(1));
		listahabitaciones.remove(listasolucion.get(2));
		
		
		System.out.println(listasolucion.get(0));
		System.out.println(listasolucion.get(1));
		System.out.println(listasolucion.get(2));
		
		
			
		nombreJugador1 = nuevapartida.txfJugador1.getText();
		nombreJugador2 = nuevapartida.txfJugador2.getText();
		
		tablero.pintarNombreJugador1(nombreJugador1);
		tablero.pintarNombreJugador2(nombreJugador2);
		
		
			nombreJugador3 = nuevapartida.txfJugador3.getText();
			tablero.pintarNombreJugador3(nombreJugador3);
		
		
		
			nombreJugador4 = nuevapartida.txfJugador4.getText();
			tablero.pintarNombreJugador4(nombreJugador4);
		
		
		if(turno==0)
		{
			nombreJugadorActual = nombreJugador1;
			tablero.pintarNombreJugadorActual(nombreJugadorActual);
		}
		else if(turno==1)
		{
			nombreJugadorActual = nombreJugador2;
			tablero.pintarNombreJugadorActual(nombreJugadorActual);
		}
		else if(turno==2)
		{
			nombreJugadorActual = nombreJugador3;
			tablero.pintarNombreJugadorActual(nombreJugadorActual);
		}
		else if(turno==3)
		{
			nombreJugadorActual = nombreJugador4;
			tablero.pintarNombreJugadorActual(nombreJugadorActual);
		}
		
		
		
		turnosJugador1++;
		this.InicioJugador1();
		this.InicioJugador2();
		this.InicioJugador3();
		this.InicioJugador4();
		
	}
	
	if (evento.getSource().equals(opciones.btnArma))
	{
		if(listaarmas.size()==0)
		{
			System.out.println("No hay más cartas de arma");
		}
		else if(listaarmas.size()!=0)
		{
			if(turno==0)
			{
				resultado = r.nextInt(listaarmas.size());
				System.out.println("Has obtenido la siguiente carta: "+listaarmas.get(resultado));
				listaInventarioJugador1.add(listaarmas.get(resultado));
				listaarmas.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==1)
			{
				resultado = r.nextInt(listaarmas.size());
				System.out.println("Has obtenido la siguiente carta: "+listaarmas.get(resultado));
				listaInventarioJugador2.add(listaarmas.get(resultado));
				listaarmas.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==2)
			{
				resultado = r.nextInt(listaarmas.size());
				System.out.println("Has obtenido la siguiente carta: "+listaarmas.get(resultado));
				listaInventarioJugador3.add(listaarmas.get(resultado));
				listaarmas.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==3)
			{
				resultado = r.nextInt(listaarmas.size());
				System.out.println("Has obtenido la siguiente carta: "+listaarmas.get(resultado));
				listaInventarioJugador4.add(listaarmas.get(resultado));
				listaarmas.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
		}
		
	}
	
	if (evento.getSource().equals(opciones.btnSospechoso))
	{
		if(listasus.size()==0)
		{
			System.out.println("No hay más cartas de sospechosos");
		}
		else if(listasus.size()!=0)
		{
			if(turno==0)
			{
				resultado = r.nextInt(listasus.size());
				System.out.println("Has obtenido la siguiente carta: "+listasus.get(resultado));
				listaInventarioJugador1.add(listasus.get(resultado));
				listasus.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==1)
			{
				resultado = r.nextInt(listasus.size());
				System.out.println("Has obtenido la siguiente carta: "+listasus.get(resultado));
				listaInventarioJugador2.add(listasus.get(resultado));
				listasus.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==2)
			{
				resultado = r.nextInt(listasus.size());
				System.out.println("Has obtenido la siguiente carta: "+listasus.get(resultado));
				listaInventarioJugador3.add(listasus.get(resultado));
				listasus.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==3)
			{
				resultado = r.nextInt(listasus.size());
				System.out.println("Has obtenido la siguiente carta: "+listasus.get(resultado));
				listaInventarioJugador4.add(listasus.get(resultado));
				listasus.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
		}
	}
	
	if (evento.getSource().equals(opciones.btnHabitacion))
	{
		if(listahabitaciones.size()==0)
		{
			System.out.println("No hay más cartas de habitaciones");
		}
		else if(listahabitaciones.size()!=0)
		{
			if(turno==0)
			{
				resultado = r.nextInt(listahabitaciones.size());
				System.out.println("Has obtenido la siguiente carta: "+listahabitaciones.get(resultado));
				listaInventarioJugador1.add(listahabitaciones.get(resultado));
				listahabitaciones.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==1)
			{
				resultado = r.nextInt(listahabitaciones.size());
				System.out.println("Has obtenido la siguiente carta: "+listahabitaciones.get(resultado));
				listaInventarioJugador2.add(listahabitaciones.get(resultado));
				listahabitaciones.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==2)
			{
				resultado = r.nextInt(listahabitaciones.size());
				System.out.println("Has obtenido la siguiente carta: "+listahabitaciones.get(resultado));
				listaInventarioJugador3.add(listahabitaciones.get(resultado));
				listahabitaciones.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
			if(turno==3)
			{
				resultado = r.nextInt(listahabitaciones.size());
				System.out.println("Has obtenido la siguiente carta: "+listahabitaciones.get(resultado));
				listaInventarioJugador4.add(listahabitaciones.get(resultado));
				listahabitaciones.remove(resultado);
				opciones.dlgOpciones.setVisible(false);
				tirada=0;
			}
		}
	}
	
	
	//GANAR LA PARTIDA
	
	if (evento.getSource().equals(resolver.btnResolver))
	{
		if(turno==0)
		{
			if(resolver.txfArma.getText().equals(listasolucion.get(0)) && resolver.txfSus.getText().equals(listasolucion.get(1)) && resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Has tardado el siguiente número de turnos: " + turnosJugador1);
				System.out.println(nombreJugador1);
				resolver.dlgGanaste.setVisible(true);
			}
			else
			{
				resolver.dlgResolver.setVisible(false);
				limpiarResolver();
				System.out.println("Algo no está bien");
				System.out.println("A partir de ahora solo puedes moverte, para que no te aburras");
			}
		}
		if(turno==1)
		{
			if(resolver.txfArma.getText().equals(listasolucion.get(0)) && resolver.txfSus.getText().equals(listasolucion.get(1)) && resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Has tardado el siguiente número de turnos: " + turnosJugador2);
				resolver.dlgGanaste.setVisible(true);
			}
			else
			{
				resolver.dlgResolver.setVisible(false);
				limpiarResolver();
				System.out.println("Algo no está bien");
				System.out.println("A partir de ahora solo puedes moverte, para que no te aburras");
			}
		}
		if(turno==2)
		{
			if(resolver.txfArma.getText().equals(listasolucion.get(0)) && resolver.txfSus.getText().equals(listasolucion.get(1)) && resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Has tardado el siguiente número de turnos: " + turnosJugador3);
				resolver.dlgGanaste.setVisible(true);
			}
			else
			{
				resolver.dlgResolver.setVisible(false);
				limpiarResolver();
				System.out.println("Algo no está bien");
				System.out.println("A partir de ahora solo puedes moverte, para que no te aburras");
			}
		}
		if(turno==3)
		{
			if(resolver.txfArma.getText().equals(listasolucion.get(0)) && resolver.txfSus.getText().equals(listasolucion.get(1)) && resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Has tardado el siguiente número de turnos: " + turnosJugador4);
				resolver.dlgGanaste.setVisible(true);
			}
			else
			{
				resolver.dlgResolver.setVisible(false);
				limpiarResolver();
				System.out.println("Algo no está bien");
				System.out.println("A partir de ahora solo puedes moverte, para que no te aburras");
			}
		}
	}
	
	
	//HACER HIPÓTESIS 
	
	if (evento.getSource().equals(hipotesis.btnResolverHipotesis))
	{
		if(turno==0)
		{
			if(!resolver.txfArma.getText().equals(listasolucion.get(0)) || !resolver.txfSus.getText().equals(listasolucion.get(1)) || !resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Tu hipótesis no es correcta");
				limpiarHipotesis();
				hipotesis.dlgHipotesis.setVisible(false);				
			}
			else 
			{
				System.out.println("");
				System.out.println("Tu hipótesis es correcta");
			}
		}
		else if(turno==1)
		{
			if(!resolver.txfArma.getText().equals(listasolucion.get(0)) || !resolver.txfSus.getText().equals(listasolucion.get(1)) || !resolver.txfHabitacion.getText().equals(listasolucion.get(2)))
			{
				System.out.println("");
				System.out.println("Tu hipótesis no es correcta");
				limpiarHipotesis();
				hipotesis.dlgHipotesis.setVisible(false);
				
			}
			else
			{
				System.out.println("Tu hipótesis es correcta");
			}
		}
		else if(turno==2)
		{
			if(!resolver.txfArma.getText().equals(listasolucion.get(0)) || !resolver.txfSus.getText().equals(listasolucion.get(1)) || !resolver.txfHabitacion.getText().equals(listasolucion.get(2)))	
				{
					System.out.println("");
					System.out.println("Tu hipótesis no es correcta");
					limpiarHipotesis();
					hipotesis.dlgHipotesis.setVisible(false);
					
				}
			else
			{
				System.out.println("Tu hipótesis es correcta");
			}
		}
		else if(turno==3)
		{
			if(!resolver.txfArma.getText().equals(listasolucion.get(0)) || !resolver.txfSus.getText().equals(listasolucion.get(1)) || !resolver.txfHabitacion.getText().equals(listasolucion.get(2)))					
					{
						System.out.println("");
						System.out.println("Tu hipótesis no es correcta");
						limpiarHipotesis();
						hipotesis.dlgHipotesis.setVisible(false);						
					}
			else
			{
				System.out.println("Tu hipótesis es correcta");
			}
		}
	}
	
	if (evento.getSource().equals(resolver.btnGanaste))
	{
		if (turno==0);
		{
		bd = new Modelo();
		connection = bd.conectar();
		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if(((nuevapartida.txfJugador1.getText().length())!=0)&&((turno==0)))
			{
				sentencia = "INSERT INTO jugadores VALUES(null, '" + nombreJugador1 + "', '" + turnosJugador1 + "');";
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			}
			else if(((nuevapartida.txfJugador2.getText().length())!=0)&&((turno==1)))
			{
				sentencia = "INSERT INTO jugadores VALUES(null, '" + nombreJugador2 + "', '" + turnosJugador2 + "');";
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			}
			else if(((nuevapartida.txfJugador3.getText().length())!=0)&&((turno==2)))
			{
				sentencia = "INSERT INTO jugadores VALUES(null, '" + nombreJugador3 + "', '" + turnosJugador3 + "');";
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			}
			else if(((nuevapartida.txfJugador4.getText().length())!=0)&&((turno==3)))
			{
				sentencia = "INSERT INTO jugadores VALUES(null, '" + nombreJugador4 + "', '" + turnosJugador4 + "');";
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			}
			else
			{
				System.out.println("Error");
			}
		}
		catch (SQLException sqle)
		{
			
		}
		finally
		{
		System.exit(0);
		}
		}
	}
		
	
	if (evento.getSource().equals(vista.btnRanking))
	{
		bd = new Modelo();
		connection = bd.conectar();
		
		sentencia = "SELECT * FROM jugadores ORDER BY puntuacionJugador ASC LIMIT 10;";
		try
		{
			
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(sentencia);
			ranking.txaRanking.selectAll();
			ranking.txaRanking.setText("");
			ranking.txaRanking.append("Nombre\t\tTurnos\n");
			while(rs.next())
			{
				ranking.txaRanking.append(rs.getString("nombreJugador")+"\t"+"\t"+rs.getInt("puntuacionJugador")+"\n");
			}
		}
		catch (SQLException sqle)
		{
			ranking.txaRanking.setText("Se ha producido un error en la consulta");
		}
		ranking.ventanaRanking.setVisible(true);
		
	}
	if (evento.getSource().equals(ranking.btnAceptarRanking))
	{
		ranking.ventanaRanking.setVisible(false);
		
	}
	if (evento.getSource().equals(vista.btnAyuda))
	{
		
			try
			{
				Runtime.getRuntime().exec("hh.exe AyudaJuego.chm");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
	}
	if (evento.getSource().equals(ayuda.btnAceptarAyuda))
	{
		ayuda.ventanaAyuda.setVisible(false);
	}
	
	//Si pulso el botón de salir del menú se cierra el programa
	
	if (evento.getSource().equals(vista.btnSalir))
	{
		System.exit(0);	
	}
}




public Toolkit getToolkit()
{
	return null;
}



	
	
	



public void limpiarHipotesis()
{
hipotesis.txfArmaHipotesis.setText("");	
hipotesis.txfSusHipotesis.setText("");	
hipotesis.txfHabitacionHipotesis.setText("");	
}

public void limpiarResolver()
{
resolver.txfArma.setText("");	
resolver.txfSus.setText("");	
resolver.txfHabitacion.setText("");	
}


public void InicioJugador1()
{
	if(posicionJugador1==19)
	{
		
	System.out.println("");
	System.out.println(nombreJugador1 + " tus cartas son las siguientes:");
	System.out.println("");
	
	resultado = r.nextInt(listasus.size());
	System.out.println(listasus.get(resultado));
	listaInventarioJugador1.add(listasus.get(resultado));
	listasus.remove(resultado);
	
	
	resultado = r.nextInt(listaarmas.size());
	System.out.println(listaarmas.get(resultado));
	listaInventarioJugador1.add(listaarmas.get(resultado));
	listaarmas.remove(resultado);
	
	resultado = r.nextInt(listahabitaciones.size());
	System.out.println(listahabitaciones.get(resultado));
	listaInventarioJugador1.add(listahabitaciones.get(resultado));
	listahabitaciones.remove(resultado);
	
	System.out.println("");
	System.out.println("Te toca tirar el dado");
	
	}
}

public void InicioJugador2()
{
	if(posicionJugador2==36)
	{
	
	resultado = r.nextInt(listasus.size());
	listaInventarioJugador2.add(listasus.get(resultado));
	listasus.remove(resultado);
	
	resultado = r.nextInt(listaarmas.size());
	listaInventarioJugador2.add(listaarmas.get(resultado));
	listaarmas.remove(resultado);
	
	resultado = r.nextInt(listahabitaciones.size());
	listaInventarioJugador2.add(listahabitaciones.get(resultado));
	listahabitaciones.remove(resultado);
	
	}
}
public void InicioJugador3()
{
	if(posicionJugador3==32)
	{
	
	resultado = r.nextInt(listasus.size());
	listaInventarioJugador3.add(listasus.get(resultado));
	listasus.remove(resultado);
	
	resultado = r.nextInt(listaarmas.size());
	listaInventarioJugador3.add(listaarmas.get(resultado));
	listaarmas.remove(resultado);
	
	resultado = r.nextInt(listahabitaciones.size());
	listaInventarioJugador3.add(listahabitaciones.get(resultado));
	listahabitaciones.remove(resultado);
	
	}
}
public void InicioJugador4()
{
	if(posicionJugador4==12)
	{

	resultado = r.nextInt(listasus.size());
	listaInventarioJugador4.add(listasus.get(resultado));
	listasus.remove(resultado);
	
	resultado = r.nextInt(listaarmas.size());
	listaInventarioJugador4.add(listaarmas.get(resultado));
	listaarmas.remove(resultado);
	
	resultado = r.nextInt(listahabitaciones.size());
	listaInventarioJugador4.add(listahabitaciones.get(resultado));
	listahabitaciones.remove(resultado);
	

	}
}

public void MostrarInicioJugador2()
{
		System.out.println("");
		System.out.println(nombreJugador2 + " tus cartas son las siguientes:");
		System.out.println("");
		System.out.println(listaInventarioJugador2.get(0));
		System.out.println(listaInventarioJugador2.get(1));
		System.out.println(listaInventarioJugador2.get(2));
		System.out.println("");
		System.out.println("Te toca tirar el dado");
	
}

public void MostrarInicioJugador3()
{
		System.out.println("");
		System.out.println(nombreJugador3 + " tus cartas son las siguientes:");
		System.out.println("");
		System.out.println(listaInventarioJugador3.get(0));
		System.out.println(listaInventarioJugador3.get(1));
		System.out.println(listaInventarioJugador3.get(2));
		System.out.println("");
		System.out.println("Te toca tirar el dado");
	
}

public void MostrarInicioJugador4()
{
		System.out.println("");
		System.out.println(nombreJugador4 + " tus cartas son las siguientes:");
		System.out.println("");
		System.out.println(listaInventarioJugador4.get(0));
		System.out.println(listaInventarioJugador4.get(1));
		System.out.println(listaInventarioJugador4.get(2));
		System.out.println("");
		System.out.println("Te toca tirar el dado");
	
}

public void windowClosing(WindowEvent evento)

{
	if(evento.getSource().equals(tablero))
	{
	System.exit(0);
	}
	
	if(evento.getSource().equals(ranking.ventanaRanking))
	{
	ranking.ventanaRanking.setVisible(false);
	}
	
	if(evento.getSource().equals(hipotesis.dlgHipotesis))
	{
	hipotesis.dlgHipotesis.setVisible(false);
	}
	
	if(evento.getSource().equals(resolver.dlgResolver))
	{
		resolver.dlgResolver.setVisible(false);
	}
	
	if(evento.getSource().equals(opciones.dlgOpciones))
	{
		System.out.println("Debes elegir una opción");
		//opciones.dlgOpciones.setVisible(false);
	}
	
	if(evento.getSource().equals(nuevapartida.ventanaNuevaPartida))
	{
		nuevapartida.ventanaNuevaPartida.setVisible(false);
	}
	
	if(evento.getSource().equals(opciones.ventanaCluedo))
	{
		System.exit(0);
	}
	
	
	if(evento.getSource().equals(ayuda.ventanaAyuda))
	{
		ayuda.ventanaAyuda.setVisible(false);
	}
	
	if(evento.getSource().equals(vista.ventanaMenu))
	{
		System.exit(0);
	}
}



public void mouseClicked(MouseEvent evento)
{
	
	
	int x = evento.getX();
	int y = evento.getY();
	
	//DADO
	
	if((x>=30)&&(x<=85)&&(y>=450)&&(y<=510))
	{
		if(turno==0 && tirada==0)
		{
			tirada = this.modelo.tirada();
			System.out.println(nombreJugador1 + " --> "+ "Tienes el siguiente número de movimientos: " +tirada);
			
		}
		else if(turno==0 && tirada>0)
		{
			System.out.println("Solo una vez por turno");
		}
		if(turno==1 && tirada==0)
		{
			tirada = this.modelo.tirada();
			System.out.println(nombreJugador2 + " --> "+ "Tienes el siguiente número de movimientos: " +tirada);
		}
		else if(turno==1 && tirada>0)
		{
			System.out.println("Solo una vez por turno");
		}
		if(turno==2 && tirada==0)
		{
			tirada = this.modelo.tirada();
			System.out.println(nombreJugador3 + " --> "+ "Tienes el siguiente número de movimientos: " +tirada);
		}
		else if(turno==2 && tirada>0)
		{
			System.out.println("Solo una vez por turno");
		}
		if(turno==3 && tirada==0)
		{
			tirada = this.modelo.tirada();
			System.out.println(nombreJugador4 + " --> "+ "Tienes el siguiente número de movimientos: " +tirada);
		}
		else if(turno==3 && tirada>0)
		{
			System.out.println("Solo una vez por turno");
		}
				
	}
	
	//RESOLVER
	
	if((x>=161)&&(x<=281)&&(y>=663)&&(y<=685))
	{
		if(listaarmas.size()==0 && listasus.size()==0 && listahabitaciones.size()==0)
		{
			if(posicionJugador1==0 || posicionJugador1==2 || posicionJugador1==4 || posicionJugador1==13 || posicionJugador1==16 || posicionJugador1==25 || posicionJugador1==26 || posicionJugador1==28 || posicionJugador1==35 && turno==0)
			{
				resolver.dlgResolver.setVisible(true);
			}
			else if(posicionJugador2==0 || posicionJugador2==2 || posicionJugador2==4 || posicionJugador2==13 || posicionJugador2==16 || posicionJugador2==25 || posicionJugador2==26 || posicionJugador2==28 || posicionJugador2==35 && turno==1)
			{
				resolver.dlgResolver.setVisible(true);
			}
			else if(posicionJugador3==0 || posicionJugador3==2 || posicionJugador3==4 || posicionJugador3==13 || posicionJugador3==16 || posicionJugador3==25 || posicionJugador3==26 || posicionJugador3==28 || posicionJugador3==35 && turno==2)
			{
				resolver.dlgResolver.setVisible(true);
			}
			else if(posicionJugador3==0 || posicionJugador3==2 || posicionJugador3==4 || posicionJugador3==13 || posicionJugador3==16 || posicionJugador3==25 || posicionJugador3==26 || posicionJugador3==28 || posicionJugador3==35 && turno==3)
			{
				resolver.dlgResolver.setVisible(true);
			}
	
		}
		else if (listaarmas.size()!=0 && listasus.size()!=0 && listahabitaciones.size()!=0)
		{
			System.out.println("Debes estar en una habitación");
		}
	}	
		
		//HIPÓTESIS
		
	if((x>=17)&&(x<=127)&&(y>=663)&&(y<=685))
	{
		if(listaarmas.size()==0 && listasus.size()==0 && listahabitaciones.size()==0)
		{
			if(posicionJugador1==0 || posicionJugador1==2 || posicionJugador1==4 || posicionJugador1==13 || posicionJugador1==16 || posicionJugador1==25 || posicionJugador1==26 || posicionJugador1==28 || posicionJugador1==35 && turno==0)
			{
				hipotesis.dlgHipotesis.setVisible(true);
			}
			else if(posicionJugador2==0 || posicionJugador2==2 || posicionJugador2==4 || posicionJugador2==13 || posicionJugador2==16 || posicionJugador2==25 || posicionJugador2==26 || posicionJugador2==28 || posicionJugador2==35 && turno==1)
			{
				hipotesis.dlgHipotesis.setVisible(true);
			}
			else if(posicionJugador3==0 || posicionJugador3==2 || posicionJugador3==4 || posicionJugador3==13 || posicionJugador3==16 || posicionJugador3==25 || posicionJugador3==26 || posicionJugador3==28 || posicionJugador3==35 && turno==2)
			{
				hipotesis.dlgHipotesis.setVisible(true);
			}
			else if(posicionJugador3==0 || posicionJugador3==2 || posicionJugador3==4 || posicionJugador3==13 || posicionJugador3==16 || posicionJugador3==25 || posicionJugador3==26 || posicionJugador3==28 || posicionJugador3==35 && turno==3)
			{
				hipotesis.dlgHipotesis.setVisible(true);
			}
		
			}
			else if (listaarmas.size()!=0 && listasus.size()!=0 && listahabitaciones.size()!=0)
			{
				System.out.println("Debes estar en una habitación");
			}
		}
		
	if((x>=298)&&(x<=416)&&(y>=663)&&(y<=685))
	{
		System.exit(0);
	}
	
}

public void keyPressed(KeyEvent pulsartecla)
{
	int key = pulsartecla.getKeyCode();
	
	
	// 0 = arriba          1 = abajo        2 = derecha           3 = izquierda
			// 4=arriba derecha    5=arriba izquierda       6=arriba abajo
			// 7=abajo derecha      8=abajo izquierda        
			// 10=arriba izquierda abajo       11=arriba derecha abajo        12=derecha izquierda   13=izquierda derecha abajo
			// 14 = izquierda derecha arriba  15= las 4
	
	if (turno==0)
	{  
				
		if(tirada>0)
		{
			boolean movido1 = false;
			
			
			
				
			//arriba 0
			
			if((posicionJugador1==26)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==26)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[21][0], casillas[21][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=21;
				movido1 = true;
			}
			else if((posicionJugador1==26)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==26)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==36)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==36)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[33][0], casillas[33][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=33;
				movido1 = true;
			}
			else if((posicionJugador1==36)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==36)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==33)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==33)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[27][0], casillas[27][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=27;
				movido1 = true;
			}
			else if((posicionJugador1==33)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==33)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==28)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==28)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[23][0], casillas[23][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=23;
				movido1 = true;
			}
			else if((posicionJugador1==28)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==28)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==34)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==34)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[29][0], casillas[29][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=29;
				movido1 = true;
			}
			else if((posicionJugador1==34)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==34)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==35)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==35)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[30][0], casillas[30][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=30;
				movido1 = true;
			}
			else if((posicionJugador1==35)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==35)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			//abajo 1
			
			if((posicionJugador1==0)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==0)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==0)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[5][0], casillas[5][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=5;
				movido1 = true;
			}
			else if((posicionJugador1==0)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==1)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==1)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==1)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[6][0], casillas[6][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=6;
				movido1 = true;
			}
			else if((posicionJugador1==1)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==13)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==13)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==13)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[21][0], casillas[21][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=21;
				movido1 = true;
			}
			else if((posicionJugador1==13)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			//derecha 2
			
			
			if((posicionJugador1==19)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[20][0], casillas[20][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=20;
				movido1 = true;
			}
			else if((posicionJugador1==19)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==19)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==19)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
				
			else if((posicionJugador1==20)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[21][0], casillas[21][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=21;
				movido1=true;
			}
			else if((posicionJugador1==20)&&(key == KeyEvent.VK_UP))
			{
				System.out.println("Imposible avanzar por aquí");
			}
			else if((posicionJugador1==20)&&(key == KeyEvent.VK_LEFT))
			{
				System.out.println("Imposible avanzar por aquí");
			}
			else if((posicionJugador1==20)&&(key == KeyEvent.VK_DOWN))
			{
				System.out.println("Imposible avanzar por aquí");
			}
						
			//izquierda 3
			
			if((posicionJugador1==12)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==12)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==12)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==12)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[11][0], casillas[11][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=11;
				movido1 = true;
			}

			if((posicionJugador1==4)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==4)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==4)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==4)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[3][0], casillas[3][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=3;
				movido1 = true;
			}

			if((posicionJugador1==11)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==11)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==11)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==11)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[10][0], casillas[10][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=10;
				movido1 = true;
			}

			if((posicionJugador1==16)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==16)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==16)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==16)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[9][0], casillas[9][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=9;
				movido1 = true;
			}

			if((posicionJugador1==25)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==25)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==25)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==25)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[24][0], casillas[24][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=24;
				movido1 = true;
			}

			if((posicionJugador1==32)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==32)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==32)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==32)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[31][0], casillas[31][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=31;
				movido1 = true;
			}

			if((posicionJugador1==31)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==31)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==31)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==31)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[30][0], casillas[30][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=30;
				movido1 = true;
			}
			
			//arriba derecha 4
			if((posicionJugador1==5)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[6][0], casillas[6][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=6;
				movido1 = true;
			}
			else if((posicionJugador1==5)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[0][0], casillas[0][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=0;
				movido1 = true;
			}
			else if((posicionJugador1==5)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==5)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			//arriba abajo 6
			
			if((posicionJugador1==14)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==14)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[7][0], casillas[7][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=7;
				movido1 = true;
			}
			else if((posicionJugador1==14)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[17][0], casillas[17][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=17;
				movido1 = true;
			}
			else if((posicionJugador1==14)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==15)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==15)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[9][0], casillas[9][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=9;
				movido1 = true;
			}
			else if((posicionJugador1==15)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[18][0], casillas[18][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=18;
				movido1 = true;
			}
			else if((posicionJugador1==15)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==17)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==17)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[14][0], casillas[14][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=14;
				movido1 = true;
			}
			else if((posicionJugador1==17)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[22][0], casillas[22][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=22;
				movido1 = true;
			}
			else if((posicionJugador1==17)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==18)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==18)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[15][0], casillas[15][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=15;
				movido1 = true;
			}
			else if((posicionJugador1==18)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[24][0], casillas[24][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=24;
				movido1 = true;
			}
			else if((posicionJugador1==18)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==27)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==27)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[22][0], casillas[22][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=22;
				movido1 = true;
			}
			else if((posicionJugador1==27)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[33][0], casillas[33][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=33;
				movido1 = true;
			}
			else if((posicionJugador1==27)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			//abajo derecha 7
			if((posicionJugador1==2)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[3][0], casillas[3][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=3;
				movido1 = true;
			}
			else if((posicionJugador1==2)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==2)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[7][0], casillas[7][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=7;
				movido1 = true;
			}
			else if((posicionJugador1==2)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			// 11=arriba derecha abajo  
			
			if((posicionJugador1==29)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[30][0], casillas[30][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=30;
				movido1 = true;
			}
			else if((posicionJugador1==29)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[24][0], casillas[24][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=24;
				movido1 = true;
			}
			else if((posicionJugador1==29)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[34][0], casillas[34][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=34;
				movido1 = true;
			}
			else if((posicionJugador1==29)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}

			if((posicionJugador1==10)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[11][0], casillas[11][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=11;
				movido1 = true;
			}
			else if((posicionJugador1==10)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[3][0], casillas[3][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=3;
				movido1 = true;
			}
			else if((posicionJugador1==10)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[9][0], casillas[9][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=9;
				movido1 = true;
			}
			else if((posicionJugador1==10)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			
			//12=derecha izquierda   
			if((posicionJugador1==8)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[9][0], casillas[9][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=9;
				movido1 = true;
			}
			else if((posicionJugador1==8)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==8)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==8)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[7][0], casillas[7][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=7;
				movido1 = true;
			}
			// 13=izquierda derecha abajo
			
			if((posicionJugador1==3)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[4][0], casillas[4][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=4;
				movido1 = true;
			}
			else if((posicionJugador1==3)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==3)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[10][0], casillas[10][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=10;
				movido1 = true;
			}
			else if((posicionJugador1==3)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[2][0], casillas[2][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=2;
				movido1 = true;
			}
			if((posicionJugador1==23)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[24][0], casillas[24][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=24;
				movido1 = true;
			}
			else if((posicionJugador1==23)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==23)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[28][0], casillas[28][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=28;
				movido1 = true;
			}
			else if((posicionJugador1==23)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[22][0], casillas[22][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=22;
				movido1 = true;
			}
			
			if((posicionJugador1==30)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[31][0], casillas[31][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=31;
				movido1 = true;
			}
			else if((posicionJugador1==30)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==30)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[35][0], casillas[35][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=35;
				movido1 = true;
			}
			else if((posicionJugador1==30)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[29][0], casillas[29][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=29;
				movido1 = true;
			}
			// 14 = izquierda derecha arriba  
			if((posicionJugador1==6)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[7][0], casillas[7][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=7;
				movido1 = true;
			}
			else if((posicionJugador1==6)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[1][0], casillas[1][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=1;
				movido1 = true;
			}
			else if((posicionJugador1==6)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				System.out.println("Imposible avanzar");
			}
			else if((posicionJugador1==6)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[5][0], casillas[5][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=5;
				movido1 = true;
			}
			// 15= las 4  
			
			if((posicionJugador1==21)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[22][0], casillas[22][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=22;
				movido1 = true;
			}
			else if((posicionJugador1==21)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[13][0], casillas[13][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				
				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=13;
				movido1 = true;
			}
			else if((posicionJugador1==21)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				
				this.tablero.actualizarJugador1(casillas[26][0], casillas[26][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=26;
				movido1 = true;
			}
			else if((posicionJugador1==21)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[20][0], casillas[20][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=20;
				movido1 = true;
				
			}

			if((posicionJugador1==22)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[23][0], casillas[23][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=23;
				movido1 = true;
			}
			else if((posicionJugador1==22)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[17][0], casillas[17][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=17;
				movido1 = true;
			}
			else if((posicionJugador1==22)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[27][0], casillas[27][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=27;
				movido1 = true;
			}
			else if((posicionJugador1==22)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[21][0], casillas[21][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=21;
				movido1 = true;
			}

			if((posicionJugador1==24)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[25][0], casillas[25][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=25;
				movido1 = true;
			}
			else if((posicionJugador1==24)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[18][0], casillas[18][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=18;
				movido1 = true;
			}
			else if((posicionJugador1==24)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[29][0], casillas[29][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=29;
				movido1 = true;
			}
			else if((posicionJugador1==24)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[23][0], casillas[23][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=23;
				movido1 = true;
			}

			if((posicionJugador1==9)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[16][0], casillas[16][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=16;
				movido1 = true;
			}
			else if((posicionJugador1==9)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[10][0], casillas[10][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=10;
				movido1 = true;
			}
			else if((posicionJugador1==9)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[15][0], casillas[15][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=15;
				movido1 = true;
			}
			else if((posicionJugador1==9)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[8][0], casillas[8][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=8;
				movido1 = true;
			}

			if((posicionJugador1==7)&&(key == KeyEvent.VK_RIGHT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[8][0], casillas[8][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=8;
				movido1 = true;
			}
			else if((posicionJugador1==7)&&(key == KeyEvent.VK_UP)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[2][0], casillas[2][1]);
				tirada=tirada-1;
				System.out.println(tirada);

				if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
				{
					opciones.dlgOpciones.setVisible(true);
				}
				else
				{
					opciones.dlgOpciones.setVisible(false);
				}
				
				posicionJugador1=2;
				movido1 = true;
			}
			else if((posicionJugador1==7)&&(key == KeyEvent.VK_DOWN)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[14][0], casillas[14][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=14;
				movido1 = true;
			}
			else if((posicionJugador1==7)&&(key == KeyEvent.VK_LEFT)&&(movido1==false))
			{
				this.tablero.actualizarJugador1(casillas[6][0], casillas[6][1]);
				tirada=tirada-1;
				System.out.println(tirada);
				posicionJugador1=6;
				movido1 = true;
			}
			
			if(posicionJugador2==36 && tirada==0)
			{
				MostrarInicioJugador2();
			}
			
			if(tirada==0)
			{
				System.out.println("");
				System.out.println("Turno de " + nombreJugador2 );
				System.out.println("");
				turno++;
				turnosJugador2++;
				nombreJugadorActual = nombreJugador2;
				tablero.pintarNombreJugadorActual(nombreJugadorActual);
				
				
			}
		}
	}	
	
	
	else if (turno==1)
	{       
		
		if(tirada>0)
		{
				boolean movido = false;
				
				//arriba 0
				
				if((posicionJugador2==26)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==26)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=21;
					movido = true;
				}
				else if((posicionJugador2==26)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==26)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				if((posicionJugador2==36)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[33][0], casillas[33][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=33;
					movido = true;
				}
				else if((posicionJugador2==36)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				else if((posicionJugador2==36)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==36)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==33)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==33)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[27][0], casillas[27][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=27;
					movido = true;
				}
				else if((posicionJugador2==33)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==33)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==28)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==28)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=23;
					movido = true;
				}
				else if((posicionJugador2==28)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==28)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==34)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==34)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=29;
					movido = true;
				}
				else if((posicionJugador2==34)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==34)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==35)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==35)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=30;
					movido = true;
				}
				else if((posicionJugador2==35)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==35)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//abajo 1
				
				if((posicionJugador2==0)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==0)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==0)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[5][0], casillas[5][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=5;
					movido = true;
				}
				else if((posicionJugador2==0)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==1)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==1)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==1)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=6;
					movido = true;
				}
				else if((posicionJugador2==1)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==13)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==13)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==13)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=21;
					movido = true;
				}
				else if((posicionJugador2==13)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//derecha 2
				
				
				if((posicionJugador2==19)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[20][0], casillas[20][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=20;
					movido = true;
				}
				else if((posicionJugador2==19)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==19)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==19)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
					
				else if((posicionJugador2==20)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=21;
					movido=true;
				}
				else if((posicionJugador2==20)&&(key == KeyEvent.VK_UP))
				{
					System.out.println("Imposible avanzar por aquí");
				}
				else if((posicionJugador2==20)&&(key == KeyEvent.VK_LEFT))
				{
					System.out.println("Imposible avanzar por aquí");
				}
				else if((posicionJugador2==20)&&(key == KeyEvent.VK_DOWN))
				{
					System.out.println("Imposible avanzar por aquí");
				}
							
				//izquierda 3
				
				if((posicionJugador2==12)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==12)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==12)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==12)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[11][0], casillas[11][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=11;
					movido = true;
				}

				if((posicionJugador2==4)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==4)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==4)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==4)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=3;
					movido = true;
				}

				if((posicionJugador2==11)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==11)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==11)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==11)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=10;
					movido = true;
				}

				if((posicionJugador2==16)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==16)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==16)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==16)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=9;
					movido = true;
				}

				if((posicionJugador2==25)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==25)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==25)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==25)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=24;
					movido = true;
				}

				if((posicionJugador2==32)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==32)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==32)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==32)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[31][0], casillas[31][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=31;
					movido = true;
				}

				if((posicionJugador2==31)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==31)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==31)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==31)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=30;
					movido = true;
				}
				
				//arriba derecha 4
				if((posicionJugador2==5)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=6;
					movido = true;
				}
				else if((posicionJugador2==5)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[0][0], casillas[0][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=0;
					movido = true;
				}
				else if((posicionJugador2==5)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==5)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//arriba abajo 6
				
				if((posicionJugador2==14)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==14)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=7;
					movido = true;
				}
				else if((posicionJugador2==14)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[17][0], casillas[17][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=17;
					movido = true;
				}
				else if((posicionJugador2==14)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==15)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==15)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=9;
					movido = true;
				}
				else if((posicionJugador2==15)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[18][0], casillas[18][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=18;
					movido = true;
				}
				else if((posicionJugador2==15)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==17)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==17)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[14][0], casillas[14][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=14;
					movido = true;
				}
				else if((posicionJugador2==17)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=22;
					movido = true;
				}
				else if((posicionJugador2==17)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==18)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==18)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[15][0], casillas[15][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=15;
					movido = true;
				}
				else if((posicionJugador2==18)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=24;
					movido = true;
				}
				else if((posicionJugador2==18)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==27)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==27)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=22;
					movido = true;
				}
				else if((posicionJugador2==27)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[33][0], casillas[33][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=33;
					movido = true;
				}
				else if((posicionJugador2==27)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//abajo derecha 7
				if((posicionJugador2==2)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=3;
					movido = true;
				}
				else if((posicionJugador2==2)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==2)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=7;
					movido = true;
				}
				else if((posicionJugador2==2)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
			
				// 11=arriba derecha abajo  
				
				if((posicionJugador2==29)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=30;
					movido = true;
				}
				else if((posicionJugador2==29)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=24;
					movido = true;
				}
				else if((posicionJugador2==29)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[34][0], casillas[34][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=34;
					movido = true;
				}
				else if((posicionJugador2==29)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador2==10)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[11][0], casillas[11][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=11;
					movido = true;
				}
				else if((posicionJugador2==10)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=3;
					movido = true;
				}
				else if((posicionJugador2==10)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=9;
					movido = true;
				}
				else if((posicionJugador2==10)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//12=derecha izquierda   
				if((posicionJugador2==8)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=9;
					movido = true;
				}
				else if((posicionJugador2==8)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==8)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==8)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=7;
					movido = true;
				}
				// 13=izquierda derecha abajo				
				
				if((posicionJugador2==3)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[4][0], casillas[4][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=4;
					movido = true;
				}
				else if((posicionJugador2==3)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==3)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=10;
					movido = true;
				}
				else if((posicionJugador2==3)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[2][0], casillas[2][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=2;
					movido = true;
				}
				if((posicionJugador2==23)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=24;
					movido = true;
				}
				else if((posicionJugador2==23)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==23)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[28][0], casillas[28][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=28;
					movido = true;
				}
				else if((posicionJugador2==23)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=22;
					movido = true;
				}
				if((posicionJugador2==30)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[31][0], casillas[31][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=31;
					movido = true;
				}
				else if((posicionJugador1==30)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==30)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[35][0], casillas[35][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=35;
					movido = true;
				}
				else if((posicionJugador2==30)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=29;
					movido = true;
				}
				// 14 = izquierda derecha arriba  
				if((posicionJugador2==6)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=7;
					movido = true;
				}
				else if((posicionJugador2==6)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[1][0], casillas[1][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=1;
					movido = true;
				}
				else if((posicionJugador2==6)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador2==6)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[5][0], casillas[5][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=5;
					movido = true;
				}
				// 15= las 4  
				
				if((posicionJugador2==21)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=22;
					movido = true;
				}
				else if((posicionJugador2==21)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[13][0], casillas[13][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					;
					posicionJugador2=13;
					movido = true;
				}
				else if((posicionJugador2==21)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[26][0], casillas[26][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=26;
					movido = true;
				}
				else if((posicionJugador2==21)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[20][0], casillas[20][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=20;
					movido = true;
				}

				if((posicionJugador2==22)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=23;
					movido = true;
				}
				else if((posicionJugador2==22)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[17][0], casillas[17][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=17;
					movido = true;
				}
				else if((posicionJugador2==22)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[27][0], casillas[27][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=27;
					movido = true;
				}
				else if((posicionJugador2==22)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=21;
					movido = true;
				}

				if((posicionJugador2==24)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[25][0], casillas[25][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=25;
					movido = true;
				}
				else if((posicionJugador2==24)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[18][0], casillas[18][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=18;
					movido = true;
				}
				else if((posicionJugador2==24)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=29;
					movido = true;
				}
				else if((posicionJugador2==24)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=23;
					movido = true;
				}

				if((posicionJugador2==9)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[16][0], casillas[16][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=16;
					movido = true;
				}
				else if((posicionJugador2==9)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=10;
					movido = true;
				}
				else if((posicionJugador2==9)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[15][0], casillas[15][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=15;
					movido = true;
				}
				else if((posicionJugador2==9)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[8][0], casillas[8][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=8;
					movido = true;
				}

				if((posicionJugador2==7)&&(key == KeyEvent.VK_RIGHT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[8][0], casillas[8][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=8;
					movido = true;
				}
				else if((posicionJugador2==7)&&(key == KeyEvent.VK_UP)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[2][0], casillas[2][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador2=2;
					movido = true;
				}
				else if((posicionJugador2==7)&&(key == KeyEvent.VK_DOWN)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[14][0], casillas[14][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=14;
					movido = true;
				}
				else if((posicionJugador2==7)&&(key == KeyEvent.VK_LEFT)&&(movido==false))
				{
					this.tablero.actualizarJugador2(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador2=6;
					movido = true;
				}
				
				if(posicionJugador3==32 && tirada==0)
				{
					MostrarInicioJugador3();
				}
				
				if(tirada==0 && turno==1)	
				{
					System.out.println("");
					System.out.println("Turno de " + nombreJugador3);
					System.out.println("");
					turno++;
					turnosJugador3++;
					nombreJugadorActual = nombreJugador3;
					tablero.pintarNombreJugadorActual(nombreJugadorActual);
				}
		}	
	}
	
	else if (turno==2)
		
		{	
		if(tirada>0)
		{
			//if (turno==2 && tirada3>0)
				//{           
					
					boolean movido2 = false;
					
					//arriba 0
					
					if((posicionJugador3==26)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==26)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[21][0], casillas[21][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=21;
						movido2 = true;
					}
					else if((posicionJugador3==26)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==26)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					if((posicionJugador3==36)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[33][0], casillas[33][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=33;
						movido2 = true;
					}
					else if((posicionJugador3==36)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					else if((posicionJugador3==36)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==36)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==33)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==33)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[27][0], casillas[27][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=27;
						movido2 = true;
					}
					else if((posicionJugador3==33)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==33)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==28)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==28)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[23][0], casillas[23][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=23;
						movido2 = true;
					}
					else if((posicionJugador3==28)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==28)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==34)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==34)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[29][0], casillas[29][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=29;
						movido2 = true;
					}
					else if((posicionJugador3==34)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==34)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==35)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==35)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[30][0], casillas[30][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=30;
						movido2 = true;
					}
					else if((posicionJugador3==35)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==35)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					//abajo 1
					
					if((posicionJugador3==0)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==0)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==0)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[5][0], casillas[5][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=5;
						movido2 = true;
					}
					else if((posicionJugador3==0)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==1)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==1)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==1)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[6][0], casillas[6][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=6;
						movido2 = true;
					}
					else if((posicionJugador3==1)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==13)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==13)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==13)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[21][0], casillas[21][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=21;
						movido2 = true;
					}
					else if((posicionJugador3==13)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					//derecha 2
					
					
					if((posicionJugador3==19)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[20][0], casillas[20][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=20;
						movido2 = true;
					}
					else if((posicionJugador3==19)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==19)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==19)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
						
					else if((posicionJugador3==20)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[21][0], casillas[21][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=21;
						movido2=true;
					}
					else if((posicionJugador3==20)&&(key == KeyEvent.VK_UP))
					{
						System.out.println("Imposible avanzar por aquí");
					}
					else if((posicionJugador3==20)&&(key == KeyEvent.VK_LEFT))
					{
						System.out.println("Imposible avanzar por aquí");
					}
					else if((posicionJugador3==20)&&(key == KeyEvent.VK_DOWN))
					{
						System.out.println("Imposible avanzar por aquí");
					}
								
					//izquierda 3
					
					if((posicionJugador3==12)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==12)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==12)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==12)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[11][0], casillas[11][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=11;
						movido2 = true;
					}

					if((posicionJugador3==4)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==4)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==4)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==4)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[3][0], casillas[3][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=3;
						movido2 = true;
					}

					if((posicionJugador3==11)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==11)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==11)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==11)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[10][0], casillas[10][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=10;
						movido2 = true;
					}

					if((posicionJugador3==16)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==16)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==16)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==16)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[9][0], casillas[9][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=9;
						movido2 = true;
					}

					if((posicionJugador3==25)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==25)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==25)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==25)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[24][0], casillas[24][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=24;
						movido2 = true;
					}

					if((posicionJugador3==32)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==32)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==32)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==32)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[31][0], casillas[31][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=31;
						movido2 = true;
					}

					if((posicionJugador3==31)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==31)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==31)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==31)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[30][0], casillas[30][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=30;
						movido2 = true;
					}
					
					//arriba derecha 4
					if((posicionJugador3==5)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[6][0], casillas[6][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=6;
						movido2 = true;
					}
					else if((posicionJugador3==5)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[0][0], casillas[0][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=0;
						movido2 = true;
					}
					else if((posicionJugador3==5)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==5)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					//arriba abajo 6
					
					if((posicionJugador3==14)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==14)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[7][0], casillas[7][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=7;
						movido2 = true;
					}
					else if((posicionJugador3==14)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[17][0], casillas[17][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=17;
						movido2 = true;
					}
					else if((posicionJugador3==14)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==15)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==15)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[9][0], casillas[9][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=9;
						movido2 = true;
					}
					else if((posicionJugador3==15)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[18][0], casillas[18][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=18;
						movido2 = true;
					}
					else if((posicionJugador3==15)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==17)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==17)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[14][0], casillas[14][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=14;
						movido2 = true;
					}
					else if((posicionJugador3==17)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[22][0], casillas[22][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=22;
						movido2 = true;
					}
					else if((posicionJugador3==17)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==18)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==18)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[15][0], casillas[15][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=15;
						movido2 = true;
					}
					else if((posicionJugador3==18)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[24][0], casillas[24][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=24;
						movido2 = true;
					}
					else if((posicionJugador3==18)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==27)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==27)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[22][0], casillas[22][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=22;
						movido2 = true;
					}
					else if((posicionJugador3==27)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[33][0], casillas[33][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=33;
						movido2 = true;
					}
					else if((posicionJugador3==27)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					//abajo derecha 7
					if((posicionJugador3==2)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[3][0], casillas[3][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=3;
						movido2 = true;
					}
					else if((posicionJugador3==2)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==2)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[7][0], casillas[7][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=7;
						movido2 = true;
					}
					else if((posicionJugador3==2)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
				
					// 11=arriba derecha abajo  
					
					if((posicionJugador3==29)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[30][0], casillas[30][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=30;
						movido2 = true;
					}
					else if((posicionJugador3==29)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[24][0], casillas[24][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=24;
						movido2 = true;
					}
					else if((posicionJugador3==29)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[34][0], casillas[34][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=34;
						movido2 = true;
					}
					else if((posicionJugador3==29)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}

					if((posicionJugador3==10)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[11][0], casillas[11][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=11;
						movido2 = true;
					}
					else if((posicionJugador3==10)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[3][0], casillas[3][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=3;
						movido2 = true;
					}
					else if((posicionJugador3==10)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[9][0], casillas[9][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=9;
						movido2 = true;
					}
					else if((posicionJugador3==10)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					
					//12=derecha izquierda   
					if((posicionJugador3==8)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[9][0], casillas[9][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=9;
						movido2 = true;
					}
					else if((posicionJugador3==8)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==8)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==8)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[7][0], casillas[7][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=7;
						movido2 = true;
					}
					// 13=izquierda derecha abajo				
					
					if((posicionJugador3==3)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[4][0], casillas[4][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=4;
						movido2 = true;
					}
					else if((posicionJugador3==3)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==3)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[10][0], casillas[10][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=10;
						movido2 = true;
					}
					else if((posicionJugador3==3)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[2][0], casillas[2][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=2;
						movido2 = true;
					}
					if((posicionJugador3==23)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[24][0], casillas[24][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=24;
						movido2 = true;
					}
					else if((posicionJugador3==23)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==23)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[28][0], casillas[28][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=28;
						movido2 = true;
					}
					else if((posicionJugador3==23)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[22][0], casillas[22][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=22;
						movido2 = true;
					}
					if((posicionJugador3==30)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[31][0], casillas[31][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=31;
						movido2 = true;
					}
					else if((posicionJugador3==30)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==30)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[35][0], casillas[35][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=35;
						movido2 = true;
					}
					else if((posicionJugador3==30)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[29][0], casillas[29][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=29;
						movido2 = true;
					}
					// 14 = izquierda derecha arriba  
					if((posicionJugador3==6)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[7][0], casillas[7][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=7;
						movido2 = true;
					}
					else if((posicionJugador3==6)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[1][0], casillas[1][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=1;
						movido2 = true;
					}
					else if((posicionJugador3==6)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						System.out.println("Imposible avanzar");
					}
					else if((posicionJugador3==6)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[5][0], casillas[5][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=5;
						movido2 = true;
					}
					// 15= las 4  
					
					if((posicionJugador3==21)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[22][0], casillas[22][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=22;
						movido2 = true;
					}
					else if((posicionJugador3==21)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[13][0], casillas[13][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=13;
						movido2 = true;
					}
					else if((posicionJugador3==21)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[26][0], casillas[26][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						opciones.dlgOpciones.setVisible(true);
						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						posicionJugador3=26;
						movido2 = true;
					}
					else if((posicionJugador3==21)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[20][0], casillas[20][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=20;
						movido2 = true;
					}

					if((posicionJugador3==22)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[23][0], casillas[23][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=23;
						movido2 = true;
					}
					else if((posicionJugador3==22)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[17][0], casillas[17][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=17;
						movido2 = true;
					}
					else if((posicionJugador3==22)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[27][0], casillas[27][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=27;
						movido2 = true;
					}
					else if((posicionJugador3==22)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[21][0], casillas[21][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=21;
						movido2 = true;
					}

					if((posicionJugador3==24)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[25][0], casillas[25][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=25;
						movido2 = true;
					}
					else if((posicionJugador3==24)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[18][0], casillas[18][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=18;
						movido2 = true;
					}
					else if((posicionJugador3==24)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[29][0], casillas[29][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=29;
						movido2 = true;
					}
					else if((posicionJugador3==24)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[23][0], casillas[23][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=23;
						movido2 = true;
					}

					if((posicionJugador3==9)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[16][0], casillas[16][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=16;
						movido2 = true;
					}
					else if((posicionJugador3==9)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[10][0], casillas[10][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=10;
						movido2 = true;
					}
					else if((posicionJugador3==9)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[15][0], casillas[15][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=15;
						movido2 = true;
					}
					else if((posicionJugador3==9)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[8][0], casillas[8][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=8;
						movido2 = true;
					}

					if((posicionJugador3==7)&&(key == KeyEvent.VK_RIGHT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[8][0], casillas[8][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=8;
						movido2 = true;
					}
					else if((posicionJugador3==7)&&(key == KeyEvent.VK_UP)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[2][0], casillas[2][1]);
						tirada=tirada-1;
						System.out.println(tirada);

						if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
						{
							opciones.dlgOpciones.setVisible(true);
						}
						else
						{
							opciones.dlgOpciones.setVisible(false);
						}
						
						posicionJugador3=2;
						movido2 = true;
					}
					else if((posicionJugador3==7)&&(key == KeyEvent.VK_DOWN)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[14][0], casillas[14][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=14;
						movido2 = true;
					}
					else if((posicionJugador3==7)&&(key == KeyEvent.VK_LEFT)&&(movido2==false))
					{
						this.tablero.actualizarJugador3(casillas[6][0], casillas[6][1]);
						tirada=tirada-1;
						System.out.println(tirada);
						posicionJugador3=6;
						movido2 = true;
					}
					
					if(posicionJugador4==12 && tirada==0)
					{
						MostrarInicioJugador4();
					}
					
					if(tirada==0 && turno==2)	
					{
						System.out.println("");
						System.out.println("Turno de " + nombreJugador4);
						System.out.println("");
						turno++;
						turnosJugador4++;
						nombreJugadorActual = nombreJugador4;
						tablero.pintarNombreJugadorActual(nombreJugadorActual);
					}
		}
		}
	else if (turno==3)
	{	
		if(tirada>0)
		{
		//if (turno==2 && tirada3>0)
			//{           
				
				boolean movido3 = false;
				
				//arriba 0
				
				if((posicionJugador4==26)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==26)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=21;
					movido3 = true;
				}
				else if((posicionJugador4==26)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==26)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				if((posicionJugador4==36)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[33][0], casillas[33][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=33;
					movido3 = true;
				}
				else if((posicionJugador4==36)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				else if((posicionJugador4==36)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==36)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==33)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==33)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[27][0], casillas[27][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=27;
					movido3 = true;
				}
				else if((posicionJugador4==33)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==33)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==28)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==28)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=23;
					movido3 = true;
				}
				else if((posicionJugador4==28)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==28)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==34)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==34)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=29;
					movido3 = true;
				}
				else if((posicionJugador4==34)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==34)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==35)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==35)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=30;
					movido3 = true;
				}
				else if((posicionJugador4==35)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==35)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//abajo 1
				
				if((posicionJugador4==0)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==0)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==0)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[5][0], casillas[5][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=5;
					movido3 = true;
				}
				else if((posicionJugador4==0)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==1)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==1)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==1)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=6;
					movido3 = true;
				}
				else if((posicionJugador4==1)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==13)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==13)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==13)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=21;
					movido3 = true;
				}
				else if((posicionJugador4==13)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//derecha 2
				
				
				if((posicionJugador4==19)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[20][0], casillas[20][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=20;
					movido3 = true;
				}
				else if((posicionJugador4==19)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==19)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==19)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
					
				else if((posicionJugador4==20)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador3=21;
					movido3=true;
				}
				else if((posicionJugador4==20)&&(key == KeyEvent.VK_UP))
				{
					System.out.println("Imposible avanzar por aquí");
				}
				else if((posicionJugador4==20)&&(key == KeyEvent.VK_LEFT))
				{
					System.out.println("Imposible avanzar por aquí");
				}
				else if((posicionJugador4==20)&&(key == KeyEvent.VK_DOWN))
				{
					System.out.println("Imposible avanzar por aquí");
				}
							
				//izquierda 3
				
				if((posicionJugador4==12)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==12)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==12)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==12)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[11][0], casillas[11][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=11;
					movido3 = true;
				}

				if((posicionJugador4==4)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==4)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==4)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==4)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=3;
					movido3 = true;
				}

				if((posicionJugador4==11)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==11)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==11)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==11)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=10;
					movido3 = true;
				}

				if((posicionJugador4==16)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==16)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==16)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==16)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=9;
					movido3 = true;
				}

				if((posicionJugador4==25)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==25)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==25)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==25)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=24;
					movido3 = true;
				}

				if((posicionJugador4==32)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==32)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==32)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==32)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[31][0], casillas[31][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=31;
					movido3 = true;
				}

				if((posicionJugador4==31)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==31)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==31)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==31)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=30;
					movido3 = true;
				}
				
				//arriba derecha 4
				if((posicionJugador4==5)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=6;
					movido3 = true;
				}
				else if((posicionJugador4==5)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[0][0], casillas[0][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=0;
					movido3 = true;
				}
				else if((posicionJugador4==5)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==5)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//arriba abajo 6
				
				if((posicionJugador4==14)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==14)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=7;
					movido3 = true;
				}
				else if((posicionJugador4==14)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[17][0], casillas[17][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=17;
					movido3 = true;
				}
				else if((posicionJugador4==14)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==15)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==15)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=9;
					movido3 = true;
				}
				else if((posicionJugador4==15)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[18][0], casillas[18][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=18;
					movido3 = true;
				}
				else if((posicionJugador4==15)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==17)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==17)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[14][0], casillas[14][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=14;
					movido3 = true;
				}
				else if((posicionJugador4==17)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=22;
					movido3 = true;
				}
				else if((posicionJugador4==17)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==18)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==18)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[15][0], casillas[15][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=15;
					movido3 = true;
				}
				else if((posicionJugador4==18)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=24;
					movido3 = true;
				}
				else if((posicionJugador4==18)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==27)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==27)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=22;
					movido3 = true;
				}
				else if((posicionJugador4==27)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[33][0], casillas[33][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=33;
					movido3 = true;
				}
				else if((posicionJugador4==27)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//abajo derecha 7
				if((posicionJugador4==2)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=3;
					movido3 = true;
				}
				else if((posicionJugador4==2)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==2)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=7;
					movido3 = true;
				}
				else if((posicionJugador4==2)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
			
				// 11=arriba derecha abajo  
				
				if((posicionJugador4==29)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[30][0], casillas[30][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=30;
					movido3 = true;
				}
				else if((posicionJugador4==29)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=24;
					movido3 = true;
				}
				else if((posicionJugador4==29)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[34][0], casillas[34][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=34;
					movido3 = true;
				}
				else if((posicionJugador4==29)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}

				if((posicionJugador4==10)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[11][0], casillas[11][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=11;
					movido3 = true;
				}
				else if((posicionJugador4==10)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[3][0], casillas[3][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=3;
					movido3 = true;
				}
				else if((posicionJugador4==10)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=9;
					movido3 = true;
				}
				else if((posicionJugador4==10)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				
				//12=derecha izquierda   
				if((posicionJugador4==8)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[9][0], casillas[9][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=9;
					movido3 = true;
				}
				else if((posicionJugador4==8)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==8)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==8)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=7;
					movido3 = true;
				}
				// 13=izquierda derecha abajo				
				
				if((posicionJugador4==3)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[4][0], casillas[4][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=4;
					movido3 = true;
				}
				else if((posicionJugador4==3)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==3)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=10;
					movido3 = true;
				}
				else if((posicionJugador4==3)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[2][0], casillas[2][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=2;
					movido3 = true;
				}
				if((posicionJugador4==23)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[24][0], casillas[24][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=24;
					movido3 = true;
				}
				else if((posicionJugador4==23)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==23)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[28][0], casillas[28][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=28;
					movido3 = true;
				}
				else if((posicionJugador4==23)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=22;
					movido3 = true;
				}
				if((posicionJugador4==30)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[31][0], casillas[31][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=31;
					movido3 = true;
				}
				else if((posicionJugador4==30)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==30)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[35][0], casillas[35][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=35;
					movido3 = true;
				}
				else if((posicionJugador4==30)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=29;
					movido3 = true;
				}
				// 14 = izquierda derecha arriba  
				if((posicionJugador4==6)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[7][0], casillas[7][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=7;
					movido3 = true;
				}
				else if((posicionJugador4==6)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[1][0], casillas[1][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=1;
					movido3 = true;
				}
				else if((posicionJugador4==6)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					System.out.println("Imposible avanzar");
				}
				else if((posicionJugador4==6)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[5][0], casillas[5][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=5;
					movido3 = true;
				}
				// 15= las 4  
				
				if((posicionJugador4==21)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[22][0], casillas[22][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=22;
					movido3 = true;
				}
				else if((posicionJugador4==21)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[13][0], casillas[13][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=13;
					movido3 = true;
				}
				else if((posicionJugador4==21)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[26][0], casillas[26][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=26;
					movido3 = true;
				}
				else if((posicionJugador4==21)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[20][0], casillas[20][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=20;
					movido3 = true;
				}

				if((posicionJugador4==22)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=23;
					movido3 = true;
				}
				else if((posicionJugador4==22)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[17][0], casillas[17][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=17;
					movido3 = true;
				}
				else if((posicionJugador4==22)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[27][0], casillas[27][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=27;
					movido3 = true;
				}
				else if((posicionJugador4==22)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[21][0], casillas[21][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=21;
					movido3 = true;
				}

				if((posicionJugador4==24)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[25][0], casillas[25][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=25;
					movido3 = true;
				}
				else if((posicionJugador4==24)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[18][0], casillas[18][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=18;
					movido3 = true;
				}
				else if((posicionJugador4==24)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[29][0], casillas[29][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=29;
					movido3 = true;
				}
				else if((posicionJugador4==24)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[23][0], casillas[23][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=23;
					movido3 = true;
				}

				if((posicionJugador4==9)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[16][0], casillas[16][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=16;
					movido3 = true;
				}
				else if((posicionJugador4==9)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[10][0], casillas[10][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=10;
					movido3 = true;
				}
				else if((posicionJugador4==9)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[15][0], casillas[15][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=15;
					movido3 = true;
				}
				else if((posicionJugador4==9)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[8][0], casillas[8][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=8;
					movido3 = true;
				}

				if((posicionJugador4==7)&&(key == KeyEvent.VK_RIGHT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[8][0], casillas[8][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=8;
					movido3 = true;
				}
				else if((posicionJugador4==7)&&(key == KeyEvent.VK_UP)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[2][0], casillas[2][1]);
					tirada=tirada-1;
					System.out.println(tirada);

					if(listaarmas.size()!=0 || listasus.size()!=0 || listahabitaciones.size()!=0)
					{
						opciones.dlgOpciones.setVisible(true);
					}
					else
					{
						opciones.dlgOpciones.setVisible(false);
					}
					
					posicionJugador4=2;
					movido3 = true;
				}
				else if((posicionJugador4==7)&&(key == KeyEvent.VK_DOWN)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[14][0], casillas[14][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=14;
					movido3 = true;
				}
				else if((posicionJugador4==7)&&(key == KeyEvent.VK_LEFT)&&(movido3==false))
				{
					this.tablero.actualizarJugador4(casillas[6][0], casillas[6][1]);
					tirada=tirada-1;
					System.out.println(tirada);
					posicionJugador4=6;
					movido3 = true;
				}
				if(tirada==0 && turno==3)	
				{
					System.out.println("");
					System.out.println("Turno de " + nombreJugador1);
					turno=0;
					turnosJugador1++;
					System.out.println("");
					nombreJugadorActual = nombreJugador1;
					tablero.pintarNombreJugadorActual(nombreJugadorActual);
				}
	}	}

}


public void mouseEntered(MouseEvent arg0){}

public void mouseExited(MouseEvent arg0){}

public void mousePressed(MouseEvent arg0){}

public void mouseReleased(MouseEvent arg0){}

public void keyReleased(KeyEvent arg0){}

public void keyTyped(KeyEvent arg0){}




}
