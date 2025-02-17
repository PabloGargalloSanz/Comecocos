package comecocos;

import java.io.*;
import java.io.File;
import java.util.Scanner;

public class prueba_escritura_puntuaciones {
	public static void main(String[]args) {
		
		String nombreArchivo = "puntuaciones.txt";
		File archivo = new File(nombreArchivo);
		Scanner sc = new Scanner(System.in);
		
		if(!archivo.exists()) {
			try {
				archivo.createNewFile();
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))){
					
					for (int i = 1; i <= 5; i++) {
						bw.write(i + ".\n ");
					}
				}					
				
			} catch (IOException e) {
				System.out.println("No se puede crear el archivo");
			}
		}
			
		try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
			String linea;
			
			while ((linea = br.readLine()) != null) {
				System.out.print(linea);
			}
			System.out.println("");
			
		} catch (IOException e) {
			System.out.println("No se ha podido leer correctamente el txt");
		}
	
		// Escribir texto
		System.out.println("Introduce tu nombre");
		
		String contenido = sc.nextLine();
		int puntos = 0;
		String n = "k";
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))){
			
			bw.write( n + "" + contenido + "" + puntos);
			
			System.out.print("Se ha actualizado las puntuaciones");
			
		} catch (IOException e) {
			System.out.print(e);
		} 
	}
}
