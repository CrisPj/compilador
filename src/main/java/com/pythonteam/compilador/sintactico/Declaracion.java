package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Expresion;
import com.pythonteam.compilador.AST.Sentencia;

public class Declaracion implements Sentencia {

    private String nombre;
    private String tipo;
    private Expresion exp;

    public Declaracion(String nombre, String tipo, Expresion exp) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.exp = exp;
    }

    public void aceptar(ASTVisitante v){
        v.visitar(this);
    }
}
