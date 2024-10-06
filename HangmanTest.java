import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest {
    private Hangman game;

    @BeforeEach
    void setUp() {
        game = new Hangman();
        game.loadWordsFromFile("C:/Users/gkris/Desktop/виселица.txt");
    }


    @Test
    void testIsValidCharacter_ValidLowercase() throws Exception {
        Method method = Hangman.class.getDeclaredMethod("isValidCharacter", char.class);
        method.setAccessible(true); // Делаем метод доступным

        assertTrue((Boolean) method.invoke(game, 'а'));
        assertTrue((Boolean) method.invoke(game, 'я'));
        assertTrue((Boolean) method.invoke(game, 'Ч'));
    }

    @Test
    void testIsValidCharacter_InvalidCharacter() throws Exception {
        Method method = Hangman.class.getDeclaredMethod("isValidCharacter", char.class);
        method.setAccessible(true);

        assertFalse((Boolean) method.invoke(game, 'a'));
        assertFalse((Boolean) method.invoke(game, '1'));
        assertFalse((Boolean) method.invoke(game, ' '));
        assertFalse((Boolean) method.invoke(game, 'I'));
    }
    @Test
    public void testLoadWordsFromFile() {
        Hangman.loadWordsFromFile("C:/Users/gkris/Desktop/виселица.txt"); // Загрузка слов из тестового файла

        List<String> words = Hangman.getWords(); // Предполагается, что вы добавите метод для получения списка слов
        assertEquals("кот", words.get(0));
        assertEquals("собака", words.get(1));
        assertEquals("мышь", words.get(2));
    }

}
