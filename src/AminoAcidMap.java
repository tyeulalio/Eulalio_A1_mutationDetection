import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// create the amino acid map to reference for translation
public class AminoAcidMap {
	private HashMap<String, String> aminoAcids = new HashMap<String, String>();
	
	// contructor
	public AminoAcidMap(){
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader("aminoAcid.txt"));
		
			StringBuilder sb = new StringBuilder();
			String line;
			line = br.readLine();

			int comma, equal;
			String codon, aaCode;
			
			while (line != null){
				// line is read in and saved into String line
				// add each code into the hash table
				equal = line.indexOf('=');
				comma = line.indexOf(',');
				
				while (comma >= 0){
					codon = line.substring(0, 3);
					aaCode = line.substring(equal+2, line.length());
					
					aminoAcids.put(codon, aaCode);
					
					line = line.substring(comma+2, line.length());
					equal = line.indexOf('=');
					comma = line.indexOf(',');
				}
				
		        line = br.readLine();
			}

		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file \"aminoAcid.txt\" was not found. Please try again.");
			System.exit(0);
		} 
		catch (IOException e) {
			System.out.println("Error with reading file: \"aminoAcid.txt\". Please try again.");
			System.exit(0);
		}
	}

	// accessor method
    public HashMap<String, String> getAAMap() {
         return aminoAcids;
    }
}
