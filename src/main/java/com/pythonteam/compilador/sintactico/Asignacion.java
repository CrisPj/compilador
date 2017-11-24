package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Expresion;
import com.pythonteam.compilador.AST.Sentencia;

public class Asignacion implements Sentencia {
    private final String nombre;
    private final Expresion exp;

    public Asignacion(String nombre, Expresion exp) {
        this.nombre = nombre;
        this.exp = exp;
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }
}
