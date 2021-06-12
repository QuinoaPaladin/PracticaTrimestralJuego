package es.studium.Cluedo;

public class Cluedo
{
	

	public static void main(String[] args)
	{
		Modelo modelo = new Modelo();
		VistaMenu vista = new VistaMenu();
		VistaTablero tablero = new VistaTablero();
		VistaNuevaPartida nuevapartida = new VistaNuevaPartida();
		VistaAyuda ayuda = new VistaAyuda();
		VistaOpciones opciones = new VistaOpciones();
		VistaResolver resolver = new VistaResolver();
		VistaHipotesis hipotesis = new VistaHipotesis();
		VistaRanking ranking = new VistaRanking();
		VistaCartasInicialesJugador1 cartasj1 = new VistaCartasInicialesJugador1();
		VistaCartasInicialesJugador2 cartasj2 = new VistaCartasInicialesJugador2();
		VistaCartasInicialesJugador3 cartasj3 = new VistaCartasInicialesJugador3();
		VistaCartasInicialesJugador4 cartasj4 = new VistaCartasInicialesJugador4();
		
		new Controlador(vista, modelo, tablero, nuevapartida, ayuda, opciones, resolver, hipotesis, ranking, cartasj1, cartasj2, cartasj3, cartasj4);
		
	}

}
