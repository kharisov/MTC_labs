import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void calculate() {
        StringReader reader = new StringReader("1 +2*\n\n3/4^( 5 )");
        try {
            Parser parser = new Parser(new Lexer(reader));
            assertEquals(1, parser.calculate());
            reader = new StringReader("1 + 2 + 3 * 4 / 2");
            parser = new Parser(new Lexer(reader));
            assertEquals(9, parser.calculate());
            reader = new StringReader("2^2^2");
            parser = new Parser(new Lexer(reader));
            assertEquals(16, parser.calculate());
            reader = new StringReader("(1 + (2 * 3) - (4 - 1))");
            parser = new Parser(new Lexer(reader));
            assertEquals(4, parser.calculate());
            reader = new StringReader("1 + 3 * -4");
            parser = new Parser(new Lexer(reader));
            assertEquals(-11, parser.calculate());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Test
    void calculateFailed() {
        StringReader reader = new StringReader("1+2)");
        try {
            Parser parser = new Parser(new Lexer(reader));
            assertThrows(IOException.class, parser::calculate);
            reader = new StringReader("1++2");
            parser = new Parser(new Lexer(reader));
            assertThrows(IOException.class, parser::calculate);
            reader = new StringReader("((1+2");
            parser = new Parser(new Lexer(reader));
            assertThrows(IOException.class, parser::calculate);
            reader = new StringReader("1^(1 1)");
            parser = new Parser(new Lexer(reader));
            assertThrows(IOException.class, parser::calculate);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}