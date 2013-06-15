package notepad;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class Notepad extends JFrame implements ActionListener {
	private TextArea textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	private MenuBar menuBar = new MenuBar(); 
	private Menu file = new Menu(); 
	private MenuItem openFile = new MenuItem();
	private MenuItem saveFile = new MenuItem();
	private MenuItem close = new MenuItem();
	
	public Notepad() {
		this.setSize(1000, 700);
		this.setTitle("Notepad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.textArea.setFont(new Font("New Times Roman", Font.BOLD, 12));
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(textArea);

		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file);
		
		this.file.setLabel("File");
		this.openFile.setLabel("Open"); 
		this.openFile.addActionListener(this); 
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		this.file.add(this.openFile); 
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);
		this.close.setLabel("Close");
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		this.close.addActionListener(this);
		this.file.add(this.close);
	}
	
    @Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == this.close) {
                this.dispose();
            }
		
		else if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser();
			int option = open.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				this.textArea.setText("");
				try {
					Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (scan.hasNext()) {
                                        this.textArea.append(scan.nextLine() + "\n");
                                    }
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		
		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser();
			int option = save.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
                                try (BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()))) {
                                    out.write(this.textArea.getText());
                                } 
				} catch (Exception ex) { 
					System.out.println(ex.getMessage());
				}
			}
		}
	}
    public static void main(String args[]) {
        Notepad app = new Notepad();
        app.setVisible(true);
}
}
