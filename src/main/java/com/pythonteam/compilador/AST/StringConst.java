package com.pythonteam.compilador.AST;

public class StringConst implements Expresion {
    public final String value;

    public StringConst(String value) {
        this.value = value;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringConst) {
            return this.value == ((StringConst)obj).value;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
