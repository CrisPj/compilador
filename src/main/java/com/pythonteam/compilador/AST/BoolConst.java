package com.pythonteam.compilador.AST;

public class BoolConst implements Expresion {
    public final boolean valor;

    public BoolConst(boolean valor) {
        this.valor = valor;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolConst) {
            return this.valor == ((BoolConst)obj).valor;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return valor ? 1 : 0;
    }

    @Override
    public String toString() {
        return ""+ valor;
    }
}
