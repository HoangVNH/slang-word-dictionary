package pkg20424098_slang_dictionary;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static HashMap<String, String> slangWordList = new HashMap<>();
    static String originalSlangWordListUrl = "data/slang.txt";

    public static void main(String[] args) {        
        String luaChon;
        FileHandler.loadSlangWordListFromFile(slangWordList, originalSlangWordListUrl);
        
        OUTER:
        while (true) {
            System.out.println("================= Slang Word Dictionary ==================");
            System.out.println("1. Search by slang word");
            System.out.println("2. Search by definition");
            System.out.println("3. Show history");
            System.out.println("4. Add new slang word");
            System.out.println("5. Edit a slang word");
            System.out.println("6. Delete a slang word");
            System.out.println("7. Reset to original slang word list");
            System.out.println("8. Random a slang word (On this day slang word)");
            System.out.print("Choose an option: ");
            luaChon = sc.nextLine();

            switch (luaChon.charAt(0)) {
                case '1':
                    SearchBySlangWord();
                    break;
                case '6':
                    sc = new Scanner(System.in);
                    System.out.print("Enter the word to delete: ");
                    String deleteKey = sc.nextLine();
                    Boolean isExisted = isKeyExisted(deleteKey);
                    
                    if (isExisted) {
                        System.out.printf("Are you sure that you want to delete %s? (Y/N): ", deleteKey);
                        String ans = sc.nextLine();
                        
                        if (ans.charAt(0) == 'Y') {
                            String isDeleted = DeleteSlangWord(deleteKey) ? "Delete successfully!" : "Could not delete this word";
                            System.out.println(isDeleted);
                        } else if (ans.charAt(0) != 'N') {
                            System.out.println("You typed invalid option!");
                        }
                    } else {
                        System.out.printf("Could not find %s!\n", deleteKey);
                    }
                    break;
                default:
                    System.out.println("exiting...");
                    break OUTER;
            }
        }
    }
    
    public static void SearchBySlangWord() {
        System.out.print("Type any word: ");
        String slangWord = sc.nextLine();
        
        String res = slangWordList.get(slangWord);
        
        if (res != null) {
            String[] definitions = res.split("|");
            
            for (String definition: definitions) {
                System.out.println("definition: " + definition);
            }
//            System.out.println("======" + res);
        } else {
            System.out.println("Sorry, we couldn't find: " + slangWord);
        }
    }
    
    public static boolean isKeyExisted(String key) {
        return slangWordList.containsKey(key);
    }
        
    public static boolean DeleteSlangWord(String deleteKey) {
        if (deleteKey != null) {
            slangWordList.remove(deleteKey);
            return true;
        }

        return false;
    }
    
}
