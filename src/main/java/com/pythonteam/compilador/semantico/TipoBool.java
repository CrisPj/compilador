package com.pythonteam.compilador.semantico;

public class TipoBool extends Tipo{
    public static TipoBool instance = new TipoBool();

    private TipoBool() {
    }

    @Override
    public String toString() {
        return "bool";
    }
}
