import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Markovian {

	/**
	 * Processes the probability matrix and prints out the Markovian chain
	 * 
	 * @param args one command line argument, input text file name/path
	 * @throws Exception throws exception if encounters issues with IO
	 */
	public static void main(String[] args) throws Exception {
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		//This reads the probability matrix that is generated for the file.
		ArrayList<ArrayList<Double>> probMatrix = new ArrayList<ArrayList<Double>>();
		ArrayList<String> words = new ArrayList<String>();
		String line = br.readLine();
		while(line.length() > 1) {
			int spaceIndex = line.indexOf(" ");
			words.add(line.substring(0, spaceIndex));
			line = line.substring(spaceIndex+1);
		}
		while((line = br.readLine()) != null) {
			ArrayList<Double> word = new ArrayList<Double>();
			while(line.length() > 1) {
				int spaceIndex = line.indexOf(" ");
				word.add(Double.parseDouble(line.substring(0, spaceIndex)));
				line = line.substring(spaceIndex+1);
			}
			probMatrix.add(word);
		}
		boolean endOfSentence = false;
		int state = 0;
		//runs until probabilistically the end of the sentence has been reached
		while(!endOfSentence) {
			double rand = Math.random();
			double sum = 0;
			for(int i = 0; i < probMatrix.get(0).size(); i++) {
				sum += probMatrix.get(state).get(i);
				if(sum >= rand) {
					System.out.print(words.get(i) + " ");
					state = i+1;
					break;
				}
				if(sum == 0.0 && i == probMatrix.get(0).size()-1) {
					endOfSentence = true;
				}
			}
		}
		br.close();
	}

}
