package com.pythonteam.compilador.AST;

public class SentenciaIf implements Sentencia{
    public final Expresion condicionBooleana;
    public final Sentencia then;
    public final Sentencia parteElse;

    public SentenciaIf(Expresion condicionBooleana, Sentencia then, Sentencia parteElse) {
        this.condicionBooleana = condicionBooleana;
        this.then = then;
        this.parteElse = parteElse;
    }

    public SentenciaIf(Expresion condicionBooleana, Sentencia then) {
        this.condicionBooleana = condicionBooleana;
        this.then = then;
        this.parteElse = new SentenciaVacia();
    }
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
    public boolean hasElseClause() {
        return !(parteElse instanceof SentenciaVacia);
    }
}
