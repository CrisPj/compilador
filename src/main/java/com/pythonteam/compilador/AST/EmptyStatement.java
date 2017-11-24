package com.pythonteam.compilador.AST;

public class EmptyStatement implements Sentencia {
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof EmptyStatement);
    }

    @Override
    public int hashCode() {
        return 2098345;
    }

    @Override
    public String toString() {
        return "{}";
    }
}
