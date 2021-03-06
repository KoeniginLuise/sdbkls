import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
public class Startansicht {
	
	static JTable table;
    static JButton search;
    static JButton settings;
    static JButton print;
    static JButton add;
    static JCheckBox cbResultsViewToggle;
    static JLabel lb;
    static JTextField suche;
    static JPanel tab1N;
    static JPanel tab1NL;
    static JPanel tab1S;
    static JPanel tab1W;
    static JPanel tab1E;
    static JPanel tab1C;
    static JComboBox klasseAuswahl;
    static ActionListener printAL;
    
    static final String ALLE_KLASSEN = "*ALLE*";
    
    public static JTable getTable() {
    	return table;
    }
    
    static boolean searchTextAreaHasBeenUsed = false;
    
    public static JPanel getPanel(JFrame frame){
		JPanel panel = new JPanel();
		center();
    	north();
    	east();
    	south();
    	west(frame);
    	panel.setLayout(new BorderLayout());
        panel.add(tab1N, BorderLayout.NORTH);
        panel.add(tab1S, BorderLayout.SOUTH);
        panel.add(tab1W, BorderLayout.WEST);
        panel.add(tab1E, BorderLayout.EAST);
        panel.add(tab1C, BorderLayout.CENTER);
        panel.setBackground(new Color(1,68,131));
		return panel;
	}
	
