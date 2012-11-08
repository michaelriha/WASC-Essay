package sjsu.wascessay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import sjsu.wascessay.PrefixTree.Node;

/**
 * A KeywordAnalyzer that is capable of performing keyword searches on an
 * ArrayList<String> based on various criteria and assigning rubric scores to 
 * each string of text
 * @author Michael Riha
 */
public class KeywordAnalyzer 
{
    private SortedSet<String>[][] keywordsUsed;
    private PrefixTree keywordTree;
    private int[][] wordCounts;
    private double[] scores;
    private int totalWords;
    
    private static final int RUBRICS = 5, WEIGHTS = 2;
    private static final int a = 6, b = 1, c = 4;
    
    /**
     * Reads keywords.txt in CWD and builds a prefix tree from it. The expected 
     * formatting for each line, one entry per line, is: keyword,weight,rubric
     * CSV values with no spacing. E.g. Critical,2,1
     */
    public KeywordAnalyzer() throws FileNotFoundException, IOException
    {
        keywordsUsed = new SortedSet[RUBRICS][WEIGHTS];
        wordCounts = new int[RUBRICS][WEIGHTS];
        scores = new double[RUBRICS+1];
        totalWords = 0;
        
        // build prefix tree from keywords.txt
        keywordTree = new PrefixTree();
        BufferedReader br = new BufferedReader(new FileReader("keywords.txt"));
        try 
        {
            String[] line = br.readLine().split(",");
            keywordTree.add(line[0], Integer.valueOf(line[1]), 
                                                    Integer.valueOf(line[2]));
            while (line != null)
            {
                line = br.readLine().split(",");
                keywordTree.add(line[0], Integer.valueOf(line[1]), 
                                                    Integer.valueOf(line[2]));
            }
        } 
        finally { br.close(); }
    }
    
    /**
     * This function parses an arbitrary amount of text stored as 
     * ArrayList<String>. As it checks for each word in the tree, it stores the
     * number of times each keyword has appeared and how many total words there
     * are for later use. These word occurrence counts are cleared every time 
     * this function is called.Then, based on keyword statistics collected it 
     * assigns a score for each rubric. 
     * 
     * @param words the text to analyze
     * @return an array of scores between 0.0 and ?.0. Index 5 contains the
     * total score, and 0-4 contain those pertaining to the 5 proficiencies
     * in the order given
     */
    public double[] calculateScores(ArrayList<String> words)
    {
        // initialize and clear any leftover occurrence fields
        Node values;
        int rubric, weight;
        keywordTree.reset();
        
        // parse the input and collect wordcount statistics for calculation
        for (String word : words)
        {
            values = keywordTree.find(word);
            if (values != null)
            {
                rubric = values.getRubric();
                weight = values.getWeight();
                ++wordCounts[rubric - 1][weight - 1];
                keywordsUsed[rubric - 1][weight - 1].add(word);
            }
            ++totalWords;
        }         
        // calculate the scores, average the sum of the scores for the total
        double score, sum = 0.0;
        for (int i = 0; i < RUBRICS; ++i)
        {
            score = calculateScore(wordCounts[i][0], wordCounts[i][1], 
                                                                    totalWords);
            scores[i] = score;
            sum += score;            
        }
        scores[RUBRICS] = sum / RUBRICS;
        return scores;
    }
    
    /**
     * Calculates an individual rubric score based on the number of weight one
     * and two words as well as the total number of words in the document
     * 
     * @param weightOneCount the number of weight one words
     * @param weightTwoCount the number of weight two words
     * @param totalWordCount the total word count
     * @return a rubric score between 0.0 and ?.0 based on the formula
     * score= (N/a)*((1+d1)^b)*((1+dr)^c)
     * N: Total number of keywords
     * d1: Density of weight one words (weightOneCount / N)
     * d2: Density of weight two words (weightTwoCount / N)
     * dr: Density of related words (N / totalWordCount)
     */
    public double calculateScore(int weightOneCount, int weightTwoCount,
                                        int totalWordCount)
    {
        if (totalWordCount <= 0) 
            return 0.0;
        int N = weightOneCount + weightTwoCount;
        double dr = N / totalWordCount;
        double d1; //, d2;
        if (N == 0)
        {
            d1 = 0;
            //d2 = 0;
        }
        else
        {
            d1 = weightOneCount / N;
            //d2 = weightTwoCount / N;
        }        
        return Math.min(1, (N / a) * Math.pow(1 + d1, b) * Math.pow(1 + dr, c));
    }
    
   /**
    * Gets how many times the keyword was found in the text
    * @param keyword the word to look for
    * @return the number of times the keyword was found
    */
    public int getKeywordOccurrences(String keyword)
    {
        return keywordTree.findNoIncrement(keyword).getOccurrences();
    }
    
    /**
     * Returns the keywords used for each rubric and weight
     * @return 2 dimensional array of SortedStrings containing the keywords for
     * keywordsUsed[rubric][weight]
     */
    public SortedSet<String>[][] getKeywordsUsed()
    {
        return keywordsUsed;
    }
}
