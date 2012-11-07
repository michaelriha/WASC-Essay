package sjsu.wascessay;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Michael
 */
public class PrefixTreeTest {
    
    public PrefixTreeTest() 
    {
    }

    /**
     * Test of addWord and find methods in PrefixTree class
     */
    @Test
    public void testAddWord() {
        System.out.println("addWord");
        
        String[] words = new String[]{"This", "Critical", "Task", "Is", "The",
            "Necessary", "Thing", "That", "We",  "Must", "Do", "Zzz", "Zzyzyx"
        };

        Integer[] weights = new Integer[]{1, 2, 2, 1, 1, 1, 2, 1,
            2, 1, 2, 1, 1
        };
        
        PrefixTree instance = new PrefixTree();
        for (int i = 0; i < words.length; ++i)
        {
            instance.add(words[i], weights[i], 1);
        }
//        TODO: FIX THESE TESTS TO WORK WITH NODE INSTEAD OF INT[]
//        for (int i = 0; i < words.length; ++i)
//        {
//            assertArrayEquals(new Integer[]{weights[i], 1}, instance.find(words[i]));
//        }
//        assertArrayEquals(null, instance.find("Tas"));
//        assertArrayEquals(null, instance.find("Notintree"));
//        assertArrayEquals(new Integer[]{2,1}, instance.find("Critically"));
//        
//        instance.add("Testword", 2, 3);
//        assertArrayEquals(new Integer[]{2,3}, instance.find("Testword"));
//        assertArrayEquals(new Integer[]{2,3}, instance.find("Testwordly"));
    }
}