	static void center(){
		
    	table = new JTable(MainTable.getTable()) {
    		public TableCellRenderer getCellRenderer(int row, int column) {
    			return new ModifiedCellRenderer();
    		}
    	};
    	//table.setColumnModel(new ModifiedColumnModel());
    	tab1C = new JPanel();
        tab1C.setBackground(new Color(1,68,131));
        tab1C.setLayout(new BorderLayout());
        tab1C.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	static void north(){
		tab1N = new JPanel();
        tab1N.setBackground(new Color(1,68,131));
        JPanel tab1NR = new JPanel();
        tab1NR.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tab1NR.setBackground(new Color(1,68,131));
        search = new JButton("Suchen");
        search.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		search();
        	}
        	
        });
        
        suche = new JTextField("Suchbegriff...");
        suche.setForeground(new Color(80, 80, 80));
        suche.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) { }
			public void mousePressed(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }
			public void mouseClicked(MouseEvent e) {
				if (searchTextAreaHasBeenUsed) {
					return;
				}
				
				searchTextAreaHasBeenUsed = true;
				
				suche.setForeground(Color.BLACK);
				suche.setText("");
			}
		});
        suche.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) { }
			public void focusGained(FocusEvent e) {
				if (searchTextAreaHasBeenUsed) {
					return;
				}
				
				searchTextAreaHasBeenUsed = true;
				
				suche.setForeground(Color.BLACK);
				suche.setText("");
			}
		});
        suche.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { }
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}
		});
        
        cbResultsViewToggle = new JCheckBox("nur Suchergebnisse anzeigen");
        cbResultsViewToggle.setSelected(true);
        cbResultsViewToggle.setBackground(new Color(1,68,131));
        cbResultsViewToggle.setForeground(Color.WHITE);
        suche.setColumns(20);
        
        JButton clearSuche = new JButton("X");
        clearSuche.setFont(suche.getFont());
        clearSuche.setMargin(new Insets(0, 6, 0, 6));
        clearSuche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suche.setText("");
				suche.requestFocus();
				searchTextAreaHasBeenUsed = true;
			}
		});        
        
        tab1NR.add(getTitelLabel());
        tab1NR.add(getImg());
        JLabel klassenAuswahlLabel = new JLabel(" in Klasse bzw. Stufe");
        klassenAuswahlLabel.setForeground(Color.WHITE);
        String[] klassen = {ALLE_KLASSEN,"7","8","9","10","Q1","Q2","Q3","Q4"};
        klasseAuswahl = new JComboBox(klassen);
        tab1NR.add(suche);
        tab1NR.add(clearSuche);
        tab1NR.add(klassenAuswahlLabel);
        tab1NR.add(klasseAuswahl);
        tab1NR.add(search);
        tab1NR.add(cbResultsViewToggle);
        tab1N.add(tab1NR);
	}
	static void east(){
		tab1E = new JPanel();
        tab1E.setBackground(new Color(1,68,131));
	}
	static void south(){
		tab1S = new JPanel();
        tab1S.setBackground(new Color(1,68,131));
	}
	static void west(JFrame frame){
        add = new JButton("Hinzuf�gen");
        add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddWindowListener listener = new AddWindowListener() {
					public void onOkButtonClicked(String vorname, String name,
							String klasse, String gebDatum, String strasse,
							String hausNummer, String plz, String ort,
							String beitrittsDatum, String medBesonderheiten,
							String anmerkungen) {
						frame.setEnabled(true);
						
						// TODO: Daten in Datenbank eintragen
					}
					public void onCancelled() {
						frame.setEnabled(true);
					}
				};
				
				AddWindow wnd = new AddWindow(listener);
				frame.setEnabled(false);
				wnd.setVisible(true);
			}
		});
        settings = new JButton("Einstellungen");
        addPrintButton();
        //lb = new JLabel("results...");
        //lb.setForeground(Color.WHITE);
        tab1W = new JPanel();
        tab1W.setBackground(new Color(1,68,131));
    	tab1W.setLayout(new BoxLayout(tab1W, BoxLayout.Y_AXIS));
        tab1W.add(add);
        tab1W.add(settings);
        tab1W.add(print);
        //tab1W.add(lb);
	}
	public static void search() {
		MainTable.refreshTable();
		table.setModel(MainTable.getTable());
		
    	String searchText = suche.getText();
    	ArrayList<String> results = new ArrayList<String>();
    	ArrayList<Integer> resultColumns = new ArrayList<Integer>();
    	ArrayList<Integer> resultRows = new ArrayList<Integer>();
    	for(int i=0; i < table.getRowCount(); i++) {
    		for (int j=0; j < table.getColumnCount(); j++) {
    			String cell = (String) table.getModel().getValueAt(i,j);
    			if (cell != null && cell.contains(searchText)) {
    				if (!klasseAuswahl.getSelectedItem().toString()
    						.equals(ALLE_KLASSEN)) {
    					// Nach Klassen filtern
    					int ccl = -1;
    					for (int ci = 0; ci < table.getColumnCount(); ci++) {
    						if (table.getColumnName(ci).equals("Klasse")) {
    							ccl = ci;
    							break;
    						}
    					}
    					
    					if (ccl != -1 && !table.getModel().
    							getValueAt(i, ccl).toString().startsWith(
    							klasseAuswahl.getSelectedItem().toString())) {
    						break;
    					}
    				}
    				
    				results.add(cell);
    				resultColumns.add(j);
    				resultRows.add(i);
    			}
    		}
    	}
    	//lb.setText(results.toString());
    	for (int i = 0; i < results.size(); i++) {
    		table.getModel().setValueAt(
    				searchText.equals("") ?
    						results.get(i) :
    						// Wenn Suchfeld leer ist, nichts highlighten
    						new HighlightedString(results.get(i)),
    				resultRows.get(i), resultColumns.get(i));
    	}
    	if (cbResultsViewToggle.isSelected()) {
    		for(int i=table.getRowCount()-1; i >= 0; i--) {
    			if (!resultRows.contains(i)) {
    				((DefaultTableModel) table.getModel()).removeRow(i);
    			}	
    		}	
    	}
    }
	
	private static void addPrintButton(){
		print = new JButton("Drucken");
		printAL = new ActionListener(){
			public void actionPerformed(ActionEvent printButtonClicked){
			        PrinterJob pj = PrinterJob.getPrinterJob();
			        pj.printDialog();
			        
			    }
		};
		print.addActionListener(printAL);
	}
	
	private static JLabel getImg() {
		JLabel klsLogoLabel = new JLabel();
		try{
			BufferedImage klsLogo = ImageIO.read(new File("kls_logo.png"));
			klsLogoLabel = new JLabel(new ImageIcon(klsLogo));
		} catch(Exception e) {
			System.out.println("Fehler beim Laden des Bildes: " + e);
		}
		return klsLogoLabel;
	}
	
	private static JLabel getTitelLabel(){
		JLabel titelLabel = new JLabel("<html><body>"
				+ "K�NIGIN-LUISE-STIFTUNG BERLIN <br> SCHULDATENBANK"
				+ "</html></body>");
		titelLabel.setForeground(Color.WHITE);
		return titelLabel;
	}
	
}
