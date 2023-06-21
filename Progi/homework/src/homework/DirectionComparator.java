package homework;

import java.util.Comparator;


/**
 * Az ir�nyok �sszehasonl�t�s��rt felel�s oszt�ly.
 * */
public class DirectionComparator implements Comparator<Direction> {
	
	/**
	 * �sszehasonl�t k�t ir�nyt.
	 * Ha az ir�nyok megegyeznek, akkor 1-et ad vissza.
	 * Ha az ir�nyok ellent�tesek, akkor (-1)-et ad vissza.
	 * Minden m�s esetben pedig null�t.
	 * @param o1 and o2 - Az �sszehasonl�tand� ir�nyok.
	 * @return Az �sszeshasonl�t�s eredm�nye.
	 * */
	public int compare(Direction o1, Direction o2) {
			
		//Ha a k�t ir�ny ugyanaz
		if(o1.equals(o2))
			return 1;
		
		//Ha a k�t ir�ny ellent�tes �s az ellent�t felt�tel�nek ford�tottja
		if((o1.equals(Direction.UP) && o2.equals(Direction.DOWN)) || (o1.equals(Direction.RIGHT) && o2.equals(Direction.LEFT)) ||
			(o1.equals(Direction.DOWN) && o2.equals(Direction.UP)) || (o1.equals(Direction.LEFT) && o2.equals(Direction.RIGHT)))
			return -1;
		
		//Minden m�s esetben a k�t ir�ny se nem ugyanaz, se nem ellent�tes
		return 0;
	}
	
}
