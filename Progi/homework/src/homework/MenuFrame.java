package homework;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * J�t�k f�men�je
 * */
public class MenuFrame extends JFrame{
	
	//----------ADATTAGOK----------
	private static final long serialVersionUID = 1L;
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	/**Referencia a j�t�kt�bla megjelen�t�s�re szolg�l� oszt�ly p�ld�ny�ra.*/
	private GameFrame game;
	
	private JButton bt_new_game;
	private JButton bt_exit;
	private JButton bt_rules;
	private JButton bt_cont_game;
	
	private static MenuFrame mf;
	
	
	
	//---------J�T�K-MEGJELEN�T�-MET�DUSOK----------
	
	
	/**
	 * A program bel�p�si pontja.
	 * */
	public static void main(String[] args) {
		mf = new MenuFrame();
		mf.setVisible(true);
	}
	
	
	/**
	 * Oszt�ly konstruktora
	 * */
	public MenuFrame() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComp();
		
		//Ablak be�ll�t�sai
		setMinimumSize(new Dimension(500, 500));
		setResizable(false);
		setTitle("Main menu");
		
		//Ablak k�z�pre �ll�t�sa
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, 0);
		
		this.pack();
		
		//Ellen�rizz�k, hogy van folytathat� j�t�k-e
		boolean van_folytathato = !(checkEmptyFile("player1.txt"));
		if(van_folytathato) {
			bt_cont_game.setEnabled(true);
			bt_cont_game.setVisible(true);
		}else {
			bt_cont_game.setEnabled(false);
			bt_cont_game.setVisible(false);
		}
	}
	
	
	/**
	 * Bet�lti az ablak elemeit*/
	private void initComp() {
		
		// GRIDLAYOUT
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if(shouldFill)
			c.fill = GridBagConstraints.HORIZONTAL;
		if(shouldWeightX)
			c.weightx = 5.0;
		c.weighty = 1.0;
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		// �dv�zl� sz�veg
		String hello_text = "Kedves j�t�kos(ok),"
				+ "�dv�z�llek benneteket a Siam j�t�kban!\n";
		JLabel lb = new JLabel(hello_text);
		lb.setHorizontalTextPosition(SwingConstants.CENTER);
		lb.setVerticalTextPosition(SwingConstants.CENTER );
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 4;
		c.ipadx = 40;
		c.insets = new Insets(50,10,20,10);
		this.add(lb, c);
		
		// �j j�t�k gomb
		bt_new_game = new JButton("�j j�t�k");
		bt_new_game.addActionListener(new NewGameActionListener());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.ipadx = 1;
		c.insets = new Insets(10,200,0,200);
		this.add(bt_new_game, c);
		
		// Kil�p�s gomb
		bt_exit = new JButton("Kil�p�s");
		bt_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}});
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.ipadx = 1;
		c.insets = new Insets(10,200,0,200);
		this.add(bt_exit, c);
		
		// J�t�kszab�lyzat gomb
		bt_rules = new JButton("J�t�kszab�lyok");
		bt_rules.addActionListener(new ActionListener() {
			String szabalyok = "A j�t�k kezdetekor a p�lya k�zep�n sorakozik a h�rom hegy; az �llatok mind a t�bla\r\n"
					+ "mellett v�rakoznak. Egy l�p�sben k�t dolgot lehet csin�lni: mozogni (egy\r\n"
					+ "v�zszintesen/f�gg�legesen szomsz�dos mez�re) vagy nyomni abba az ir�nyba, amerre az adott\r\n"
					+ "�llat n�z. A mozg�s v�g�n az �llatok tetsz�legesen forgathat�ak, �s a mozg�s �llhat csak\r\n"
					+ "forgat�sb�l is.\r\n"
					+ "A l�k�s szab�lya l�nyeg�ben annyi, hogy �ssze kell adni a l�k�s ir�ny�ba n�z� �llatokat,\r\n"
					+ "�s le kell vonni ebb�l az ellent�tes ir�nyba n�z�k sz�m�t. Ha az eredm�ny pozit�v, akkor\r\n"
					+ "minden(ki) eggyel arr�bb megy. Az gy�z, akinek siker�l egy hegyet lel�knie a t�bl�r�l, viszont\r\n"
					+ "a lel�k�nek az sz�m�t, aki a lel�k�tt hegyet legk�zelebbr�l nyomta (teh�t a l�k�s pillanat�ban\r\n"
					+ "legk�zelebb volt �s a l�k�s ir�ny�ba n�zett).";
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), szabalyok, "J�t�kszab�lyzat", JOptionPane.INFORMATION_MESSAGE);
				//JOptionPane.showConfirmDialog(new JFrame(), szabalyok, "J�t�kszab�lyzat", JOptionPane.NO_OPTION);
			}
		});
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.ipadx = 1;
		c.insets = new Insets(10,200,0,200);
		this.add(bt_rules, c);
		
		// J�t�k folytat�s gomb
		bt_cont_game = new JButton("J�t�k folytat�sa");
		bt_cont_game.addActionListener(new ContGameActionListener());
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 2;
		c.ipadx = 1;
		c.insets = new Insets(10,200,10,200);
		this.add(bt_cont_game, c);
	}
	
	
	/**
	 * Figyeli az �j j�t�k gombot.
	 * Hat�s�ra egy �j j�t�kot ind�t a felhaszn�l�.
	 * */
	private class NewGameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			game = new GameFrame();
			game.setVisible(true);
			mf.setVisible(false);
		}
	}
	
	
	/**
	 * Figyeli a J�t�k folytat�sa felirat� gombot.
	 * */
	private class ContGameActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			game = new GameFrame();
			game.contGame();
			game.setVisible(true);
			mf.setVisible(false);
		}
	}
	
	
	/**
	 * Ellen�rzi, hogy a megadott f�jl tartalma �res-e
	 * @param f - Az ellen�rz�sre ker�lend� f�jl neve
	 * @return IGAZ, ha a f�jl �res, k�l�nben HAMIS.
	 * */
	private boolean checkEmptyFile(String f) {
		File wd = new File(System.getProperty("user.dir"));
		File file = new File(wd, f);
		if(file.length() == 0) {
			return true;
		}
		return false;
	}
	
}
