import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static final int PUERTO = 2004;
	
	public static void main(String[] args) {
		System.out.println("==============================");
		System.out.println("    APLICACION DE SERVIDOR    ");
		System.out.println("==============================");

		InputStreamReader entrada = null;
		PrintStream salida = null;
		
		Socket socketAlCliente = null;
		
		InetSocketAddress direccion = new InetSocketAddress(PUERTO);
		
		try (ServerSocket serverSocket = new ServerSocket()){
			serverSocket.bind(direccion);
			
			int peticion = 0;
			
			while(true) {
				System.out.println("SERVIDOR: Esperando peticion por el puerto "+PUERTO);
				socketAlCliente = serverSocket.accept();
				System.out.println("SERVIDOR: Petición número "+ ++peticion +" recibida");
				
				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);
				
				String stringRecibido = bf.readLine();
				
				System.out.println("SERVIDOR: Me ha llegado del cliente "+stringRecibido);
				
				String[] operadores = stringRecibido.split("-");
				char cOperacion = operadores[0].charAt(0);
				int cNum1 = Integer.parseInt(operadores[1]);
				int cNum2 = Integer.parseInt(operadores[2]);

				
				int resultado = 0;
				
				switch(cOperacion) {
					case 'a': // Suma
						resultado = cNum1 + cNum2;
						break;
					
					case 'b': // Resta
						resultado = cNum1 - cNum2;
						break;
						
					case 'c': // Multiplicación
						resultado = cNum1 * cNum2;
						break;
						
					case 'd': // División
						resultado = cNum1 / cNum2;
						break;
					
				}
				
				System.out.println("SERVIDOR: El calculo de los números es: "+resultado);
				
				salida = new PrintStream(socketAlCliente.getOutputStream());
				salida.println(resultado);
				
				socketAlCliente.close();
			}
		} catch(IOException e) {
			System.err.println("SERVIDOR: Error de I/O");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> "+e);
			e.printStackTrace();
		}
	}

}
