package assignment3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Node class is similar to search tree, but with a variable amount of nodes attached to it and a node that
 * allows reverse traversal. The nodes that the self points to are stored in an ArrayList. Each node contains
 * a String value and all of the nodes that branch off contain a string that differs by its parent by one letter.
 * The {@link Ladder} class does not allow duplicate words stored in subtrees.
 * @author ZachJ
 */

public class Node {

    //Defines the node
    private String word;

    //Used for reverse tree reversal (BFS)
    private Node back;

    //Holds all nodes
    private ArrayList<Node> nodes;

    /**
     * Takes in a String, sets the back pointer to null and creates an empty array list for pointer storage. And node that is directly
     * associated with this node will only be other strings that differ by one letter i.e. hello, cello
     * @param word is a String that a node holds.
     */
    public Node(String word){
        this.word = word;
        this.back = null;
        nodes = new ArrayList<>();
    }

    /**
     * Searches the dictionary for any word that differs by the object's word by one letter and creates a new node that is stored in the object's
     * array list. Also sets the back pointer of these new nodes to the current object's pointer. Furthermore, once all the words are discovered and added,
     * the function will reorganize them such that the words that are most similar to the end word are pushed to the front of the list. If the
     * end word is HELLO, and the words are CELLS, HELLS, BELLS they will be reorganized to HELLS, BELLS, CELLS.
     * @param dict is the dictionary that contains all the x letter words. Either by all 5 letter or all 6 letter etc..
     * @param end is the end of the word ladder and is used to create a priority of the nodes for shorter graph traversals
     */
    public void fillWithMatches(ArrayList<String> dict, String end){
        for (String s: dict){
            if (oneLetterDif(s)){
                nodes.add(new Node(s));
                nodes.get(nodes.size()-1).back = this;
            }
        }
        reorganize(end);
    }

    /**
     * Defines a new comparator that compares two nodes by the String value they hold based on how many letter match the end word i.e.
     * if end is HELLO and one i comparing HELLS and CELLS, HELLS contains 4 letters that match HELLO whereas CELLS only contains 3. HELLO will
     * ranked lower in the queue since it is a FIFO
     * @param end is the last word in the word ladder and is what determines a strings ranking
     */
    private void reorganize(String end){
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                int count1 = 0;
                int count2 = 0;
                for (int i = 0; i < o1.getWord().length(); i++){
                    if (o1.getWord().charAt(i) == end.charAt(i)){
                        count1++;
                    }
                    if (o2.getWord().charAt(i) == end.charAt(i)){
                        count2++;
                    }
                }
                return - (count1 - count2);
            }
        };
        nodes.sort(comparator);
    }

    /**
     * Determines if a word is one letter different from the object's word i.e HELLO and CELLO. Assumes all words passed in are of similar length
     * to object's word length
     * @param compare is the word to compare to the object's word
     * @return a boolean if the String compare is one letter different
     */
    private boolean oneLetterDif(String compare){
        int count = 0;
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) != compare.charAt(i)){
                count++;
            }
        }
        return count == 1;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getBack() {
        return back;
    }

}
