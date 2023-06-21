package homework;

import javax.swing.table.AbstractTableModel;



/**
 * A mez�k adatait tartalmazza t�bl�zat bet�lt�s�hez.
 * */
public class FieldData extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	
	
	//----------ADATTAGOK----------
	/**Referencia a t�bl�n tal�lhat� mez�kre*/
	Field[][] fields;
	/**T�rolja a t�bla sorainak sz�m�t*/
	private static final int rows = 5;
	/**T�rolja a t�bla oszlopainak sz�m�t*/
	private static final int cols = 5;
	
 
	
	//---------MET�DUSOK----------
	
	/**
	 * Be�ll�tja a t�bla mez�inek a szomsz�dos mez�ket.
	 * */
	private void setFieldNeighbors() {
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; ++j) {
				
				//Ha a mez� sarok �S ...
				//... �S bal oldalon van �S...
				if(j == 0 && (i == 0 || i==rows-1)) {
					//... �S fent.
					if(i == 0) {
						fields[i][j].SetNeighbor(Direction.RIGHT, fields[i][j+1]);
						fields[i][j].SetNeighbor(Direction.DOWN, fields[i+1][j]);
					///... �S lent.
					}else {
						fields[i][j].SetNeighbor(Direction.RIGHT, fields[i][j+1]);
						fields[i][j].SetNeighbor(Direction.UP, fields[i-1][j]);
					}
				//... �S jobb oldalon van ...
				}else if(j == cols-1 && (i == 0 || i==rows-1)){
					//... �S fent.
					if(i == 0) {
						fields[i][j].SetNeighbor(Direction.LEFT, fields[i][j-1]);
						fields[i][j].SetNeighbor(Direction.DOWN, fields[i+1][j]);
					///... �S lent.
					}else {
						fields[i][j].SetNeighbor(Direction.LEFT, fields[i][j-1]);
						fields[i][j].SetNeighbor(Direction.UP, fields[i-1][j]);
					}
				//Ha a mez� a j�t�kt�bla sz�l�n helyezkedik el �S...
				//Ekkor kiz�r�lag k�t szomsz�dot �ll�tunk att�l f�gg�en, hogy azok egy v�zszintes/f�gg�leges egyenesbe esnek-e.
				//(pl.: A t�bla fels� sz�l�n kiz�r�lag a mez� bal �s jobb oldali szomsz�dait �ll�tjuk.)
				//Fontos, hogy mivel a SetNeighbor egy oda-vissza szomsz�d-be�ll�t� f�ggv�ny, �gy a mez�k egyes szomsz�dival nem kell foglalkoznunk.
				//... �S a mez�nek v�zszintesen �ll�that� a szomsz�dai ...
				}else if((i == 0 || i == rows-1) && (j != 1 || j != cols-1-1)) {
					fields[i][j].SetNeighbor(Direction.RIGHT, fields[i][j+1]);
					fields[i][j].SetNeighbor(Direction.LEFT, fields[i][j-1]);
				//... �S a mez�nek f�gg�legesen �ll�that� a szomsz�dai ...
				}else if((j == 0 || j == cols-1) && (i != 1 || i != rows-1-1)) {
					fields[i][j].SetNeighbor(Direction.UP, fields[i-1][j]);
					fields[i][j].SetNeighbor(Direction.DOWN, fields[i+1][j]);
				}
				//... minden m�s esetben a mez�nek n�gy szomsz�dja lesz
				else {
					fields[i][j].SetNeighbor(Direction.RIGHT, fields[i][j+1]);
					fields[i][j].SetNeighbor(Direction.DOWN, fields[i+1][j]);
					fields[i][j].SetNeighbor(Direction.LEFT, fields[i][j-1]);
					fields[i][j].SetNeighbor(Direction.UP, fields[i-1][j]);
				}
			}
		}
	}
	
	
	/**
	 * Be�ll�tja a k�z�ps� h�rom mez� tartalm�t.
	 * Felvessz�k a szikl�kat.
	 * */
	private void setRocks(Rock[] rocks) {
		
		int row = 3-1;
		int col = 3-1;
		fields[row][col-1].AddThing(rocks[0]);
		fields[row][col].AddThing(rocks[1]);
		fields[row][col+1].AddThing(rocks[2]);
	}
	
	
	
	/**
	 * Az oszt�ly konstruktora. Felt�lti a mez�ket.
	 * Be�ll�tja a mez�k szomsz�djait.
	 * Elhelyezi a szikl�kat a t�bl�n.
	 * */
	public FieldData(Rock[] rocks) {
		fields = new Field[rows][cols];
		for(int i = 0; i<rows; ++i)
			for(int j = 0; j<cols; ++j)
				fields[i][j] = new Field();
		this.setFieldNeighbors();
		
		setRocks(rocks);
	}
	
	
	public String getColumnName(int col) {
		switch(col) {
		case 0: return "Mezo1";
		case 1: return "Mezo2";
		case 2: return "Mezo3";
		case 3: return "Mezo4";
		default: return "Mezo5";
		}
	}
	
	
	@Override
	public int getRowCount() {
		return fields.length;
	}

	
	@Override
	public int getColumnCount() {
		return fields[0].length;
	}
	
	
	public Class<? extends Object> getColumnClass(int column)
    {
        return getValueAt(0, column).getClass();
    }
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Field field = fields[rowIndex][columnIndex];
		return field;
	}
	
	
	public void setValueAt(Object value, int row, int col) {
		Pawn p = (Pawn)value;
		Field f = fields[row][col];
		f.AddThing(p);
		p.SetField(f);
		p.SetField(f);
		this.fireTableDataChanged();
	}

}
