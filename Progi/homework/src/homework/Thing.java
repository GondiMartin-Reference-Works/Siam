package homework;

import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * A t�bl�n tal�lhat� t�rgyak (b�bu, hegy) oszt�lya.
 * */
public class Thing extends JButton implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	//----------ADATTAGOK----------
	/**Referencia a mez�re, amin a t�rgy (Thing) �ll.*/
	protected Field field;			
	
	
	//---------MET�DUSOK----------
	/**
	 * Thing konstruktora.
	 * Egy �jonnan l�trehozott t�rgy alap�rtelmezetten a t�bla sz�l�n �ll.
	 * */
	public Thing() {
		field = null;
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	
	/**
	 * Visszaadja, hogy a t�rgy �ppen melyik mez�n �ll.
	 * Ha a visszat�r�tett �rt�k nulla, akkor a t�rgy nem �ll a t�bl�n.
	 * @return Mez�
	 * */
	public Field GetField() { return field; }
	
	
	/**
	 * Be�ll�that�, hogy a t�rgy �ppen melyik mez�n �lljon.
	 * Ha a be�ll�tand� mez� �r�ke null, akkor a t�rgy a t�bla mellett fog �llni, teh�t nem �ll a t�bl�n.
	 * @param f - Adott mez�, ahov� �ll�tjuk a t�rgyat.
	 * */
	public void SetField(Field f) { 
		field = f; 
	}
}
