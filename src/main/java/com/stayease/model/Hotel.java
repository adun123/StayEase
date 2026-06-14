package com.stayease.model;

import java.util.List;

public class Hotel {
    private String namaHotel;
    private List<Kamar> daftarKamar;
    private List<Reservasi> daftarReservasi;

    public Hotel(String namaHotel, List<Kamar> daftarKamar, List<Reservasi> daftarReservasi) {
        this.namaHotel = namaHotel;
        this.daftarKamar = daftarKamar;
        this.daftarReservasi = daftarReservasi;
    }

    public String getNamaHotel() { return namaHotel; }
    public List<Kamar> getDaftarKamar() { return daftarKamar; }
    public List<Reservasi> getDaftarReservasi() { return daftarReservasi; }
    public void tambahKamar(Kamar kamar) { daftarKamar.add(kamar); }
    public Kamar cariKamar(String nomorKamar) {
        return daftarKamar.stream().filter(k -> k.getNomorKamar().equals(nomorKamar)).findFirst().orElse(null);
    }
    public void tambahReservasi(Reservasi reservasi) { daftarReservasi.add(reservasi); }
}
