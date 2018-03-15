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