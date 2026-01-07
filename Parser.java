import java.util.List;

public class Parser {

    private final List<Minilexer.Tipo> tokens;
    private int pos = 0;

    public Parser(List<Minilexer.Tipo> tokens) {
        this.tokens = tokens;
    }

    private Minilexer.Tipo actual() {
        return tokens.get(pos);
    }

    private void avanzar() {
        if (pos < tokens.size() - 1) pos++;
    }

    private void esperar(Minilexer.Token esperado, String mensajeError) {
        if (actual().token == esperado) {
            avanzar();
        } else {
            throw new RuntimeException(
                mensajeError + ". Encontrado: '" + actual().lexema + "'"
            );
        }
    }

    // Punto de entrada
    public void stmtList() {
        while (actual().token != Minilexer.Token.EOF) {
            stmt();
        }
    }

    // Stmt → ID '=' Expr ';' | 'print' '(' Expr ')' ';'
    private void stmt() {
        if (actual().token == Minilexer.Token.IDENTIFICADOR) {
            // Asignación
            avanzar(); // ID
            esperar(Minilexer.Token.IGUAL, "Se esperaba '=' después del identificador");
            expr();
            esperar(Minilexer.Token.PUNTOYCOMA, "Se esperaba ';' al final de la asignación");
        }
        else if (actual().token == Minilexer.Token.PRINT) {
            // Print
            avanzar(); // 'print'
            esperar(Minilexer.Token.PARENTESIS_ABRE, "Se esperaba '(' después de 'print'");
            expr();
            esperar(Minilexer.Token.PARENTESIS_CIERRE, "Se esperaba ')' después de la expresión");
            esperar(Minilexer.Token.PUNTOYCOMA, "Se esperaba ';' al final del print");
        }
        else {
            throw new RuntimeException(
                "Sentencia no válida cerca de: '" + actual().lexema + "'"
            );
        }
    }

    // Expr → Term { ('+' | '-') Term }
    private void expr() {
        term();
        while (actual().token == Minilexer.Token.MAS ||
               actual().token == Minilexer.Token.MENOS) {
            avanzar(); // + o -
            term();
        }
    }

    // Term → Factor { ('*' | '/') Factor }
    private void term() {
        factor();
        while (actual().token == Minilexer.Token.MULTIPLICACION ||
               actual().token == Minilexer.Token.DIVISION) {
            avanzar(); // * o /
            factor();
        }
    }

    // Factor → ID | NUM | '(' Expr ')'
    private void factor() {
        switch (actual().token) {
            case IDENTIFICADOR:
            case LITERAL_NUMERICO:
                avanzar();
                break;
            case PARENTESIS_ABRE:
                avanzar();
                expr();
                esperar(Minilexer.Token.PARENTESIS_CIERRE, "Faltaba ')'");
                break;
            default:
                throw new RuntimeException(
                    "Factor no válido: '" + actual().lexema + "'"
                );
        }
    }
}
