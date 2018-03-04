import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.*;
import java.nio.charset.*;

public class ProjectMain extends JFrame {
	
	private JPanel panel, buttonPanel;
	private JButton setInputFile, setOutputFile, clear, help;
	private JLabel numberOfWords, numberOfLines, numberOfLinesRemoved, averageLineWords, averageLineLength;
	private JTextArea textArea;
	private JFileChooser fileChooser;
	private File inputFile;
	private FileReader reader;
	private FileData fileData;
	
	public ProjectMain() {
		createView();
		setTitle("CSE360 - Project");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(950, 550);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
	}
	
	public void createView() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 950, 550);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(15, 15, 190, 490);
		getContentPane().add(panel);
		panel.add(buttonPanel);
		panel.setBackground(Color.GRAY);
		buttonPanel.setBackground(Color.WHITE);
		fileData = new FileData(null);
		
		// File Chooser
		fileChooser = new JFileChooser();
		
		// Buttons:
			// Select Input File
		setInputFile = new JButton("Input File");
		setInputFile.setBounds(20, 10, 150, 50);
		setInputFile.setBackground(Color.LIGHT_GRAY);
		setInputFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(null);
				try {
                    reader = new FileReader(fileChooser.getSelectedFile().toString());
                    inputFile = fileChooser.getSelectedFile();
                    fileData = new FileData(fileChooser.getSelectedFile().toString());
                    BufferedReader br = new BufferedReader(reader);
                    updateLabels();
                    textArea.read(br, null);
                    br.close();
                    textArea.requestFocus();
                }
                catch(Exception e2) {
                	System.out.println("{1} exception caught."+ e2);
                }
			}
		});
			// Select Output File
		setOutputFile = new JButton("Output File");
		setOutputFile.setBounds(20, 70, 150, 50);
		setOutputFile.setBackground(Color.LIGHT_GRAY);
		setOutputFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser.showSaveDialog(ProjectMain.this);
				try {
					if (inputFile != null) {
						FileWriter outputFile = new FileWriter(fileChooser.getSelectedFile()+".txt");
						outputFile.write(textArea.getText());
						
						outputFile.close();
					} else {
						System.out.println("File is empty");
					}
				}
				catch(Exception e2) {
					System.out.println("{2} exception caught."+ e2);
				}
			}
		});
		clear = new JButton("Clear");
		clear.setBounds(20, 130, 150, 50);
		clear.setBackground(Color.LIGHT_GRAY);
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fileData = new FileData("");
				textArea.setText("");
				updateLabels();
			}
		});
		help = new JButton("Help");
		help.setBounds(20, 190, 150, 50);
		help.setBackground(Color.LIGHT_GRAY);
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileData = new FileData(null);
				textArea.setText("\n + Click the \"Input File\" button to select the text file that you wish to have formatted.\n\n"
								+ "\n + Click the \"Output File\" button to create a new text document with the formatted text.\n\n"
								+ "\n + Click the \"Clear\" button to clear the text preview area.\n\n"
								+ "\n + Click the \"Help\" button to display these instructions.");
				updateLabels();
			}
		});
		buttonPanel.add(setInputFile);
		buttonPanel.add(setOutputFile);
		buttonPanel.add(clear);
		buttonPanel.add(help);
		
		// Labels:
		numberOfWords = new JLabel("Number of words: " + fileData.getNumOfWords());
		numberOfLines = new JLabel("Number of lines: " + fileData.getNumOfLines());
		numberOfLinesRemoved = new JLabel("Number of lines removed: " + fileData.getNumOfLinesRemoved());
		averageLineWords = new JLabel("Average words per line: " + fileData.getAvgLineWords());
		averageLineLength = new JLabel("Average line length: " + fileData.getAvgLineLength());
		numberOfWords.setBounds(7, 350, 150, 80);
		buttonPanel.add(numberOfWords);
		numberOfLines.setBounds(7, 370, 150, 80);
		buttonPanel.add(numberOfLines);
		numberOfLinesRemoved.setBounds(7, 390, 190, 80);
		buttonPanel.add(numberOfLinesRemoved);
		averageLineWords.setBounds(7, 410, 190, 80);
		buttonPanel.add(averageLineWords);
		averageLineLength.setBounds(7, 430, 190, 80);
		buttonPanel.add(averageLineLength);
		
		// TextField
		textArea = new JTextArea("\n + Click the \"Input File\" button to select the text file that you wish to have formatted.\n\n"
							+ "\n + Click the \"Output File\" button to create a new text document with the formatted text.\n\n"
							+ "\n + Click the \"Clear\" button to clear the text preview area.\n\n"
							+ "\n + Click the \"Help\" button to display these instructions.");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(220, 14, 715, 491);
		panel.add(scrollPane);
		
	}

	public void updateLabels() {
		numberOfWords.setText("Number of words: " + fileData.getNumOfWords());
		numberOfLines.setText("Number of lines: " + fileData.getNumOfLines());
		numberOfLinesRemoved.setText("Number of lines removed: " + fileData.getNumOfLinesRemoved());
		averageLineWords.setText("Average words per line: " + fileData.getAvgLineWords());
		averageLineLength.setText("Average line length: " + fileData.getAvgLineLength());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ProjectMain().setVisible(true);
			}
		});
	}

}
