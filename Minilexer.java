import java.util.ArrayList;
import java.util.List;

public class Minilexer {

    public enum Token {
        IDENTIFICADOR, LITERAL_NUMERICO,
        PRINT, IGUAL, PUNTOYCOMA, PARENTESIS_ABRE, PARENTESIS_CIERRE,
        MAS, MENOS, MULTIPLICACION, DIVISION,
        EOF
    }

    public static class Tipo {
        public String lexema;
        public Token token;

        public Tipo(String lexema, Token token) {
            this.lexema = lexema;
            this.token = token;
        }

        @Override
        public String toString() {
            return "(" + token + ", \"" + lexema + "\")";
        }
    }

    public static class AnalizadorLexico {

        private final String entrada;
        private int posicion = 0;

        public AnalizadorLexico(String entrada) {
            this.entrada = entrada;
        }

        public List<Tipo> tokenizar() {
            List<Tipo> tokens = new ArrayList<>();
            Tipo t;

            do {
                t = siguienteToken();
                tokens.add(t);
            } while (t.token != Token.EOF);

            return tokens;
        }

        private void saltarEspacios() {
            while (posicion < entrada.length()
                    && Character.isWhitespace(entrada.charAt(posicion))) {
                posicion++;
            }
        }

        private Tipo siguienteToken() {
            saltarEspacios();

            if (posicion >= entrada.length()) {
                return new Tipo("", Token.EOF);
            }

            char c = entrada.charAt(posicion);

            switch (c) {
                case '=':
                    posicion++;
                    return new Tipo("=", Token.IGUAL);
                case ';':
                    posicion++;
                    return new Tipo(";", Token.PUNTOYCOMA);
                case '(':
                    posicion++;
                    return new Tipo("(", Token.PARENTESIS_ABRE);
                case ')':
                    posicion++;
                    return new Tipo(")", Token.PARENTESIS_CIERRE);
                case '+':
                    posicion++;
                    return new Tipo("+", Token.MAS);
                case '-':
                    posicion++;
                    return new Tipo("-", Token.MENOS);
                case '*':
                    posicion++;
                    return new Tipo("*", Token.MULTIPLICACION);
                case '/':
                    posicion++;
                    return new Tipo("/", Token.DIVISION);
            }

            // Identificadores o print
            if (esLetra(c) || c == '_') {
                StringBuilder sb = new StringBuilder();
                while (posicion < entrada.length()) {
                    char ch = entrada.charAt(posicion);
                    if (esLetra(ch) || esDigito(ch) || ch == '_') {
                        sb.append(ch);
                        posicion++;
                    } else {
                        break;
                    }
                }
                String lexema = sb.toString();
                if (lexema.equals("print")) {
                    return new Tipo(lexema, Token.PRINT);
                } else {
                    return new Tipo(lexema, Token.IDENTIFICADOR);
                }
            }

            // Números
            if (esDigito(c)) {
                StringBuilder sb = new StringBuilder();
                while (posicion < entrada.length()
                        && esDigito(entrada.charAt(posicion))) {
                    sb.append(entrada.charAt(posicion));
                    posicion++;
                }
                return new Tipo(sb.toString(), Token.LITERAL_NUMERICO);
            }

            throw new RuntimeException(
                "Carácter no reconocido: '" + c + "' en posición " + posicion
            );
        }

        private boolean esLetra(char c) {
            return (c >= 'a' && c <= 'z') ||
                   (c >= 'A' && c <= 'Z');
        }

        private boolean esDigito(char c) {
            return (c >= '0' && c <= '9');
        }
    }
}
