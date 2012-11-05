/**
 * An AlphabetTree to store words that will later be used for text parsing. 
 * Each level has a branching factor of 26 possible letters
 * The only values stored in the tree are at the leaf nodes, since the
 * "letter pointers" are implicit (a-z map to 0-25)
 * 
 * @author Michael Riha
 */
public class AlphabetTree 
{
    private Node root;
    
    /**
     * A node in the tree which has up to 26 children and a weighted value
     * If the weight is 0, then the node is not a leaf on the tree
     */
    private class Node
    {
        private double weight;
        private Node[] children;
        
        private Node()
        {
            weight = 0.0;
            children = new Node[26];
        }
    }
    
    /**
     * Constructs an empty AlphabetTree
     */
    public AlphabetTree()
    {
        root = new Node();
    }
    
    /**
     * Adds a word to the AlphabetTree
     * @param word the word to add
     * @param weight the weight of the word
     */
    public void addWord(String word, double weight)
    {
        Node cur = root;
        int next_idx;
        word = word.toLowerCase();
        
        for (int i = 0; i < word.length(); ++i)
        {
            next_idx = (int) (word.charAt(i) - 'a');
            
            if (cur.children[next_idx] == null) // add node that wasn't there
                cur.children[next_idx] = new Node();
            
            cur = cur.children[next_idx]; // descend the tree
        }
        cur.weight = weight; // reached a leaf so add the weight
    }
    
    /**
     * Get the weight of a word in the tree, if it exists, including wildcards
     * E.g. if "critic" is in the tree, "critically" will map to that
     * @param word the word to look for
     * @return the weight of the word parameter
     */
    public double getWeight(String word)
    {
        Node cur = root;
        Node next;
        word = word.toLowerCase();
        
        for (int i = 0; i < word.length(); ++i)
        {
            if (cur.weight > 0.1) // "wildcard" found
                return cur.weight;
            
            next = cur.children[(int) word.charAt(i) - 'a'];
            
            if (next == null) // word not in tree
                return 0.0;
            
            cur = next; // descend the tree
        }
        return cur.weight;
    }   
}
