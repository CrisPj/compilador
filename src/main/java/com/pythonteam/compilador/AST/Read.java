package com.pythonteam.compilador.AST;

public class Read implements Sentencia{
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
