/**
 * An AlphabetTree to store words that will later be used for text parsing. 
 * Each level has a branching factor of 26 possible letters
 * @author Michael Riha
 */
public class AlphabetTree 
{
    private Node root;
    
    private class Node
    {
        private double weight; // non-zero weight means a leaf node
        private Node[] pointers;
        
        private Node()
        {
            weight = 0.0;
            pointers = new Node[26];
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
            
            if (cur.pointers[next_idx] == null) // add node that wasn't there
                cur.pointers[next_idx] = new Node();
            
            cur = cur.pointers[next_idx]; // descend the tree
        }
        cur.weight = weight; // reached a leaf so add the weight
    }
    
    /**
     * Get the weight of a word in the tree, if it exists
     * @param word the word to look for
     * @return the weight of the word parameter
     */
    public double getWeight(String word)
    {
        Node cur = root;
        Node next_ptr;
        word = word.toLowerCase();
        
        for (int i = 0; i < word.length(); ++i)
        {
            if (cur.weight > 0.1) // "wildcard" found
                return cur.weight;
            
            next_ptr = cur.pointers[(int) word.charAt(i) - 'a'];
            
            if (next_ptr == null) // word not in tree
                return 0.0;
            
            cur = next_ptr; // descend the tree
        }
        return cur.weight;
    }   
}
