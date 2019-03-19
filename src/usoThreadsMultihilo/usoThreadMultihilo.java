package usoThreadsMultihilo;

import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class usoThreadMultihilo {

	//-----CREACION DE LA VENTANA PRA NUESTRO PROGRAMA-----
	
	public static void main(String[] args) {
		
		JFrame marco = new MarcoRebote();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.setVisible(true);				
		
		marco.setLocationRelativeTo(null); 	 
		
		marco.setResizable(false);
				
	}
}


//------ PASO 1 CREAR CLASE QUE IMPLEMENTE INTERFAZ RUNNABLE------ 
class PelotaHilos implements Runnable  {
	
//-----CREAMOS CONSTRUCTOR----
	public PelotaHilos(Pelota unaPelota, Component unComponente) {
		
		pelota = unaPelota;
		
		componente = unComponente;
	}
	
//-------------- CONSTRUIR METODO RUN----------------
	public void run() {
		
		
//------------ PASO 2 ESCRIBIR CODIGO DE LA TAREA DENTRO DEL METODO RUN-----
		for (int i=1; i <= 3000; i++) {
			
			pelota.mueve_pelota(componente.getBounds());
			
			componente.paint(componente.getGraphics());
			
			try {
				//----MOVEMOS LENTO EL HILO PARA PODER VERLO----
				Thread.sleep(5);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	private Pelota pelota;
	private Component componente;
	
}

//-----------------CLASE PARA EL MOVIMIENTO DE LA PELOTA------------------
class Pelota {

	
	//----------MUEVE LA PELOTA E INVERTIR POSICION AL CHOCAR CON EL LIMITE 
	public void mueve_pelota(Rectangle2D limites) {
		
		x+= dx;
		
		y+= dy;
		
		if (x < limites.getMinX()) {
			
			x = limites.getMinX();
			
			dx = -dx;
		}
		if (x + TAMX >= limites.getMaxX()) {
			
			x = limites.getMaxX() - TAMX;
			
			dx = -dx;
		}
		if (y < limites.getMinY()) {
			
			y = limites.getMinY();
			
			dy = -dy;
		}
		if (y + TAMY >= limites.getMaxY()) {
			
			y = limites.getMaxY() - TAMY;
			
			dy = -dy;
		}
	}
	
	//--------FORMA DE LA PELOTA EN SU POSICION INICIAL---------
	public Ellipse2D getShape() {
		
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
	}
	private static final int TAMX = 30;
	private static final int TAMY = 30;
	private double x = 0;
	private double y = 0;
	private double dx = 1;
	private double dy = 1;
	
}

//---------------- PANEL QUE DIBUJA LAS PELOTAS------------------
class LaminaPelota extends JPanel{
	
	public void add(Pelota b) {
		
		pelotas.add(b);
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		for(Pelota b: pelotas) {
			g2.fill(b.getShape());
		}
	}
	private ArrayList<Pelota> pelotas = new ArrayList<Pelota>();
	
}

//-------MARCO IMPLEMENTANDO EL PANEL Y LOS BOTONES---------------
class MarcoRebote extends JFrame{
	
	public MarcoRebote() {
		
		setBounds(600,300,400,350);
		
		setTitle("EJEMPLO DE MULTIHILO");
		
		lamina = new LaminaPelota();
		
		add(lamina, BorderLayout.CENTER);

		JPanel laminaBotones = new JPanel();
		
		ponerBoton(laminaBotones, "INICIAR", new ActionListener() {
			
			public void actionPerformed(ActionEvent evento) {
				
				comienza_el_juego();
			}
		});
	
		ponerBoton(laminaBotones, "SALIR", new ActionListener() {
		
			public void actionPerformed(ActionEvent evento) {
			
				System.exit(0);
			}
		});
		ponerBoton(laminaBotones, "DETENER", new ActionListener() {
			
			public void actionPerformed(ActionEvent evento) {
				
				detener();
			}
		});
		
		add(laminaBotones, BorderLayout.SOUTH);
	}
	
	public void ponerBoton(Container c, String titulo, ActionListener oyente) {
		
		JButton boton = new JButton(titulo);
		
		c.add(boton);
		
		boton.addActionListener(oyente);
	}
	
	//--------------------AGREGAR LA PELOTA Y REBOTARLA 3000 VECES
	public void comienza_el_juego() {
		
		Pelota pelota = new Pelota();					
		
		lamina.add(pelota);								
		
		Runnable r = new PelotaHilos(pelota, lamina);	//----- PASO 3 CREAMOS INSTANCIA DE CLASE RUNNABLE
		
		t = new Thread(r);								//----- PASO 4 CREAMOS INSTANCIA DE CLASE THREAD PASANDO COMO PARAMETRO EL OBJETO ANTERIOR
		
		t.start();										//----- PASO 5 INICIAMOS EL TRHEAD CON METODO
	}
	
	public void detener() {
		
		t.stop(); //------  METODO PARA DETENER EL HILO.--
		
	}
	private Thread t;
	private LaminaPelota lamina;
}
