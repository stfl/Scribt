package ashGui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ASH_Panel1 extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private JTextArea output;
	
	private JButton startButton;
	
	private boolean anableStartButton = false;
	private boolean sorceSelected = false;
	private boolean destinationSelected = true;
	
	public ASH_Panel1() {
		
		this.setLayout(new BorderLayout());
		
		// JTextArea
		output = new JTextArea();
		output.setRows(6);
		output.setEditable(false);
		output.setLineWrap(true);
		
		output.setText("Welcome to AHSs Worker Bee 1.0!\n" +
				"Please select a sorce folder and a destination folder from the Menu, \n" +
				"then press the Start- Button!");
		
		JScrollPane scrollPane = new JScrollPane(output); 
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		startButton = new JButton();
		startButton.addActionListener(this);
		startButton.setActionCommand("start");
		
		ImageIcon startButtonIcon = createImageIcon("startbutton.jpg");
		startButton.setIcon(startButtonIcon);
		startButton.setContentAreaFilled(false);
		startButton.setToolTipText("Enebled if sorce and destination is valid.");
		startButton.setEnabled(anableStartButton);
		
		ImageIcon beeIcon = createImageIcon("WorkerBee.png");
		JLabel beeLabel = new JLabel(beeIcon);
		
		this.add(beeLabel, BorderLayout.CENTER);
		
		this.add(startButton, BorderLayout.EAST);
		this.add(scrollPane, BorderLayout.AFTER_LAST_LINE);
		
		this.setVisible(true);
	}
	
	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = ASH_Panel1.class.getResource(path);
	    
	    return new ImageIcon(imgURL);
	}
	
	/**
	 * set the Text in the JTextArea.
	 * @param output
	 */
	public void setOutput(String text) {
		String txt = output.getText() + "\n\n" + text;
		this.output.setText(txt);
		this.output.setCaretPosition(txt.length());
	}

	/**
	 * set the boolean for sorce selected -> true
	 * if sorce and destination is selected, enable the start button.
	 * 
	 * @param sorceSelected
	 */
	public void setSorceSelected(boolean sorceSelected) {
		this.sorceSelected = sorceSelected;
		if (this.destinationSelected == true) {
			this.anableStartButton = true;
			this.startButton.setEnabled(true);
		}
	}
	
	/**
	 * set the boolean for destination selected -> true
	 * if destination and destination is selected, enable the start button.
	 * 
	 * @param destinationSelected
	 */
	public void setDestinationSelected(boolean destinationSelected) {
		this.destinationSelected = destinationSelected;
		if (this.sorceSelected == true) {
			this.anableStartButton = true;
			this.startButton.setEnabled(true);
		}
	}
	
	// Observer Pattern
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String e = arg0.getActionCommand();
		
		// startButton
		if (e.equals("start")) {
			this.setOutput("starting...");
			new Thread(ASH_JFrame.instance().getWorkerBee()).start();
		}
	}
	
}
