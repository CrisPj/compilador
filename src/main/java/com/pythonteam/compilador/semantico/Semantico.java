package com.pythonteam.compilador.semantico;

import com.pythonteam.compilador.AST.*;
import com.pythonteam.compilador.Errores.PilaErrores;
import com.pythonteam.compilador.sintactico.Asignacion;
import com.pythonteam.compilador.sintactico.BinOp;
import com.pythonteam.compilador.sintactico.Bloque;
import com.pythonteam.compilador.sintactico.Declaracion;
import com.pythonteam.compilador.sintactico.UnaryOp;

import java.util.HashMap;
import java.util.Objects;

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
                PilaErrores.addSemantico("no existe la declaracion");
            }
            pilaSemantica.put(declaracion.nombre, declaracion.tipo);

            declaracion.exp.aceptar(this);
            if (!checarTipo(tipo, declaracion.tipo)) {
                PilaErrores.addSemantico(tipo.toString() + "  es diferente de " + declaracion.tipo.toString() + " en " + declaracion.toString());
            }

            tipo = new TipoNull();

        }

        @Override
        public void visitar(Read read) {
            if (read.var!=null)
            {
                read.var.aceptar(this);
            }
            if (tipo != TipoInt.instance)
            {
                PilaErrores.addSemantico("Read solo acepta variables del tipo Int");
            }
            tipo = new TipoNull();
        }

        @Override
        public void visitar(Print print) {
            if (print.var!=null)
            {
                print.var.aceptar(this);
            }
            else tipo=TipoString.instance;
            if (tipo != TipoString.instance)
            {
                PilaErrores.addSemantico("Print solo acepta string");
            }
            tipo = new TipoNull();
        }

        @Override
        public void visitar(WhileLoop whileLoop) {
            whileLoop.condicion.aceptar(this);
            if (tipo != TipoBool.instance)
            {
                PilaErrores.addSemantico("la condicion de While debe ser bool");
            }
            pilaSemantica.pushBloque();
            whileLoop.sentencias.aceptar(this);
            pilaSemantica.popBloque();
            tipo = new TipoNull();
        }

        @Override
        public void visitar(SentenciaIf sentenciaIf) {
            sentenciaIf.condicionBooleana.aceptar(this);
            if (tipo != TipoBool.instance)
            {
                PilaErrores.addSemantico("If fue " + tipo.toString());
            }
            pilaSemantica.pushBloque();
            sentenciaIf.then.aceptar(this);
            pilaSemantica.popBloque();
            pilaSemantica.pushBloque();
            sentenciaIf.parteElse.aceptar(this);
            pilaSemantica.popBloque();

            tipo = new TipoNull();
        }

        @Override
        public void visitar(UnaryOp unaryOp) {
            unaryOp.operando.aceptar(this);

            if (tipo == null)
                PilaErrores.addSemantico(unaryOp.opNombre + "No puede ser null");
            if (!Objects.equals(unaryOp.opNombre, "!"))
            tipo = TipoInt.instance;

        }

        @Override
        public void visitar(Asignacion asignacion) {
            Tipo tipoVar = pilaSemantica.get(asignacion.nombre);
            if (tipoVar == null)
            {
                PilaErrores.addSemantico("Variable no declarada");
            }
            asignacion.exp.aceptar(this);
            if (!checarTipo(tipo,tipoVar))
            {
                PilaErrores.addSemantico("No se puede asignar " + tipo.toString());
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
        public void visitar(Var var) {
            tipo = pilaSemantica.get(var.name);
            if (tipo == null)
            {
                PilaErrores.addSemantico("Variable desnococida " + var.name);
                tipo = new TipoNull();
            }
        }

        @Override
        public void visitar(BinOp binOp) {
            binOp.izquierda.aceptar(this);
            Tipo tIzq = tipo;
            binOp.derecha.aceptar(this);
            Tipo tDer = tipo;
            if (!checarTipo(tIzq,tDer))
            {
                PilaErrores.addSemantico("Tipo incompatibles en " + binOp.nombre);
            }
            if(Objects.equals(binOp.nombre, "+") || Objects.equals(binOp.nombre, "-") || Objects.equals(binOp.nombre, "/") || Objects.equals(binOp.nombre, "*"))
                tipo = TipoInt.instance;
            else if (Objects.equals(binOp.nombre, "==") || Objects.equals(binOp.nombre, ">=") || Objects.equals(binOp.nombre, "<=") || Objects.equals(binOp.nombre, "!=")
                    || Objects.equals(binOp.nombre, "||") || Objects.equals(binOp.nombre, "&&")
                    || Objects.equals(binOp.nombre, "<") || Objects.equals(binOp.nombre, ">") )
                tipo = TipoBool.instance;

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
