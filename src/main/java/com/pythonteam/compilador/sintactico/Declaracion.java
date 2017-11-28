package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Expresion;
import com.pythonteam.compilador.AST.Sentencia;
import com.pythonteam.compilador.semantico.Tipo;
import com.pythonteam.compilador.semantico.TipoBool;
import com.pythonteam.compilador.semantico.TipoInt;
import com.pythonteam.compilador.semantico.TipoString;

import java.util.Objects;

public class Declaracion implements Sentencia {

    public String nombre;
    public Tipo tipo;
    public Expresion exp;

    public Declaracion(String nombre, String tipo, Expresion exp) {
        this.nombre = nombre;
        if (Objects.equals(tipo, "int"))
            this.tipo = TipoInt.instance;
        else if (Objects.equals(tipo, "string"))
            this.tipo = TipoString.instance;
        else if (Objects.equals(tipo, "boolean"))
            this.tipo = TipoBool.instance;
        this.exp = exp;
    }

    public void aceptar(ASTVisitante v){
        v.visitar(this);
    }

    @Override
    public String toString() {
        return tipo.toString() + " " + nombre + " = " + exp.toString();
    }
}
