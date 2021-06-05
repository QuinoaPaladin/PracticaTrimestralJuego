package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

public class VistaNuevaPartida
{
	Frame ventanaNuevaPartida = new Frame("Nueva Partida");
	Label lblJugador1 = new Label("Nombre del jugador Nº1");
	Label lblJugador2 = new Label("Nombre del jugador Nº2");
	Label lblJugador3 = new Label("Nombre del jugador Nº3");
	Label lblJugador4 = new Label("Nombre del jugador Nº4");
	TextField txfJugador1 = new TextField("Jugador 1");
	TextField txfJugador2 = new TextField("Jugador 2");
	TextField txfJugador3 = new TextField("Jugador 3");
	TextField txfJugador4 = new TextField("Jugador 4");
	Button btnAceptarNuevaPartida = new Button("Comenzar la partida");
}
