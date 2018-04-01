package assignment3;

import java.util.ArrayList;
import java.util.Set;

/**
 * Ladder class creates the head of a graph using the {@link Node} class that will contain and methods to solve a word ladder problem.
 * This ladder contains the methods to solve the word ladder both recursively (DFS: {@link #findLadderDFS()}) and
 * using a priority queue (BFS: {@link #findLadderBFS()}).
 * @author ZachJ
 */
public class Ladder {

    // Head of the graph
    private Node head;

    //Start word of word ladder
    private String start;

    //End word of word ladder
    private String end;

    // Contains all dictionary words that were passed in via a Set
    private ArrayList<String> words;

    // Priority queue for BFS
    private ArrayList<Node> queue;

    /**
     * Takes in a dictionary Set (all unique Strings) and creates an ArrayList out of it so that it is easier to manage. The beginning
     * and end of the word ladder are passed in and are set as the member's variables and a new head node is created.
     * @param dict is a Set object that contains all unique Strings
     * @param start is a String that is the beginning of a word ladder
     * @param end is a String that is the end of a word ladder
     * @see Node
     */
    public Ladder(Set<String> dict, String start, String end){
        this.start = start;
        this.end = end;
        words = new ArrayList<>(dict);
        head = new Node(start);
    }

    /**
     * Implementation of the Breadth First Search using a priority queue. To reduce complexity, the graph of nodes is built layer by layer
     * instead of all at once. Worst case complexity is O(n^2) due to the searching of all elements on the words list for a match for each element
     * The queue is initialized with the head pointer, and inside the loop the queue is popped to see if it matches the end word. If not, the popped node
     * has its child nodes populated to create the next layer in the tree and the words used are removed from circulation to be rid of duplicates.
     * When the node that is popped is the end word, it will return via reverse tree traversal using the nodes back pointer and populate an ArrayList with the
     * nodes String value, thus creating the return array and guaranteeing the shortest ladder. If no ladder is found, the queue will reach zero and deliver and
     * empty array filled with the start and end word.
     * @return an ArrayList of the word ladder found
     */
    public ArrayList<String> findLadderBFS(){
        //Create a queue and populate it with the head
        queue = new ArrayList<>();
        queue.add(head);
        Node ptr;

        //Proceed layer by layer down the tree until the queue is empty
        while (!queue.isEmpty()){
            ptr = queue.remove(0);
            //Check if the popped node contains the end word
            if (!ptr.getWord().equals(end)){
                words.remove(ptr.getWord());        //If not, remove the word from circulation to disallow duplication
                ptr.fillWithMatches(words, end);    //Fill child nodes with new matches
                for (Node node: ptr.getNodes()){
                    words.remove(node.getWord());
                    queue.add(node);
                }
            }
            //Word is found and time to build up the return Arraylist by traversing backwards up the tree
            else {
                ArrayList<String> wordLadder = new ArrayList<>();
                while (ptr.getWord().equals(start)){
                    wordLadder.add(0, ptr.getWord().toLowerCase());
                    ptr = ptr.getBack();
                }
                wordLadder.add(0, ptr.getWord().toLowerCase());
                return wordLadder;
            }
        }
        //If this is reached, no word ladder is found and an ArrayList is created to hold the first and last
        ArrayList<String> noLadder = new ArrayList<>();
        noLadder.add(start.toLowerCase());
        noLadder.add(end.toLowerCase());
        return noLadder;
    }

    /**
     * Helper function to build an entire tree for DFS search. Implements a similar graph build as BFS
     */
    private void buildTree(){
        queue = new ArrayList<>();
        queue.add(head);
        Node ptr;
        while(!queue.isEmpty()){
            ptr = queue.remove(0);
            words.remove(ptr.getWord());
            ptr.fillWithMatches(words, end);
            for (Node node: ptr.getNodes()){
                words.remove(node.getWord());
                queue.add(node);
            }
        }
    }

    /**
     * Implements a Depth first search of a word ladder graph. The graph is first built entirely and then traversed recursively using a helper
     * function. The words are traversed using a stack, where the last word in is the first out is the word is not found and the function recurses
     * backwards. If the word is found, the stack is untouched and returned containing the ladder. Worst case complexity is O(n^2).
     * @return
     */
    public ArrayList<String> findLadderDFS(){
        buildTree();
        ArrayList<String> stack = new ArrayList<>();
        ArrayList<String> noLadder = new ArrayList<>();
        noLadder.add(start.toLowerCase());
        noLadder.add(end.toLowerCase());
        for (int i=0; i<stack.size(); i++){
            stack.set(i, stack.get(i).toLowerCase());
        }
        return findLadderDFSHelper(head, stack) ? stack : noLadder;
    }

    /**
     * Recursive helper function. Tree will recurse until the node's pointer list is empty which wither occurs due to a word having no matches
     * or if the tree has been thoroughly recursed and the nodes removed so that the same path isn't taken again
     * @param ptr is the node that was a child and becomes the parent node
     * @param stack is the ArrayList that contains the Strings that will become the returned word ladder
     * @return a boolean if the word ladder is found.
     */
    private boolean findLadderDFSHelper(Node ptr, ArrayList<String> stack){
        stack.add(ptr.getWord());
        //If word is found, return true and recurse upwards
        if (ptr.getWord().equals(end)){
            return true;
        }
        //If parent's child nodes are empty, pop the recent word off the stack and return false
        if (ptr.getNodes().isEmpty()){
            stack.remove(stack.size()-1);
            return false;
        }
        //If the returned value is false, remove the parent's first child node, pop the word off the stack, and start again.
        if(!findLadderDFSHelper(ptr.getNodes().get(0), stack)){
            stack.remove(stack.size()-1);
            ptr.getNodes().remove(0);
            return findLadderDFSHelper(ptr, stack);
        }
        return true;
    }

}
