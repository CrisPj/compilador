package com.pythonteam.models;

import com.pythonteam.compilador.lexico.Tipo;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
//   static ArrayDeque<Token> tokens = new ArrayDeque<>();
   static HashMap<String, Token> tokens = new HashMap<>();
   static HashMap<String, Token> errores = new HashMap<>();
   static Map<String, Integer> ids = new HashMap<>();
   static int aid = 100;
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
   }


    public static void add(Token token)
    {
        if (token.tipo == Tipo.ERROR)
            errores.put(token.lexema, token);
        else
            tokens.put(token.lexema,token);
    }


    public static String getErrors()
    {
        String result ="Errores:\n";
        for (Token token:errores.values()) {
            result += "Error en la línea:" + token.linea + " caracter: " + token.posicion + "\n";
        }
        return result;
    }

    public static void genData() {
       data = new Object[size()][7];
        int index = 0;
        for (Token tok : tokens.values()) {
            int id = 0;
            if (tok.tipo == Tipo.ID) {
                id = aid;
                aid++;
            }
            else if (tok.tipo == Tipo.EOF || tok.tipo == Tipo.Texto)
            {
                id=0;
            }
            else
                id = ids.get(tok.lexema) == null ? 0 : ids.get(tok.lexema);

            data[index][0] = tok.tipo.ordinal();
            data[index][1] = id;
            data[index][2] = tok.lexema;
            data[index][3] = tok.posicion;
            data[index][4] = tok.linea;
            data[index][5] = tok.valor;
            data[index][6] = tok.clasificacion;
            index++;
        }
    }

    public static void empty() {
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
}
