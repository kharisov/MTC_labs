import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void getLexeme() {
        StringReader reader = new StringReader("1  +2*\n\n3/4^( 5 ) ");
        try {
            Lexer lexer = new Lexer(reader);
            Lexeme tmpLex;
            assertEquals(LexemeType.NUMBER, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("1", tmpLex.getLexemeText());
            assertEquals(LexemeType.PLUS, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("+", tmpLex.getLexemeText());
            assertEquals(LexemeType.NUMBER, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("2", tmpLex.getLexemeText());
            assertEquals(LexemeType.ASTERISK, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("*", tmpLex.getLexemeText());
            assertEquals(LexemeType.NUMBER, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("3", tmpLex.getLexemeText());
            assertEquals(LexemeType.SLASH, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("/", tmpLex.getLexemeText());
            assertEquals(LexemeType.NUMBER, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("4", tmpLex.getLexemeText());
            assertEquals(LexemeType.CARET, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("^", tmpLex.getLexemeText());
            assertEquals(LexemeType.LEFT_PAREN, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("(", tmpLex.getLexemeText());
            assertEquals(LexemeType.NUMBER, (tmpLex = lexer.getLexeme()).getType());
            assertEquals("5", tmpLex.getLexemeText());
            assertEquals(LexemeType.RIGHT_PAREN, (tmpLex = lexer.getLexeme()).getType());
            assertEquals(")", tmpLex.getLexemeText());
            assertEquals(LexemeType.EOF, (tmpLex = lexer.getLexeme()).getType());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}