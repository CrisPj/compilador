package com.pythonteam.models;

import com.pythonteam.compilador.lexico.Tipo;

import java.util.*;

public class TablaSimbolos {
   static ArrayList<Token> tokens = new ArrayList<>();
   static ArrayList<Token> errores = new ArrayList();
   static Map<String, Integer> ids = new HashMap<>();
   static int aid = 100;
   static Integer serial=0;
   static Object data[][];

   static {
       ids.put("boolean",0);
       ids.put("else",1);
       ids.put("false",2);
       ids.put("if",3);
       ids.put("int",4);
       ids.put("main",5);
       ids.put("print",6);
       ids.put("read",7);
       ids.put("string",8);
       ids.put("true",9);
       ids.put("while",10);
       ids.put(")",30);
       ids.put("(",31);
       ids.put("}",32);
       ids.put("{",33);
       ids.put(",",34);
       ids.put("*",41);
       ids.put("/",42);
       ids.put("-",43);
       ids.put("+",44);
       ids.put("=",45);
       ids.put(">",50);
       ids.put("<",51);
       ids.put("!",52);
       ids.put("|",53);
       ids.put("&",54);
       ids.put(";",55);
   }


    public static void add(Token token)
    {

        if (token.tipo == Tipo.ERROR)
            errores.add(token);
        else
            if (token.tipo == Tipo.ID)
            {
                int fid = findTokenID(token.lexema);
                if (fid != -1)
                {
                    token.id = fid;
                    tokens.add(token);
                }
                else
                {
                    token.id = serial++;
                    tokens.add(token);
                }
            }
            else{
                token.id = serial++;
                tokens.add(token);
            }
    }

    private static int findTokenID(String lexema) {
        for (Token tokena: tokens)
            if (Objects.equals(tokena.lexema, lexema)) return tokena.id;
        return -1;
    }


    public static String getErrors()
    {
        String result ="Errores:\n";
        for (Token token:errores) {
            result += "Error en la línea:" + token.linea + " caracter: " + token.posicion + "\n";
        }
        return result;
    }

    public static void genData() {
       data = new Object[size()][8];
        int index = 0;
        for (Token tok : tokens) {
            int id = 0;
            if (tok.tipo == Tipo.ID) {
                id = aid++;
            }
            else if (tok.tipo == Tipo.EOF || tok.tipo == Tipo.Texto)
            {
                id=0;
            }
            else
                id = ids.get(tok.lexema) == null ? 0 : ids.get(tok.lexema);

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
       aid=100;
        serial=0;
        tokens.clear();
        errores.clear();
    }

    public static boolean hayErrores() {
        return errores.isEmpty();
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
