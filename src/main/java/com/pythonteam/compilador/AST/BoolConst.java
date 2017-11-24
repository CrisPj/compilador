package com.pythonteam.compilador.AST;

public class BoolConst implements Expresion {
    public final boolean value;

    public BoolConst(boolean value) {
        this.value = value;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolConst) {
            return this.value == ((BoolConst)obj).value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value ? 1 : 0;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
