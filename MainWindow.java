import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


 
public class MainWindow
{
    JFrame frame;
    JTabbedPane tabs;
    JPanel tab1;
    JPanel tab2;
    JPanel tab1N;
    JPanel tab1S;
    JPanel tab1W;
    JPanel tab1E;
    JPanel tab1C;
    JTable table;
    
    JButton search;
    JButton settings;
    JButton add;
    
    JLabel lb;
    
    JTextField suche;
    
	public static void main(String[] args){
		MainWindow mainWindow = new MainWindow();
	}
	

    public MainWindow()
    {
    	DefaultTableModel model = new DefaultTableModel(new String[] {"Test1", "Test2"}, 0 );
    	table = new JTable(model);
        frame = new JFrame("Sch�lerDatenbank");
        tabs = new JTabbedPane();
        tab1 = new JPanel();
        tab2 = Suchkonfiguration.getPanel();
        tab1N = new JPanel();
        tab1S = new JPanel();
        tab1W = new JPanel();
        tab1E = new JPanel();
        tab1C = new JPanel();
        
        search = new JButton("Suchen");
        add = new JButton("Hinzuf�gen");
        settings = new JButton("Einstellungen");
        
        search.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		search();
        	}
        	
        });
        
        lb = new JLabel("results...");
        
        tab1W.setLayout(new BoxLayout(tab1W, BoxLayout.Y_AXIS));
        
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); frame.pack(); frame.setVisible( true );
        
        suche = new JTextField("Suchbegriff...");
        suche.setSize(600,300);

        frame.setSize(600, 800);
        
        tab1W.add(add);
        tab1W.add(settings);
        tab1W.add(lb);

        tabs.addTab("�bersicht", tab1);
        tabs.addTab("Suchkonfiguration", tab2);
        frame.add(tabs);
        tab1.setLayout(new BorderLayout());
        tab1.add(tab1N, BorderLayout.NORTH);
        tab1.add(tab1S, BorderLayout.SOUTH);
        tab1.add(tab1W, BorderLayout.WEST);
        tab1.add(tab1E, BorderLayout.EAST);
        tab1.add(tab1C, BorderLayout.CENTER);
        
        tab1N.add(suche);
        tab1N.add(search);
        //tab1C.add(new JTextField("test"));
        tab1C.setLayout(new BorderLayout());
        tab1C.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
        
        

        
    }
    
    public void search() {
    	String searchText = suche.getText();
    	ArrayList<String> results = new ArrayList<String>();
    	ArrayList<Integer> resultColumns = new ArrayList<Integer>();
    	ArrayList<Integer> resultRows = new ArrayList<Integer>();
    	
    	for(int i=0; i < table.getRowCount(); i++) 
    	{
    		for (int j=0; j < table.getColumnCount(); j++)
    		{
    			String cell = (String) table.getModel().getValueAt(i,j);
    			if (cell.contains(searchText)) {
    				results.add(cell);
    				resultColumns.add(j);
    				resultRows.add(i);
    			}
    		}
    	}
    	
    	lb.setText(results.toString());
    	
    	
    	for (int i = 0; i < results.size(); i++) {
    		table.getModel().setValueAt("*" + results.get(i) + "*", resultRows.get(i), resultColumns.get(i));
    	}
    }
    
}
