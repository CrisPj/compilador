package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.ASTVisitante;
import com.pythonteam.compilador.AST.Sentencia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bloque implements Sentencia {
    public final List<Sentencia> sentencias;

    public Bloque(List<Sentencia> sentencias) {
        this.sentencias = Collections.unmodifiableList(sentencias);
    }

    public Bloque(Sentencia... sentencias) {
        this(Arrays.asList(sentencias));
    }

    public void aceptar(ASTVisitante v) {
        v.visitar(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bloque) {
            Bloque that = (Bloque)obj;
            return this.sentencias.equals(that.sentencias);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return sentencias.hashCode();
    }
}
