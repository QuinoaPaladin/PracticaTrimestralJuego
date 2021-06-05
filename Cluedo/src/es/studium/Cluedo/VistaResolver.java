package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

public class VistaResolver
{
	
	//INTENTAR RESOLVER
	Frame frmResolver = new Frame("Resolver");
	Dialog dlgResolver = new Dialog(frmResolver, "Resolver caso", true);
	Label lblResolver = new Label ("¿Cuál es tu acusación?");
	Label lblarma = new Label ("Arma: ");
	Label lblsus = new Label ("Sospechoso: ");
	Label lblhabitacion = new Label ("Habitación: ");
	TextField txfArma = new TextField(15);
	TextField txfSus = new TextField(15);
	TextField txfHabitacion = new TextField(15);
	Button btnResolver = new Button("Aceptar");
	
	//HAS GANADO
	Dialog dlgGanaste = new Dialog(frmResolver, "¡Resolviste el caso!", true);
	Label lblGanaste = new Label("¡Has resuelto el caso!");
	Button btnGanaste = new Button("Aceptar");
	
	public VistaResolver()
	{
		dlgResolver.setLayout(new FlowLayout());
		dlgResolver.setSize(185,275);
		dlgResolver.setResizable(false);
		dlgResolver.setLocationRelativeTo(null);
		dlgResolver.add(lblResolver);
		dlgResolver.add(lblarma);
		dlgResolver.add(txfArma);
		dlgResolver.add(lblsus);
		dlgResolver.add(txfSus);
		dlgResolver.add(lblhabitacion);
		dlgResolver.add(txfHabitacion);
		dlgResolver.add(btnResolver);
		dlgResolver.setVisible(false);	
		
		dlgGanaste.setLayout(new FlowLayout());
		dlgGanaste.setSize(185,140);
		dlgGanaste.setResizable(false);
		dlgGanaste.setLocationRelativeTo(null);
		dlgGanaste.add(lblGanaste);
		dlgGanaste.add(btnGanaste);
		dlgResolver.setVisible(false);
		
	}
}
