package com.pythonteam.compilador.Errores;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class PilaErrores {
    static ArrayDeque<Error> errores = new ArrayDeque<>();
    static Map<Integer, String> mensajes = new HashMap<>();
    static int id = 0;

    static
    {
        mensajes.put(100,"Lexico");
        mensajes.put(200,"Se esperaba main");
        mensajes.put(201,"Se esperaba un patentesis '('");
        mensajes.put(202,"Se esperaba un patentesis ')'");
        mensajes.put(203,"Se esperaba una llave '}'");
        mensajes.put(204,"Se esperaba un llave '{'");
        mensajes.put(204,"Valor no esperado");

    }


    public static void addError(int idError, int linea, int pos)
    {
      errores.add(new Error(id++, idError, linea, pos));
    }

    public static boolean empty() { return errores.isEmpty();  }


    public static void limpiar() {
        errores.clear();
        id = 0;
    }

    public static String getErrores() {
        String result = "\n";
        for (Error error:errores
                ) {
            result += result
                    + "id: " + error.getId()
                    + " Error: " + mensajes.get(error.getIdError())
                    + " linea: " + error.getLinea()
                    + " Pos: " + error.getPos() + "\n";
        }

        return result;    }
}
