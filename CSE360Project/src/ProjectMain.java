import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.*;
import java.nio.charset.*;

public class ProjectMain extends JFrame {
	
	private JPanel panel, buttonPanel;
	private JButton setInputFile, setOutputFile, clear, help, left, center, right, oneSpace, twoSpace;
	private JLabel charactersPerLine, numberOfWords, numberOfLines, numberOfLinesRemoved, numberOfSpacesAdded,
	averageLineWords, averageLineLength;
	private JTextField charLimitInput;
	private JTextArea textArea;
	private JFileChooser fileChooser;
	private File inputFile;
	private FileReader reader;
	private FileData fileData;
	private JScrollPane scrollPane;
	
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
						FileWriter fw = new FileWriter(fileChooser.getSelectedFile().toString());
						PrintWriter pw = new PrintWriter(fw);
						fileData.formatFile(inputFile.toString(), pw);
						
						reader = new FileReader(fileChooser.getSelectedFile().toString());
						fileData = new FileData(fileChooser.getSelectedFile().toString());
	                    BufferedReader br = new BufferedReader(reader);
	                    updateLabels();
	                    textArea.read(br, null);
	                    inputFile = fileChooser.getSelectedFile();
	                    
	                    br.close();
						
						pw.close();
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
						+ "\n + Click the \"Help\" button to display these instructions.\n\n"
						+ "\n + Click the \"L\" button to align the text left.\n\n"
						+ "\n + Click the \"C\" button to align the text full.\n\n"
						+ "\n + Click the \"R\" button to align the text right.\n\n"
						+ "\n + Click the \"Single\" button to make the text single spaced.\n\n"
						+ "\n + Click the \"Double\" button to make the text double spaced.\n\n");
				updateLabels();
			}
		});
		left = new JButton("L");
		left.setBounds(20, 250, 50, 20);
		left.setBackground(Color.LIGHT_GRAY);
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputFile != null) {
					try {
						reader = new FileReader(inputFile.toString());
						BufferedReader br = new BufferedReader(reader);
						String line, result = "";
						while ((line = br.readLine()) != null) {
							line = line.trim();
							result += line + "\n";
						}
						textArea.setText(result);
					}
					catch(Exception e) {}
				}
			}		
		});
		center = new JButton("C");
		center.setBounds(70, 250, 50, 20);
		center.setBackground(Color.LIGHT_GRAY);
		center.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputFile != null) {
					
				}
			}		
		});
		right = new JButton("R");
		right.setBounds(120, 250, 50, 20);
		right.setBackground(Color.LIGHT_GRAY);
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputFile != null) {
					try {
						reader = new FileReader(inputFile.toString());
						BufferedReader br = new BufferedReader(reader);
						String line, result = "";
						while ((line = br.readLine()) != null) {
							line = line.trim();
							if(line.length() != 0) {
								while(line.length() <= 100) {
									line = ' ' + line;
								}
							}
							result += line + "\n";
							
						}
						textArea.setText(result);
					}
					catch(Exception e) {}
				}
				
			}		
		});
		oneSpace = new JButton("Single");
		oneSpace.setBounds(20, 280, 75, 20);
		oneSpace.setBackground(Color.LIGHT_GRAY);
		oneSpace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		twoSpace = new JButton("Double");
		twoSpace.setBounds(95, 280, 75, 20);
		twoSpace.setBackground(Color.LIGHT_GRAY);
		twoSpace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		charLimitInput = new JTextField("Input a file and an integer > 0");
		charLimitInput.setBounds(7, 330, 175, 20);
		charLimitInput.setBackground(Color.LIGHT_GRAY);
		charLimitInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputFile != null && Integer.parseInt(charLimitInput.getText()) > 0) {
					try {
						fileData.setCharPerLine(Integer.parseInt(charLimitInput.getText()));
						updateLabels();
					}
					catch(Exception e) {}
					finally {
						charLimitInput.setText("");
					}
				}
				else {
					charLimitInput.setText("Input a file and an integer > 0");
				}
			}
			
		});
		
		buttonPanel.add(setInputFile);
		buttonPanel.add(setOutputFile);
		buttonPanel.add(clear);
		buttonPanel.add(help);
		buttonPanel.add(left);
		buttonPanel.add(center);
		buttonPanel.add(right);
		buttonPanel.add(oneSpace);
		buttonPanel.add(twoSpace);
		buttonPanel.add(charLimitInput);
		
		// Labels:
		charactersPerLine = new JLabel("Characters per line: 80");
		numberOfWords = new JLabel("Number of words: " + fileData.getNumOfWords());
		numberOfLines = new JLabel("Number of lines: " + fileData.getNumOfLines());
		numberOfLinesRemoved = new JLabel("Number of lines removed: " + fileData.getNumOfLinesRemoved());
		numberOfSpacesAdded = new JLabel("Number of spaces added: " + fileData.getNumOfSpacesAdded());
		averageLineWords = new JLabel("Average words per line: " + fileData.getAvgLineWords());
		averageLineLength = new JLabel("Average line length: " + fileData.getAvgLineLength());
		charactersPerLine.setBounds(7, 280, 150, 80);
		buttonPanel.add(charactersPerLine);
		numberOfWords.setBounds(7, 330, 150, 80);
		buttonPanel.add(numberOfWords);
		numberOfLines.setBounds(7, 350, 150, 80);
		buttonPanel.add(numberOfLines);
		numberOfLinesRemoved.setBounds(7, 370, 190, 80);
		buttonPanel.add(numberOfLinesRemoved);
		numberOfSpacesAdded.setBounds(7, 390, 190, 80);
		buttonPanel.add(numberOfSpacesAdded);
		averageLineWords.setBounds(7, 410, 190, 80);
		buttonPanel.add(averageLineWords);
		averageLineLength.setBounds(7, 430, 190, 80);
		buttonPanel.add(averageLineLength);
		
		// TextField
		textArea = new JTextArea("\n + Click the \"Input File\" button to select the text file that you wish to have formatted.\n\n"
				+ "\n + Click the \"Output File\" button to create a new text document with the formatted text.\n\n"
				+ "\n + Click the \"Clear\" button to clear the text preview area.\n\n"
				+ "\n + Click the \"Help\" button to display these instructions.\n\n"
				+ "\n + Click the \"L\" button to align the text left.\n\n"
				+ "\n + Click the \"C\" button to align the text full.\n\n"
				+ "\n + Click the \"R\" button to align the text right.\n\n"
				+ "\n + Click the \"Single\" button to make the text single spaced.\n\n"
				+ "\n + Click the \"Double\" button to make the text double spaced.\n\n");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("monospaced",Font.PLAIN,12));
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(220, 14, 715, 491);
		panel.add(scrollPane);
		
	}

	public void updateLabels() {
		charactersPerLine.setText("Characters per line: " + fileData.getCharPerLine());
		numberOfWords.setText("Number of words: " + fileData.getNumOfWords());
		numberOfLines.setText("Number of lines: " + fileData.getNumOfLines());
		numberOfLinesRemoved.setText("Number of lines removed: " + fileData.getNumOfLinesRemoved());
		numberOfSpacesAdded.setText("Number of spaces added: " + fileData.getNumOfSpacesAdded());
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