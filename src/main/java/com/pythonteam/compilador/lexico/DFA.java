package com.pythonteam.compilador.lexico;

import java.util.Objects;
import java.util.Set;

public class DFA {
    private Transiciones transiciones;
    private int inicial;
    private Set<Integer> estadoAceptados;

    public DFA(Transiciones transiciones, int inicial, Set<Integer> estadoAceptados) {
        this.transiciones = Objects.requireNonNull(transiciones, "Transition function is null.");
        this.inicial = inicial;
        this.estadoAceptados = Objects.requireNonNull(estadoAceptados, "Accepting state set is null.");
    }

    public boolean matches(String text) {
        return transiciones.processAll(inicial, text.toCharArray())
                .map(estadoAceptados::contains)
                .orElse(false);
    }
}
