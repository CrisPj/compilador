package com.pythonteam.models;

import com.pythonteam.compilador.lexico.Tipo;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
   static ArrayDeque<Token> tokens = new ArrayDeque<>();
   static Map<String, Integer> ids = new HashMap<>();
   static int aid = 100;

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
        tokens.add(token);
    }


    public static String getErrors()
    {
        String result = "\u001B[31m"+"Errores:\n";
        for (Token token:tokens) {
            if (token.tipo == Tipo.ERROR)
                result += "Error en la línea:" + token.linea + " caracter: " + token.posicion + "\n";
        }
        return result;
    }

    public static String impr() {
        String result = "\u001B[0m"+"ID General - Id especifico - LEXEMA - LINEA - POSICION - VALOR\n";
        for (Token token:  tokens) {
            int id = 0;
            if (token.tipo == Tipo.ID) {
                id = aid;
                aid++;
            }
            else if (token.tipo == Tipo.EOF || token.tipo == Tipo.Texto || token.tipo == Tipo.ERROR)
            {
                id=0;
            }
            else
                id = ids.get(token.lexema) == null ? 0 : ids.get(token.lexema);


           result += token.tipo.ordinal() + " --- " + id + " --- " + token.lexema+" --- "+token.linea+" --- "+token.posicion+" --- "+token.valor+"\n";
        }
        return result;
    }

    public static void empty() {
        tokens.clear();
    }
}
