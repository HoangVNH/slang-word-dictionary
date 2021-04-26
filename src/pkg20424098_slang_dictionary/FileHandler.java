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
    private static final String SLANG_URL = "data/slang.txt";
    private static final String HISTORY_URL = "data/search_history.txt";

    public static void LoadSlangWordListFromFile(HashMap<String, List<String>> slangWordList, String slangUrl) {
        String url = slangUrl.isEmpty() ? SLANG_URL : slangUrl;

        try {
            FileReader fr = new FileReader(new File(url));

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
                br.close();
                fr.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void WriteSlangWordListToFile(HashMap<String, List<String>> slangWordList) {
        try {
            FileWriter fw = new FileWriter(new File(SLANG_URL));

            try (BufferedWriter bw = new BufferedWriter(fw)) {
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
                fw.close();
                bw.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void LoadSearchHistoryFromFile(List<String> historySearched) {
        try {
            BufferedReader br;
            try (FileReader fr = new FileReader(new File(HISTORY_URL))) {
                br = new BufferedReader(fr);
                String line;
                while((line = br.readLine()) != null) {
                    historySearched.add(line);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void WriteSeachHistoryToFile(List<String> historySearched) {
        try {
            BufferedWriter bw;
            try (FileWriter fw = new FileWriter(new File(HISTORY_URL))) {
                bw = new BufferedWriter(fw);
                for (String searchedWord: historySearched) {
                    fw.write(searchedWord + "\n");
                }
                fw.close();
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}