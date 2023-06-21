package homework;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * Megjelen�tend� ablak (Frame) oszt�lya.
 * */
public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	
 
	//----------ADATTAGOK----------
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static int rocks_max_num = 3;
	
	/**Referencia a j�t�kt�bla adataira*/
	private FieldData field_data;
	/**Referencia a b�but�bla adataira*/
	private PawnData pawn_data;
	/**Referencia a t�bl�n tal�lhat� szikl�k adataira*/
	private Rock[] rock_data;
	
	private JTable board;
	private JTable pawn_board;
	
	private JButton bt_step_off_on;
	private JButton bt_step;
	private JButton bt_push;
	private JButton bt_rotate;
	private JButton bt_done;
	private boolean player1_turn;
	private boolean bt_step_set_enabled;
	private boolean bt_rotate_set_enabled;
	
	private JComboBox<Direction> direction_box;
	private JLabel lb_irany;
	private JLabel lb_babu;
	private JLabel lb_mezo;
	private JLabel lb_jatekos;
	private final static JLabel lb_kiv = new JLabel("Kiv�lasztott ");
	
	
	//---------J�T�K-MEGJELEN�T�-MET�DUSOK----------
	
	
	/**
	 * Kirajzolja helyesen a t�bl�zat gombjainak a k�p�t MEZ�K T�BL�ZAT ALAPJ�N
	 * */
	private static class JFieldRenderer implements TableCellRenderer{ 
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Field field = (Field)value;
			Thing thing = field.GetThing();
			if(thing != null && thing.toString().equals("Pawn"))
				return (Pawn)thing;
			else if(thing != null && thing.toString().equals("Rock")) {
				return (Rock)thing;
			}
				
			return field;
		}
		
	}
	
	
	/**
	 * Kirajzolja helyesen a t�bl�zat gombjainak a k�p�t B�BUK T�BL�ZAT ALAPJ�N
	 * */
	private static class JPawnRenderer implements TableCellRenderer{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return (Pawn)value;
		}
		
	}
	
	
	/**
	 * Komponensek bet�lt�se el�re
	 * */
	private void initComponents() {
		
		//Az ablak layout - GRIDBAGLAYOUT
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if(shouldFill)
			c.fill = GridBagConstraints.HORIZONTAL;
		if(shouldWeightX)
			c.weightx = 5.0;
		c.weighty = 1.0;
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		
		//BORDERLAYOUT - CENTER - J�t�kt�bla
		board = new JTable(field_data);
		board.addMouseListener(new FieldMouseListener());
		board.setRowHeight(150);
		board.setCellSelectionEnabled(true);
		board.setRowSelectionAllowed(false);
		board.setColumnSelectionAllowed(false);
		board.getColumn("Mezo1").setCellRenderer(new JFieldRenderer());
		board.getColumn("Mezo2").setCellRenderer(new JFieldRenderer());
		board.getColumn("Mezo3").setCellRenderer(new JFieldRenderer());
		board.getColumn("Mezo4").setCellRenderer(new JFieldRenderer());
		board.getColumn("Mezo5").setCellRenderer(new JFieldRenderer());
		board.setFillsViewportHeight(true);
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 2;
		c.gridwidth = 5;
		c.ipadx = 250;
		c.insets = new Insets(50,0,20,10);
		this.add(board, c);
		
	 
		//B�buk helyei
		pawn_board = new JTable(pawn_data);
		pawn_board.addMouseListener(new PawnMouseListener());
		pawn_board.setRowHeight(150);
		pawn_board.setCellSelectionEnabled(true);
		pawn_board.setRowSelectionAllowed(false);
		pawn_board.setColumnSelectionAllowed(false);
		pawn_board.getColumn("Player1").setCellRenderer(new JPawnRenderer());
		pawn_board.getColumn("Player2").setCellRenderer(new JPawnRenderer());
		board.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 2;
		c.gridwidth = 1;
		c.ipadx = 15;
		c.insets = new Insets(50,10,20,20);
		this.add(pawn_board, c);
		
		//J�t�k statisztik�k
		lb_babu = new JLabel("b�bu: semmi");
		lb_babu.setBackground(Color.RED);
		lb_irany = new JLabel("ir�ny: UP");
		lb_mezo = new JLabel("mez�: semmi");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(20, 10,5,20);
		this.add(lb_kiv, c);
		c.gridwidth = 2;
		c.gridx = 6;
		lb_jatekos = new JLabel("j�t�kos: p1");
		this.add(lb_jatekos, c);
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(20, 10,5,1);
		this.add(lb_babu, c);
		c.gridy = 1;
		this.add(lb_mezo, c);
		c.gridy = 2;
		this.add(lb_irany, c);
		
		
		//L�k�s ir�ny�nak kiv�laszt�sa - JCOMBO
		direction_box = new JComboBox<Direction>();
		direction_box.addActionListener(new DirectionSelectActionListener());
		direction_box.addItem(Direction.UP);
		direction_box.addItem(Direction.RIGHT);
		direction_box.addItem(Direction.DOWN);
		direction_box.addItem(Direction.LEFT);
		c.gridx = 6;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipady = 10;
		c.ipadx = 0;
		c.insets = new Insets(1, 10,1,1);
		this.add(direction_box, c);
		
		
		//Gombjaink
		bt_step = new JButton("L�pj!");
		bt_step.addActionListener(new StepActionListener());
		bt_step.addChangeListener(null);
		bt_step_off_on = new JButton("L�pj fel/le a t�bl�r�l!");
		bt_step_off_on.addActionListener(new StepOffOnActionListener());
		bt_push = new JButton("L�kj!");
		bt_push.addActionListener(new PushActionListener());
		bt_rotate = new JButton("Forogj!");
		bt_rotate.addActionListener(new RotateActionListener());
		bt_done = new JButton("Passz!");
		bt_done.addActionListener(new DoneActionListener());
		c.ipadx = -500;
		c.ipady = 10;
		c.gridx = 7;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(5, 5,5,10);
		this.add(bt_step, c);
		c.gridx = 6;
		c.gridy = 2;
		this.add(bt_rotate, c);
		c.gridy = 3;
		c.gridx = 6;
		this.add(bt_step_off_on, c);
		c.gridy = 4;
		c.gridx = 6;
		c.gridwidth = 2;
		this.add(bt_done, c);
		c.gridy = 3;
		c.gridx = 7;
		c.gridwidth = 1;
		this.add(bt_push, c);
		
	}
	
	
	/**
	 * GameFrame konstruktora
	 * */
	public GameFrame() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//J�t�k elemek inic
		rock_data = new Rock[rocks_max_num];
		for (int i = 0; i < rock_data.length; i++) {
			rock_data[i] = new Rock();
		}
		pawn_data = new PawnData();
		field_data = new FieldData(rock_data);
		
		
		//J�t�k kezdete
		player1_turn = true; //Els� j�t�kos kezd;
		bt_step_set_enabled = true;
		bt_rotate_set_enabled = true;
		
		
		// Bez�r�skor mentj�k az adatokat
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println("Closed");
					e.getWindow().dispose();
					// Mez�k adatai
					ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("fields.txt"));
					oos1.writeObject(field_data.fields);
					oos1.flush();
					oos1.close();
					
					// B�bu t�bla adatai - PLAYER1
					ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("player1.txt"));
					oos2.writeObject(pawn_data.player1);
					oos2.close();
					
					// B�bu t�bla adatai - PLAYER1
					ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream("player2.txt"));
					oos3.writeObject(pawn_data.player2);
					oos3.close();
					
					// A j�t�kban �ppen soron l�v� j�t�kos
					ObjectOutputStream oos4 = new ObjectOutputStream(new FileOutputStream("player_turn.txt"));
					oos4.writeObject(player1_turn);
					oos4.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		
		//Ablak be�ll�t�sai
		setMinimumSize(new Dimension(1500, 250));
		initComponents();
		setResizable(false);
		setTitle("Game");
		
		//Ablak k�z�pre �ll�t�sa
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, 0);
		
		this.pack();
	}
	
	
	/**
	 * Bet�lti folytat�sra a f�jlba elmentett j�t�kot.
	 * */
	public void contGame() {
		try {
			// Mez�k adatai
			ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream("fields.txt"));
			field_data.fields = (Field[][])ois1.readObject();
			ois1.close();
			
			// B�bu t�bla adatai - PLAYER1
			ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("player1.txt"));
			pawn_data.player1 = (Pawn[])ois2.readObject();
			ois2.close();
			
			// B�bu t�bla adatai - PLAYER1
			ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream("player2.txt"));
			pawn_data.player2 = (Pawn[])ois3.readObject();
			ois3.close();
			
			// A j�t�kban �ppen soron l�v� j�t�kos
			ObjectInputStream ois4 = new ObjectInputStream(new FileInputStream("player_turn.txt"));
			player1_turn = (boolean)ois4.readObject();
			ois4.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	//---------J�T�K-LOGIKA-OSZT�LYOK/MET�DUSOK----------
	
	/**
	 * Figyeli, hogy a b�buk t�bl�zat�ban �ppen melyik cella van kijel�lve.
	 * */
	private class PawnMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int col = pawn_board.getSelectedColumn();
			int row = pawn_board.getSelectedRow();
			if(pawn_board.getValueAt(row, col) != null)
				lb_babu.setText("b�bu: (" + (col + 1) + ", " + (row +1) + ")");
			else
				lb_babu.setText("b�bu: semmi");
			lb_babu.updateUI();
		}


		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	
	/**
	 * Figyeli, hogy a mez�k t�bl�zat�ban �ppen melyik cella van kijel�lve.
	 * */
	private class FieldMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int col = board.getSelectedColumn();
			int row = board.getSelectedRow();
			Field f;
			if((f=(Field)board.getValueAt(row, col)) != null) {
				
				//Ha a mez�n tal�lhat� dolog b�bu
				if(f.GetThing() != null && f.GetThing().toString().equals("Pawn"))
					lb_babu.setText("b�bu: (" + (col + 1) + ", " + (row +1) + ")");
				else
					lb_babu.setText("b�bu: semmi");
				
				lb_mezo.setText("mez�: (" + (col + 1) + ", " + (row +1) + ")");
				
			}else
				lb_mezo.setText("mez�: semmi");
			lb_mezo.updateUI();
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	//L�P�SSEL KAPCSOLATOS
	
	/**
	 * Figyeli a L�pj le/fel a t�bl�r�l! felirat� gombot.
	 * Ha a j�t�kos l�pett, akkor m�r nem l�khet, viszont van lehet�s�ge forgat�sra.
	 * Ha tov�bb nem szeretne csin�lni semmit, r�nyom a passz gombra.
	 * Itt csak a j�t�kt�bl�r�l val� le/fel l�p�st kezelj�k.
	 * */
	private class StepOffOnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e){
			
			Pawn p = null;
			Field f = null;
			
			//B�BUT EL�SZ�R L�PTET�NK T�BL�RA
			//Ha van kiv�lasztott mez� a j�t�kt�bl�n �S b�bu a b�but�bl�n �S az NEM null
			if(board.getSelectedColumn() >= 0 && pawn_board.getSelectedColumn() >= 0 && 
					(p = (Pawn)pawn_board.getValueAt(pawn_board.getSelectedRow(), pawn_board.getSelectedColumn())) != null) {
				
				//Ha a j�t�kos a saj�tj�t v�lasztotta ki
				if((player1_turn && pawn_board.getSelectedColumn() == 0) || (!player1_turn && pawn_board.getSelectedColumn() == 1)) {
					
					//Ha a kiv�lasztott mez�re l�phet�nk, mert nincs senki rajta
					if((f = (Field)board.getValueAt(board.getSelectedRow(), board.getSelectedColumn())).GetThing() == null) {
						p.SetField(f);
						f.AddThing(p);
						pawn_board.setValueAt(null, pawn_board.getSelectedRow(), pawn_board.getSelectedColumn());
						bt_step_set_enabled = false;
					
					}else
						JOptionPane.showMessageDialog(new JFrame(), "A kiv�lasztott mez�n m�r vannak!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
				}else
					JOptionPane.showMessageDialog(new JFrame(), "Saj�t b�but v�lassz, ne az ellenfel�t!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
				
			}
			
			//B�BU M�R A J�T�KT�BL�N �S LEVESSZ�K
			//Ha van kiv�lasztott mez� a j�t�kt�bl�n �S cella a b�but�bl�n �S az null �S az a j�t�koshoz tartozik
			else if(board.getSelectedColumn() >= 0 && pawn_board.getSelectedColumn() >= 0 &&
					pawn_board.getValueAt(pawn_board.getSelectedRow(), pawn_board.getSelectedColumn()) == null && 
					(player1_turn && pawn_board.getSelectedColumn() == 0) || (!player1_turn && pawn_board.getSelectedColumn() == 1)) {
				
				//Ha a j�t�kos olyan mez�t v�lasztott amin van valami
				if((f = (Field)board.getValueAt(board.getSelectedRow(), board.getSelectedColumn())).GetThing() != null) {
					Thing t = f.GetThing();
					
					//Ha a j�t�kos kiv�lasztotta a saj�t b�buj�t
					if(t.toString().equals("Pawn") &&  
							((player1_turn && (p=(Pawn)f.GetThing()).getIcon().toString().equals("E")) || (!player1_turn && (p=(Pawn)f.GetThing()).getIcon().toString().equals("R")))) {
						p.SetField(null);
						f.RemoveThing();
						pawn_board.setValueAt(p, pawn_board.getSelectedRow(), pawn_board.getSelectedColumn());
						bt_step_set_enabled = false;
					}else
						JOptionPane.showMessageDialog(new JFrame(), "Probl�ma forr�sa, hogy nem saj�t b�but v�lasztott�l.", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
				}else
					JOptionPane.showMessageDialog(new JFrame(), "Ez egy �res mez�!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
			}else
				JOptionPane.showMessageDialog(new JFrame(), "Nincs kiv�lasztott mez� a j�t�kt�bl�n!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
			
			//Ha a l�p�s sikeres volt, a l�p�s gombokat letiltjuk
			if(!bt_step_set_enabled) {
				bt_step.setEnabled(bt_step_set_enabled);
				bt_step_off_on.setEnabled(bt_step_set_enabled);
				bt_push.setEnabled(false);
				field_data.fireTableDataChanged();
				pawn_data.fireTableDataChanged();
			}
		}
	}
	
 
	/**
	 * Figyeli a L�pj! felirat� gombot.
	 * Ha a j�t�kos l�pett, akkor m�r nem l�khet, viszont van lehet�s�ge forgat�sra.
	 * Ha tov�bb nem szeretne csin�lni semmit, r�nyom a passz gombra.
	 * Itt csak a j�t�kt�bl�n bel�li l�p�st kezelj�k
	 * */
	private class StepActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			Pawn p = null;
			Field f = null;
			int lelepett = 0;
			
			//B�BU M�R A J�T�KT�BL�N VAN �S CSAK L�P�NK
			//Ha van kiv�lasztott mez� a j�t�kt�bl�n
			if(board.getSelectedColumn() >= 0) {
				
				//Ha a j�t�kos olyan mez�t v�lasztott amin valami
				if((f = (Field)board.getValueAt(board.getSelectedRow(), board.getSelectedColumn())).GetThing() != null) {
					Thing t = f.GetThing();
					
					//Ha a j�t�kos kiv�lasztotta a saj�t b�buj�t
					if(t.toString().equals("Pawn") &&  
							((player1_turn && (p=(Pawn)f.GetThing()).getIcon().toString().equals("E")) || (!player1_turn && (p=(Pawn)f.GetThing()).getIcon().toString().equals("R")))) {
						lelepett = p.Step((Direction)direction_box.getSelectedItem());
						//Sikeres l�p�s
						if(lelepett == -1 || lelepett == 1) {
							bt_step_set_enabled = false;
							if(lelepett == -1) {
								int column = 0;
								if(player1_turn)
									column = 0;
								else
									column = 1;
								p.SetField(null);
								pawn_board.setValueAt(p, pawn_data.firstNullValueAt(column) , column);
								f.RemoveThing();
							}
						}
					}else
						JOptionPane.showMessageDialog(new JFrame(), "Probl�ma forr�sa, hogy nem saj�t b�but v�lasztott�l.", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
				}else
					JOptionPane.showMessageDialog(new JFrame(), "Ez egy �res mez�!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
			}else
				JOptionPane.showMessageDialog(new JFrame(), "Nincs kiv�lasztott mez� a j�t�kt�bl�n!", "Hiba l�p�skor", JOptionPane.ERROR_MESSAGE);
			
			//Ha a l�p�s sikeres volt, a l�p�s gombokat letiltjuk
			if(!bt_step_set_enabled) {
				bt_step.setEnabled(bt_step_set_enabled);
				bt_step_off_on.setEnabled(bt_step_set_enabled);
				bt_push.setEnabled(false);
				field_data.fireTableDataChanged();
				pawn_data.fireTableDataChanged();
			}
		}
	}
	
	
	/**
	 * Passz felirat� gombot figyeli.
	 * Ha r�nyomnak, automatikusan a m�sik j�t�kos ker�l sorra.
	 * Ekkor minden gomb �jra el�rhet�.
	 * */
	private class DoneActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//Ha eddig az els� j�t�kos k�re volt...
			if(player1_turn) {
				player1_turn = false;
				lb_jatekos.setText("j�t�kos: p2");
			}else {
				player1_turn = true;
				lb_jatekos.setText("j�t�kos: p1");
			}
			
			//Gombok alap�rtelmezettbe �ll�t�sa
			bt_step_off_on.setEnabled(true);
			bt_step.setEnabled(true);
			bt_push.setEnabled(true);
			bt_rotate.setEnabled(true);
			bt_step_set_enabled = true;
			bt_rotate_set_enabled = true;
		}
		
	}
	
	
	/**
	 * JCombo box - ir�nyok kiv�laszt�s�nak - mefigyel�je
	 * Amikor egy ir�nyt kiv�lasztunk, jelen�ts�k meg az ir�ny sz�veg�t a j�t�kt�bla f�l�tt.
	 * */
	private class DirectionSelectActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			lb_irany.setText("ir�ny: " + direction_box.getSelectedItem());
		}
	}
	
	
	/**
	 * Forogj! felirat� gombot figyeli.
	 * Be�ll�tja a j�t�kt�bl�n tal�lhat� b�buk ir�ny�t n�z�s szerint.
	 * */
	private class RotateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Pawn p;
			//Ha van aktu�lisan kiv�lasztott mez�
			if(board.getSelectedColumn()>=0  ) {
				
				//Ha van azon �ll� t�rgy �S az egy b�bu �S az a j�t�kos�
				if((p = (Pawn)((Field)board.getValueAt(board.getSelectedRow(), board.getSelectedColumn())).GetThing()) != null && p.toString().equals("Pawn") &&
						((player1_turn && p.getIcon().toString().equals("E")) || (!player1_turn && p.getIcon().toString().equals("R")))) {
					p.SetFacing((Direction) direction_box.getSelectedItem());
					bt_rotate_set_enabled = false;
				}else
					JOptionPane.showMessageDialog(new JFrame(), "Ez egy �res mez� vagy nem saj�t b�but v�lasztott�l! ", "Hiba forgat�skor", JOptionPane.ERROR_MESSAGE);
			}else
				JOptionPane.showMessageDialog(new JFrame(), "Nincs kiv�lasztott mez� a j�t�kt�bl�n!", "Hiba forgat�skor", JOptionPane.ERROR_MESSAGE);
		
			//Ha a forgat�s sikeres volt, a forgat�s gombot letiltjuk
			if(!bt_rotate_set_enabled) {
				bt_rotate.setEnabled(bt_rotate_set_enabled);
				bt_push.setEnabled(false);
				field_data.fireTableDataChanged();
				pawn_data.fireTableDataChanged();
			}
		
		}
	}
	
	
	/**
	 * Figyeli a L�kj! felirat� gombot.
	 * Az adott ir�nyban a kiv�lasztott b�buval a j�t�kt�bl�n l�k�st kezdem�nyez�nk.
	 * */
	private class PushActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Pawn p;
			boolean push_success = false;
			
			//Ha van aktu�lisan kiv�lasztott mez�
			if(board.getSelectedColumn()>=0  ) {
				
				//Ha van azon �ll� t�rgy �S az egy b�bu �S az a j�t�kos� �S a l�k�s ir�ny�ban van valami
				if((p = (Pawn)((Field)board.getValueAt(board.getSelectedRow(), board.getSelectedColumn())).GetThing()) != null && p.toString().equals("Pawn") && 
						((player1_turn && p.getIcon().toString().equals("E")) || (!player1_turn && p.getIcon().toString().equals("R"))) &&
						p.GetField().GetNeighbor(p.GetFacing()).GetThing() != null) {
					Thing leesett_babu_e = p.Push();
					push_success = true;
					//Ha valamilyen t�rgy k�zben a l�k�s sor�n leesett
					if(leesett_babu_e != null) {
						
						//Ha az egy b�bu volt ... akkor visszatessz�k a b�but�bl�ra
						if(leesett_babu_e.toString().equals("Pawn")) {
							int column = 0;
							if(leesett_babu_e.getIcon().toString().equals("E"))
								column = 0;
							else
								column = 1;
							p = (Pawn)leesett_babu_e;
							pawn_board.setValueAt(p, pawn_data.firstNullValueAt(column) , column);	
						}
						//M�s esetben, egy szikla lehetett csak, ekkor a j�t�knak v�ge, �s a j�t�kos nyert
						else {
						
						
							//Nyertes eld�nt�se: aki az utols� a sorban
							Field utolso_a_sorban = p.GetField();
							//Utols� sorban l�v� elem
							while(utolso_a_sorban.GetNeighbor(p.GetFacing()) != null) {
								utolso_a_sorban = utolso_a_sorban.GetNeighbor(p.GetFacing());
							}
							//Addig megy�nk vissza, am�g meg nem tal�ljuk a nyertest: B�bu �S a l�k�s ir�ny�ba n�zett
							Pawn p_nyertes = p;
							Direction ellentetes_irany = p.GetFacing().Ellentet(p.GetFacing());
							while(utolso_a_sorban.GetNeighbor(ellentetes_irany) != p.GetField().GetNeighbor(ellentetes_irany)) {
								
								// Ha az adott mez�n b�bu van, �s a l�k�s ir�ny�ba n�z, akkor meg van a nyertes b�bu
								if(utolso_a_sorban.GetThing().toString().equals("Pawn") && ((Pawn)utolso_a_sorban.GetThing()).GetFacing().equals(p.GetFacing())) {
									p_nyertes = (Pawn)utolso_a_sorban.GetThing();
									break;
								}
								
								// L�p�nk vissza
								utolso_a_sorban = utolso_a_sorban.GetNeighbor(ellentetes_irany);
							}
							
							
							String nyertes = "";
							if(p_nyertes.getIcon().toString().equals("E"))
								nyertes = "ELS� J�T�KOS!";
							else
								nyertes = "M�SODIK J�T�KOS!";
							
							// F�jlok t�rl�se
							File wd = new File(System.getProperty("user.dir"));
							String[] fajl_nevek = {"fields.txt", "player_turn.txt", "player1.txt", "player2.txt"};
							for(int i = 0; i<fajl_nevek.length; ++i) {
								File fl = new File(wd, fajl_nevek[i]);
								fl.delete();
							}
							
							
							//Kihirdet�s
							JOptionPane.showMessageDialog(new JFrame(), "Gratul�lok, nyert a " + nyertes, "V�ge a j�t�knak.", JOptionPane.OK_OPTION);
							System.exit(0);
						}
						
					}
				}else
					JOptionPane.showMessageDialog(new JFrame(), "Ez egy �res mez� vagy nem saj�t b�but v�lasztott�l, avagy nincs mit l�kni!", "Hiba l�k�skor", JOptionPane.ERROR_MESSAGE);
			}else
				JOptionPane.showMessageDialog(new JFrame(), "Nincs kiv�lasztott mez� a j�t�kt�bl�n!", "Hiba l�k�skor", JOptionPane.ERROR_MESSAGE);
		
			//Ha a l�k�s sikeres volt
			if(push_success) {
				bt_push.setEnabled(false);
				bt_rotate.setEnabled(false);
				bt_step.setEnabled(false);
				bt_step_off_on.setEnabled(false);
				field_data.fireTableDataChanged();
				pawn_data.fireTableDataChanged();
			}
		}
	}
}
