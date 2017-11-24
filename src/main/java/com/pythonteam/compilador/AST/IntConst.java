package com.pythonteam.compilador.AST;

public class IntConst implements Expresion {
    public final int value;

    public IntConst(int value) {
        this.value = value;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntConst) {
            return this.value == ((IntConst)obj).value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
