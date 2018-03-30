import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.DecimalFormat;

public class FileData {
	
	private int charPerLine, numOfLines, numOfWords, numOfChars, numOfLinesRemoved, numOfSpacesAdded;
	private double avgLineWords, avgLineLength;
	private DecimalFormat avgFormat;
	private String[] words;
	
	public FileData(String input) {
		this.charPerLine = 80;
		this.numOfLines = 0;
		this.numOfWords = 0;
		this.numOfLinesRemoved = 0;
		this.numOfSpacesAdded = 0;
		this.avgLineWords = 0.00;
		this.avgLineLength = 0.00;
		this.avgFormat = new DecimalFormat("0.00");
		calculateFileData(input);

	}
	
	public void calculateFileData(String input) {
		reset();
		try {
			FileReader reader = new FileReader(input);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			int totalLength, lengthExSpaces;
			while ((line = br.readLine()) != null) {
				totalLength = line.length();
				lengthExSpaces = line.replaceAll(" ", "").length();
				numOfSpacesAdded += (totalLength - lengthExSpaces);
				line = line.trim();
				numOfLines++;
				if (line.trim().isEmpty() || line.trim().equals("") || line.trim().equals("\n")) {
					numOfLines--;
				} else {
					words = line.split(" ");
					numOfWords += words.length;
					numOfChars += line.length();
				}
			}
			avgLineWords = (double) numOfWords / numOfLines;
			avgLineLength = (double) numOfChars / numOfLines;
			br.close();
		}
		catch(Exception e2) {

		}
	}
	
	public void formatFile(String input, PrintWriter outputFile) {
		String line = "";
		try {
			FileReader reader = new FileReader(input);
			BufferedReader br = new BufferedReader(reader);
			int limit = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if(line.length() == 0) {
					numOfLinesRemoved++;
				}
				else if(limit == 0 && line.length() > charPerLine && line.indexOf(" ") == -1) {
					outputFile.println(line);
				}
				else if(limit > 0 && line.length() > charPerLine && line.indexOf(" ") == -1) {
					outputFile.println();
					outputFile.println(line);
					limit = 0;
				}
				else {
					words = line.split(" ");
					
					for(int i = 0; i < words.length; i++) {
						if(words[i].length() == 0) {
							
						}
						else if(limit == 0) {
							outputFile.print(words[i]);
							limit = words[i].length();
						}
						else if(limit+words[i].length()+1 <= charPerLine) {
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
	
	private void reset() {
		this.charPerLine = 80;
		this.numOfLines = 0;
		this.numOfWords = 0;
		this.numOfChars = 0;
		this.numOfSpacesAdded = 0;
		this.avgLineWords = 0.00;
		this.avgLineLength = 0.00;
	}
	
	public int getCharPerLine() {
		return charPerLine;
	}
	
	public void setCharPerLine(int input) {
		charPerLine = input;
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
	
	public int getNumOfSpacesAdded() {
		return numOfSpacesAdded;
	}
	
	public void setNumOfSpacesAdded(int num) {
		numOfSpacesAdded = num;
	}
	
	public String getAvgLineWords() {
		return avgFormat.format(avgLineWords);
	}
	
	public String getAvgLineLength() {
		return avgFormat.format(avgLineLength);
	}
	
}