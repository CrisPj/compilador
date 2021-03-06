package com.pythonteam.compilador.sintactico;

import com.pythonteam.compilador.AST.*;
import com.pythonteam.compilador.Errores.PilaErrores;
import com.pythonteam.compilador.lexico.Tipo;
import com.pythonteam.models.TablaSimbolos;
import com.pythonteam.models.Token;

import java.util.ArrayList;

import static com.pythonteam.compilador.lexico.Tipo.*;

public class Parser {
    private final ArrayList<Token> tokens;
    private int index;
    private final Token eof;

    public static Sentencia parsearSentencias() {
        Parser parser = new Parser();
        return parser.parseSentencia();
    }

    private Sentencia parseSentencia() {
        Token primero = obtenerPrimero();
        Token segundo = obtenerSegundo();
        if (primero.getidGen() == 33)
        {
            return parseBloque();
        }
        else if (primero.getTipo() == EOF)
        {
            PilaErrores.addError(200,primero.getLinea(),primero.getPosicion());
            return null;
        }
        else if (primero.getidGen() == 3)
        {
            return parseIf();
        }
        else if (primero.getidGen() == 6)
        {
            return parsePrint();
        }
        else if (primero.getidGen() == 7)
        {
            return parseRead();
        }
        else if (primero.getidGen() == 10)
        {
            return parseWhile();
        }
        else if (primero.getTipo() == ID && segundo.getidGen()==45)
            return parseAsignacion();
        else if ((primero.getidGen()==0 || primero.getidGen()==4 || primero.getidGen()==8)&& segundo.getTipo() == ID)
            return parseDeclaracion();
        else
        {

            Expresion exp = parseExpr();
            consume(55);
            return exp;
        }

    }

    private Sentencia parseRead() {
        consume(7);
        consume(31);
        if (obtenerPrimero().getTipo() == Numero)
        {
            int text = Integer.parseInt(consume().getLexema());
            consume(30);
            consume(55);
            return new Read(text);
        }else if (obtenerPrimero().getTipo() == ID)
        {
            Var var = new Var(consume().getLexema());
            consume(30);
            consume(55);
            return new Read(var);
        } else PilaErrores.addError(200,obtenerPrimero().getLinea(),obtenerPrimero().getPosicion());

        return null;

    }

    private Sentencia parsePrint() {
        consume(6);
        consume(31);
        if (obtenerPrimero().getTipo() == Texto)
        {
            String text = consume().getLexema();
            consume(30);
            consume(55);
            return new Print(text);
        }else if (obtenerPrimero().getTipo() == ID)
        {
            Var var = new Var(consume().getLexema());
            consume(30);
            consume(55);
            return new Print(var);
        }

        else
        {
            int linea = obtenerPrimero().getLinea();
            int pos = obtenerPrimero().getPosicion();
            consume();
            consume(30);
            consume(55);
            PilaErrores.addError(205,linea,pos);
        }

        return null;
    }

    private Sentencia parseWhile() {
        consume(10);
        consume(31);
        Expresion cabeza = parseExpr();
        consume(30);
        Sentencia cuerpo = parseSentencia();
        return new WhileLoop(cabeza, cuerpo);
    }

    private SentenciaIf parseIf() {
        consume(3);
        consume(31);
        Expresion condicion = parseExpr();
        consume(30);
        Sentencia then = parseSentencia();
        if (obtenerPrimero().getidGen() == 1)
        {
            consume(1);
            Sentencia elses = parseSentencia();
            return new SentenciaIf(condicion, then, elses);
        }
        else
        {

            return new SentenciaIf(condicion,then);
        }
    }

    private Sentencia parseAsignacion() {
        String nombre = consume().getLexema();
        consume(45);
        Expresion exp = parseExpr();
        Sentencia asignacion = new Asignacion(nombre, exp);
        consume(55);
        return asignacion;
    }

    private Sentencia parseDeclaracion() {
        String tipo = obtenerPrimero().getLexema();
        index++;
        String nombre = consume(ID).getLexema();
        consume(45);
        Expresion expr = parseExpr();
        Sentencia decl = new Declaracion(nombre, tipo, expr);
        consume(55);
        return decl;
    }

