package com.stayease.model;

public abstract class Kamar {
    private String nomorKamar;
    private double hargaKamar;
    private StatusKamar statusKamar;

    public Kamar(String nomorKamar, double hargaKamar, StatusKamar statusKamar) {
        this.nomorKamar = nomorKamar;
        this.hargaKamar = hargaKamar;
        this.statusKamar = statusKamar;
    }

    public String getNomorKamar() { return nomorKamar; }
    public double getHargaKamar() { return hargaKamar; }
    public StatusKamar getStatusKamar() { return statusKamar; }
    public void setHargaKamar(double hargaKamar) { this.hargaKamar = hargaKamar; }
    public void setStatusKamar(StatusKamar statusKamar) { this.statusKamar = statusKamar; }
    public abstract double hitungTotalHarga(int durasiMenginap);
    public abstract String getTipeKamar();
}
