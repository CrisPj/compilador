package com.pythonteam.compilador.AST;

public class SentenciaVacia implements Sentencia {
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
