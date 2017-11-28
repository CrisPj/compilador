package com.pythonteam.models;

import com.pythonteam.compilador.Errores.PilaErrores;
import com.pythonteam.compilador.lexico.Tipo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TablaSimbolos {
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static Map<String, Integer> ids = new HashMap<>();
    private static int aid = 100;
    private static Integer serial = 0;
    private static Object data[][];

    static {
        ids.put("boolean", 0);
        ids.put("else", 1);
        ids.put("false", 2);
        ids.put("if", 3);
        ids.put("int", 4);
        ids.put("main", 5);
        ids.put("print", 6);
        ids.put("read", 7);
        ids.put("string", 8);
        ids.put("true", 9);
        ids.put("while", 10);
        ids.put(")", 30);
        ids.put("(", 31);
        ids.put("}", 32);
        ids.put("{", 33);
        ids.put(",", 34);
        ids.put("*", 41);
        ids.put("/", 42);
        ids.put("-", 43);
        ids.put("+", 44);
        ids.put("=", 45);
        ids.put(">", 50);
        ids.put("<", 51);
        ids.put("!", 52);
        ids.put("||", 53);
        ids.put("&&", 54);
        ids.put(";", 55);
        ids.put(">=", 56);
        ids.put("<=", 57);
        ids.put("==", 58);
        ids.put("!=", 59);
    }


    public static void add(Token token) {

        if (token.tipo == Tipo.ERROR)
            PilaErrores.addError(100, token.getLinea(), token.getPosicion());
        else if (token.tipo == Tipo.ID) {
            int fid = findTokenID(token.lexema);
            if (fid != -1) {
                token.id = fid;
                tokens.add(token);
            } else {
                token.id = serial++;
                tokens.add(token);
            }
        } else {
            token.id = serial++;
            tokens.add(token);
        }
    }

    private static int findTokenID(String lexema) {
        for (Token tokena : tokens)
            if (Objects.equals(tokena.lexema, lexema)) return tokena.id;
        return -1;
    }

    public static void genData() {
        data = new Object[size()][8];
        int index = 0;
        for (Token tok : tokens) {
            int id;
            if (tok.tipo == Tipo.ID) {
                id = aid++;
            } else if (tok.tipo == Tipo.EOF || tok.tipo == Tipo.Texto) {
                id = 0;
            } else
                id = ids.get(tok.lexema) == null ? -1 : ids.get(tok.lexema);

            data[index][0] = tok.id;
            data[index][1] = tok.tipo.ordinal();
            tok.setIdGen(id);
            data[index][2] = id;
            data[index][3] = tok.lexema;
            data[index][4] = tok.posicion;
            data[index][5] = tok.linea;
            data[index][6] = tok.valor;
            index++;
        }
    }

    public static void empty() {
        aid = 100;
        serial = 0;
        tokens.clear();
    }

    public static int size() {
        return tokens.size();
    }

    public static Object[][] getData() {
        return data;
    }

    public static Token get(int i) {
        return tokens.get(i);
    }

    public static ArrayList<Token> getTokens() {
        return tokens;
    }
}
