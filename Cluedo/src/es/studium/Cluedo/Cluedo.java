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
		
		new Controlador(vista, modelo, tablero, nuevapartida, ayuda, opciones, resolver, hipotesis, ranking);
		
	}

}
