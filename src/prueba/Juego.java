package prueba;

import java.util.Scanner;

public class Juego {
    public static void main(String[] args) {
        Mapa mapa = new Mapa(15, 15);
        Personaje personaje = new Personaje(mapa);
        Fantasmas fantasmas = new Fantasmas(mapa);
        Funcionalidades funcionalidades = new Funcionalidades(mapa);
        Generaciones generaciones = new Generaciones(mapa);
        Comprobaciones comprobaciones = new Comprobaciones(mapa);
       
        mapa.setPersonaje(personaje);
        mapa.setFantasmas(fantasmas);
        
 
        boolean seguirJugando = true;
        Scanner sc = new Scanner(System.in);
 
        while (seguirJugando) {
            mapa.imprimirMapa();
            
            char direccion = sc.next().charAt(0);
 
            personaje.movimientoPersonaje(direccion);
            fantasmas.movimientoFantasma();
 
            if (comprobaciones.igualdadPosicion()) {
                seguirJugando = false;
                System.out.println("¡Has sido atrapado por un fantasma! GAME OVER");
                System.out.println("Puntuación: " /* + funcionalidades.getPuntuacion() */ );
            }
 
            if (!mapa.hayPuntos()) {
                seguirJugando = false;
                System.out.println("Has ganado!! Puntuación: " /*+  funcionalidades.getPuntuacion()*/ );
            }
        }
        sc.close();
    }
}
