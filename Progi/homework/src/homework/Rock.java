package homework;

import javax.swing.ImageIcon;



/**
 * Szikla (hegy) oszt�ly.
 * */
public class Rock extends Thing{
	private static final long serialVersionUID = 1L;

	
	//----------ADATTAGOK----------
	/**Referencia a hegy megjelen�t�se sor�n l�that� k�pre/ikonra.*/
	private static final ImageIcon icon = new ImageIcon("rock.png");
	
	

	//---------MET�DUSOK----------
	/**
	 * Szikla konstruktora.
	 * */
	public Rock() {
		super();
		this.setIcon(icon);
	}
	
	
	/**
	 * Fel�l�rjuk a toString() f�ggv�nyt.
	 * Ezzel megsp�roljuk, hogy k�l�n attrib�tumot vegy�nk fel a n�v t�rol�s�ra.
	 * */
	public String toString() {
		return "Rock";
	}
}
