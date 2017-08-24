package com.jasahub.recyclerview3.model;

import java.io.Serializable;

/**
 * Created by SE on 23/08/2017.
 */
/* implementasikan interface Serializable agar data bisa dikirimkan ke halaman detail*/
public class Hotel implements Serializable {
    public String judul;
    public String deskripsi;
    public String detail;
    public String lokasi;
    public String foto;

    public Hotel(String judul, String deskripsi, String detail, String lokasi, String foto) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.detail = detail;
        this.lokasi = lokasi;
        this.foto = foto;

    }
}
