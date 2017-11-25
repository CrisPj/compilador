package com.pythonteam.compilador.AST;

public class WhileLoop implements Sentencia{
    public final Expresion condicion;
    public final Sentencia sentencias;

    public WhileLoop(Expresion condicion, Sentencia sentencias) {
        this.condicion = condicion;
        this.sentencias = sentencias;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
