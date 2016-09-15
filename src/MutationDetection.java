import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MutationDetection {
	static InputSequence seq1;
	static InputSequence seq2;
	static boolean forward, direction5;
	
	public static void main(String[] args){
		int repeat = 0;
		while (repeat < 2){ // must have no errors in both checks
			repeat = 0;
			// Read in sequences from .txt files
			repeat += getInputSequences();
			// Check whether the length of two sequences are the same
			repeat += checkLengths();
			
			if (repeat < 2){
				System.out.println("Try again? (Y/N)");
				
				Scanner in = new Scanner(System.in);
				char moveOn = Character.toLowerCase(in.nextLine().charAt(0));
				if (moveOn == 'y'){}
				else System.exit(0);
			}
		}
		
		// Transcription: manipulate sequences based on info from user. Forward/Reverse. 5' or 3'. Change T for U.
		getTranscriptionInfo();
		seq1.createTranscript(forward, direction5);
		seq2.createTranscript(forward, direction5);
		
		System.out.println(seq1.getTranscript() + " " + seq2.getTranscript());

		// Translation: generate protein sequence
		seq1.translate();
		seq2.translate();
		
		System.out.println(seq1.getProteinSeq() + " " + seq2.getProteinSeq());
		
		// Compare nucleotide and protein sequences for mismatches. Indicate number of mismatches.
		
	}
	
	public static void getTranscriptionInfo(){
		Scanner in = new Scanner(System.in);
		int input;
		boolean repeat = true;
		
		// prompt user for option: forward/reverse
		while (repeat){
			System.out.println("Do you want the forward or reverse transcript? Enter 0 for forward, or 1 for reverse.");
			input = in.nextInt();
			if (input == 0) {
				forward = true;
				repeat = false;
			}
			else if (input == 1) {
				forward = false;
				repeat = false;
			}
			else {
				System.out.println("Invalid input. Must be 0 or 1. Please try again");
			}
		}
		
		repeat = true;
		// prompt user for option: 5' or 3'
		while (repeat){
			System.out.println("Do you want the transcript from 5' or 3'? Enter 0 for 5' or 1 for 3'");
			input = in.nextInt();
			if (input == 0) {
				direction5 = true;
				repeat = false;
			}
			else if (input == 1) {
				direction5 = false;
				repeat = false;
			}
			else {
				System.out.println("Invalid input. Must be 0 or 1. Please try again");
			}
		}
		
	}
	
	public static int checkLengths(){
		if (seq1.getLength() == seq2.getLength()) return 1;
		else{
			System.out.println("The length of the two input sequences must be equal.");
			System.out.println("\"" + seq1.getFileName() + "\" has sequence length of " + seq1.getLength());
			System.out.println("\"" + seq2.getFileName() + "\" has sequence length of " + seq2.getLength());
			return 0;
		}
		
	}
	
	public static int getInputSequences(){
		Scanner in = new Scanner(System.in);
		
		String sequence = "";
		String fileName1 = "";
		String fileName2 = "";
		boolean proceed = false;
		
		while (!proceed){
			// get first input
			System.out.println("Enter the name of the first sequence input file: ");
			fileName1 = in.nextLine();
			// get second input
			System.out.println("Enter the name of the second sequence input file: ");
			fileName2 = in.nextLine();

			System.out.println("First file: \"" + fileName1 + "\", second file: \"" + fileName2 + "\". Continue? (Y/N?)");
			char moveOn = Character.toLowerCase(in.nextLine().charAt(0));
			if (moveOn == 'y'){
				proceed = true;
			}
		}
		
		// create the inputSequences
		seq1 = new InputSequence(fileName1); 
		seq2 = new InputSequence(fileName2);
		
		seq1.extractSequence();
		seq2.extractSequence();
		if (seq1.getError() || seq2.getError()) return 0;		
		
		seq1.findLength();
		seq2.findLength();
		
		return 1; // return 1 if everything went smoothly
	}

}
