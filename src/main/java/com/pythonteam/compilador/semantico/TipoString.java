package com.pythonteam.compilador.semantico;

public class TipoString extends Tipo{
    public static TipoString instance = new TipoString();

    private TipoString() {

    }

    @Override
    public String toString() {
        return "string";
    }
}
