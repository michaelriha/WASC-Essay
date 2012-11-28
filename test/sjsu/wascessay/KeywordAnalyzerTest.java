package sjsu.wascessay;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Michael Riha
 */
public class KeywordAnalyzerTest 
{
    @Test
    public void testKeywordAnalyzer() throws FileNotFoundException, IOException, DocumentException 
    {
        // Test readKeywordFile
        KeywordAnalyzer instance = new KeywordAnalyzer();
        instance.readKeywordFile("D:/keywords.txt");
        assertEquals(0, instance.getKeywordOccurrences("critical"));
        
        // Get a test pdf and parse the text
        String filename = "D:/test3.pdf";
        ArrayList<String> text = PdfExtract.convertToText(filename);
        instance.parseText(text);
        
        // Print a detailed report about the file
        StringBuilder sb = new StringBuilder();        
        int totOne = 0, totTwo = 0, total = 0;
        int[][] wordCounts = instance.getWordCounts();
        
        for (int i = 0; i < 5; ++i)
            for (int j = 0; j < 2; ++j)
            {
                if (j == 0)
                    totOne += wordCounts[i][j];
                else
                    totTwo += wordCounts[i][j];
                total += wordCounts[i][j];
            }
        
        sb.append("Keywords in ").append(filename).append(": ").append(total);
        sb.append("\nTotal Weight 1 Keywords: ").append(totOne).append("\n");
        sb.append("Total Weight 2 Keywords: ").append(totTwo);
        sb.append("\nTotal words: ").append(instance.getTotalWords());
        
        double[] scores = instance.calculateScores();
        sb.append("\nTotal score: ").append(String.format("%.2f", scores[5]));
        
        SortedSet<String>[][] sets = instance.getKeywordsUsed();
        StringBuilder line = new StringBuilder(); // "keyword group" 
        String lastWildcard, word;
        Iterator < String > iter;
        
        for (int i = 0; i < 5; ++i)
        {
            sb.append("\n\nRubric ").append(i+1).append(" Score: ");
            sb.append(String.format("%.2f", scores[i]));
            for (int j = 0; j < 2; ++j)
            {
                lastWildcard = sets[i][j].first();
                iter = sets[i][j].iterator();
                line = new StringBuilder();
                sb.append("\n  Weight ").append(j+1).append(" keywords used: ").append(wordCounts[i][j]).append("\n      ");
                while (iter.hasNext())
                {
                    word = iter.next();
                    if (!word.startsWith(lastWildcard))
                    {                        
                        line.append(" ").append(instance.getKeywordOccurrences(lastWildcard)).append(" | ").append(word);
                        lastWildcard = word;
                    }
                    else
                        line.append(", ").append(word);
                }
                line.append(" ");
                line.delete(0, 2);
                sb.append(line);
            }
        }
        System.out.println(sb.toString());
    }
}
