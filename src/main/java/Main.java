import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String START_COMMAND = "старт";
    private static List<String> LIBRARY;
    private static String SECRET_WORD;
    private static String GAME_FIELD;
    private static boolean GAME_STATUS;
    private static boolean END_GAME_STATUS;
    private static int ERROR_COUNT;
    private static List<String> WRONG_LETTERS;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        do {
            startGameInitialize();
            if (GAME_STATUS) {
                setWord();
                resetGameField();
                setRandomOpenLetters();
                System.out.println(GAME_FIELD);
                while (!END_GAME_STATUS) {
                    playerTurn();
                    System.out.println(GAME_FIELD);
                    checkGameStatus();
                }
                viewResult();
            }
        } while (GAME_STATUS);
    }

    public static void startGameInitialize() {
        System.out.println("Для старта новой игры введите 'Старт'. Для отмены игры нажмите Enter: ");
        String commandFromUserInput = scanner.nextLine().toLowerCase();
        if (commandFromUserInput.equals(START_COMMAND)) {
            ERROR_COUNT = 6;
            WRONG_LETTERS = new ArrayList<>();
            GAME_FIELD = "";
            GAME_STATUS = true;
            END_GAME_STATUS = false;
        } else {
            GAME_STATUS = false;
        }
    }

    public static void setWord() {
        try {
            LIBRARY = Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int randomIndex = random.nextInt(LIBRARY.size());
        SECRET_WORD = LIBRARY.get(randomIndex);
    }

    public static void resetGameField() {
        for (int i = 0; i < SECRET_WORD.length(); i++) {
            GAME_FIELD += "-";
        }
    }

    public static void setGameField(List<Integer> indexes) {
        StringBuilder gameField = new StringBuilder(GAME_FIELD);
        for (Integer index : indexes) {
            gameField.setCharAt(index, SECRET_WORD.charAt(index));
        }
        GAME_FIELD = gameField.toString();
    }

    public static void setRandomOpenLetters() {
        Character firstLetter;
        Character secondLetter;
        do {
            int randomFirstIndex = random.nextInt(SECRET_WORD.length());
            int randomSecondIndex = random.nextInt(SECRET_WORD.length());
            firstLetter = SECRET_WORD.charAt(randomFirstIndex);
            secondLetter = SECRET_WORD.charAt(randomSecondIndex);
        } while (firstLetter.equals(secondLetter));

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < SECRET_WORD.length(); i++) {
            if (firstLetter == SECRET_WORD.charAt(i) || secondLetter == SECRET_WORD.charAt(i)) {
                indexes.add(i);
            }
        }
        setGameField(indexes);
    }

    public static void playerTurn() {
        char userLetter = scanner.next().charAt(0);
        if (GAME_FIELD.contains(Character.toString(userLetter))) {
            errorNotify("Такая буква уже есть в слове.");
        } else if (!GAME_FIELD.contains(Character.toString(userLetter)) && SECRET_WORD.contains(Character.toString(userLetter))) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < SECRET_WORD.length(); i++) {
                if (userLetter == SECRET_WORD.charAt(i)) {
                    indexes.add(i);
                }
            }
            setGameField(indexes);
        } else {
            if (!WRONG_LETTERS.contains(Character.toString(userLetter))) {
                ERROR_COUNT--;
                errorNotify("Такой буквы в слове нет.");
                WRONG_LETTERS.add(Character.toString(userLetter));
            } else {
                errorNotify("Такую букву вы уже вводили и ее нет в слове.");
            }
        }
    }

    public static void errorNotify(String message) {
        System.out.println(message + " " + "Осталось попыток: " + ERROR_COUNT);
    }

    public static void checkGameStatus() {
        if (!GAME_FIELD.contains("-") || ERROR_COUNT == 0) {
            END_GAME_STATUS = true;
        }
    }

    public static void viewResult() {
        if (!GAME_FIELD.contains("-") && ERROR_COUNT != 0) {
            System.out.println("Победа!");
        } else {
            System.out.println("Вы проиграли. Попробуйте еще раз. Загаданное слово: " + SECRET_WORD);
        }
    }
}
