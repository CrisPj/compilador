package com.pythonteam.models;

import java.util.ArrayDeque;

public class TablaSimbolos {
   static ArrayDeque<Token> tokens = new ArrayDeque<>();

    public static void add(Token token)
    {
        tokens.add(token);
    }

    public static String impr() {
        String result = "ID - LEXEMA - LINEA - POSICION - VALOR\n";
        for (Token token:  tokens)
        {
            result += token.tipo + "-" + token.tipo.ordinal() + " --- " + token.lexema+" --- "+token.linea+" --- "+token.posicion+" --- "+token.valor+"\n";
        }
        return result;
    }

    public static void empty() {
        tokens.clear();
    }
}
