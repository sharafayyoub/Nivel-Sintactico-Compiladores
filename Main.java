import java.util.List;

public class Main {
    public static void main(String[] args) {

        String codigo = "x = 5; print(x); x = (5 + 2) * 3 ;";

        // Usamos el constructor que recibe String
        Minilexer.AnalizadorLexico lexer = new Minilexer.AnalizadorLexico(codigo);
        List<Minilexer.Tipo> tokens = lexer.tokenizar();

        Parser parser = new Parser(tokens);

        try {
            parser.stmtList();
            System.out.println("Código válido.");
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}