    private Expresion parseExpr() {
        Expresion izquierda = parseMathexpr();
        Token op = obtenerPrimero();
        switch (op.getidGen()) {
            case 45:
            case 52:
            case 53:
            case 54:
            case 51:
            case 50:
            case 56:
            case 57:
            case 58:
            case 59:
                consume();
                Expresion der = parseMathexpr();
                return new BinOp(izquierda, op.getLexema(), der);
            default:
                return izquierda;
        }
    }

    private Expresion parseMathexpr() {
        Expresion izquierda = parseTermino();
        while (true) {
            Token op = obtenerPrimero();
            switch (op.getidGen()) {
                case 44:
                case 43:
                    consume();
                    Expresion derecha = parseTermino();
                    izquierda = new BinOp(izquierda, op.getLexema(), derecha);
                    break;
                default:
                    return izquierda;
            }
        }
    }

    private Expresion parseTermino() {
        Expresion izquierda = parseFactor();
        while (true) {
            Token op = obtenerPrimero();
            switch (op.getidGen()) {
                case 41:
                case 42:
                    consume();
                    Expresion derecha = parseFactor();
                    izquierda = new BinOp(izquierda, op.getLexema(), derecha);
                    break;
                default:
                    return izquierda;
            }
        }
    }

    private Expresion parseFactor() {
        Token token = consume();
        if (token.getidGen() == 31)
        {
            Expresion e = parseExpr();
            consume(30);
            return e;
        }
        else
        {
            if (token.getidGen() == 52)
            {
                return new UnaryOp("!",parseFactor());
            }
            if (token.getidGen() == 43)
            {
                return new UnaryOp("-",parseFactor());
            }
            if(token.getTipo() == Numero)
                return new IntConst(Integer.parseInt(token.getLexema()));
            if (token.getTipo() == Texto)
                return new StringConst(token.getLexema());
            if (token.getidGen() == 2 || token.getidGen() == 9)
            {
                return token.getidGen() == 9 ? new BoolConst(true) : new BoolConst(false);
            }
            if (token.getTipo() == ID)
            {
                return new Var(token.getLexema());
            }
        }
        PilaErrores.addError(300,token.getLinea(),token.getPosicion());
        return null;
    }

    private Token consume() {
        Token token = obtenerPrimero();
        index++;
        return token;
    }

    private Token consume(Tipo id) {
        Token actual = obtenerPrimero();

        if (actual.getTipo() == id) {
            index++;
            return actual;
        } else {
            PilaErrores.addError(205,actual.getLinea(),actual.getPosicion());
            return actual;
        }

    }

    private Token obtenerSegundo() {
        return obtener(1);
    }

    private Token obtenerPrimero() {
        return obtener(0);
    }

    private Token obtener(int desp){
        if (index+ desp < tokens.size()) {
            return tokens.get(index + desp);
        } else {
            return eof;
        }
    }

    private Bloque parseBloque() {
        consume(33);
        ArrayList<Sentencia> sentencias = new ArrayList<Sentencia>();
        while (true) {
            Token t = obtenerPrimero();
            if (t.getidGen()== 32 || t.getTipo() == EOF) {
                break;
            } else {
                sentencias.add(parseSentencia());
            }
        }
        consume(32);
        return new Bloque(sentencias);
    }


    private Token consume(int esperado) {
        Token actual = obtenerPrimero();
        if (actual.getidGen() == esperado) {
            index++;
            return actual;
        } else {
            PilaErrores.addError(0,actual.getLinea(),actual.getPosicion(),"Se esperaba" + actual.getTipo().name());
            return actual;
        }
    }

    private Parser() {
        this.tokens = TablaSimbolos.getTokens();
        this.index = 3;
        if (tokens.isEmpty()) {
            this.eof = new Token(EOF, "<EOF>", 0, 0);
        } else {
            Token ultimo = tokens.get(tokens.size() - 1);
            this.eof = new Token(EOF, "<EOF>", ultimo.getLinea(), ultimo.getPosicion());
        }
    }
}
