import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static final int PUERTO = 2004; // Declaración del puerto
	
	public static void main(String[] args) {
		// Declaración de todas las variables usadas en el programa
		
		Socket socketAlCliente = null; // El socket es el objeto que se conectara con el socket del cliente
		
		InputStreamReader entrada = null; // Canal por el cual se va a recibir la información del cliente
		PrintStream salida = null; // Canal por el que sacaramos datos y enviaremos al cliente
		
		InetSocketAddress direccion = new InetSocketAddress(PUERTO); // Objeto para especificar el puerto en el que va a escuchar el servidor
		
		/*
		 * peticion = contador de peticiones
		 * num1 = primer número introducido por el usuario
		 * num2 = segundo número introducido por el usuario
		 * resultado = resultado de la operacion entre num1 y num2
		 * 
		 * */
		int peticion, num1, num2, resultado=0;
		char operacion=' '; // Guarda la letra enlacada a cada operación
		
		System.out.println("==============================");
		System.out.println("    APLICACION DE SERVIDOR    ");
		System.out.println("==============================");

		
		try (ServerSocket serverSocket = new ServerSocket()){ // Se crea un nuevo socket de servidor que se cerrara automaticamente al terminar el try
			serverSocket.bind(direccion); // Se especifica a que puerto va a escuchar el socket del servidor
			
			peticion = 0;
			
			while(true) { // Bucle infinito para que se vuelva a ejecutar al terminar con un cliente
				System.out.println("SERVIDOR: Esperando peticion por el puerto "+PUERTO);
				socketAlCliente = serverSocket.accept(); // Se queda esperando que un cliente haga petición
				System.out.println("SERVIDOR: Petición número "+ ++peticion +" recibida");
				
				entrada = new InputStreamReader(socketAlCliente.getInputStream()); // Obtiene el mensaje enviado por el cliente
				BufferedReader bf = new BufferedReader(entrada); // Se pasa a un buffer para poder tener acceso al metodo readLine()
				
				String stringRecibido = bf.readLine(); // Se guarda en un string el contenido del mensaje recibido
				
				System.out.println("SERVIDOR: Me ha llegado del cliente "+stringRecibido);
				
				String[] operadores = stringRecibido.split(":");
				operacion = operadores[0].charAt(0);
				num1 = Integer.parseInt(operadores[1]);
				num2 = Integer.parseInt(operadores[2]);

				
				resultado = 0;
				
				switch(operacion) {
					case 'a': // Suma
						resultado = num1 + num2;
						break;
					
					case 'b': // Resta
						resultado = num1 - num2;
						break;
						
					case 'c': // Multiplicación
						resultado = num1 * num2;
						break;
						
					case 'd': // División
						resultado = num1 / num2;
						break;
					
				}
				
				System.out.println("SERVIDOR: El calculo de los números es: "+resultado);
				
				salida = new PrintStream(socketAlCliente.getOutputStream()); // Se envia el resultado al cliente con esta linea y la linea de abajo
				salida.println(resultado);
				
				socketAlCliente.close(); // Se cierra la conexión con el cliente antes de terminar el programa para evitar errores
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
