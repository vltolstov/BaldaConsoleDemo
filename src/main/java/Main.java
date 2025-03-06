import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String START_COMMAND = "старт";
    private static List<String> LIBRARY;
    private static String SECRET_WORD;
    private static ArrayList<Character> SECRET_LETTERS;
    private static String WORD_WITH_OPEN_LETTERS;
    private static boolean GAME_STATUS;
    private static int ERROR_COUNT;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        do {
            gameInitialize();
            if (GAME_STATUS) {
                setWord();
                setRandomOpenLetters();
            }
        } while (GAME_STATUS);
    }

    public static void gameInitialize(){
        System.out.println("Для старта новой игры введите 'Старт'. Для отмены игры нажмите Enter: ");
        String commandFromUserInput = scanner.nextLine().toLowerCase();
        if(commandFromUserInput.equals(START_COMMAND)){
            GAME_STATUS = true;
            ERROR_COUNT = 0;
            SECRET_LETTERS = new ArrayList<>();
            WORD_WITH_OPEN_LETTERS = "";

        } else {
            GAME_STATUS = false;
        }
    }

    public static void setWord(){
        try {
            LIBRARY = Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int randomIndex = random.nextInt(LIBRARY.size());
        SECRET_WORD = LIBRARY.get(randomIndex);

        for(int i = 0; i < SECRET_WORD.length(); i++){
            SECRET_LETTERS.add(i, SECRET_WORD.charAt(i));
            WORD_WITH_OPEN_LETTERS += "-";
        }
        System.out.println(SECRET_LETTERS.toString());
        System.out.println(WORD_WITH_OPEN_LETTERS);
    }

    public static void setRandomOpenLetters(){
        int wordActualSize = SECRET_LETTERS.size();
        int firstOpenLetterIndex;
        int secondOpenLetterIndex;
        do {
            firstOpenLetterIndex = random.nextInt(wordActualSize);
            secondOpenLetterIndex = random.nextInt(wordActualSize);
        } while (firstOpenLetterIndex == secondOpenLetterIndex);
        StringBuilder stringBuilder = new StringBuilder(WORD_WITH_OPEN_LETTERS);
        stringBuilder.setCharAt(firstOpenLetterIndex, SECRET_LETTERS.get(firstOpenLetterIndex));
        stringBuilder.setCharAt(secondOpenLetterIndex, SECRET_LETTERS.get(secondOpenLetterIndex));
        WORD_WITH_OPEN_LETTERS = stringBuilder.toString();
        System.out.println(WORD_WITH_OPEN_LETTERS);
    }

    public static void inputUserLetter(){

    }

    public static void checkLetter(){

    }
}
