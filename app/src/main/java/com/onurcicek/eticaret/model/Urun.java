package com.onurcicek.eticaret.model;

/**
 * Created by plox on 03.01.2017.
 */

public class Urun {

    private String urun_id,urun_name,urun_metin,urun_fotograf,urun_kategori,urun_fiyat,urun_aciklama,urun_detay;


    public Urun() {
    }



    public Urun(String urun_id,String urun_name,String urun_metin,String urun_fotograf, String urun_kategori, String urun_fiyat,String urun_aciklama,String urun_detay) {
        this.urun_id=urun_id;
        this.urun_name = urun_name;
        this.urun_metin=urun_metin;
        this.urun_fotograf = urun_fotograf;
        this.urun_kategori = urun_kategori;
        this.urun_fiyat = urun_fiyat;
        this.urun_aciklama=urun_aciklama;
        this.urun_detay=urun_detay;
    }

    public String getUrun_id() {
        return urun_id;
    }

    public void setUrun_id(String urun_id) {
        this.urun_id = urun_id;
    }

    public String getUrun_name() {
        return urun_name;
    }

    public void setUrun_name(String urun_name) {
        this.urun_name = urun_name;
    }


    public String getUrun_metin() {
        return urun_metin;
    }

    public void setUrun_metin(String urun_metin) {
        this.urun_metin = urun_metin;
    }


    public String getUrun_fotograf() {
        return urun_fotograf;
    }

    public void setUrun_fotograf(String urun_fotograf) {
        this.urun_fotograf = urun_fotograf;
    }

    public String getUrun_kategori() {
        return urun_kategori;
    }

    public void setUrun_kategori(String urun_kategori) {
        this.urun_kategori = urun_kategori;
    }

    public String getUrun_fiyat() {
        return urun_fiyat;
    }

    public void setUrun_fiyat(String urun_fiyat) {
        this.urun_fiyat = urun_fiyat;
    }

    public String getUrun_aciklama() {
        return urun_aciklama;
    }

    public void setUrun_aciklama(String urun_aciklama) {
        this.urun_aciklama = urun_aciklama;
    }

    public String getUrun_detay() {
        return urun_detay;
    }

    public void setUrun_detay(String urun_detay) {
        this.urun_detay = urun_detay;
    }


}
