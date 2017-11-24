package com.pythonteam.compilador.AST;

public class Var implements  Expresion{
    public final String name;

    public Var(String name) {
        this.name = name;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Var) {
            return this.name.equals(((Var)obj).name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
