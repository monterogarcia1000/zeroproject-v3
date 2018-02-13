package com.iesemilidarder.core;
// Objeto para guardar los restaurantes

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity

public class Restaurant {

    @Column(name="RES_NOM")
    private String nombre;

    @Column(name="RES_ADRECA")
    private  String direccion;

    @Column(name="RES_WEB")
    private String web;

    @Column(name="RES_TELEFON")
    private  String telefono;

    @Column(name="RES_URL_IMG")
    private String url_imagen;

    @Column(name="TRS_DESCRIPCIO")
    private String tipo;

    @Column(name="RES_CODI")
    private String ID;

//Utilizamos una ArrayList para guardar los comentarios

    private ArrayList<String> opinions;

    public ArrayList<String> getOpinions() {
        return opinions;
    }

    public void setOpinions(ArrayList<String> opinions) {
        this.opinions = opinions;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

//Contructor de los comentarios

    public Restaurant() {
        this.opinions = new ArrayList<String>();
    }
}