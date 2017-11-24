package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Expresion;

public class BinOp implements Expresion {
    private final Expresion izquierda;
    private final String nombre;
    private final Expresion derecha;

    public BinOp(Expresion izquierda, String nombre, Expresion derecha) {
        this.izquierda = izquierda;
        this.nombre = nombre;
        this.derecha = derecha;
    }

    public void aceptar(ASTVisitante v){
        v.visitar(this);
    }
}
