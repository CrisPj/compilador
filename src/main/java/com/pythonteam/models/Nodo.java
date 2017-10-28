package com.pythonteam.models;

public class Nodo {
    private int id;
    private int id_rels;
    private boolean inicial;
    private boolean nFinal;
    private String etiqueta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public boolean isnFinal() {
        return nFinal;
    }

    public void setnFinal(boolean nFinal) {
        this.nFinal = nFinal;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getId_rels() {
        return id_rels;
    }

    public void setId_rels(int id_rels) {
        this.id_rels = id_rels;
    }
}
