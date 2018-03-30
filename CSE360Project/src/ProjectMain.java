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
	private boolean single;
	
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
		single = true;
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
                    single = true;
                }
                catch(Exception e2) {
                	
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
				try {
					if (inputFile != null) {
						int result = fileChooser.showSaveDialog(null);
						if(result != JFileChooser.CANCEL_OPTION
								&& result != JFileChooser.ERROR_OPTION) {
							FileWriter fw = new FileWriter(fileChooser.getSelectedFile().toString());
							PrintWriter pw = new PrintWriter(fw);
							fileData.formatFile(inputFile.toString(), pw);
							
							reader = new FileReader(fileChooser.getSelectedFile().toString());
							fileData.calculateFileData(fileChooser.getSelectedFile().toString());
							BufferedReader br = new BufferedReader(reader);
							updateLabels();
							textArea.read(br, null);
							inputFile = fileChooser.getSelectedFile();
							
							br.close();
							pw.close();
						}
					}
				}
				catch(Exception e2) {

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
							result += line + '\n';
							if(!single) {
								result += '\n';
							}
						}
						fileData.setNumOfSpacesAdded(result.length()-result.replaceAll(" ", "").length());
						textArea.setText(result);
						updateLabels();
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
					try {
						reader = new FileReader(inputFile.toString());
						BufferedReader br = new BufferedReader(reader);
						String line, temp = "", result = "";
						String[] words;
						int numOfWords, numOfChars, numOfSpaces;
						while ((line = br.readLine()) != null) {
							numOfWords = 0; numOfChars = 0; numOfSpaces = 0;
							line = line.trim();
							words = line.split(" ");
							for(int i = 0; i < words.length; i++) {
								if(words[i].length() != 0) {
									numOfWords++;
									numOfChars += words[i].length();
								}
							}
							if(line.length() == 0) {
								result += '\n';
							}
							else if(line.length() >= 99) {
								result += line + '\n';
							}
							else if(numOfWords == 1) {
								result += words[0] + '\n';
							}
							else if(numOfWords == 2) {
								result += words[0];
								numOfSpaces = 100 - words[0].length() - words[words.length-1].length()-1;
								for(int i = 0; i < numOfSpaces; i++) {
									result += ' ';
								}
								result += words[words.length-1] + '\n';
							}
							else {
								temp = "";
								temp += words[0];
								numOfSpaces = (100-numOfChars) / (numOfWords-1);
								for(int i = 1; i < words.length-1; i++) {
									if(words[i].length() != 0) {
										for(int j = 0; j < numOfSpaces; j++) {
											temp += ' ';
										}
										temp += words[i];
									}
								}
								numOfSpaces = 100-temp.length()-words[words.length-1].length()-1;
								for(int i = 0; i < numOfSpaces; i++) {
									temp += ' ';
								}
								temp += words[words.length-1];
								result += temp + '\n';
							}
							if(!single) {
								result += '\n';
							}
						}
						fileData.setNumOfSpacesAdded(result.length()-result.replaceAll(" ", "").length());
						textArea.setText(result);
						updateLabels();
					}
					catch(IOException e) {}
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
								while(line.length() <= 98) {
									line = ' ' + line;
								}
							}
							result += line + '\n';
							if(!single) {
								result += '\n';
							}
						}
						fileData.setNumOfSpacesAdded(result.length()-result.replaceAll(" ", "").length());
						textArea.setText(result);
						updateLabels();
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
				if(inputFile != null) {
					if(!single) {
						try {
							reader = new FileReader(inputFile.toString());
							BufferedReader br = new BufferedReader(reader);
							String line, result = "";
							while ((line = br.readLine()) != null) {
								line = line.trim();
								result += line + "\n";							
							}
							fileData.setNumOfSpacesAdded(result.length()-result.replaceAll(" ", "").length());
							textArea.setText(result);
							updateLabels();
						}
						catch(Exception ex) {}
						finally {
							single = true;
						}
					}
				}		
			}
			
		});
		twoSpace = new JButton("Double");
		twoSpace.setBounds(95, 280, 75, 20);
		twoSpace.setBackground(Color.LIGHT_GRAY);
		twoSpace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputFile != null) {
					if(single) {
						try {
							reader = new FileReader(inputFile.toString());
							BufferedReader br = new BufferedReader(reader);
							String line, result = "";
							while ((line = br.readLine()) != null) {
								line = line.trim();
								result += line + "\n\n";							
							}
							fileData.setNumOfSpacesAdded(result.length()-result.replaceAll(" ", "").length());
							textArea.setText(result);
							updateLabels();
						}
						catch(Exception ex) {}
						finally {
							single = false;
						}
					}
				}				
			}
			
		});
		charLimitInput = new JTextField("Input a file and an integer > 0");
		charLimitInput.setBounds(7, 330, 175, 20);
		charLimitInput.setBackground(Color.LIGHT_GRAY);
		charLimitInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(inputFile != null) {
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
					charLimitInput.setText("Input a file first");
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