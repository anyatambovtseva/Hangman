import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman
{
    private static List<String> words = new ArrayList<>(); //todo статика -> убрать
    private static final int MAX_TRIES = 5;

    // todo сейчас здесь всё намешано - и чтение и запись в консоль и сама логика игры - нужно разнести по разным классам
    public static void main(String[] args)
    {
        loadWordsFromFile("C:/Users/79326/Desktop/виселица.txt"); // todo чтение файла из папки resources
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        printWelcomeMessage();

        do
        {
            // todo сейчас здесь может поиграть только один игрок, создайте класс для игрока и храните там информацию
            playAgain = false;
            String wordToGuess = getRandomWord();
            StringBuilder guessedWord = new StringBuilder("_".repeat(wordToGuess.length()));
            int tries = 0;
            boolean[] guessedLetters = new boolean[33];

            while (tries < MAX_TRIES && guessedWord.toString().contains("_"))
            {
                System.out.println("Слово: " + guessedWord);
                System.out.println("Попытки оставшиеся: " + (MAX_TRIES - tries));
                System.out.print("Введите букву (или введите /help для помощи, /exit для выхода): ");
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("/help"))
                {
                    printHelpMessage();
                    continue;
                }

                if (input.equals("/exit"))
                {
                    System.out.println("Вы вышли из игры. Спасибо за игру!");
                    scanner.close(); // todo он тут не нужен, в конце цикла уже есть этот вызов
                    return;
                }

                if (input.length() != 1 || !isValidCharacter(input.charAt(0)))
                {
                    System.out.println("Пожалуйста, введите одну букву русского алфавита.");
                    continue;
                }

                char guess = input.charAt(0);

                if (guessedLetters[guess - 'а'])
                {
                    System.out.println("Вы уже угадали эту букву. Попробуйте снова.");
                    continue;
                }

                guessedLetters[guess - 'а'] = true;

                if (wordToGuess.indexOf(guess) >= 0)
                {
                    for (int i = 0; i < wordToGuess.length(); i++)
                    {
                        if (wordToGuess.charAt(i) == guess)
                        {
                            guessedWord.setCharAt(i, guess);
                        }
                    }
                    System.out.println("Правильно!");
                }
                else
                {
                    tries++;
                    System.out.println("Неправильно! Буква не в слове.");
                }
            }

            if (guessedWord.toString().equals(wordToGuess))
            {
                System.out.println("Поздравляем! Вы угадали слово: " + wordToGuess);
            }
            else
            {
                System.out.println("Вы проиграли! Загаданное слово было: " + wordToGuess);
            }

            System.out.print("Хотите сыграть еще раз? (да/нет): ");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("да"))
            {
                playAgain = true;
            }

        }
        while (playAgain);

        scanner.close();
    }

    private static void loadWordsFromFile(String filename) //todo статика -> убрать
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                words.add(line.trim());
            }
        }
        catch (IOException e)
        {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static String getRandomWord() //todo статика -> убрать
    {
        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    private static void printWelcomeMessage() //todo статика -> убрать
    {
        System.out.println("Добро пожаловать в игру 'Виселица'!");
        System.out.println("Правила игры:");
        System.out.println("1. Я загадаю слово на тему животные, а вы будете пытаться его угадать, вводя буквы.");
        System.out.println("2. У вас есть " + MAX_TRIES + " попыток.");
        System.out.println("3. Если вы введете букву, которая не входит в слово, попытка будет считаться использованной.");
        System.out.println("4. Если вы хотите получить помощь, введите команду /help в любой момент.");
        System.out.println("5. Чтобы выйти из игры, введите команду /exit.");
        System.out.println("Удачи!");
    }

    private static void printHelpMessage() //todo статика -> убрать
    {
        System.out.println("Правила игры:");
        System.out.println("1. Я загадаю слово на тему животные, а вы будете пытаться его угадать, вводя буквы.");
        System.out.println("2. У вас есть " + MAX_TRIES + " попыток.");
        System.out.println("3. Если вы введете букву, которая не входит в слово, попытка будет считаться использованной.");
        System.out.println("4. Чтобы увидеть это сообщение снова, введите команду /help.");
        System.out.println("5. Чтобы выйти из игры, введите команду /exit.");
    }

    private static boolean isValidCharacter(char c)
    {
        return (c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я'); // Проверка на русские буквы
    }
}