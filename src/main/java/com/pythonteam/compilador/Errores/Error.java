package com.pythonteam.compilador.Errores;

public class Error {
    private int id;
    private int idError;
    private String mensaje;
    private int linea;
    private int pos;

    public Error(int id, int idError, int linea, int pos) {
        this.id = id;
        this.idError = idError;
        this.linea = linea;
        this.pos = pos;
    }


    public Error(int id, int idError, int linea, int pos, String mensaje) {
        this.id = id;
        this.idError = idError;
        this.linea = linea;
        this.pos = pos;
        this.mensaje = mensaje;
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
