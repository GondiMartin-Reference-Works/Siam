package homework;


/**
 * Felsorolás a táblán való mozgatás irányaira.
 * */
public enum Direction{
	
	/**Felfelé irány*/
	UP,
	/**Lefelé irány*/
	DOWN,
	/**Jobb oldali irány*/
	RIGHT,
	/**Bal oldali irány*/
	LEFT;
	
	
	/**
	 * Visszaadja az adott irány ellentétjét.
	 * @param d -  Adott irány, keressük az ellentétjét.
	 * @return Az ellentétes irány.
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
