package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

public class VistaHipotesis
{

	Frame frmHipotesis = new Frame("Hip�tesis");
	Dialog dlgHipotesis = new Dialog(frmHipotesis, "Hip�tesis", true);
	Label lblhipotesis = new Label("Realizar hip�tesis");
	Label lblarmahipotesis = new Label ("Arma: ");
	Label lblsushipotesis = new Label ("Sospechoso: ");
	Label lblhabitacionhipotesis = new Label ("Habitaci�n: ");
	TextField txfArmaHipotesis = new TextField(15);
	TextField txfSusHipotesis = new TextField(15);
	TextField txfHabitacionHipotesis = new TextField(15);
	Button btnResolverHipotesis = new Button("Aceptar");
	
	public VistaHipotesis()
	{
		dlgHipotesis.setLayout(new FlowLayout());
		dlgHipotesis.setSize(185,275);
		dlgHipotesis.setResizable(false);
		dlgHipotesis.setLocationRelativeTo(null);
		dlgHipotesis.add(lblhipotesis);
		dlgHipotesis.add(lblarmahipotesis);
		dlgHipotesis.add(txfArmaHipotesis);
		dlgHipotesis.add(lblsushipotesis);
		dlgHipotesis.add(txfSusHipotesis);
		dlgHipotesis.add(lblhabitacionhipotesis);
		dlgHipotesis.add(txfHabitacionHipotesis);
		dlgHipotesis.add(btnResolverHipotesis);
		dlgHipotesis.setVisible(false);	
	}
}
