package sjsu.wascessay;

import static org.junit.Assert.*;
import org.junit.Test;
import sjsu.wascessay.PrefixTree.Node;

/**
 * @author Michael Riha
 */
public class PrefixTreeTest {
    
    public PrefixTreeTest() 
    {
    }
    /**
     * Test of addWord and find methods in PrefixTree class
     */
    @Test
    public void testAddAndFind() {
        System.out.println("add & find");
        
        String[] words = new String[]{"This", "Critical", "Task", "Is", "The",
            "Necessary", "Thing", "That", "We",  "Must", "Do", "Zzz", "Zzyzyx"
        };

        Integer[] weights = new Integer[]{1, 2, 2, 1, 1, 1, 2, 1,
            2, 1, 2, 1, 1
        };
        
        Integer[] rubrics = new Integer[]{1, 2, 3, 4, 5, 4, 3, 2,
            1, 4, 2, 4, 3
        };
        
        // insert the words and weights, then make sure they were stored right
        PrefixTree instance = new PrefixTree();
        for (int i = 0; i < words.length; ++i)
        {
            instance.add(words[i], weights[i], rubrics[i]);
        }
        
        Node values;
        for (int i = 0; i < words.length; ++i)
        {
            System.out.println("Inserting " + words[i]);
            values = instance.find(words[i]);
            assertEquals((int)weights[i], values.getWeight());
        }
        
        // make sure Tas does not match for Task, but Tasks does (wildcard)
        assertEquals(null, instance.find("Tas"));
        assertEquals(2, instance.find("Tasks").getWeight());
        // longer wildcard + value which has no matching letters
        assertEquals(null, instance.find("Znotintree"));
        assertEquals(2, instance.find("Critically").getWeight());
    }
}
