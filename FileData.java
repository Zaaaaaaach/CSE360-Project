import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.DecimalFormat;

public class FileData {
	
	private int numOfLines, numOfWords, numOfLinesRemoved;
	private double avgLineWords, avgLineLength;
	private File inputFile;
	private DecimalFormat avgFormat;
	
	public FileData(File inputFile) {
		this.inputFile = inputFile;
		this.numOfLines = 0;
		this.numOfWords = 0;
		this.numOfLinesRemoved = 0;
		this.avgLineWords = 0.00;
		this.avgLineLength = 0.00;
		this.avgFormat = new DecimalFormat("0.00");
		calculateFileData(inputFile);
	}
	
	private void calculateFileData(File inputFile) {
		try {
			FileReader reader = new FileReader(inputFile.getName());
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				numOfLines++;
				numOfWords += words.length;
			}
			br.close();
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
		return avgFormat.format(avgLineLength);
	}
	
	public String getAvgLineLength() {
		return avgFormat.format(avgLineLength);
	}
	
}
