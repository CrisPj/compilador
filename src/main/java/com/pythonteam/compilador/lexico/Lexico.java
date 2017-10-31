package com.pythonteam.compilador.lexico;

import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

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
        TOperadoresAritmeticos.addTransition(0,1,'+');
        TOperadoresAritmeticos.addTransition(0,1,'-');
        TOperadoresAritmeticos.addTransition(0,1,'=');
        TOperadoresAritmeticos.addTransition(0,1,'*');
        TOperadoresAritmeticos.addTransition(0,1,'/');
        Set<Integer> estadosFinalesOPArit = new HashSet<>(Arrays.asList(1));
        DFAOpAritmeticos = new DFA(TOperadoresAritmeticos, 0, estadosFinalesOPArit);


        Transiciones TOpRel = new Transiciones();
        TOpRel.addTransition(0,1,'<');
        TOpRel.addTransition(0,1,'>');
        TOpRel.addTransition(0,1,'!');
        TOpRel.addTransition(0,1,'&');
        TOpRel.addTransition(0,1,'|');
        Set<Integer> estadosFinalesOPRel = new HashSet<>(Arrays.asList(1));
        DFAOpRel = new DFA(TOpRel, 0, estadosFinalesOPRel);


        Transiciones TDelim = new Transiciones();
        TDelim.addTransition(0,1,'(');
        TDelim.addTransition(0,1,')');
        TDelim.addTransition(0,1,'{');
        TDelim.addTransition(0,1,'}');
        TDelim.addTransition(0,1,',');
        Set<Integer> estadosFinalesDelim= new HashSet<>(Arrays.asList(1));
        DFADelim = new DFA(TDelim, 0, estadosFinalesDelim);


        Transiciones TID = new Transiciones();
        TID.addTransition(0,1,'a');
        TID.addTransition(0,1,'b');
        TID.addTransition(0,1,'c');
        TID.addTransition(0,1,'d');
        TID.addTransition(0,1,'e');
        TID.addTransition(0,1,'f');
        TID.addTransition(0,1,'g');
        TID.addTransition(0,1,'h');
        TID.addTransition(0,1,'i');
        TID.addTransition(0,1,'j');
        TID.addTransition(0,1,'k');
        TID.addTransition(0,1,'l');
        TID.addTransition(0,1,'m');
        TID.addTransition(0,1,'n');
        TID.addTransition(0,1,'o');
        TID.addTransition(0,1,'p');
        TID.addTransition(0,1,'q');
        TID.addTransition(0,1,'r');
        TID.addTransition(0,1,'s');
        TID.addTransition(0,1,'t');
        TID.addTransition(0,1,'u');
        TID.addTransition(0,1,'v');
        TID.addTransition(0,1,'w');
        TID.addTransition(0,1,'x');
        TID.addTransition(0,1,'y');
        TID.addTransition(0,1,'z');
        TID.addTransition(0,1,'A');
        TID.addTransition(0,1,'B');
        TID.addTransition(0,1,'C');
        TID.addTransition(0,1,'D');
        TID.addTransition(0,1,'E');
        TID.addTransition(0,1,'F');
        TID.addTransition(0,1,'G');
        TID.addTransition(0,1,'H');
        TID.addTransition(0,1,'I');
        TID.addTransition(0,1,'J');
        TID.addTransition(0,1,'K');
        TID.addTransition(0,1,'L');
        TID.addTransition(0,1,'M');
        TID.addTransition(0,1,'N');
        TID.addTransition(0,1,'O');
        TID.addTransition(0,1,'P');
        TID.addTransition(0,1,'Q');
        TID.addTransition(0,1,'R');
        TID.addTransition(0,1,'S');
        TID.addTransition(0,1,'T');
        TID.addTransition(0,1,'U');
        TID.addTransition(0,1,'V');
        TID.addTransition(0,1,'W');
        TID.addTransition(0,1,'X');
        TID.addTransition(0,1,'Y');
        TID.addTransition(0,1,'Z');

        TID.addTransition(1,1,'a');
        TID.addTransition(1,1,'b');
        TID.addTransition(1,1,'c');
        TID.addTransition(1,1,'d');
        TID.addTransition(1,1,'e');
        TID.addTransition(1,1,'f');
        TID.addTransition(1,1,'g');
        TID.addTransition(1,1,'h');
        TID.addTransition(1,1,'i');
        TID.addTransition(1,1,'j');
        TID.addTransition(1,1,'k');
        TID.addTransition(1,1,'l');
        TID.addTransition(1,1,'m');
        TID.addTransition(1,1,'n');
        TID.addTransition(1,1,'o');
        TID.addTransition(1,1,'p');
        TID.addTransition(1,1,'q');
        TID.addTransition(1,1,'r');
        TID.addTransition(1,1,'s');
        TID.addTransition(1,1,'t');
        TID.addTransition(1,1,'u');
        TID.addTransition(1,1,'v');
        TID.addTransition(1,1,'w');
        TID.addTransition(1,1,'x');
        TID.addTransition(1,1,'y');
        TID.addTransition(1,1,'z');
        TID.addTransition(1,1,'A');
        TID.addTransition(1,1,'B');
        TID.addTransition(1,1,'C');
        TID.addTransition(1,1,'D');
        TID.addTransition(1,1,'E');
        TID.addTransition(1,1,'F');
        TID.addTransition(1,1,'G');
        TID.addTransition(1,1,'H');
        TID.addTransition(1,1,'I');
        TID.addTransition(1,1,'J');
        TID.addTransition(1,1,'K');
        TID.addTransition(1,1,'L');
        TID.addTransition(1,1,'M');
        TID.addTransition(1,1,'N');
        TID.addTransition(1,1,'O');
        TID.addTransition(1,1,'P');
        TID.addTransition(1,1,'Q');
        TID.addTransition(1,1,'R');
        TID.addTransition(1,1,'S');
        TID.addTransition(1,1,'T');
        TID.addTransition(1,1,'U');
        TID.addTransition(1,1,'V');
        TID.addTransition(1,1,'W');
        TID.addTransition(1,1,'X');
        TID.addTransition(1,1,'Y');
        TID.addTransition(1,1,'Z');

        TID.addTransition(1,1,'0');
        TID.addTransition(1,1,'1');
        TID.addTransition(1,1,'2');
        TID.addTransition(1,1,'3');
        TID.addTransition(1,1,'4');
        TID.addTransition(1,1,'5');
        TID.addTransition(1,1,'6');
        TID.addTransition(1,1,'7');
        TID.addTransition(1,1,'8');
        TID.addTransition(1,1,'9');

        Set<Integer> estadosFinalesId = new HashSet<>(Arrays.asList(1));
        DFAIDs = new DFA(TID, 0, estadosFinalesId);


        Transiciones TNumero = new Transiciones();
        TNumero.addTransition(0,1,'1');
        TNumero.addTransition(0,1,'2');
        TNumero.addTransition(0,1,'3');
        TNumero.addTransition(0,1,'4');
        TNumero.addTransition(0,1,'5');
        TNumero.addTransition(0,1,'6');
        TNumero.addTransition(0,1,'7');
        TNumero.addTransition(0,1,'8');
        TNumero.addTransition(0,1,'9');

        TNumero.addTransition(1,1,'0');
        TNumero.addTransition(1,1,'1');
        TNumero.addTransition(1,1,'2');
        TNumero.addTransition(1,1,'3');
        TNumero.addTransition(1,1,'4');
        TNumero.addTransition(1,1,'5');
        TNumero.addTransition(1,1,'6');
        TNumero.addTransition(1,1,'7');
        TNumero.addTransition(1,1,'8');
        TNumero.addTransition(1,1,'9');

        Set<Integer> estadosFinalesNum = new HashSet<>(Arrays.asList(1));
        numero = new DFA(TNumero, 0, estadosFinalesNum);

        Transiciones TpalabrasReservadas = new Transiciones();

        TpalabrasReservadas.addTransition(0, 1, 'b');
        TpalabrasReservadas.addTransition(1, 2, 'o');
        TpalabrasReservadas.addTransition(2, 3, 'o');
        TpalabrasReservadas.addTransition(3, 4, 'l');
        TpalabrasReservadas.addTransition(4, 5, 'e');
        TpalabrasReservadas.addTransition(5, 6, 'a');
        TpalabrasReservadas.addTransition(6, 7, 'n');

        TpalabrasReservadas.addTransition(0, 8, 'e');
        TpalabrasReservadas.addTransition(8, 9, 'l');
        TpalabrasReservadas.addTransition(9, 10, 's');
        TpalabrasReservadas.addTransition(10, 11, 'e');

        TpalabrasReservadas.addTransition(0, 12, 'f');
        TpalabrasReservadas.addTransition(12, 13, 'a');
        TpalabrasReservadas.addTransition(13, 14, 'l');
        TpalabrasReservadas.addTransition(14, 15, 's');
        TpalabrasReservadas.addTransition(15, 16, 'e');

        TpalabrasReservadas.addTransition(0, 17, 'i');
        TpalabrasReservadas.addTransition(17, 18, 'f');
        TpalabrasReservadas.addTransition(17, 19, 'n');
        TpalabrasReservadas.addTransition(19, 20, 't');


        TpalabrasReservadas.addTransition(0, 22, 'm');
        TpalabrasReservadas.addTransition(22, 23, 'a');
        TpalabrasReservadas.addTransition(23, 24, 'i');
        TpalabrasReservadas.addTransition(24, 25, 'n');

        TpalabrasReservadas.addTransition(0, 26, 'p');
        TpalabrasReservadas.addTransition(26, 27, 'r');
        TpalabrasReservadas.addTransition(27, 28, 'i');
        TpalabrasReservadas.addTransition(28, 29, 'n');
        TpalabrasReservadas.addTransition(29, 30, 't');

        TpalabrasReservadas.addTransition(0, 31, 'r');
        TpalabrasReservadas.addTransition(31, 32, 'e');
        TpalabrasReservadas.addTransition(32, 33, 'a');
        TpalabrasReservadas.addTransition(33, 34, 'd');

        TpalabrasReservadas.addTransition(0, 35, 's');
        TpalabrasReservadas.addTransition(35, 36, 't');
        TpalabrasReservadas.addTransition(36, 37, 'r');
        TpalabrasReservadas.addTransition(37, 38, 'i');
        TpalabrasReservadas.addTransition(38, 39, 'n');
        TpalabrasReservadas.addTransition(39, 40, 'g');

        TpalabrasReservadas.addTransition(0, 41, 't');
        TpalabrasReservadas.addTransition(41, 42, 'r');
        TpalabrasReservadas.addTransition(42, 43, 'u');
        TpalabrasReservadas.addTransition(43, 44, 'e');

        TpalabrasReservadas.addTransition(0, 45, 'w');
        TpalabrasReservadas.addTransition(45, 46, 'h');
        TpalabrasReservadas.addTransition(46, 47, 'i');
        TpalabrasReservadas.addTransition(47, 48, 'l');
        TpalabrasReservadas.addTransition(48, 49, 'e');


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
            return new Token(Tipo.PalabraReservada, lexema, line, pos);
        else if (numero.matches(lexema))
            return new Token(Tipo.Numero, lexema, line, pos);
        else if (DFAIDs.matches(lexema))
        {
            return new Token(Tipo.ID, lexema, line, pos);
        }
        else if (DFAOpAritmeticos.matches(""+caracter))
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.OperadorAritmetico, lexema, line, pos);
        }
        else if (DFAOpRel.matches(""+caracter))
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.OperadorRelacional, lexema, line, pos);
        }
        else if (DFADelim.matches(""+caracter))
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.Delimitador, lexema, line, pos);
        }
        else if (caracter == '\uFFFF')
        {
            lexema = ""+caracter;
            getChar();
            return new Token(Tipo.EOF,lexema,line,pos);
        }
        else
        {
            getChar();
            return new Token(Tipo.ERROR,lexema,line,pos);
        }

    }

    private void quitarEspacios()
    {
        while (caracter == '\n' || caracter == '\t' || Character.isWhitespace(caracter))
        {
            if (caracter == '\n')
            {
                pos = 1;
                line++;
                getChar();
            }
            if (!(caracter == '\n') && Character.isWhitespace(caracter))
            {
                pos++;
                getChar();
            }

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

    public void imprimir() {
        System.out.println(TablaSimbolos.impr());
    }
}
