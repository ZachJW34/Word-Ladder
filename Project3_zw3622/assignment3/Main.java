package assignment3;


import java.util.*;
import java.io.*;

// Word ladder
/*
 * EE422C Project 3 submission by
 * Zachary Williams
 * zw3622
 * 15470
 * Spring 2017
 * Slip days used:
 */

public class Main {

    public Scanner kb;
    public PrintStream ps;

	public static void main(String[] args) throws Exception {

        Scanner kb;	// input Scanner for commands
        PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
        ArrayList<String> firstAndLast = parse(kb); //Get array list of start and end inputted words
        //If the array is empty, quit was inputted and the program should end.
        if (!firstAndLast.isEmpty()){
            ArrayList<String> ladderDFS = getWordLadderDFS(firstAndLast.get(0),
                    firstAndLast.get(1));

            printLadder(ladderDFS);
            ArrayList<String> ladderBFS = getWordLadderBFS(firstAndLast.get(0),
                    firstAndLast.get(1));
            printLadder(ladderBFS);
        }
	}

    /**
     * Unused
     */
	public static void initialize() {

	}
	
	/**
     * Takes two arguments i.e. the start and end of a word ladder that should be searched Note: If command is /quit, return empty ArrayList.
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
	    System.out.print("Enter the start word: ");
        ArrayList<String> firstAndLast = new ArrayList<>();
	    String startWord = keyboard.next();
	    if (startWord.equals("/quit"))
	        return firstAndLast;
	    System.out.print("\nEnter the end word: ");
	    String endWord = keyboard.next();
	    if (startWord.length() != endWord.length() || startWord.isEmpty())
	        return firstAndLast;
	    firstAndLast.add(startWord);
	    firstAndLast.add(endWord);
	    return firstAndLast;
	}

    /**
     * Calling of the Depth First Search word ladder algorithm based upon the two words passed in.
     * @param start is the top of the word ladder
     * @param end is the bottom of the word ladder
     * @return an array list with the word ladder found or just the start and end is none is found
     * @see Ladder
     */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Set<String> dict = makeDictionary();
		Ladder ladder = new Ladder(dict, start.toUpperCase(), end.toUpperCase());
		return ladder.findLadderDFS();
	}

    /**
     * Calling of the Breadth First Search word ladder algorithm based upon the two words passed in.
     * @param start is the top of the word ladder
     * @param end is the bottom of the word ladder
     * @return an array list with the word ladder found or just the start and end is none is found
     * @see Ladder
     */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Set<String> dict = makeDictionary();
		Ladder ladderBFS = new Ladder(dict, start.toUpperCase(), end.toUpperCase());
		return ladderBFS.findLadderBFS();
	}

    /**
     * Print the ladder returned from a word ladder search. Handles whether a word ladder was found or not
     * @param ladder is the word ladder between the two entered words
     */
	public static void printLadder(ArrayList<String> ladder) {
        if (ladder.size() == 2){
            System.out.println("no word ladder can be found between " +
                    ladder.get(0) + " and " + ladder.get(ladder.size()-1) + ".");
        } else{
            System.out.println("a " + ladder.size() + "-rung word ladder exists between " +
                    ladder.get(0) + " and " + ladder.get(ladder.size()-1) + ".");
            for (String s: ladder){
                System.out.println(s);
            }
        }
	}

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}

}
