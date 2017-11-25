package com.pythonteam.compilador.AST;

public class IntConst implements Expresion {
    public final int value;

    public IntConst(int value) {
        this.value = value;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
