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
	
	public FileData(File input) {
		this.numOfLines = 0;
		this.numOfWords = 0;
		this.numOfChars = 0;
		this.numOfLinesRemoved = 0;
		this.avgLineWords = 0.00;
		this.avgLineLength = 0.00;
		this.avgFormat = new DecimalFormat("0.00");
		formatFile(input);
		calculateFileData(input);
	}
	
	private void calculateFileData(File input) {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(input.getName()));
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
			avgLineWords = numOfWords / numOfLines;
			avgLineLength = numOfChars / numOfLines;
			br.close();
		}
		catch(Exception e2) {}
	}
	
	public void formatFile(File input) {
		String line = "";
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(input.getName()));
			PrintWriter outputFile = new PrintWriter(new FileWriter("output.txt"));
			for (int i = 0; i < words.length; i++) {
				if (count + words.length <= 80) {
					outputFile.print(words[i] + " ");
					count += words[i].length() + 2;
				} else {
					outputFile.print(words[i] + "\n");
					count = words[i].length();
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
