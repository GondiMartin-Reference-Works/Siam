package homework;


/**
 * Felsorol�s a t�bl�n val� mozgat�s ir�nyaira.
 * */
public enum Direction{
	
	/**Felfel� ir�ny*/
	UP,
	/**Lefel� ir�ny*/
	DOWN,
	/**Jobb oldali ir�ny*/
	RIGHT,
	/**Bal oldali ir�ny*/
	LEFT;
	
	
	/**
	 * Visszaadja az adott ir�ny ellent�tj�t.
	 * @param d -  Adott ir�ny, keress�k az ellent�tj�t.
	 * @return Az ellent�tes ir�ny.
	 * */
	public Direction Ellentet(Direction d) {
		switch(d) {
		case UP: return DOWN; 
		case DOWN: return UP;
		case RIGHT: return LEFT;
		default: return RIGHT;
		}
	}
}
