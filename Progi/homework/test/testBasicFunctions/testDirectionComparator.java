package testBasicFunctions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import homework.Direction;
import homework.DirectionComparator;



public class testDirectionComparator {

	Direction d1, d2;
	DirectionComparator d_cmp;
	
	@Before
	public void loadUpDirections() {
		d1 = Direction.UP;
		d2 = Direction.DOWN;
		d_cmp = new DirectionComparator();
	}
	
	@Test
	public void testOppositeDirection() {
		assertEquals(-1, d_cmp.compare(d1, d2));
	}
	
	@Test
	public void testOppositeDirectionWithEllentetMethod_UP_DOWN() {
		d1 = d1.Ellentet(d1);
		d2 = d2.Ellentet(d2);
		assertEquals(-1, d_cmp.compare(d1, d2));
		assertEquals(-1, d_cmp.compare(d2, d1));
	}
	
	@Test
	public void testOppositeDirectionWithEllentetMethod_RIGHT_LEFT() {
		d1 = Direction.LEFT;
		d2 = Direction.RIGHT;
		d1 = d1.Ellentet(d1);
		d2 = d2.Ellentet(d2);
		assertEquals(-1, d_cmp.compare(d1, d2));
		assertEquals(-1, d_cmp.compare(d2, d1));
	}
	
	@Test
	public void testSameDirection() {
		d1 = d2;
		assertEquals(1, d_cmp.compare(d1, d2));
	}
	
	@Test
	public void testNothingCommonDirection1() {
		d2 = Direction.LEFT;
		assertEquals(0, d_cmp.compare(d1, d2));
	}

}
