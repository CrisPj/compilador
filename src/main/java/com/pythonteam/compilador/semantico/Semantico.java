package com.pythonteam.compilador.semantico;

import com.pythonteam.compilador.AST.*;
import com.pythonteam.compilador.PilaErrores;
import com.pythonteam.compilador.sintactico.Bloque;
import com.pythonteam.compilador.sintactico.Declaracion;

import java.util.HashMap;

public class Semantico {
    public Semantico(Sentencia objetitos) {
        checar(objetitos, Tipos.tipos);
    }

    private void checar(Sentencia objetitos, HashMap<String, Tipo> tipos) {
        Recorrido recorrido = new Recorrido(tipos);
        objetitos.aceptar(recorrido);
    }

    private class Recorrido extends ASTVisitante{
        private PilaSemantica pilaSemantica = new PilaSemantica();
        private Tipo tipo = new TipoNull();

        public Recorrido(HashMap<String, Tipo> tipos) {
            pilaSemantica.putAll(tipos);
        }

        @Override
        public void visitar(Declaracion declaracion) {
            if (pilaSemantica.containsKey(declaracion.nombre)) {
                PilaErrores.addError(400,0,0);
            }
            pilaSemantica.put(declaracion.nombre, declaracion.tipo);

            declaracion.exp.aceptar(this);
            if (!checarTipo(tipo, declaracion.tipo)) {
                PilaErrores.addError(400,0,0);
            }

            tipo = new TipoNull();

        }

        private boolean checarTipo(Tipo tipo1, Tipo tipo2) {
            return tipo1.equals(tipo2);
        }

        @Override
        public void visitar(Bloque bloque) {
            pilaSemantica.pushBloque();
            for (Sentencia stmt : bloque.sentencias) {
                stmt.aceptar(this);
            }
            pilaSemantica.popBloque();
        }

        @Override
        public void visitar(IntConst intConst) {
            tipo = TipoInt.instance;
        }

        @Override
        public void visitar(BoolConst boolConst) {
            tipo = TipoBool.instance;
        }

        @Override
        public void visitar(StringConst stConst) {
            tipo = TipoString.instance;
        }

    }
}
