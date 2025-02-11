package comecocos;

import java.util.Random;

public class fantasmaAleatorio {
	int x, y;
	char[][] mapa;
	Random ran = new Random();
	
	public fantasmaAleatorio(int startX, int startY, char[][] mapa) {
		this.x = startX;
		this.y = startY;
		this.mapa = mapa;
		this.ran = ran;
	}
	
	public void movimientoFantasma() {
		int[][] direccion = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
		int nuevaX, nuevaY;
		
		do {
			int posicion = ran.nextInt(4);
			nuevaX = x + direccion[posicion][0];
			nuevaY = y + direccion[0][posicion];
			
		} while (mapa[nuevaX][nuevaY] == '#' && mapa[nuevaX][nuevaY] == '|' );
		
		x = nuevaX;
		y = nuevaY;
	}
	
	public int getx() { return x;}
	public int gety() { return y;}
	

}
