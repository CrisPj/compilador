package com.pythonteam.compilador.AST;

public class WhileLoop implements Sentencia{
    public final Expresion cabeza;
    public final Sentencia cuerpo;

    public WhileLoop(Expresion cabeza, Sentencia cuerpo) {
        this.cabeza= cabeza;
        this.cuerpo = cuerpo;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
