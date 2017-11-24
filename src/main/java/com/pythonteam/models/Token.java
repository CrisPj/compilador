package com.pythonteam.models;

import com.pythonteam.compilador.lexico.Tipo;

public class Token {

    int id;
    Tipo tipo;
    int idGen;
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

    public int getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setIdGen(int idGen) {
        this.idGen = idGen;
    }

    public int getidGen() {
        return idGen;
    }

    public int getLinea() {
        return linea;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getLexema() {
        return lexema;
    }
}
