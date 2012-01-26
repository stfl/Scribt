package ashGui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PreferencesFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	JPanel contentPane = new JPanel();
	JRadioButton yesRadioButton;
	JRadioButton noRadioButton;
	JTextArea textArea;
	JButton saveButton;
	JButton cancelButton;
	
	boolean changeNames = false;
	boolean rename = false;
	
	public PreferencesFrame() {
		init();
	}
	
	public void init() {
		
		this.setPreferredSize(new Dimension(430, 130));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		textArea = new JTextArea();
		textArea.setRows(2);
		textArea.setText("Do you want the Bee to change the pdf- names \n and copy them to" +
				"a Location you choose?");
		textArea.setEditable(false);
		JScrollPane textPane = new JScrollPane(textArea);
		textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		textPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(textPane, BorderLayout.BEFORE_FIRST_LINE);
		
		yesRadioButton = new JRadioButton("Yes");
		yesRadioButton.setMnemonic(KeyEvent.VK_B);
		yesRadioButton.setActionCommand("yes");
		yesRadioButton.setSelected(true);
		yesRadioButton.addActionListener(this);
		yesRadioButton.setSelected(false);
		
		noRadioButton = new JRadioButton("No");
		noRadioButton.setMnemonic(KeyEvent.VK_B);
		noRadioButton.setActionCommand("no");
		noRadioButton.setSelected(true);
		noRadioButton.addActionListener(this);
		noRadioButton.setSelected(true);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(yesRadioButton);
	    group.add(noRadioButton);
		
	    saveButton = new JButton("Save");
	    saveButton.addActionListener(this);
	    saveButton.setActionCommand("save");
	    
	    cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(this);
	    cancelButton.setActionCommand("cancel");
	    
	    JPanel buttonPanel = new JPanel(new GridLayout(1, 0));
	    buttonPanel.add(saveButton);
	    buttonPanel.add(cancelButton);
	    
	    JPanel radioPanel = new JPanel(new GridLayout(1, 0));
        radioPanel.add(yesRadioButton);
        radioPanel.add(noRadioButton);
	    
        this.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
	    this.add(radioPanel, BorderLayout.CENTER);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String e = arg0.getActionCommand();
		if (e.equals("yes")) {
			changeNames = true;
		}
		if (e.equals("no")) {
			changeNames = false;
		}
		if (e.equals("cancel")) {
			this.setVisible(false);
		}
		if (e.equals("save")) {
			this.rename = this.changeNames;
			this.setVisible(false);
			ASH_JFrame.instance().setMenuEnable(rename);
		}
	}

	public boolean isChangeNames() {
		return rename;
	}

}
