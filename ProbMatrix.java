import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ProbMatrix {
	
	/**
	 * Generates a probability matrix where each entry A[x][y] is the probability that word x+1 is the predecessor of word y
	 * row 0 (x==0) represents when the line starts and is the probability. of a word being the first word in a sentence 
	 * first line of text file is the words delimited by a space
	 * 
	 * @param args one parameter, the name/path of input file
	 * @throws Exception for any issues with IO
	 */
	public static void main (String args[]) throws Exception {
		File file = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		BufferedWriter bw = new BufferedWriter(new FileWriter("probMat"+file));
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> freqMatrix = new ArrayList<ArrayList<Integer>>();
		freqMatrix.add(new ArrayList<Integer>());
		String sentence;
		while((sentence = br.readLine()) != null) {
			String prev = "";
			while(sentence.length()>0) {
				int space = sentence.indexOf(" ");
				//this makes sure that the sentence doesn't have any spaces anymore, and breaks up another word off the sentence with every loop entry
				String word = space != -1 ? sentence.substring(0, space) : sentence;
				sentence = space != -1 ? sentence.substring(space+1) : "";
				//makes sure that the word has not been documented yet, if it does, add it for the first time.
				if(!words.contains(word)) {
					words.add(word);
					freqMatrix.add(new ArrayList<Integer>());
					 
				}
				//gets index of every word and word before it, and adds one to the frequency of that occurring
				int index = words.indexOf(word);
				int prevIndex = prev.equals("") ? 0 : words.indexOf(prev)+1;
				freqMatrix = incrementFreq(freqMatrix, prevIndex, index);
				prev = word;
			}
		}
		//finds the maximum length of columns in the matrix
		int max = freqMatrix.get(0).size();
		for(ArrayList<Integer> x : freqMatrix) {
			if(x.size() > max) {
				max = x.size();
			}
		}
		//makes sure all rows have the same number of columns
		for(int i = 0; i < freqMatrix.size(); i++) {
			while(freqMatrix.get(i).size()<max) {
				freqMatrix.get(i).add(0);
			}
		}
		//converts the frequency matrix to a probability matrix
		ArrayList<ArrayList<Double>> probMatrix = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < freqMatrix.size(); i++) {
			probMatrix.add(new ArrayList<Double>());
			int sum = 0;
			for(int j : freqMatrix.get(i)) {
				sum += j;
			}
			if(sum != 0) {
				for(int j : freqMatrix.get(i)) {
					probMatrix.get(i).add((double)j/(double)sum);
				}
			}
			else {
				for(int j : freqMatrix.get(i)) {
					probMatrix.get(i).add((double)j);
				}
			}
		}
		
		//writes matrix into a text file
		for(String w : words) {
			bw.write(w + " ");
		}
		bw.write("\n");
		for(ArrayList<Double> x : probMatrix) {
			for(double num : x) {
				bw.write(num + " ");
			}
			bw.write("\n");
		}
		 
		bw.close();
		br.close();
	}
	/**
	 * increments frequency of instance that the word in prevIndex comes before the word in index.
	 * 
	 * @param freqMatrix the frequency matrix before incrementing
	 * @param prevIndex the index of the preceeding word
	 * @param index the index of the word
	 * @return the frequency matrix after incrementing entry A[prevIndex][index] by one
	 */
	public static ArrayList<ArrayList<Integer>> incrementFreq(ArrayList<ArrayList<Integer>> freqMatrix, int prevIndex, int index){
		while(freqMatrix.get(prevIndex).size() <= index) {
			freqMatrix.get(prevIndex).add(0);
		}
		freqMatrix.get(prevIndex).set(index, freqMatrix.get(prevIndex).get(index)+1);
		return freqMatrix;
	}
}
