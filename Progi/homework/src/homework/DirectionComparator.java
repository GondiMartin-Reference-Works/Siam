package homework;

import java.util.Comparator;


/**
 * Az irányok összehasonlításáért felelõs osztály.
 * */
public class DirectionComparator implements Comparator<Direction> {
	
	/**
	 * Összehasonlít két irányt.
	 * Ha az irányok megegyeznek, akkor 1-et ad vissza.
	 * Ha az irányok ellentétesek, akkor (-1)-et ad vissza.
	 * Minden más esetben pedig nullát.
	 * @param o1 and o2 - Az összehasonlítandó irányok.
	 * @return Az összeshasonlítás eredménye.
	 * */
	public int compare(Direction o1, Direction o2) {
			
		//Ha a két irány ugyanaz
		if(o1.equals(o2))
			return 1;
		
		//Ha a két irány ellentétes és az ellentét feltételének fordítottja
		if((o1.equals(Direction.UP) && o2.equals(Direction.DOWN)) || (o1.equals(Direction.RIGHT) && o2.equals(Direction.LEFT)) ||
			(o1.equals(Direction.DOWN) && o2.equals(Direction.UP)) || (o1.equals(Direction.LEFT) && o2.equals(Direction.RIGHT)))
			return -1;
		
		//Minden más esetben a két irány se nem ugyanaz, se nem ellentétes
		return 0;
	}
	
}
