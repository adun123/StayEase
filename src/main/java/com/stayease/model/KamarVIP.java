package com.stayease.model;

public class KamarVIP extends Kamar {
    private double biayaTambahan;

    public KamarVIP(String nomorKamar, double hargaKamar, StatusKamar statusKamar, double biayaTambahan) {
        super(nomorKamar, hargaKamar, statusKamar);
        this.biayaTambahan = biayaTambahan;
    }

    public double getBiayaTambahan() { return biayaTambahan; }
    public void setBiayaTambahan(double biayaTambahan) { this.biayaTambahan = biayaTambahan; }

    @Override
    public double hitungTotalHarga(int durasiMenginap) {
        return (getHargaKamar() + biayaTambahan) * durasiMenginap;
    }

    @Override
    public String getTipeKamar() { return "VIP"; }
}
