package homework;


import java.io.Serializable;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;



/**
 * Mez� oszt�ly.
 * Lesz�rmazik a JButton oszt�lyb�l, hogy �gy viselkedhessen, mint egy gomb. (F�k�nt k�p kirajzol�s miatt sz�ks�ges ez.)
 * T�rolja az �ppen egyetlen rajta �ll� t�rgyat (Thing).
 * T�rolja tov�bb� a szomsz�dos mez�it (Field).
 * */
public class Field extends JButton implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	//----------ADATTAGOK----------
	/**Referencia a mez�n tal�lhat� t�rgyra*/
	private Thing thing;												
	/**Referencia a mez� szomsz�djaira adott ir�nynak megfelel�en.*/
	private HashMap<Direction, Field> neighbors;						
	/**Referencia a mez� megjelen�t�se sor�n l�that� k�pre/ikonra.*/
	private static final ImageIcon icon = new ImageIcon("field.jpg");	
	
	
	//---------MET�DUSOK----------
	/**
	 * Field konstruktora.
	 * */
	public Field() {
		this.setIcon(icon);
		thing = null;
		neighbors = new HashMap<Direction, Field>();
	}
	
	
	/**
	 * Visszaadja az adott ir�nyban tal�lhat� mez� szomsz�dj�t.
	 * Ha a nem l�tezik szomsz�dja az adott ir�nyban null �rt�ket t�r�t vissza.
	 * @param d - Adott ir�ny.
	 * @return Szomsz�d adott ir�nyban. 
	 * */
	public Field GetNeighbor(Direction d) {
		Field f = neighbors.get(d);
		if(f == null)
			return null;
		return f;
	}
	
	
	/**
	 * Felt�lti a mez� szomsz�djainak t�bl�j�t (list�j�t) egy adott szomsz�ddal �S a szomsz�d t�bl�j�t kit�lti �NMAG�VAL.
	 * Ha a nem l�tezik szomsz�dja az adott ir�nyban, csak akkor veszi fel.
	 * Ha a felvenni k�v�nt mez� null, akkor nem t�rt�nik t�rol�s.
	 * Mindezek mellett a sikeresen felvett szomsz�dnak is elt�roljuk EZT a mez�t.
	 * @param d - Adott ir�ny.
	 * @param f - Adott mez�.
	 * */
	public void SetNeighbor(Direction d, Field f) {
		Field foglalt_e = neighbors.get(d);
		if(foglalt_e == null && f != null) {
			neighbors.put(d, f);
			f.SetNeighbor(d.Ellentet(d), this);
		}
			
	}
	
	
	/**
	 * Visszaadja a mez�n tal�lhat� t�rgy (Thing) referenci�j�t.
	 * @return Mez�n tal�lhat� t�rgy.
	 * */
	public Thing GetThing() { return thing; }
	
	
	/**
	 * Elt�vol�tja a mez�n tal�lhat� egyetlen t�rgyat.
	 * */
	public void RemoveThing() {
		thing = null;
	}
	
	
	/**
	 * Felveszi a mez�re az adott t�rgyat.
	 * @param t - Az adott t�rgy, amit felvesz�nk a mez�re.
	 * */
	public void AddThing(Thing t) {
		thing = t;
	}
}
