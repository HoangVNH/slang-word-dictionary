package pkg20424098_slang_dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static HashMap<String, List<String>> slangWordList = new HashMap<String, List<String>>();
    static List<String> historySearched = new ArrayList();

    public static void main(String[] args) {
        String luaChon;
        FileHandler.LoadSlangWordListFromFile(slangWordList, "");
        FileHandler.LoadSearchHistoryFromFile(historySearched);

        OUTER:
        while (true) {
            System.out.println("================= Slang Word Dictionary ==================");
            System.out.println("======================== Menu ============================");
            System.out.println("1) Search by slang word");
            System.out.println("2) Search by definition");
            System.out.println("3) Show history");
            System.out.println("4) Add new slang word");
            System.out.println("5) Edit a slang word");
            System.out.println("6) Delete a slang word");
            System.out.println("7) Reset to original slang word list");
            System.out.println("8) Random a slang word (On this day slang word)");
            System.out.println("9) Random a slang word with answers (Quiz)");
            System.out.println("10) Random a definition with answers (Quiz)");
            System.out.println("11) Exit");
            System.out.println("===========================================================");
            System.out.print("Select an option -> ");
            luaChon = sc.nextLine();

            switch (luaChon) {
                case "1":
                    sc = new Scanner(System.in);
                    System.out.print("Type any word: ");
                    String slangWord = sc.nextLine();
                    SearchBySlangWord(slangWord);
                    break;
                case "2": {
                    sc = new Scanner(System.in);
                    System.out.print("Type any definition: ");
                    String definition = sc.nextLine();
                    SearchByDefinition(definition);
                    break;
                }
                case "3":
                    ShowHistory();
                    break;
                case "4": {
                    sc = new Scanner(System.in);
                    System.out.print("Type slang word to add: ");
                    String key = sc.nextLine().toUpperCase();
                    System.out.print("Type definition: ");
                    String definition = sc.nextLine();
                    AddNewSlangWord(key, definition);
                    break;
                }
                case "6":
                    sc = new Scanner(System.in);
                    System.out.print("Type a word to delete: ");
                    String deleteKey = sc.nextLine();
                    Boolean isExisted = isKeyExisted(deleteKey);

                    if (isExisted) {
                        System.out.printf("Are you sure that you want to delete %s? (Y/N): ", deleteKey);
                        String ans = sc.nextLine();
                        
                        if (ans.charAt(0) == 'Y' || ans.charAt(0) == 'y') {
                            String isDeleted = DeleteSlangWord(deleteKey) ? "Deleted successfully!" : "Could not delete this word";
                            System.out.println(isDeleted);
                        } else if (ans.charAt(0) != 'N') {
                            System.out.println("You typed invalid option!");
                        }
                    } else {
                        System.out.printf("Could not find %s!\n", deleteKey);
                    }
                    break;
                case "7":
                    String originalSlangListUrl = "data/original_slang.txt";
                    slangWordList.clear();
                    FileHandler.LoadSlangWordListFromFile(slangWordList, originalSlangListUrl);
                    System.out.println("Reset data to inital value successfully!");
                    break;
                case "8":
                    String randomSlangWord = RandomizeSlangWord();
                    System.out.println("On this day slang word: " + randomSlangWord);
                    System.out.println("Definitions: ");
                    slangWordList.get(randomSlangWord).forEach(def -> {
                        System.out.println("============== " + def.trim());
                    });
                    break;
                default:
                    FileHandler.WriteSlangWordListToFile(slangWordList);
                    FileHandler.WriteSeachHistoryToFile(historySearched);
                    System.out.println("Saving...");
                    break OUTER;
            }
        }
    }
    
    public static void SearchBySlangWord(String key) {
        historySearched.add(key);
        
        List<String> definitions = slangWordList.get(key);

        if (definitions != null && definitions.size() > 0) {
            definitions.forEach(definition -> {
                System.out.println("=======" + definition);
            });
        } else {
            System.out.println("Could not find " + key);
        }
    }
    
    public static void SearchByDefinition(String definition) {
        historySearched.add(definition);

        List<String> words = new ArrayList();
        
        for (String slangWord: slangWordList.keySet()) {
            if (slangWordList.get(slangWord).contains(definition)) {
                words.add(slangWord);
            }
        }

        if (words.size() > 0) {
            words.forEach(word -> {
                System.out.println("=======" + word);
            });
        } else {
            System.out.println("Could not find " + definition);
        }
    }
    
    public static boolean isKeyExisted(String key) {
        return slangWordList.containsKey(key);
    }
    
    public static void ShowHistory() {
        System.out.println("================= Search History ==================");
        
        historySearched.forEach(word -> {
            System.out.println(word);
        });
    }
    
    public static void AddNewSlangWord(String key, String definition) {
        List<String> definitions = new ArrayList();
        definitions.add(definition);

        if (slangWordList.containsKey(key)) {
            System.out.printf("%s is already existed! Do you want to overwrite it? (Y/N): ", key);
            String ans = sc.nextLine();
            
            if (ans.equals("y") || ans.equals("Y")) {
                slangWordList.put(key, definitions);
            } else {
                List<String> definitionList = slangWordList.get(key);
                
                definitionList.forEach(def -> {
                    definitions.add(def);
                });
                
                slangWordList.put(key, definitions);
            }
        } else {
            slangWordList.put(key, definitions);
        }
        
        System.out.println("Added new slang word successfully!");
    }
        
    public static boolean DeleteSlangWord(String deleteKey) {
        if (deleteKey != null) {
            slangWordList.remove(deleteKey);
            return true;
        }

        return false;
    }
    
    public static String RandomizeSlangWord() {
        Object[] keys = slangWordList.keySet().toArray();
        String randomSlangWord = (String) keys[new Random().nextInt(keys.length)];
        
        return randomSlangWord;
    }
}
