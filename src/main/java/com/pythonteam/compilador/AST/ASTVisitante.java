package com.pythonteam.compilador.AST;

import com.pythonteam.compilador.sintactico.*;

public abstract class ASTVisitante {
    public void visitar(SentenciaIf sentenciaIf) {}
    public void visitar(Bloque bloque) {}
    public void visitar(SentenciaVacia sentenciaVacia) {}
    public void visitar(WhileLoop whileLoop) {}
    public void visitar(Print whileLoop) {}
    public void visitar(Read whileLoop) {}

    public void visitar(BoolConst boolConst) {}
    public void visitar(IntConst intConst) {}
    public void visitar(StringConst intConst) {}
    public void visitar(Var var) {}

    public void visitar(Declaracion declaracion) { }

    public void visitar(UnaryOp unaryOp) { }

    public void visitar(Asignacion asignacion) {

    }

    public void visitar(BinOp binOp) {

    }
}
