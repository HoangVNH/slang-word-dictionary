package pkg20424098_slang_dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    private static BufferedReader br;
    
    public static void openFile(String url) {
        try {
            br = new BufferedReader(new FileReader(url));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loadSlangWordListFromFile(HashMap<String, String> slangWordList, String url) {
        try {
            openFile(url);
            
            String s;
            
            while((s = br.readLine()) != null) {
                String[] words = s.split("`", 2);
                
                if (words.length > 1) {
                    slangWordList.put(words[0], words[1]);
                } else {
                    slangWordList.put(words[0], null);
                }
            }
            
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}