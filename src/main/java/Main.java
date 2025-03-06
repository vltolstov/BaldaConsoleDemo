import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static String START_COMMAND = "старт";
    private static ArrayList<Character> WORD;
    private static int WORD_SIZE = 3;
    private static String OPEN_LETTERS;
    private static boolean GAME_STATUS;
    private static int ERROR_COUNT;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        do {
            gameInitialize();
            if (GAME_STATUS) {
                setWord();
            }
        } while (GAME_STATUS);
    }

    public static void gameInitialize(){
        System.out.println("Для старта новой игры введите 'Старт'. Для отмены игры нажмите Enter: ");
        String commandFromUserInput = scanner.nextLine().toLowerCase();
        if(commandFromUserInput.equals(START_COMMAND)){
            GAME_STATUS = true;
            ERROR_COUNT = 0;
            WORD = new ArrayList<>();
        } else {
            GAME_STATUS = false;
        }
    }

    public static void setWord(){
        //слово без пробелов, не пустое, >= 3 символа
        System.out.println("Загадайте слово (не менее " + (WORD_SIZE - 1) + " букв)");
        String userWord = "";
        while (userWord.isEmpty() || userWord.contains(" ") || userWord.length() < WORD_SIZE){
            userWord = scanner.nextLine().toLowerCase();
            if(userWord.isEmpty() || userWord.contains(" ") || userWord.length() < WORD_SIZE){
                System.out.println("Неподходящее слово. Загадайте другое");
            }
        }
        for(int i = 0; i < userWord.length(); i++){
            WORD.add(i, userWord.charAt(i));
        }
        System.out.println(WORD.toString());
    }
}
