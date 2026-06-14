package com.stayease.model;

import java.time.LocalDate;

public class CheckOut {
    private String idCheckOut;
    private Reservasi reservasi;
    private LocalDate tanggalKeluar;
    private double totalTagihan;
    private Staff petugas;

    public CheckOut(String idCheckOut, Reservasi reservasi, LocalDate tanggalKeluar, Staff petugas) {
        this.idCheckOut = idCheckOut;
        this.reservasi = reservasi;
        this.tanggalKeluar = tanggalKeluar;
        this.petugas = petugas;
        hitungTotalTagihan();
    }

    public String getIdCheckOut() { return idCheckOut; }
    public Reservasi getReservasi() { return reservasi; }
    public LocalDate getTanggalKeluar() { return tanggalKeluar; }
    public double getTotalTagihan() { return totalTagihan; }
    public Staff getPetugas() { return petugas; }

    public void hitungTotalTagihan() {
        totalTagihan = reservasi.getKamar().hitungTotalHarga(reservasi.getDurasiMenginap());
    }

    public void prosesCheckOut() {
        if (reservasi.getStatusReservasi() != StatusReservasi.CHECK_IN) {
            throw new IllegalStateException("Reservasi belum check-in.");
        }
        hitungTotalTagihan();
        reservasi.setStatusReservasi(StatusReservasi.CHECK_OUT);
        reservasi.getKamar().setStatusKamar(StatusKamar.DIBERSIHKAN);
    }
}
