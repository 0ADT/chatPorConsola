package codigo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private Socket socket;
	private DataInputStream bufferDeEntrada = null;
	private DataOutputStream bufferDeSalida = null;
	Scanner sc = new Scanner(System.in);

	public Cliente() {

	}

	public void conectarse(String ip, int puerto) {
		try {
			socket = new Socket(ip, puerto);
			System.out.println("Conectando a: " + socket.getInetAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void escribirDatos() {
		String entrada = "";
		while (true) {
			System.out.print("[Usted] => ");
			entrada = sc.nextLine();
			if (entrada.length() > 0) {
				try {
					bufferDeSalida.writeUTF(entrada);
					bufferDeSalida.flush();
				} catch (IOException e) {
					System.out.println("IOException on enviar");
				}
			}
		}
	}

	public void recibirDatos() {
		String st = "";
		try {
			do {
				st = (String) bufferDeEntrada.readUTF();
				try {
					bufferDeSalida.writeUTF(st);
					bufferDeSalida.flush();
				} catch (IOException e) {
					System.out.println("IOException on enviar");
				}
				System.out.print("\n[Usted] => ");
			} while (!st.equals("Ñ"));
		} catch (IOException e) {
		}
	}

	public void cerrarConexion() {
		try {
			bufferDeEntrada.close();
			bufferDeSalida.close();
			socket.close();
			System.out.println("Conexión terminada");
		} catch (IOException e) {
			System.out.println("IOException on cerrarConexion()");
		} finally {
			System.exit(0);
		}
	}

	public static void main(String[] argumentos) {
		Cliente cliente = new Cliente();

		cliente.escribirDatos();
	}

}
