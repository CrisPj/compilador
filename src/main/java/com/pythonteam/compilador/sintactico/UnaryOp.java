package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Expresion;

public class UnaryOp implements Expresion {
    public final String opNombre;
    public final Expresion operando;

    public UnaryOp(String s, Expresion expresion) {
        this.opNombre = s;
        this.operando = expresion;
    }

    public void aceptar(ASTVisitante v){
        v.visitar(this);
    }
}
