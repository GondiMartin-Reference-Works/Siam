package homework;

import javax.swing.ImageIcon;



/**
 * B�bu oszt�ly.
 * */
public class Pawn extends Thing{
	private static final long serialVersionUID = 1L;
	
	
	//----------ADATTAGOK----------
	/**Megadja, hogy adott b�bu �ppen melyik ir�nyba n�z.*/
	private Direction facing;	
	
	
	//---------MET�DUSOK----------
	
	
	/**
	 * B�bu param�ter N�LK�LI konstruktora.
	 * */
	public Pawn() { 
		super();
		facing = Direction.UP;
	}
	
	
	/**
	 * B�bu param�teres konstruktora.
	 * @param i - Az ikon, amivel az adott j�t�kos b�buit megk�l�nb�ztetj�k.
	 * */
	public Pawn(ImageIcon i) { 
		super();
		this.setIcon(i);
		SetFacing(Direction.UP);
	}
	
 
	/**
	 * Visszaadja, hogy a b�bu (Pawn) �ppen melyik ir�nyba n�z.
	 * @return  Az ir�ny.
	 * */
	public Direction GetFacing() { return facing; }
	
	
	/**
	 * Be�ll�tja, hogy a b�bu melyik ir�nyba n�zzen.
	 * @param d - Adott ir�ny.
	 * */
	public void SetFacing(Direction d) { 
		facing = d;
		
		//Ha a b�bu rendelkezik k�ppel
		if(this.getIcon() != null) {
			
			//Ha a j�t�kos a orrszarv�kkal van
			if(this.getIcon().toString().equals("R")) {
				switch(d) {
				case DOWN: this.setIcon(new ImageIcon("rhino_DOWN.jpg", "R"));
					break;
				case LEFT: this.setIcon(new ImageIcon("rhino_LEFT.jpg", "R"));
					break;
				case RIGHT: this.setIcon(new ImageIcon("rhino_RIGHT.jpg", "R"));
					break;
				case UP: this.setIcon(new ImageIcon("rhino_UP.jpg", "R"));
					break;
				default: this.setIcon(new ImageIcon("rhino_UP.jpg", "R"));
					break;
				
				}
				
			}//M�s k�l�nben a j�t�kos az elef�ntokkal van
			else {
				switch(d) {
				case DOWN: this.setIcon(new ImageIcon("elephant_DOWN.jpg", "E"));
					break;
				case LEFT: this.setIcon(new ImageIcon("elephant_LEFT.jpg", "E"));
					break;
				case RIGHT: this.setIcon(new ImageIcon("elephant_RIGHT.jpg", "E"));
					break;
				case UP: this.setIcon(new ImageIcon("elephant_UP.jpg", "E"));
					break;
				default: this.setIcon(new ImageIcon("elephant_UP.jpg", "E"));
					break;
				
				}
			}
		}
	}
	
	
	/**
	 * Adott ir�nyba l�pteti eggyel - a legk�zelebbi szomsz�dos mez�re - a b�but.
	 * Ha nincs az adott ir�nyba legk�zelebbi szomsz�dos mez�, akkor a j�t�kost lel�pteti a t�bl�r�l.
	 * @param d - Adott ir�ny.
	 * @return Eld�nti, hogy a j�t�kos a l�p�ssel mit okozott. -1: lel�pett a t�bl�r�l; 0: sikertelen l�p�s; 1: sikeres l�p�s
	 * */
	public int Step(Direction d) {
		int lelepett = 0;
		Field itt_vagyok = this.GetField();
		if(itt_vagyok != null) {
			Field lepek_ide = itt_vagyok.GetNeighbor(d);
			if(lepek_ide != null) {
				if(lepek_ide.GetThing() == null) {
					lepek_ide.AddThing(this);
					itt_vagyok.RemoveThing();
					this.SetField(lepek_ide);
					lelepett = 1;
				}
			}else {
				lelepett = -1;
				this.SetField(lepek_ide);
			}
		}
		return lelepett;
	}
	
	
	/**
	 * Fel�l�rjuk a toString() f�ggv�nyt.
	 * Ezzel megsp�roljuk, hogy k�l�n attrib�tumot vegy�nk fel a n�v t�rol�s�ra.
	 * */
	public String toString() {
		return "Pawn";
	}
	
	
	/**
	 * Ki�rt�keli a l�k�s eredm�ny�t.
	 * @return - A l�k�s sz�mbeli �rt�ke.
	 * */
	private int calculatePush() {
		
		//Ki�rt�kelj�k a l�k�s eredm�ny�t
		int lokes_erteke = 0;
		Field iterator = this.GetField();
		while(iterator != null && iterator.GetThing() != null) {			//Addig l�punk, am�g van szomsz�dos mez� adott ir�nyba �S azon van vmi
			
			//Aktu�lis mez�n tal�lhat� t�rgy referenci�ja
			Thing t1 = iterator.GetThing();
			if(t1 != null) {
				String t1_name = t1.toString();
				
				//Ha az aktu�lis mez�n l�tezik egy t�rgy �s az b�bu ...
				if(t1_name.equals("Pawn")) {
					Pawn p = (Pawn)t1;
					DirectionComparator dc = new DirectionComparator();
					lokes_erteke += dc.compare(p.GetFacing(), facing);
				}
			}
			iterator = iterator.GetNeighbor(facing);	//Tov�bb l�p�nk a b�bu n�z�s�nek megfelel�en a k�vetkez� szomsz�dos mez�re.
		}
		return lokes_erteke;
	}
	
