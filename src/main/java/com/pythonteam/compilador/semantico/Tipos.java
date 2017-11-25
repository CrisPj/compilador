package com.pythonteam.compilador.semantico;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Tipos {
    protected static final HashMap<String, Tipo> tipos = new HashMap<String, Tipo>();
    static {
        for (String op : new String[] { "+", "-", "*", "/" }) {
            Tipo tipo = TipoInt.instance;
            tipos.put(op, tipo);
        }

        for (String op : new String[] { "<", ">", "<=", ">=", "==" }) {
            Tipo tipo = TipoInt.instance;
            Tipo tipo2 = TipoBool.instance;
            tipos.put(op, tipo);
            tipos.put(op, tipo2);
        }

        tipos.put("!", TipoBool.instance);

        tipos.put("print", TipoString.instance);
        tipos.put("read", TipoInt.instance);
    }
}
