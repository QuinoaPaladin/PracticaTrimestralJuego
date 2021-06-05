package es.studium.Cluedo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class VistaTablero extends Frame 
{
		
	private static final long serialVersionUID = 1L;
	
	
	
	Image imagentablero;
	Image dado;
	Toolkit herramienta;
	
	
	//FICHAS DE JUGADORES
	
	int xJugador1 = 25;
	int yJugador1 = 292;
	
	int xJugador2 = 140;
	int yJugador2 = 409;
	
	int xJugador3 = 400;
	int yJugador3 = 327;
	
	int xJugador4 = 401;
	int yJugador4 = 116;
	
	//NOMBRES DE JUGADORES
	
	int xNombreJugador1 = 325;
	int yNombreJugador1 = 550;
	String jugador1 = "";
	
	int xNombreJugador2 = 325;
	int yNombreJugador2 = 575;
	String jugador2 = "";
	
	int xNombreJugador3 = 325;
	int yNombreJugador3 = 600;
	String jugador3 = "";
	
	int xNombreJugador4 = 325;
	int yNombreJugador4 = 625;
	String jugador4 = "";
	
	
	int xJugadorActual = 255;
	int yJugadorActual = 475;
	String jugadorActualBase = "Es el turno de ";
	String jugadorActualCambia = "Jugador 1";
	
	public VistaTablero()
	{
		herramienta = getToolkit();
		imagentablero = herramienta.getImage("tableronuevo.jpg");
		dado = herramienta.getImage("dado.png");
		this.setTitle("Cluedo");
		this.setBackground(Color.magenta);
		this.setSize(440,700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		//ventanaTablero.setVisible(false);
	}
	public void paint(Graphics g)
	{
		Font fuente = new Font("Jokerman", Font.BOLD, 14);
		g.setFont(fuente);
		g.drawImage(imagentablero,0,0,this);
		g.drawImage(dado,20,440,this);
		g.drawString("Tirar el dado", 16,525);
		
		
		g.drawString(jugadorActualBase + jugadorActualCambia, 255,475);
		
		g.drawString(jugador1, xNombreJugador1, yNombreJugador1);
		g.drawString(jugador2, xNombreJugador2, yNombreJugador2);
		g.drawString(jugador3, xNombreJugador3, yNombreJugador3);
		g.drawString(jugador4, xNombreJugador4, yNombreJugador4);
		
		
		g.drawString("Decir hipótesis", 20, 680);
		g.drawString("Resolver Caso", 164, 680);
		g.drawString("Terminar Partida", 300, 680);
		
		//Hipótesis
		g.drawRect(17, 663, 110, 22);
		//Resolver
		g.drawRect(161, 663, 110, 22);
		//Terminar
		g.drawRect(298, 663, 118, 22);
		
		
		//JUGADORES
		
		//Jugador1 izquierda
		g.setColor(Color.blue);
		g.fillRect(xJugador1, yJugador1, 16, 18);
		//Jugador2 abajo izquierda
		g.setColor(Color.red);
		g.fillRect(xJugador2, yJugador2, 17, 17);
		//Jugador3 derecha
		g.setColor(Color.green);
		g.fillRect(xJugador3, yJugador3, 17, 18);
		//Jugador4 arriba derecha
		g.setColor(Color.orange);
		g.fillRect(xJugador4, yJugador4, 17, 18);
		
		//CUADROS PARA SABER QUIÉN ES QUIÉN DEBAJO
		g.setColor(Color.blue);
		g.fillRect(300, 536, 18, 18);
		//Jugador2 abajo izquierda
		g.setColor(Color.red);
		g.fillRect(300, 561, 18, 18);
		//Jugador3 derecha
		g.setColor(Color.green);
		g.fillRect(300, 586, 18, 18);
		//Jugador4 arriba derecha
		g.setColor(Color.orange);
		g.fillRect(300, 611, 18, 18);
		
		
		
		
		//Casillas
		g.setColor(Color.CYAN);
		g.drawRect(40, 277, 50, 50);
		g.drawRect(90, 277, 50, 50);
		g.drawRect(140, 277, 47, 33);
		g.drawRect(140, 309, 30, 50);
		g.drawRect(140, 359, 30, 48);
		g.drawRect(156, 229, 31, 48);
		g.drawRect(156, 180, 31, 48);
		g.drawRect(42, 130, 63, 34);
		g.drawRect(106, 130, 48, 50);
		g.drawRect(123, 49, 31, 80);
		g.drawRect(155, 147, 32, 33);
		g.drawRect(187, 147, 83, 33);
		g.drawRect(269, 147, 49, 33);
		g.drawRect(286, 49, 32, 66);
		g.drawRect(286, 115, 58, 33);
		g.drawRect(343, 115, 58, 33);
		g.drawRect(187, 292, 82, 18);
		g.drawRect(270, 181, 47, 45);
		g.drawRect(270, 227, 47, 35);
		g.drawRect(270, 263, 47, 47);
		g.drawRect(270, 310, 45, 50);
		g.drawRect(270, 359, 30, 50);
		g.drawRect(316, 328, 41, 33);
		g.drawRect(357, 328, 41, 33);
	}
	public void actualizarJugador1(int x, int y)
	{
		this.xJugador1 = x-2;
		this.yJugador1 = y-2;
		repaint();
	}
	public void actualizarJugador2(int x, int y)
	{
		this.xJugador2 = x-4;
		this.yJugador2 = y-4;
		repaint();
	}
	public void actualizarJugador3(int x, int y)
	{
		this.xJugador3 = x-6;
		this.yJugador3 = y-6;
		repaint();
	}
	public void actualizarJugador4(int x, int y)
	{
		this.xJugador4 = x-8;
		this.yJugador4 = y-8;
		repaint();
	}
	
	public void pintarNombreJugador1(String nombreJugador1)
	{
		
		this.jugador1 = nombreJugador1;
		repaint();
	}
	public void pintarNombreJugador2(String nombreJugador2)
	{
		
		this.jugador2 = nombreJugador2;
		repaint();
	}
	public void pintarNombreJugador3(String nombreJugador3)
	{
		
		this.jugador3 = nombreJugador3;
		repaint();
	}
	public void pintarNombreJugador4(String nombreJugador4)
	{
		
		this.jugador4 = nombreJugador4;
		repaint();
	}
	
	public void pintarNombreJugadorActual(String nombreJugadorActual)
	{
		
		this.jugadorActualCambia = nombreJugadorActual;
		repaint();
	}
}
