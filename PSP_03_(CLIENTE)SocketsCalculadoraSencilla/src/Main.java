import java.awt.im.InputContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	public static final int PUERTO = 2004;
	public static final String IP_SERVER = "localhost";
	
	public static void main(String[] args) {
		// Declaración de todas las variables usadas en el programa
		Scanner sc = new Scanner(System.in);
		
		int num1, num2 = 0;
		String op="";
		String envioFinal="";
		
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO); // Se define la direccion del servidor con su ip y puerto
		
		int operacion = 0;
		char operacionLetra = ' ';
		
		System.out.println("=============================");
		System.out.println("    APLICACION DE CLIENTE    ");
		System.out.println("=============================");
		
		
		try (Socket socketAlServidor = new Socket()){
			
			System.out.println("1. Sumar");
			System.out.println("2. Restar");
			System.out.println("3. Multiplicar");
			System.out.println("4. Dividir");
			System.out.println("5. Salir");
			operacion = Integer.parseInt(sc.nextLine());
			
			switch(operacion) {
				case 1:
					operacionLetra = 'a';
					op="suma";
					break;
					
				case 2:
					operacionLetra = 'b';
					op="resta";
					break;
					
				case 3:
					operacionLetra = 'c';
					op="división";
					break;
					
				case 4:
					operacionLetra = 'd';
					op="multiplicación";
					break;

				case 5:
					System.out.println("CLIENTE: Fin del programa");
					return;
			}
			
			System.out.println("Introduca el numero 1: ");
			num1=Integer.parseInt(sc.nextLine());
			System.out.println("Introduca el numero 2: ");
			num2=Integer.parseInt(sc.nextLine());
			
			envioFinal = operacionLetra+":"+num1+":"+num2;
			
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			socketAlServidor.connect(direccionServidor); // Se conecta al servidor
			System.out.println("CLIENTE: Conexion establecida... a "+IP_SERVER+" por el puerto "+PUERTO);
			
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream()); // Se envia con esta linea y la de abajo la información al servidor
			salida.println(envioFinal);
			
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream()); // Se recibe la operación resulta del servidor
			BufferedReader bf = new BufferedReader(entrada);
			
			System.out.println("CLIENTE: Esperando al resultado del servidor...");
			String resultado = bf.readLine(); // Se le el resultado de la operación
			
			System.out.println("CLIENTE: El resultado de la "+op+"es: "+resultado);
		} catch(UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección"+IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> "+e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
	}

}
