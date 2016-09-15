import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// Takes the fileName that as input and reads file to find sequence
// finds sequence length
public class InputSequence {
	private String fileName;
	private String sequence;
	private int length;
	private boolean error = false;
	private String mRNA;
	private String proteinSeq;
	
	// constructor
	public InputSequence(String fileName){
		this.fileName = fileName;
	}
	
	// accessor method
	public String getFileName(){
		return fileName;
	}
	
	public String getSequence(){
		return sequence;
	}
	
	public int getLength(){
		return length;
	}
	
	public boolean getError(){
		return error;
	}
	
	public String getTranscript(){
		return mRNA;
	}
	
	public String getProteinSeq(){
		return proteinSeq;
	}
	
	// get the sequences from the input file
	public void extractSequence(){
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(fileName));
		
			StringBuilder sb = new StringBuilder();
			String line;
		
			line = br.readLine(); // leave the first line alone
			line = br.readLine(); // get only the second line

			while (line != null){
				sb.append(line);
		        line = br.readLine();
			}

			sequence = sb.toString();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file \"" + fileName + "\" was not found. Please try again.");
			error = true;
		} 
		catch (IOException e) {
			System.out.println("Error with reading file: \"" + fileName + "\". Please try again.");
			error = true;
		}
	}
	
	public void findLength(){
		length = sequence.length();
	}
	
	// create transcript
	public void createTranscript(boolean forward, boolean direction5){
		StringBuilder tempSeq = new StringBuilder(length);
		StringBuilder reverseSeq = new StringBuilder(length);
		StringBuilder orientedTempSeq = new StringBuilder(length);
		StringBuilder transcript = new StringBuilder(length);
		char nucleotide;
		
		System.out.println(sequence);
		
		// make the DNA either forward or reverse
		if (forward){
			tempSeq.append(sequence);
		}
		
		else {
			for (int i = length-1; i >= 0; i--){
				nucleotide = sequence.charAt(i);
				reverseSeq.append(changeNucleotide(nucleotide));
			}
			tempSeq.append(reverseSeq);
		}
		
		// turn DNA 3' direction if direction5 is false
		if (direction5){
			orientedTempSeq.append(tempSeq); 
		}
		else{
			for (int x = length-1; x >=0; x--){
				orientedTempSeq.append(tempSeq.charAt(x));
			}
		}

		// change T to U
		for (int y = 0; y < length; y++){
			nucleotide = orientedTempSeq.charAt(y); 
			//System.out.println(nucleotide);
			if (nucleotide == 'T'){
				transcript.append('U');
				//System.out.println("changed T to U");
			}
			else{
				transcript.append(nucleotide);//changeNucleotide(nucleotide));
				//System.out.println("here");
			}
		}
		
		mRNA = transcript.toString();
	}
	
	private char changeNucleotide(char input){
		switch (input){
			case 'A': return 'T';
			case 'T': return 'A';
			case 'C': return 'G';
			case 'G': return 'C';
		}
		return 'z';
	}
	
	public void translate(){
		AminoAcidMap map = new AminoAcidMap();
		HashMap<String, String> aaMap = map.getAAMap();
		
		String codon = "";
		String aa = "";
		StringBuilder tempProtein = new StringBuilder(length);
		
		for (int i = 0; i < length-3; i+=3){
			codon = mRNA.substring(i, i+3);
			aa = aaMap.get(codon);
			tempProtein.append(aa);
			
			//System.out.println("codon = " + codon);
			//System.out.println("aa = " + aa);
			//System.out.println("tempProtein = " + tempProtein);
		}
		
		proteinSeq = tempProtein.toString();
	}
}
