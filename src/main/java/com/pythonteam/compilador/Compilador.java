package com.pythonteam.compilador;

import com.pythonteam.compilador.lexico.Lexico;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Compilador {
    public Compilador(RandomAccessFile buffer) throws IOException {
        Lexico lex = new Lexico(buffer);
        lex.imprimir();
        Sintactico sin = new Sintactico();
        Semantico semantico = new Semantico();

    }
}