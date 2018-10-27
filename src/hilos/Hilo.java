package hilos;

import java.io.DataInputStream;
import java.io.IOException;

import codigoEjemplo.Servidor;

public class Hilo extends Thread{
	private boolean suspender = false;
	private int port;
	private Servidor servidor;
	
	public Hilo(int port, Servidor server) {
		this.port = port;
		this.servidor = server;
	}
	
	public void run() {
		while (suspender) {
			try {
				servidor.levantarConexion(port);
				servidor.flujos();
				servidor.recibirDatos();
			} finally {
				servidor.cerrarConexion();
			}
		}
		
		suspender = false;
	}
	
	public void suspederHilo() {
		suspender = true;
	}
	
}
