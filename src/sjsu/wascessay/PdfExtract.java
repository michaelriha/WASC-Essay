package sjsu.wascessay;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Program for extracting words from a PDF document
 * @author Akshat Kukreti
 */
public class PdfExtract {
    private static final int START_SIZE = 2000;
    
    /**
     * function for reading a PDF document and extracting words from it
     * @param filename is the path to the PDF document
     * @return cleanwords is an ArrayList containing all the words present in 
     * the document
     * @throws DocumentException
     * @throws IOException 
     */
    public static ArrayList<String> convertToText(String filename) 
            throws DocumentException, IOException
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
                        else if(c == '-'){
                            if(word.length() > 0 &&
                                !(word.charAt(word.length()-1) == '-')){    
                                word.append(c);
                            }
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
        
        /**
          *Cleaning up words. Getting rid of strings that are only hyphens and
          *words that are only spaces
          */ 
        ArrayList<String> cleanwords = new ArrayList<>(START_SIZE);
        Iterator worditerator = words.iterator();
        while(worditerator.hasNext()){
            String str = worditerator.next().toString();
            if(str.indexOf('-') == 0 || str.indexOf(' ') == 0 || 
                    str.contains("--")){
            }
            else{
                cleanwords.add(str);
            }
        }
        return cleanwords;      
    }
}

