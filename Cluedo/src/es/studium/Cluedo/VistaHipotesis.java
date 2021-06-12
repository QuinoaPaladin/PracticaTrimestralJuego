package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

public class VistaHipotesis
{

	Frame frmHipotesis = new Frame("Hipótesis");
	Dialog dlgHipotesis = new Dialog(frmHipotesis, "Hipótesis", true);
	Dialog dlgHipotesisRespuestaEquivocada = new Dialog (frmHipotesis, "Hipótesis", true);
	Dialog dlgHipotesisRespuestaCorrecta = new Dialog (frmHipotesis, "Hipótesis", true);
	Label lblhipotesis = new Label("Realizar hipótesis");
	Label lblarmahipotesis = new Label ("Arma: ");
	Label lblsushipotesis = new Label ("Sospechoso: ");
	Label lblhabitacionhipotesis = new Label ("Habitación: ");
	Label lblRespuestaEquivocada = new Label ("Hipótesis incorrecta");
	Label lblRespuestaAcertada = new Label ("Hipótesis correcta");
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
		
		dlgHipotesisRespuestaEquivocada.setLayout(new FlowLayout());
		dlgHipotesisRespuestaEquivocada.setSize(400,80);
		dlgHipotesisRespuestaEquivocada.setResizable(false);
		dlgHipotesisRespuestaEquivocada.setLocationRelativeTo(null);
		dlgHipotesisRespuestaEquivocada.add(lblRespuestaEquivocada);
		dlgHipotesisRespuestaEquivocada.setVisible(false);
		
		dlgHipotesisRespuestaCorrecta.setLayout(new FlowLayout());
		dlgHipotesisRespuestaCorrecta.setSize(400,80);
		dlgHipotesisRespuestaCorrecta.setResizable(false);
		dlgHipotesisRespuestaCorrecta.setLocationRelativeTo(null);
		dlgHipotesisRespuestaCorrecta.add(lblRespuestaAcertada);
		dlgHipotesisRespuestaCorrecta.setVisible(false);	
	}
}
