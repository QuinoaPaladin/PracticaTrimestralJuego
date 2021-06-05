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
	
	//Resolver el caso
	
	
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
	}
}
