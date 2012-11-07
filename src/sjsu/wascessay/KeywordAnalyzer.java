package sjsu.wascessay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import sjsu.wascessay.PrefixTree.Node;

/**
 * A KeywordAnalyzer that is capable of performing keyword searches on an
 * ArrayList<String> based on various criteria and assigning rubric scores to 
 * each string of text
 * @author Michael Riha
 */
public class KeywordAnalyzer 
{
    private PrefixTree keywords;

    /**
     * Reads keywords.txt in CWD and builds a prefix tree from it. The expected 
     * formatting for each line, one entry per line, is: keyword,weight,rubric
     * CSV values with no spacing. E.g. Critical,2,1
     */
    public KeywordAnalyzer() throws FileNotFoundException, IOException
    {
        keywords = new PrefixTree();
        BufferedReader br = new BufferedReader(new FileReader("keywords.txt"));
        try 
        {
            String[] line = br.readLine().split(",");
            keywords.add(line[0], Integer.valueOf(line[1]), Integer.valueOf(line[2]));
            
            while (line != null)
            {
                line = br.readLine().split(",");
            }
        } finally 
        {
            br.close();
        }
    }
    
    /**
     * Calculates all rubric scores for an arbitrary amount of ASCII text
     * @param words the text to analyze
     * @return an array of scores between 0.0 and 4.0. Index 0 contains the
     * total score, and 1-5 contain those pertaining to the 5 proficiencies
     * in the order given
     */
    public double[] calculateRubricScores(ArrayList<String> words)
    {
        int totalWords = 0, weight = 0, rubric = 0;
        double[] scores = new double[6];       
        int[] totalWeights = new int[6]; // for separating  proficiencies 1-5
        int[] totalKeywords = new int[6]; // index 0 is not used
        
        for (String word : words)
        {
            Node values = keywords.find(word);
            if (values != null)
            {
                weight = values.getWeight();
                rubric = values.getRubric();
                totalWeights[rubric] += weight;
                totalKeywords[rubric] += 1;
            }
            ++totalWords;
        }
        return scores;
    }
    
}
