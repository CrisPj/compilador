package com.pythonteam.compilador.lexico;

import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

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
        palabrasReservadas.put("boolean", Tipo.BOOL);
        palabrasReservadas.put("else", Tipo.ELSE);
        palabrasReservadas.put("false", Tipo.FALSE);
        palabrasReservadas.put("if", Tipo.IF);
        palabrasReservadas.put("int", Tipo.ENTERO);
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

        long si = buffer.length();
        caracter = getChar(buffer);
        while (cont <= si)
        {
            TablaSimbolos.add(getToken(buffer));
        }
    }

    private Token getToken(RandomAccessFile buffer) throws IOException {

        if (caracter == '/')
        {
            caracter = getChar(buffer);
            if (caracter == '/')
            {
                cont+=buffer.readLine().length()+1;
                caracter = getChar(buffer);
                pos=1;
                line++;
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

    private char getChar(RandomAccessFile buffer) {
        cont++;
        char result = 0;
        try {
            result = (char)buffer.readByte();
        } catch (IOException e) {
            return (char)-1;
        }
        return result;
    }

    boolean esLetra(char caracter)
    {
        return (caracter >= 'a' && caracter <= 'z') || (caracter >= 'A' && caracter <= 'Z');
    }

    boolean esNumero(char caracter)
    {
        return (caracter > '0' && caracter < '9');
    }

    public void imprimir() {
        System.out.println(TablaSimbolos.impr());
    }
}
