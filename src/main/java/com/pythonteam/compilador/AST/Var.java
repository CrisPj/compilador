package com.pythonteam.compilador.AST;

public class Var implements  Expresion{
    public final String name;

    public Var(String name) {
        this.name = name;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

}