	/**
	 * Adott ir�nyba a b�bu tol�st kezdem�nyez.
	 * Tol�s csak abba az ir�nyba t�rt�nhet, amerre a b�bu �ppen n�z.
	 * T�nyleges l�k�s csak akkor t�rt�nik, ha a l�k�s �rt�ke pozit�v.
	 * @return Visszat�r�ti a leesett t�rgyat a t�bl�r�l.
	 * */
	public Thing Push() {
		Thing leesett_targy = null;
		//Ha a l�k�s eredm�nye pozit�v, csak abban az esetben t�rt�nik l�k�s
		if(calculatePush() > 0) {
			
			//Elt�roljuk, ki a l�k�s ir�ny�ban az utols� mez� a b�but�l
			Field utolso_a_sorban = this.GetField();
			while(utolso_a_sorban.GetNeighbor(facing) != null && utolso_a_sorban.GetThing()!= null) {
				utolso_a_sorban = utolso_a_sorban.GetNeighbor(facing);
			}
			
			//Majd az utols� mez�t�l kezdve az �ppen aktu�lis mez�n tal�lhat� t�rgyat eggyel arr�bb l�ptetj�k.
			Field iterator = utolso_a_sorban;
			Field innen_indultam = this.GetField();
			while(iterator != innen_indultam.GetNeighbor(facing.Ellentet(facing))) {
				Thing t1 = iterator.GetThing();											//Aktu�lis mez�n tal�lhat� t�rgy.
				if(t1 != null) {														//Megvizsg�ljuk, hogy van-e az aktu�lis mez�n tolhat� t�rgy ...
					Field szomszed_iranyban = iterator.GetNeighbor(facing);				//Aktu�lis mez� adott szomsz�dja.
					if(szomszed_iranyban != null) {										// ... Megvizsg�ljuk, hogy a szomsz�d l�tezik-e ...
						szomszed_iranyban.AddThing(t1);									// ... HA L�TEZIK SZOMSZ�D: Szomsz�dra l�ptetj�k az aktu�lis mez�n tal�lhat� t�rgyat.
					}
					else if(szomszed_iranyban == null && t1.toString().equals("Pawn"))	// ... HA NEM L�TEZIK �S A T�RGY EGY B�BU, akkor ezt jelezz�k visszat�r�si �rt�kk�nt, hogy ez a b�bu leesett a l�k�s sor�n.
						leesett_targy = (Pawn)t1;
					else if(szomszed_iranyban == null && t1.toString().equals("Rock"))	// ... HA NEM L�TEZIK �S A T�RGY EGY SZIKLA, akkor visszat�r�tj�k, mint leesett t�rgyat a t�bl�r�l.
						leesett_targy = (Rock)t1;
					t1.SetField(szomszed_iranyban);										//Be�ll�tjuk t�rgy mez�j�nek a szomsz�dot. (Lehet null a szomsz�d, ha nem l�tezik, �s ez �gy j�.)
					iterator.RemoveThing();
				}
				iterator = iterator.GetNeighbor(facing.Ellentet(facing));				//Tov�bb l�p�nk a k�vetkez� szomsz�dos mez�re ... visszafel�.
			}
		}
		return leesett_targy;
	}
}