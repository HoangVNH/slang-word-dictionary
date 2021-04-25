package pkg20424098_slang_dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    private static final String URL = "data/slang.txt";

    public static void LoadSlangWordListFromFile(HashMap<String, List<String>> slangWordList) {
        try {
            FileReader fr = new FileReader(new File(URL));
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                
                while((line = br.readLine()) != null) {
                    if (line.contains("`")) {
                        String[] words = line.split("`");
                        String key = words[0];
                        String[] definitions = words[1].split("\\|");
                        List<String> definitionList = Arrays.asList(definitions);
                        
                        slangWordList.put(key, definitionList);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void WriteSlangWordListToFile(HashMap<String, List<String>> slangWordList) {
        try {
            BufferedWriter bw;
            try (FileWriter fw = new FileWriter(new File(URL))) {
                bw = new BufferedWriter(fw);
                for (String key: slangWordList.keySet()) {
                    fw.write(key + "`");
                    List<String> temp = slangWordList.get(key);
                    
                    for (int i = 0; i < temp.size(); i++) {
                        fw.write(temp.get(i));
                        
                        if (i + 1 < temp.size()) {
                            fw.write("|");
                        }
                    }
                    
                    fw.write("\n");
                }
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}