public class Minilexer{
    public enum Token{
        INDENTIFICADOR, LITERAL_NUMERICO, 
        PRINT, IGUAL, PUNTOYOCMA, PARENTESIS_ABRE, PARENTESIS_CIERRE, MAS,
        MENOS, MULTIPLICACION, DIVISION, EOF

    }
    public class Tipo{
        String lexema;
        Token token;

    }
    public class AnalizadorLexico{
        String entrada= " x = 5; print(x); ";
        int posicion= 0; 
        
    }

}

    
