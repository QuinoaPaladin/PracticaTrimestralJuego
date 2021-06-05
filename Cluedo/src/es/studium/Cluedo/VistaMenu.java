package es.studium.Cluedo;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;

import javax.swing.BoxLayout;

public class VistaMenu extends Frame
{
	
	private static final long serialVersionUID = 1L;
	
	
	Frame ventanaMenu = new Frame("Menú Principal");
	Label lblCluedo = new Label("CLUEDO");
	Button btnNuevaPartida = new Button("Nueva Partida");
	Button btnRanking = new Button("Ranking");
	Button btnAyuda = new Button("Ayuda");
	Button btnSalir = new Button("Salir");
	
	
	public VistaMenu()
	{
		ventanaMenu.setLayout(new BoxLayout(ventanaMenu, BoxLayout.Y_AXIS));            
		ventanaMenu.setTitle("Menú Principal");
		lblCluedo.setAlignment(Label.CENTER);
		ventanaMenu.add(lblCluedo);
		ventanaMenu.add(btnNuevaPartida);
		ventanaMenu.add(btnRanking);
		ventanaMenu.add(btnAyuda);
		ventanaMenu.add(btnSalir);
		ventanaMenu.setResizable(false);
		ventanaMenu.setSize(600, 600); 
		ventanaMenu.setVisible(true);
		       
	}
	
	
	
}
