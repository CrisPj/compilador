package com.pythonteam.compilador.AST;

public class Read implements Sentencia{
    private int mensaje;
    public Var var;

    public Read(Var var) {
        this.var = var;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
    public Read(int mensaje)
    {
        this.mensaje = mensaje;
    }
}
