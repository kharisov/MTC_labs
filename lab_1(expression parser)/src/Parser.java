import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    private Lexer lexer;
    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        currentLexeme = lexer.getLexeme();
    }

    public int calculate() throws IOException {
        int value = parseExpression();
        if (currentLexeme.getType() == LexemeType.EOF)
            return value;
        else
            throw new IOException("Wrong syntax: no EOF");
    }

    private int parseExpression() throws IOException {
        int tmp = parseTerm();
        while (currentLexeme.getType() == LexemeType.PLUS || currentLexeme.getType() == LexemeType.MINUS) {
            if (currentLexeme.getType() == LexemeType.PLUS) {
                currentLexeme = lexer.getLexeme();
                tmp += parseTerm();
            } else {
                currentLexeme = lexer.getLexeme();
                tmp -= parseTerm();
            }
        }
        return tmp;
    }

    private int parseTerm() throws IOException {
        int tmp = parseFactor();
        while (currentLexeme.getType() == LexemeType.ASTERISK || currentLexeme.getType() == LexemeType.SLASH) {
            if (currentLexeme.getType() == LexemeType.ASTERISK) {
                currentLexeme = lexer.getLexeme();
                tmp *= parseFactor();
            } else {
                currentLexeme = lexer.getLexeme();
                tmp /= parseFactor();
            }
        }
        return tmp;
    }

    private int parseFactor() throws IOException {
        int tmp = parsePower();
        if (currentLexeme.getType() == LexemeType.CARET) {
            currentLexeme = lexer.getLexeme();
            tmp = (int)Math.pow(tmp, parseFactor());
        }
        return tmp;
    }

    private int parsePower() throws IOException {
        if (currentLexeme.getType() == LexemeType.MINUS) {
            currentLexeme = lexer.getLexeme();
            return parseAtom() * (-1);
        } else
            return parseAtom();
    }

    private int parseAtom() throws IOException {
        if (currentLexeme.getType() == LexemeType.NUMBER) {
            int value = Integer.parseInt(currentLexeme.getLexemeText());
            currentLexeme = lexer.getLexeme();
            return value;
        } else if (currentLexeme.getType() == LexemeType.LEFT_PAREN) {
            currentLexeme = lexer.getLexeme();
            int value = parseExpression();
            if (currentLexeme.getType() == LexemeType.RIGHT_PAREN) {
                currentLexeme = lexer.getLexeme();
                return value;
            } else
                throw new IOException("Wrong syntax: no closing parenthesis");
        } else {
            throw new IOException("Wrong syntax: can't parse atom");
        }
    }

    public static void main(String[] args) {
        try (InputStreamReader in = new InputStreamReader(System.in)) {
            Parser parser = new Parser(new Lexer(in));
            System.out.println("Expression result: " + parser.calculate());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
