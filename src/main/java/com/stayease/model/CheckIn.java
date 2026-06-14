package com.stayease.model;

import java.time.LocalDate;

public class CheckIn {
    private String idCheckIn;
    private Reservasi reservasi;
    private LocalDate tanggalMasuk;
    private Staff petugas;

    public CheckIn(String idCheckIn, Reservasi reservasi, LocalDate tanggalMasuk, Staff petugas) {
        this.idCheckIn = idCheckIn;
        this.reservasi = reservasi;
        this.tanggalMasuk = tanggalMasuk;
        this.petugas = petugas;
    }

    public String getIdCheckIn() { return idCheckIn; }
    public Reservasi getReservasi() { return reservasi; }
    public LocalDate getTanggalMasuk() { return tanggalMasuk; }
    public Staff getPetugas() { return petugas; }

    public boolean validasiCheckIn() {
        return reservasi != null
                && reservasi.getStatusReservasi() == StatusReservasi.DIBOOKING
                && reservasi.getKamar().getStatusKamar() == StatusKamar.TERSEDIA;
    }

    public void prosesCheckIn() {
        if (!validasiCheckIn()) throw new IllegalStateException("Reservasi tidak valid untuk check-in.");
        reservasi.setStatusReservasi(StatusReservasi.CHECK_IN);
        reservasi.getKamar().setStatusKamar(StatusKamar.TERISI);
    }
}
