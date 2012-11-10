package sjsu.wascessay;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Akshat Kukreti
 */
public class PdfExtract {
    private static final int START_SIZE = 2000;
    public static ArrayList<String> convertToText(String filename) throws DocumentException, IOException
    {
        boolean brackopen = false;
        boolean brackclose = false;
        
        //Stores the words extracted from the pdf
        ArrayList<String> words = new ArrayList<>(START_SIZE);
        
        //Accumuates a word
        StringBuilder word = new StringBuilder();

        if(filename.isEmpty()){
            System.exit(1);
        }
        
        try{
            PdfReader reader = new PdfReader(filename);
            int numpages = reader.getNumberOfPages();
            for(int i=1; i<=numpages; i++){
                byte[] pagecontent = reader.getPageContent(i);
                int contentlength = pagecontent.length;
                char[] charcontent = new char[contentlength];
                
                for(int j=0; j<contentlength; j++){
                    char c = (char)pagecontent[j];
                    charcontent[j] = c;
                }
                
                //Escaped characters
                for(int k=0; k<contentlength; k++){
                    if(charcontent[k] == '\\'){
                        charcontent[k] = ' ';
                        charcontent[k+1] = ' ';
                    }
                }
                
                for(int l=0; l<contentlength; l++){
                    if(charcontent[l] == '('){
                        brackopen = true;
                        continue;
                    }
                    if(charcontent[l] == ')'){
                        brackclose = true;
                    }
                    if(brackopen && !brackclose){
                        char c = charcontent[l];
                        if((int)c >= 65 && (int)c <= 90 || 
                                (int)c >= 97 && (int)c <= 122 ){
                            char lowerc = Character.toLowerCase(c);
                            word.append(lowerc);
                        }
                        if(c == ' '){
                            String wordstring = word.toString();
                            if (!wordstring.isEmpty()){
                                words.add(wordstring);
                                word.delete(0, word.length());
                            }
                        }
                    }
                    if(brackopen && brackclose){
                        brackopen = false;
                        brackclose = false;
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
        return words;        
    }
}

