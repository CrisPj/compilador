package com.pythonteam.compilador.AST;

public class Print implements Sentencia{
    private String mensaje;
    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
    public Print(String mensaje)
    {
        this.mensaje = mensaje;
    }
}
