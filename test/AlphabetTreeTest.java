import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Michael
 */
public class AlphabetTreeTest {
    
    public AlphabetTreeTest() 
    {
    }

    /**
     * Test of addWord method, of class AlphabetTree.
     */
    @Test
    public void testAddWord() {
        System.out.println("addWord");
        
        String[] words = new String[]{"This", "Critical", "Task", "Is", "The",
            "Necessary", "Thing", "That", "We",  "Must", "Do", "Zzz", "Zzyzyx"
        };

        Double[] weights = new Double[]{1.0, 2.7, 3.4, 1.6, 1.1, 4.0, 3.2, 1.1,
            3.0, 3.7, 3.8, 1.0, 4.0
        };
        
        AlphabetTree instance = new AlphabetTree();
        for (int i = 0; i < words.length; ++i)
        {            
            instance.addWord(words[i], weights[i]);
        }
        
        for (int i = 0; i < words.length; ++i)
        {
            assertEquals(weights[i], instance.getWeight(words[i]), 0.01);
        }
        
        assertEquals(0.0, instance.getWeight("Tas"), 0.01);
        assertEquals(0.0, instance.getWeight("Notintree"), 0.01);
        assertEquals(2.7, instance.getWeight("Critically"), 0.01);
    }
}
