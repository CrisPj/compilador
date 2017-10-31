package com.pythonteam.compilador.lexico;

import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Lexico {

    private final static Map<Character, Tipo> puntuacion;
    private final DFA numero;
    int cont = 0;
    int pos = 1;
    int line = 1;
    char caracter;
    static
    {
        puntuacion = new HashMap<Character, Tipo>();
        puntuacion.put('(',Tipo.Delimitador);
        puntuacion.put(')',Tipo.Delimitador);
        puntuacion.put('{',Tipo.Delimitador);
        puntuacion.put('}',Tipo.Delimitador);
        puntuacion.put(',',Tipo.Delimitador);
        puntuacion.put('!',Tipo.Delimitador);
        puntuacion.put('"',Tipo.Delimitador);
        puntuacion.put('&',Tipo.OperadorRelacional);
        puntuacion.put('|',Tipo.OperadorRelacional);
        puntuacion.put('"',Tipo.OperadorRelacional);
        puntuacion.put('*',Tipo.OperadorAritmetico);
        puntuacion.put('/',Tipo.OperadorAritmetico);
        puntuacion.put('+',Tipo.OperadorAritmetico);
        puntuacion.put('-',Tipo.OperadorAritmetico);
    }

    private DFA palabrasReservadas;

    public Lexico(RandomAccessFile buffer) throws IOException {
        TablaSimbolos.empty();
        pos = 1;
        line = 1;
        cont = 0;
        buffer.seek(0);

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


        TpalabrasReservadas.addTransition(0, 19, 'i');
        TpalabrasReservadas.addTransition(19, 20, 'n');
        TpalabrasReservadas.addTransition(20, 21, 't');


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
        TpalabrasReservadas.addTransition(36, 37, 't');
        TpalabrasReservadas.addTransition(37, 38, 'r');
        TpalabrasReservadas.addTransition(38, 39, 'i');
        TpalabrasReservadas.addTransition(39, 40, 'n');
        TpalabrasReservadas.addTransition(40, 41, 'g');

        TpalabrasReservadas.addTransition(0, 42, 't');
        TpalabrasReservadas.addTransition(42, 43, 'r');
        TpalabrasReservadas.addTransition(43, 44, 'u');
        TpalabrasReservadas.addTransition(44, 45, 'e');

        TpalabrasReservadas.addTransition(0, 46, 'w');
        TpalabrasReservadas.addTransition(46, 47, 'h');
        TpalabrasReservadas.addTransition(47, 48, 'i');
        TpalabrasReservadas.addTransition(48, 49, 'l');
        TpalabrasReservadas.addTransition(49, 50, 'e');


        Set<Integer> estadosFinales = new HashSet<>(Arrays.asList(7,11,14,16,18,21,25,30,34,41,45,50));
        palabrasReservadas = new DFA(TpalabrasReservadas, 0, estadosFinales);

        long si = buffer.length();
        caracter = getChar(buffer);
        while (cont <= si)
        {
            TablaSimbolos.add(getToken(buffer));
        }
    }

    private Token getToken(RandomAccessFile buffer) throws IOException {

        // Los comentarios son omitidos durante la lectura del archivo
        if (caracter == '/')
        {
            caracter = getChar(buffer);
            if (caracter == '/') {
                cont += buffer.readLine().length() + 1;
                caracter = getChar(buffer);
                pos = 1;
                line++;
            }
            else if(esNumero(caracter))
            {
                return new Token(Tipo.OperadorAritmetico, "/", line, pos );
            }
        }
        while (caracter == '\n' || caracter == '\t' || Character.isWhitespace(caracter))
        {
            if (caracter == '\n')
            {
                pos = 1;
                line++;
                caracter = getChar(buffer);
            }
            if (!(caracter == '\n') && Character.isWhitespace(caracter))
            {
                pos++;
                caracter = getChar(buffer);
            }

        }
        if (esNumero(caracter))
        {
            String palabra = "" + caracter;
            pos++;
            caracter = getChar(buffer);

            boolean valido = true;
            while (esNumero(caracter) || esLetra(caracter))
            {
                if (esLetra(caracter))
                {
                    valido = false;
                }
                palabra += caracter;
                pos++;
                caracter = getChar(buffer);
            }
            if (!valido)
                return new Token(Tipo.ERROR,palabra,line,pos);
            return new Token(Tipo.Numero,palabra,line,pos);
        }
        if (esLetra(caracter) )
        {
            String palabra = ""+caracter;
            pos++;
            caracter = getChar(buffer);

            while (esLetraNumero(caracter))
            {
                palabra += caracter;
                pos++;
                caracter = getChar(buffer);
            }
            //Tipo tipo = palabrasReservadas.matches(palabra);
            if (palabrasReservadas.matches(palabra))
                return new Token(Tipo.PalabraReservada, palabra,line,pos-palabra.length());

                return new Token(Tipo.ID, palabra,line,pos-palabra.length());
        }
        if (caracter == '\uFFFF'){
            return new Token(Tipo.EOF, "EOF",line,pos);
        }
        if (caracter == '"')
        {
            String palabra = "" + caracter;
            pos++;
            caracter = getChar(buffer);
            while (caracter != '"')
            {
                if (caracter == '\n') {
                    pos = 1;
                    line++;
                }
                if (caracter == '\uFFFF')
                {
                    return new Token(Tipo.ERROR,palabra,line,pos);
                }
                palabra+=caracter;
                pos++;
                caracter = getChar(buffer);
            }
            return new Token(Tipo.Texto,palabra+caracter,line,pos);
        }
        if (caracter == '<')
        {
            getChar(buffer);
            pos++;
            if (caracter == '=')
            {
                caracter = getChar(buffer);
                pos++;
                return new Token(Tipo.OperadorRelacional,"<=",line,pos);
            }
            return new Token(Tipo.OperadorRelacional, "<",line,pos);

        }
        if (caracter == '>')
        {
            getChar(buffer);
            pos++;
            if (caracter == '=')
            {
                caracter = getChar(buffer);
                pos++;
                return new Token(Tipo.OperadorRelacional,">=",line,pos);
            }
            return new Token(Tipo.OperadorRelacional, ">",line,pos);

        }
        if (caracter == '=')
        {
            caracter = getChar(buffer);
            pos++;
            if (caracter == '=')
            {
                caracter = getChar(buffer);
                pos++;
                return new Token(Tipo.OperadorRelacional,"==",line,pos);
            }
            return new Token(Tipo.OperadorAritmetico, "=",line,pos);
        }
        Tipo tipo = puntuacion.get(caracter);
        char old = caracter;
        caracter = getChar(buffer);
        pos++;
        if (tipo != null)
            return new Token(tipo, ""+old, line, pos-1);

            return new Token(Tipo.ERROR, ""+old, line,pos-1);
    }

    private boolean esLetraNumero(char caracter) {
        return esNumero(caracter) || esLetra(caracter);
    }

    private char getChar(RandomAccessFile buffer)
    {
        cont++;
        char result = 0;
        try {
            result = (char)buffer.readByte();
        } catch (IOException e) {
            return (char)-1;
        }
        return result;
    }

    private boolean esLetra(char caracter)
    {
        return (caracter >= 'a' && caracter <= 'z') || (caracter >= 'A' && caracter <= 'Z');
    }

    private boolean esNumero(char caracter)
    {
        return (caracter > '0' && caracter < '9');
    }

    public void imprimir() {
        System.out.println(TablaSimbolos.impr());
    }
}
