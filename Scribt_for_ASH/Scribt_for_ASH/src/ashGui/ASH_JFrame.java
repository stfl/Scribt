package ashGui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mainScribt.WorkerBeeForASH;

public class ASH_JFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static ASH_JFrame instance = null;
	
	File sorce;
	File destination;
	
	ASH_Panel1 contentPane;
	JMenuBar menuBar;
	JMenu dateiMenu;
	JMenu helpMenu;
	
	JMenuItem exitItem;
	JMenuItem helpItem;
	
	JMenuItem selectSorceItem;
	JMenuItem selectDestItem;
	
	final JFileChooser fileChooser = new JFileChooser();
	WorkerBeeForASH workerBee = new WorkerBeeForASH();
	
	public ASH_JFrame() {
		
		createAndShowGui();
	}
	
	/**
	 * initializing UI
	 */
	private void createAndShowGui() {
			
		// initialize fileChooser
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(400, 20));
		
		//Build the datei- menu.
		dateiMenu = new JMenu("Datei");
		dateiMenu.setMnemonic(KeyEvent.VK_A);
		dateiMenu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(dateiMenu);
		
		// build the help menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_A);
		helpMenu.getAccessibleContext().setAccessibleDescription(
		        "Help Menu");
		menuBar.add(helpMenu);
		
		// datei menu items
		exitItem = new JMenuItem("Exit", KeyEvent.VK_T);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		exitItem.getAccessibleContext().setAccessibleDescription("Exits the Programm");
		exitItem.addActionListener(this);
		exitItem.setActionCommand("exit");
		
		selectSorceItem = new JMenuItem("Select Sorce Folder", KeyEvent.VK_T);
		selectSorceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		selectSorceItem.getAccessibleContext().setAccessibleDescription("Opens Filechooser");
		selectSorceItem.addActionListener(this);
		selectSorceItem.setActionCommand("sorce");
		
		selectDestItem = new JMenuItem("Select Destination Folder", KeyEvent.VK_T);
		selectDestItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		selectDestItem.getAccessibleContext().setAccessibleDescription("Opens Filechooser");
		selectDestItem.addActionListener(this);
		selectDestItem.setActionCommand("destination");
		
		// help Item
		helpItem = new JMenuItem("Help", KeyEvent.VK_T);
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		helpItem.getAccessibleContext().setAccessibleDescription("Opens Help");
		helpItem.addActionListener(this);
		helpItem.setActionCommand("help");
		
		// menue tree
		dateiMenu.add(selectSorceItem);
		dateiMenu.add(selectDestItem);
		dateiMenu.add(exitItem);
		helpMenu.add(helpItem);
		
		this.setJMenuBar(menuBar);
		
		contentPane = new ASH_Panel1();
		this.setContentPane(contentPane);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(500, 500));
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	// Singelton Pattern
	public static ASH_JFrame instance() {
		if (instance == null) {
			instance = new ASH_JFrame();
		}
		return instance;
	}

	// Observer Pattern
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String e = arg0.getActionCommand();
		
		// exit
		if (e.equals("exit")) {
			this.dispose();
		}
		if (e.equals("help")) {
			contentPane.setOutput("Ask STLEN or ALTAT! :-P");
		}
		if (e.equals("sorce")) {
			fileChooser.showOpenDialog(this);
			sorce = fileChooser.getSelectedFile();
			if (sorce != null) {
				contentPane.setOutput("Selected Sorce: " + sorce.getAbsolutePath());
				workerBee.setPathToFiles(sorce.getAbsolutePath());
			}
			contentPane.setSorceSelected(true);
		}
		if (e.equals("destination")) {
			fileChooser.showOpenDialog(this);
			destination = fileChooser.getSelectedFile();
			if (destination != null) {
				contentPane.setOutput("Selected Destination: " + destination.getAbsolutePath() 
						+ "[Debug] hasn't been implemented yet");
			}
			contentPane.setDestinationSelected(true);
		}
	}
	
	public boolean getUmbenennen() {
		return false;
	}

	public ASH_Panel1 getContentPane() {
		return contentPane;
	}

	public WorkerBeeForASH getWorkerBee() {
		return workerBee;
	}
	
}
