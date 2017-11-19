package com.pythonteam.compilador.lexico;

import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Lexico {

    private final static Set<Character> delimitadores;
    private final DFA DFAIDs;
    private final DFA DFAOpAritmeticos;
    private final DFA DFAOpRel;
    private final DFA DFADelim;
    private DFA numero;
    private RandomAccessFile buffer = null;
    int cont = 0;
    int pos = 1;
    int line = 1;
    char caracter;
    static
    {
        delimitadores = new HashSet<>();

        delimitadores.add('(');
        delimitadores.add(')');
        delimitadores.add('{');
        delimitadores.add('}');
        delimitadores.add(',');
        delimitadores.add('!');
        delimitadores.add('"');
        delimitadores.add('&');
        delimitadores.add('|');
        delimitadores.add('"');
        delimitadores.add('*');
        delimitadores.add('/');
        delimitadores.add('+');
        delimitadores.add('-');
        delimitadores.add('=');
        delimitadores.add('<');
        delimitadores.add('>');
        delimitadores.add(' ');
        delimitadores.add('ퟀ');
        delimitadores.add('\uFFFF');
        delimitadores.add('\n');
        delimitadores.add('\t');
    }

    private DFA palabrasReservadas;

    public Lexico(RandomAccessFile buffer) throws IOException {
        this.buffer = buffer;
        TablaSimbolos.empty();
        pos = 1;
        line = 1;
        cont = 0;
        buffer.seek(0);

        Transiciones TOperadoresAritmeticos = new Transiciones();
        TOperadoresAritmeticos.addTransicion(0,1,'+');
        TOperadoresAritmeticos.addTransicion(0,1,'-');
        TOperadoresAritmeticos.addTransicion(0,1,'=');
        TOperadoresAritmeticos.addTransicion(0,1,'*');
        TOperadoresAritmeticos.addTransicion(0,1,'/');
        Set<Integer> estadosFinalesOPArit = new HashSet<>(Arrays.asList(1));
        DFAOpAritmeticos = new DFA(TOperadoresAritmeticos, 0, estadosFinalesOPArit);


        Transiciones TOpRel = new Transiciones();
        TOpRel.addTransicion(0,1,'<');
        TOpRel.addTransicion(0,1,'>');
        TOpRel.addTransicion(0,1,'!');
        TOpRel.addTransicion(0,1,'&');
        TOpRel.addTransicion(0,1,'|');
        Set<Integer> estadosFinalesOPRel = new HashSet<>(Arrays.asList(1));
        DFAOpRel = new DFA(TOpRel, 0, estadosFinalesOPRel);


        Transiciones TDelim = new Transiciones();
        TDelim.addTransicion(0,1,'(');
        TDelim.addTransicion(0,1,')');
        TDelim.addTransicion(0,1,'{');
        TDelim.addTransicion(0,1,'}');
        TDelim.addTransicion(0,1,',');
        Set<Integer> estadosFinalesDelim= new HashSet<>(Arrays.asList(1));
        DFADelim = new DFA(TDelim, 0, estadosFinalesDelim);


        Transiciones TID = new Transiciones();
        TID.addTransicion(0,1,'a');
        TID.addTransicion(0,1,'b');
        TID.addTransicion(0,1,'c');
        TID.addTransicion(0,1,'d');
        TID.addTransicion(0,1,'e');
        TID.addTransicion(0,1,'f');
        TID.addTransicion(0,1,'g');
        TID.addTransicion(0,1,'h');
        TID.addTransicion(0,1,'i');
        TID.addTransicion(0,1,'j');
        TID.addTransicion(0,1,'k');
        TID.addTransicion(0,1,'l');
        TID.addTransicion(0,1,'m');
        TID.addTransicion(0,1,'n');
        TID.addTransicion(0,1,'o');
        TID.addTransicion(0,1,'p');
        TID.addTransicion(0,1,'q');
        TID.addTransicion(0,1,'r');
        TID.addTransicion(0,1,'s');
        TID.addTransicion(0,1,'t');
        TID.addTransicion(0,1,'u');
        TID.addTransicion(0,1,'v');
        TID.addTransicion(0,1,'w');
        TID.addTransicion(0,1,'x');
        TID.addTransicion(0,1,'y');
        TID.addTransicion(0,1,'z');
        TID.addTransicion(0,1,'A');
        TID.addTransicion(0,1,'B');
        TID.addTransicion(0,1,'C');
        TID.addTransicion(0,1,'D');
        TID.addTransicion(0,1,'E');
        TID.addTransicion(0,1,'F');
        TID.addTransicion(0,1,'G');
        TID.addTransicion(0,1,'H');
        TID.addTransicion(0,1,'I');
        TID.addTransicion(0,1,'J');
        TID.addTransicion(0,1,'K');
        TID.addTransicion(0,1,'L');
        TID.addTransicion(0,1,'M');
        TID.addTransicion(0,1,'N');
        TID.addTransicion(0,1,'O');
        TID.addTransicion(0,1,'P');
        TID.addTransicion(0,1,'Q');
        TID.addTransicion(0,1,'R');
        TID.addTransicion(0,1,'S');
        TID.addTransicion(0,1,'T');
        TID.addTransicion(0,1,'U');
        TID.addTransicion(0,1,'V');
        TID.addTransicion(0,1,'W');
        TID.addTransicion(0,1,'X');
        TID.addTransicion(0,1,'Y');
        TID.addTransicion(0,1,'Z');

        TID.addTransicion(1,1,'a');
        TID.addTransicion(1,1,'b');
        TID.addTransicion(1,1,'c');
        TID.addTransicion(1,1,'d');
        TID.addTransicion(1,1,'e');
        TID.addTransicion(1,1,'f');
        TID.addTransicion(1,1,'g');
        TID.addTransicion(1,1,'h');
        TID.addTransicion(1,1,'i');
        TID.addTransicion(1,1,'j');
        TID.addTransicion(1,1,'k');
        TID.addTransicion(1,1,'l');
        TID.addTransicion(1,1,'m');
        TID.addTransicion(1,1,'n');
        TID.addTransicion(1,1,'o');
        TID.addTransicion(1,1,'p');
        TID.addTransicion(1,1,'q');
        TID.addTransicion(1,1,'r');
        TID.addTransicion(1,1,'s');
        TID.addTransicion(1,1,'t');
        TID.addTransicion(1,1,'u');
        TID.addTransicion(1,1,'v');
        TID.addTransicion(1,1,'w');
        TID.addTransicion(1,1,'x');
        TID.addTransicion(1,1,'y');
        TID.addTransicion(1,1,'z');
        TID.addTransicion(1,1,'A');
        TID.addTransicion(1,1,'B');
        TID.addTransicion(1,1,'C');
        TID.addTransicion(1,1,'D');
        TID.addTransicion(1,1,'E');
        TID.addTransicion(1,1,'F');
        TID.addTransicion(1,1,'G');
        TID.addTransicion(1,1,'H');
        TID.addTransicion(1,1,'I');
        TID.addTransicion(1,1,'J');
        TID.addTransicion(1,1,'K');
        TID.addTransicion(1,1,'L');
        TID.addTransicion(1,1,'M');
        TID.addTransicion(1,1,'N');
        TID.addTransicion(1,1,'O');
        TID.addTransicion(1,1,'P');
        TID.addTransicion(1,1,'Q');
        TID.addTransicion(1,1,'R');
        TID.addTransicion(1,1,'S');
        TID.addTransicion(1,1,'T');
        TID.addTransicion(1,1,'U');
        TID.addTransicion(1,1,'V');
        TID.addTransicion(1,1,'W');
        TID.addTransicion(1,1,'X');
        TID.addTransicion(1,1,'Y');
        TID.addTransicion(1,1,'Z');

        TID.addTransicion(1,1,'0');
        TID.addTransicion(1,1,'1');
        TID.addTransicion(1,1,'2');
        TID.addTransicion(1,1,'3');
        TID.addTransicion(1,1,'4');
        TID.addTransicion(1,1,'5');
        TID.addTransicion(1,1,'6');
        TID.addTransicion(1,1,'7');
        TID.addTransicion(1,1,'8');
        TID.addTransicion(1,1,'9');

        Set<Integer> estadosFinalesId = new HashSet<>(Arrays.asList(1));
        DFAIDs = new DFA(TID, 0, estadosFinalesId);


        Transiciones TNumero = new Transiciones();
        TNumero.addTransicion(0,1,'1');
        TNumero.addTransicion(0,1,'0');
        TNumero.addTransicion(0,1,'2');
        TNumero.addTransicion(0,1,'3');
        TNumero.addTransicion(0,1,'4');
        TNumero.addTransicion(0,1,'5');
        TNumero.addTransicion(0,1,'6');
        TNumero.addTransicion(0,1,'7');
        TNumero.addTransicion(0,1,'8');
        TNumero.addTransicion(0,1,'9');

        TNumero.addTransicion(1,1,'0');
        TNumero.addTransicion(1,1,'1');
        TNumero.addTransicion(1,1,'2');
        TNumero.addTransicion(1,1,'3');
        TNumero.addTransicion(1,1,'4');
        TNumero.addTransicion(1,1,'5');
        TNumero.addTransicion(1,1,'6');
        TNumero.addTransicion(1,1,'7');
        TNumero.addTransicion(1,1,'8');
        TNumero.addTransicion(1,1,'9');

        Set<Integer> estadosFinalesNum = new HashSet<>(Arrays.asList(1));
        numero = new DFA(TNumero, 0, estadosFinalesNum);

        Transiciones TpalabrasReservadas = new Transiciones();

        TpalabrasReservadas.addTransicion(0, 1, 'b');
        TpalabrasReservadas.addTransicion(1, 2, 'o');
        TpalabrasReservadas.addTransicion(2, 3, 'o');
        TpalabrasReservadas.addTransicion(3, 4, 'l');
        TpalabrasReservadas.addTransicion(4, 5, 'e');
        TpalabrasReservadas.addTransicion(5, 6, 'a');
        TpalabrasReservadas.addTransicion(6, 7, 'n');

        TpalabrasReservadas.addTransicion(0, 8, 'e');
        TpalabrasReservadas.addTransicion(8, 9, 'l');
        TpalabrasReservadas.addTransicion(9, 10, 's');
        TpalabrasReservadas.addTransicion(10, 11, 'e');

        TpalabrasReservadas.addTransicion(0, 12, 'f');
        TpalabrasReservadas.addTransicion(12, 13, 'a');
        TpalabrasReservadas.addTransicion(13, 14, 'l');
        TpalabrasReservadas.addTransicion(14, 15, 's');
        TpalabrasReservadas.addTransicion(15, 16, 'e');

        TpalabrasReservadas.addTransicion(0, 17, 'i');
        TpalabrasReservadas.addTransicion(17, 18, 'f');
        TpalabrasReservadas.addTransicion(17, 19, 'n');
        TpalabrasReservadas.addTransicion(19, 20, 't');


        TpalabrasReservadas.addTransicion(0, 22, 'm');
        TpalabrasReservadas.addTransicion(22, 23, 'a');
        TpalabrasReservadas.addTransicion(23, 24, 'i');
        TpalabrasReservadas.addTransicion(24, 25, 'n');

        TpalabrasReservadas.addTransicion(0, 26, 'p');
        TpalabrasReservadas.addTransicion(26, 27, 'r');
        TpalabrasReservadas.addTransicion(27, 28, 'i');
        TpalabrasReservadas.addTransicion(28, 29, 'n');
        TpalabrasReservadas.addTransicion(29, 30, 't');

        TpalabrasReservadas.addTransicion(0, 31, 'r');
        TpalabrasReservadas.addTransicion(31, 32, 'e');
        TpalabrasReservadas.addTransicion(32, 33, 'a');
        TpalabrasReservadas.addTransicion(33, 34, 'd');

        TpalabrasReservadas.addTransicion(0, 35, 's');
        TpalabrasReservadas.addTransicion(35, 36, 't');
        TpalabrasReservadas.addTransicion(36, 37, 'r');
        TpalabrasReservadas.addTransicion(37, 38, 'i');
        TpalabrasReservadas.addTransicion(38, 39, 'n');
        TpalabrasReservadas.addTransicion(39, 40, 'g');

        TpalabrasReservadas.addTransicion(0, 41, 't');
        TpalabrasReservadas.addTransicion(41, 42, 'r');
        TpalabrasReservadas.addTransicion(42, 43, 'u');
        TpalabrasReservadas.addTransicion(43, 44, 'e');

        TpalabrasReservadas.addTransicion(0, 45, 'w');
        TpalabrasReservadas.addTransicion(45, 46, 'h');
        TpalabrasReservadas.addTransicion(46, 47, 'i');
        TpalabrasReservadas.addTransicion(47, 48, 'l');
        TpalabrasReservadas.addTransicion(48, 49, 'e');


        Set<Integer> estadosFinales = new HashSet<>(Arrays.asList(7,11,14,16,18,20,25,30,34,40,44,49));
        palabrasReservadas = new DFA(TpalabrasReservadas, 0, estadosFinales);

        long si = buffer.length();
        getChar();
        while (cont <= si)
        {
            TablaSimbolos.add(getToken(buffer));
        }
    }

    private Token getToken(RandomAccessFile buffer) throws IOException {

        // Los comentarios son omitidos durante la lectura del archivo
        if (caracter == '\n')
        {
            int antPos,antLine;
            antPos = pos;
            antLine = line;
            pos = 1;
            line++;
            getChar();
            return new Token(Tipo.Numero, "SALTO",antLine,antPos);
        }
        if (caracter == '/')
        {
            getChar();
            if (caracter == '/') {
                cont += buffer.readLine().length() + 1;
                getChar();
                pos = 1;
                line++;
            }
            else if(esNumero(caracter))
            {
                return new Token(Tipo.OperadorAritmetico, "/", line, pos );
            }
        }
        quitarEspacios();
        String lexema = "";
        if (caracter == '"')
        {
            lexema+=caracter;
            getChar();
            while (caracter != '"')
            {
                lexema +=caracter;
                getChar();
            }
            lexema+=caracter;
            getChar();
            return new Token(Tipo.Texto,lexema,line,pos);
        }
        while (!delimitadores.contains(caracter))
        {
            lexema+=caracter;
            getChar();
        }

        if (palabrasReservadas.matches(lexema))
            return new Token(Tipo.PalabraReservada, lexema, line, pos-2);
        else if (numero.matches(lexema))
            return new Token(Tipo.Numero, lexema, line, pos-2);
        else if (DFAIDs.matches(lexema))
        {
            return new Token(Tipo.ID, lexema, line, pos-lexema.length());
        }
        else if (DFAOpAritmeticos.matches(""+caracter)  && lexema.length() == 0)
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.OperadorAritmetico, lexema, line, pos-2);
        }
        else if (DFAOpRel.matches(""+caracter) && lexema.length() == 0)
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.OperadorRelacional, lexema, line, pos-2);
        }
        else if (DFADelim.matches(""+caracter) && lexema.length() == 0)
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.Delimitador, lexema, line, pos-2);
        }
        else if (caracter == '\uFFFF'&& lexema.length() == 0)
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.EOF,lexema,line,pos-2);
        }
        else
        {
            getChar();
            return new Token(Tipo.ERROR,lexema,line,pos - lexema.length());
        }

    }

    private void quitarEspacios()
    {
        while (caracter == '\t' || Character.isWhitespace(caracter))
        {
                getChar();
        }
    }

    private void getChar()
    {
        cont++;
        pos++;
        try {
            caracter = (char)buffer.readByte();
        } catch (IOException e) {
            caracter = (char) -1;
        }
    }

    private boolean esNumero(char caracter)
    {
        return (caracter > '0' && caracter < '9');
    }
}
