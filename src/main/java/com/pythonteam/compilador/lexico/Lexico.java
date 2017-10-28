package com.pythonteam.compilador.lexico;

import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Lexico {

    private final static Map<String, Tipo> palabrasReservadas;
    private final static Map<Character, Tipo> puntuacion;
    int cont = 0;
    int pos = 1;
    int line = 1;
    char caracter;
    static
    {
        palabrasReservadas = new HashMap<String, Tipo>();
        palabrasReservadas.put("main", Tipo.MAIN);
        palabrasReservadas.put("print", Tipo.PRINT);
        palabrasReservadas.put("read", Tipo.READ);
        palabrasReservadas.put("string", Tipo.STRING);
        palabrasReservadas.put("true", Tipo.TRUE);
        palabrasReservadas.put("while", Tipo.WHILE);

        puntuacion = new HashMap<Character, Tipo>();
        puntuacion.put('(',Tipo.PARENIZQ);
        puntuacion.put(')',Tipo.PARENDER);
        puntuacion.put('{',Tipo.LLAVEIZQ);
        puntuacion.put('}',Tipo.LLAVEDER);
        puntuacion.put(',',Tipo.COMA);
        puntuacion.put('=',Tipo.ASIGNACION);
        puntuacion.put('!',Tipo.EXCLAMACION);
        puntuacion.put('"',Tipo.CADENA);
        puntuacion.put('&',Tipo.AND);
        puntuacion.put('|',Tipo.OR);
        puntuacion.put('"',Tipo.CADENA);
        puntuacion.put('>',Tipo.MAYOR);
        puntuacion.put('<',Tipo.MENOR);
        puntuacion.put('*',Tipo.POR);
        puntuacion.put('/',Tipo.ENTRE);
        puntuacion.put('+',Tipo.MAS);
        puntuacion.put('-',Tipo.MENOS);
    }

    public Lexico(RandomAccessFile buffer) throws IOException {
        TablaSimbolos.empty();
        pos = 1;
        line = 1;
        cont = 0;
        buffer.seek(0);

        Transiciones transiciones = new Transiciones();

        transiciones.addTransition(0, 1, 'b');
        transiciones.addTransition(1, 2, 'o');
        transiciones.addTransition(2, 3, 'o');
        transiciones.addTransition(3, 4, 'l');
        transiciones.addTransition(4, 5, 'e');
        transiciones.addTransition(5, 6, 'a');
        transiciones.addTransition(6, 7, 'n');

        transiciones.addTransition(0, 8, 'e');
        transiciones.addTransition(8, 9, 'l');
        transiciones.addTransition(9, 10, 's');
        transiciones.addTransition(10, 11, 'e');

        transiciones.addTransition(0, 12, 'f');
        transiciones.addTransition(12, 8, 'a');

        transiciones.addTransition(0, 13, 'i');
        transiciones.addTransition(13, 14, 'f');

        transiciones.addTransition(13, 15, 'n');
        transiciones.addTransition(15, 16, 't');

        


        Set<Integer> acceptingStates = new HashSet<>(Arrays.asList(7,11,14,16));
        DFA dfa = new DFA(transiciones, 0, acceptingStates);
System.out.println(dfa.matches("fi"));
System.out.println(dfa.matches("if"));
System.out.println(dfa.matches("int"));


System.exit(0);
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
                return new Token(Tipo.ENTRE, "/", line, pos );
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
            if(caracter == '\t')
            {
                pos++;
                caracter = getChar(buffer);
            }
            if (!(caracter == '\n' || caracter == '\t') && Character.isWhitespace(caracter))
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
            return new Token(Tipo.INT,palabra,line,pos);
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
            Tipo tipo = palabrasReservadas.get(palabra);
            if (tipo != null)
                return new Token(tipo, palabra,line,pos-palabra.length());

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
            return new Token(Tipo.CADENA,palabra+caracter,line,pos);
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
