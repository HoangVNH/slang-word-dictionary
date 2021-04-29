package pkg20424098_slang_dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
            System.out.println();
            System.out.println("======================== Menu ============================");
            System.out.println("Please enter a choice and press <Enter>");
            System.out.println("\t1) Search by slang word");
            System.out.println("\t2) Search by definition");
            System.out.println("\t3) Show history");
            System.out.println("\t4) Add new slang word");
            System.out.println("\t5) Edit a slang word");
            System.out.println("\t6) Delete a slang word");
            System.out.println("\t7) Reset to original slang word list");
            System.out.println("\t8) Random a slang word (On this day slang word)");
            System.out.println("\t9) Random a slang word with answers (Quiz)");
            System.out.println("\t10) Random a definition with answers (Quiz)");
            System.out.println("\t11) Exit");
            System.out.println("===========================================================");
            System.out.print("Enter your choice: ");
            luaChon = sc.nextLine();
            System.out.println();

            switch (luaChon) {
                case "1":
                    sc = new Scanner(System.in);
                    System.out.print("Type any word: ");
                    String slangWord = sc.nextLine().toUpperCase();
                    SearchBySlangWord(slangWord);
                    break;
                case "2": {
                    sc = new Scanner(System.in);
                    System.out.print("Type any definition: ");
                    String definition = sc.nextLine().toUpperCase();
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
                case "5":
                    sc = new Scanner(System.in);
                    System.out.print("Type slang word to edit: ");
                    String key = sc.nextLine().toUpperCase();
                    
                    if (!isKeyExisted(key)) {
                        System.out.println("Sorry, could not find " + key);
                    } else {
                        EditSlangWord(key);
                    }
                    break;
                case "6":
                    sc = new Scanner(System.in);
                    System.out.print("Type a word to delete: ");
                    String deleteKey = sc.nextLine().toUpperCase();
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
                    System.out.println();
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
                    System.out.println();
                    break;
                case "9":
                    RandomizeSlangWordQuiz();
                    break;
                case "11": {
                    FileHandler.WriteSlangWordListToFile(slangWordList);
                    FileHandler.WriteSeachHistoryToFile(historySearched);
                    System.out.println("Saving data to files...\n");
                    break OUTER;
                }
                default:
                    System.out.println("Invalid operation! Please try again...\n");
            }
        }
    }
    
    public static void SearchBySlangWord(String key) {
        historySearched.add(key);
        
        if (isKeyExisted(key)) {
            List<String> definitions = slangWordList.get(key);
            int count = 1;
            
            for (int i = 0; i < definitions.size(); i++) {
                System.out.printf("%d. %s\n", count++, definitions.get(i).trim());
            }
        } else {
            System.out.println("Could not find " + key);
        }

        System.out.println();
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
            int count = 1;

            for (int i = 0; i < words.size(); i++) {
                System.out.printf("%d. %s\n", count++, words.get(i).trim());
            }
        } else {
            System.out.println("Could not find " + definition);
        }
        
        System.out.println();
    }
    
    public static boolean isKeyExisted(String key) {
        return slangWordList.containsKey(key);
    }
    
    public static void ShowHistory() {
        System.out.println("================= Search History ==================");
        int count = 1;
        
        for (int i = 0; i < historySearched.size(); i++) {
            System.out.printf("%d. %s\n", count++, historySearched.get(i));
        }

        System.out.println();
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
        
        System.out.println("Added new slang word successfully!\n");
    }
    
    public static void EditSlangWord(String key) {
        System.out.println("Definitions:");

        List<String> definitions = slangWordList.get(key);
        List<String> tempDefinitions = new ArrayList();
        
        definitions.forEach((String def) -> {
            tempDefinitions.add(def);
        });
        
        int count = 1;

        for (int i = 0; i < definitions.size(); i++) {
            System.out.printf("%d. %s\n", count++, definitions.get(i).trim());
        }
        System.out.println();
        
        System.out.print("Which one you want to modify?: ");
        int idx = sc.nextInt();
        sc.nextLine();
        System.out.println();
        
        System.out.println("Choose an action :");
        System.out.println("\t1. Add new definition");
        System.out.println("\t2. Delete definition");
        System.out.println("\t3. Replace with a new definition");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        boolean isEdited = true;
        
        idx -= 1;
        
        if (choice == 1) {
            tempDefinitions.remove(idx);

            System.out.print("Enter new definition: ");
            String newDefinition = sc.nextLine();
            
            tempDefinitions.add(newDefinition);
            slangWordList.put(key, tempDefinitions);
        } else if (choice == 2) {
            if (tempDefinitions.size() == 1) {
                isEdited = false;
                System.out.println("Could not perform this action!");
            } else {
                tempDefinitions.remove(idx);
                slangWordList.put(key, tempDefinitions);
            }
        } else if (choice == 3) {
            System.out.print("Enter the replace definition: ");            
            String newDefinition = sc.nextLine();
            
            tempDefinitions.add(newDefinition);
            slangWordList.put(key, tempDefinitions);
        }

        if (isEdited) {
            System.out.println("Edited successfully!");
        }
        System.out.println();
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
    
    public static void RandomizeSlangWordQuiz() {
        Random rand = new Random();
        List<String> options = new ArrayList();
        
        String opt1 = RandomizeSlangWord();
        String question = opt1;
        List<String> opt1Definitions = slangWordList.get(opt1);
        opt1 = opt1Definitions.get(rand.nextInt(opt1Definitions.size())).trim();
        String answer = opt1;
        options.add(opt1);
        
        String opt2 = RandomizeSlangWord();
        List<String> opt2Definitions = slangWordList.get(opt2);
        opt2 = opt2Definitions.get(rand.nextInt(opt2Definitions.size())).trim();
        options.add(opt2);
        
        String opt3 = RandomizeSlangWord();
        List<String> opt3Definitions = slangWordList.get(opt3);
        opt3 = opt3Definitions.get(rand.nextInt(opt3Definitions.size())).trim();
        options.add(opt3);
        
        String opt4 = RandomizeSlangWord();
        List<String> opt4Definitions = slangWordList.get(opt4);
        opt4 = opt4Definitions.get(rand.nextInt(opt4Definitions.size())).trim();
        options.add(opt4);
        
        System.out.printf("Question: What is the correct definition of %s?\n", question);
        
        opt1 = options.get(rand.nextInt(options.size()));
        options.remove(opt1);
        System.out.println("A. " + opt1);
        opt2 = options.get(rand.nextInt(options.size()));
        options.remove(opt2);
        System.out.println("B. " + opt2);
        opt3 = options.get(rand.nextInt(options.size()));
        options.remove(opt3);
        System.out.println("C. " + opt3);
        opt4 = options.get(0);
        options.clear();
        System.out.println("D. " + opt4);
        
        System.out.print("Your answer is: ");
        String ans = sc.nextLine();
        
        if (ans.equalsIgnoreCase("a") && opt1.equals(answer)) {
            System.out.println("Correct!\n");
        } else if (ans.equalsIgnoreCase("b") && opt2.equals(answer)) {
            System.out.println("Correct!\n");
        } else if (ans.equalsIgnoreCase("c") && opt3.equals(answer)) {
            System.out.println("Correct!\n");
        } else if (ans.equalsIgnoreCase("d") && opt4.equals(answer)) {
            System.out.println("Correct!\n");
        } else {
            System.out.println("Incorrect!\n");
        }
    }
}
