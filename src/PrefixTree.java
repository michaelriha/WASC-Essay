/**
 * A PrefixTree to store keywords. Keywords have their corresponding weights
 * stored in the leaves of the tree.
 * 
 * @author Michael Riha
 */
public class PrefixTree
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
     * Constructs an empty PrefixTree
     */
    public PrefixTree()
    {
        root = new Node();
    }
    
    /**
     * Adds a word to the PrefixTree. Does not allow duplicates or words whose 
     * prefix is already in the tree.
     * @param word the word to add
     * @param weight the weight of the word
     * @return true if the word was added, false if it was already in the tree
     */
    public boolean addWord(String word, double weight)
    {
        Node cur = root;
        int next_idx;
        word = word.toLowerCase();
        
        for (int i = 0; i < word.length(); ++i)
        {
            next_idx = (int) (word.charAt(i) - 'a');
            
            // add node that wasn't there unless this node is already weighted
            if (cur.children[next_idx] == null)
                if (cur.weight == 0.0)
                    cur.children[next_idx] = new Node();
                else return false;
            
            cur = cur.children[next_idx]; // descend the tree
        }
        
        if (cur.weight == 0.0) // reached a leaf so set the weight
        {
            cur.weight = weight;
            return true;
        }
        else return false; // already in the tree           
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
