package testBasicFunctions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import homework.Direction;
import homework.Field;
import homework.Pawn;
import homework.Rock;




public class TestPawn {

	Pawn p;
	Rock r;
	static Field[][] table = new Field[3][3];
	
	@BeforeClass
	public static void createTable() {
		for(int i = 0; i<3; ++i)
			for(int j = 0; j<3; ++j)
				table[i][j] = new Field();
		//Szomsz�dok kezdetleges felv�tele - k�zi
		table[1][1].SetNeighbor(Direction.UP, table[0][1]);
		table[1][1].SetNeighbor(Direction.DOWN, table[2][1]);
		table[1][1].SetNeighbor(Direction.LEFT, table[1][0]);
		table[1][1].SetNeighbor(Direction.RIGHT, table[1][2]);
	}

	@Before
	public void createThings() {
		p = new Pawn();
		r = new Rock();
	}
	
	@Test
	public void testNewPawn() {
		assertEquals(null, p.GetField());
		assertEquals(Direction.UP, p.GetFacing());
	}
	
	@Test
	public void testPutPawnOnTableThenRotate() {
		Field f = new Field();
		p.SetField(f);
		p.SetFacing(Direction.DOWN);
		assertEquals(f, p.GetField());
		assertEquals(Direction.DOWN, p.GetFacing());
	}
	
	@Test
	public void testSteping() {
		p.SetField(table[1][1]); //K�z�pre helyezz�k.
		p.Step(Direction.UP);
		assertEquals(table[0][1].getBorder(), p.GetField().getBorder());
	}
	
	@Test
	public void testStepingBadly() {
		p.SetField(null);
		p.Step(Direction.UP);
		
		assertEquals(null, p.GetField());
	}
	
	@Test
	public void testSteppingOffTheTable() {
		p.SetField(table[0][1]);
		p.Step(Direction.UP);
		
		assertEquals(null, p.GetField()); //Nem vagyunk egyik mez�n se.
	}
	
	@Test
	public void testName() {
		assertEquals("Pawn", p.toString());
	}
	
	@Test
	public void testPush() {
		p.SetField(table[1][1]);
		table[1][1].AddThing(p);
		p.SetFacing(Direction.UP);
		r.SetField(table[0][1]);
		table[0][1].AddThing(r);
		p.Push();
		assertEquals(table[0][1], p.GetField());
		assertEquals(null, r.GetField());
	}
	
	@Test
	public void testPushOffTheTable() {
		p.Push();
		assertEquals(null, p.GetField());
	}
	
	@Test
	public void testPushNothingHappens(){
		p.SetField(table[1][1]);
		table[1][1].AddThing(p);
		Pawn p2 = new Pawn();
		p2.SetField(table[0][1]);
		table[0][1].AddThing(p2);
		p2.SetFacing(Direction.DOWN);
		p.Push();
		assertEquals(table[1][1], p.GetField());
	}
}
