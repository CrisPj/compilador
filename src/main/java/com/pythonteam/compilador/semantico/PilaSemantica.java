package com.pythonteam.compilador.semantico;

import java.util.*;

public class PilaSemantica extends AbstractMap<String, Tipo> {

    private Deque<HashMap<String, Tipo>> pila = new ArrayDeque<HashMap<String, Tipo>>();

    public PilaSemantica()
    {
        pila.push(new HashMap<String, Tipo>());
    }

    @Override
    public Set<Entry<String, Tipo>> entrySet() {
        return pila.peek().entrySet();
    }

    @Override
    public Tipo put(String s, Tipo tipo) {
        return pila.peek().put(s,tipo);
    }

    public void pushBloque() {
        HashMap<String, Tipo> nuevoCampo = new HashMap<String, Tipo>(pila.peek().size());
        nuevoCampo.putAll(pila.peek());
        pila.push(nuevoCampo);
    }

    public void popBloque() {
        pila.pop();
    }
}
