import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.DecimalFormat;

public class FileData {
	
	private int numOfLines, numOfWords, numOfLinesRemoved;
	private double avgLineWords, avgLineLength;
	private DecimalFormat avgFormat;
	
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
			while((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				numOfLines++;
				numOfWords += words.length;
			}
			br.close();
		}
		catch(Exception e2) {

			System.out.println("{0} exception caught."+ e2);
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
		return avgFormat.format(avgLineLength);
	}
	
	public String getAvgLineLength() {
		return avgFormat.format(avgLineLength);
	}
	
}
