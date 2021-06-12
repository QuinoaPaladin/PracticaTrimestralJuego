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
	Label lblTurnosLetras = new Label("Has tardado:");
	Label lblTurnosNumero = new Label("5");
	Button btnGanaste = new Button("Aceptar");
	
	//HAS PERDIDO
	Dialog dlgPerdiste = new Dialog(frmResolver, "Has perdido...", true);
	Label lblPerdiste = new Label("No has acertado...ahora solo puedes moverte, para que no te aburras");
	
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
		dlgGanaste.add(lblTurnosLetras);
		dlgGanaste.add(lblTurnosNumero);
		dlgGanaste.add(btnGanaste);
		dlgResolver.setVisible(false);
		
		dlgPerdiste.setLayout(new FlowLayout());
		dlgPerdiste.setSize(400,80);
		dlgPerdiste.setResizable(false);
		dlgPerdiste.setLocationRelativeTo(null);
		dlgPerdiste.add(lblPerdiste);
		dlgPerdiste.setVisible(false);
		
	}
}
