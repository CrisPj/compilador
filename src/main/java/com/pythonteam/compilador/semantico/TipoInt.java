package com.pythonteam.compilador.semantico;

public class TipoInt  extends Tipo{
    public static TipoInt instance = new TipoInt();

    public TipoInt() {
    }

    @Override
    public String toString() {
        return "Integer";
    }
}
