package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;

public class VistaOpciones
{
	
	//Opciones al entrar en casilla de habitación
	Frame ventanaCluedo = new Frame("Cluedo");
	Dialog dlgOpciones = new Dialog(ventanaCluedo, "Opciones", true);
	Label lblpregunta = new Label ("¿Qué quieres investigar?");
	Button btnArma = new Button("Arma");
	Button btnSospechoso = new Button("Sospechoso");
	Button btnHabitacion = new Button("Habitación");
	
	//DIÁLOGO IMPOSIBLE AVANZAR
	Dialog dlgAvanzar = new Dialog (ventanaCluedo, "Imposible", true);
	Label lblImposible = new Label ("Imposible avanzar por aquí");
	
	//DIÁLOGO OBTENCIÓN CARTA
	Dialog dlgCartaNueva = new Dialog (ventanaCluedo, "Nueva Carta", true);
	Label lblcartaNueva = new Label ("Nueva Carta");
	
	//DIÁLOGO DEBES ESTAR EN HABITACIÓN
	Dialog dlgEstarHabitacion = new Dialog (ventanaCluedo, "Debes moverte", true);
	Label lblEstarHabitacion = new Label ("Debes estar en una habitación");		
			
	public VistaOpciones()
	{		
		dlgOpciones.setLayout(new FlowLayout());
		dlgOpciones.setSize(400,80);
		dlgOpciones.setResizable(false);
		dlgOpciones.setLocationRelativeTo(null);
		dlgOpciones.add(lblpregunta);
		dlgOpciones.add(btnArma);
		dlgOpciones.add(btnSospechoso);
		dlgOpciones.add(btnHabitacion);
		dlgOpciones.setVisible(false);		
		
		dlgAvanzar.setLayout(new FlowLayout());
		dlgAvanzar.setSize(200,80);
		dlgAvanzar.setResizable(false);
		dlgAvanzar.setLocationRelativeTo(null);
		dlgAvanzar.add(lblImposible);
		dlgAvanzar.setVisible(false);	
		
		dlgCartaNueva.setLayout(new FlowLayout());
		dlgCartaNueva.setSize(200,80);
		dlgCartaNueva.setResizable(false);
		dlgCartaNueva.setLocationRelativeTo(null);
		dlgCartaNueva.add(lblcartaNueva);
		dlgCartaNueva.setVisible(false);	
		

		dlgEstarHabitacion.setLayout(new FlowLayout());
		dlgEstarHabitacion.setSize(400,80);
		dlgEstarHabitacion.setResizable(false);
		dlgEstarHabitacion.setLocationRelativeTo(null);
		dlgEstarHabitacion.add(lblEstarHabitacion);
		dlgEstarHabitacion.setVisible(false);
	}
}
