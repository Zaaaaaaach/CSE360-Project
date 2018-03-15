import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.DecimalFormat;

public class FileData {
	
	private int numOfLines, numOfWords, numOfChars, numOfLinesRemoved;
	private double avgLineWords, avgLineLength;
	private DecimalFormat avgFormat;
	private String[] words;
	
	public FileData(String input) {

		this.numOfLines = 0;
		this.numOfWords = 0;
		this.numOfLinesRemoved = 0;
		this.avgLineWords = 0.00;
		this.avgLineLength = 0.00;
		this.avgFormat = new DecimalFormat("0.00");
		formatFile(input);
		calculateFileData(input);

	}
	
	private void calculateFileData(String input) {
		
		try {
			FileReader reader = new FileReader(input);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while ((line = br.readLine()) != null) {
				numOfLines++;
				if (line.trim().isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					numOfLinesRemoved++;
				} else {
					words = line.split(" ");
					numOfWords += words.length;
					numOfChars += line.length();
				}
			}
			numOfLines -= numOfLinesRemoved;
			avgLineWords = (double) numOfWords / numOfLines;
			avgLineLength = (double) numOfChars / numOfLines;
			br.close();
		}
		catch(Exception e2) {

		}
	}
	
	public void formatFile(String input) {
		String line = "";
		int count = 0;
		try {
			FileReader reader = new FileReader(input);
			BufferedReader br = new BufferedReader(reader);
			FileWriter writer = new FileWriter("output.txt");
			PrintWriter outputFile = new PrintWriter(writer);
			int limit = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if(line.length() == 0) {
					
				}
				else if(limit == 0 && line.length() > 80 && line.indexOf(" ") == -1) {
					outputFile.println(line);
				}
				else if(limit > 0 && line.length() > 80 && line.indexOf(" ") == -1) {
					outputFile.println();
					outputFile.println(line);
					limit = 0;
				}
				else {
					words = line.split(" ");
					for(int i = 0; i < words.length; i++) {
						if(limit == 0) {
							outputFile.print(words[i]);
							limit = words[i].length();
						}
						else if(limit+words[i].length()+1 <= 80) {
							outputFile.print(" "+words[i]);
							limit += words[i].length()+1;
						}
						else {
							outputFile.println();
							outputFile.print(words[i]);
							limit = words[i].length();
						}
					}
				}
			}
			br.close();
			outputFile.close();
		}
		catch(Exception e2) {}
	}
	
	public int getNumOfLines() {
		return numOfLines;
	}
	
	public int getNumOfWords() {
		return numOfWords;
	}
	
	public int getNumOfLinesRemoved() {
		return numOfLinesRemoved;
	}
	
	public String getAvgLineWords() {
		return avgFormat.format(avgLineWords);
	}
	
	public String getAvgLineLength() {
		return avgFormat.format(avgLineLength);
	}
	
}