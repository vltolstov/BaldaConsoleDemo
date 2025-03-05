import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static String START_COMMAND = "старт";
    private static ArrayList<String> WORD;
    private static boolean GAME_STATUS;
    private static int ERROR_COUNT;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        do {
            gameInitialize();
            //тут сама игра
        } while (GAME_STATUS);
    }

    public static void gameInitialize(){
        System.out.println("Для старта новой игры введите 'Старт'. Для отмены игры нажмите Enter: ");
        String commandFromUserInput = scanner.nextLine().toLowerCase();
        if(commandFromUserInput.equals(START_COMMAND)){
            GAME_STATUS = true;
            ERROR_COUNT = 0;
        } else {
            GAME_STATUS = false;
        }
    }
}
