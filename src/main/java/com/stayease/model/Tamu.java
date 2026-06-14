package com.stayease.model;

public class Tamu {
    private String idTamu;
    private String nama;
    private String noTelepon;

    public Tamu(String idTamu, String nama, String noTelepon) {
        this.idTamu = idTamu;
        this.nama = nama;
        this.noTelepon = noTelepon;
    }

    public String getIdTamu() { return idTamu; }
    public String getNama() { return nama; }
    public String getNoTelepon() { return noTelepon; }
    public void setNama(String nama) { this.nama = nama; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
}
