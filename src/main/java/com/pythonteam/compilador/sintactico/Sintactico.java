package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.Sentencia;
import com.pythonteam.compilador.Errores.PilaErrores;
import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;



public class Sintactico {

    private Sentencia sentencia;

    public Sintactico()
    {
        if (TablaSimbolos.size() < 3)
        {
            PilaErrores.addError(200,0,0);
        }
        else
        for (int i = 0; i < 3; i++) {
            Token token = TablaSimbolos.get(i);
            if(token.getidGen() == 5){
                i++;
                token = TablaSimbolos.get(i);
                if (token.getidGen() == 31)
                {
                    token = TablaSimbolos.get(++i);
                    if (token.getidGen() == 30)
                    {
                        sentencia = Parser.parsearSentencias();
                    }
                    else
                    {
                        PilaErrores.addError(202,token.getLinea(),token.getPosicion());
                        break;
                    }
                }
                else
                {
                    PilaErrores.addError(201,token.getLinea(),token.getPosicion());
                    break;
                }

            }
            else
                PilaErrores.addError(200,token.getLinea(),token.getPosicion());
            break;
        }
    }

    public Sentencia getArbolito() {
        return sentencia;
    }
}
