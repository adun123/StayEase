package com.stayease.model;

public class KamarStandard extends Kamar {
    public KamarStandard(String nomorKamar, double hargaKamar, StatusKamar statusKamar) {
        super(nomorKamar, hargaKamar, statusKamar);
    }

    @Override
    public double hitungTotalHarga(int durasiMenginap) {
        return getHargaKamar() * durasiMenginap;
    }

    @Override
    public String getTipeKamar() { return "STANDARD"; }
}
