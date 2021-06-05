package es.studium.Cluedo;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;

public class VistaRanking
{
	Frame ventanaRanking = new Frame("Ranking");
	Button btnAceptarRanking = new Button("Aceptar");
	TextArea txaRanking = new TextArea("5,40");
	
	public VistaRanking()
	{
		ventanaRanking.setLayout(new FlowLayout());
		ventanaRanking.setSize(500,250);
		ventanaRanking.setResizable(false);
		ventanaRanking.setLocationRelativeTo(null);
		ventanaRanking.add(txaRanking);
		ventanaRanking.add(btnAceptarRanking);
	}
	
}
