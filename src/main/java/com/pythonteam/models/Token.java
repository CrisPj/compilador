package com.pythonteam.models;

import com.pythonteam.compilador.lexico.Tipo;

public class Token {

    Tipo tipo;
    String lexema;
    int posicion;
    int linea;
    Object valor;
    String clasificacion;

    public Token(Tipo tipo, String lexema, int linea, int posicion) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = posicion;
        this.linea = linea;
        if (tipo == Tipo.ID)
        this.valor = 0;
    }
}
