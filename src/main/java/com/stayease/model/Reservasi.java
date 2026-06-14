package com.stayease.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservasi {
    private String idReservasi;
    private Tamu tamu;
    private Kamar kamar;
    private LocalDate tanggalCheckIn;
    private LocalDate tanggalCheckOut;
    private int durasiMenginap;
    private double totalBiaya;
    private StatusReservasi statusReservasi;

    public Reservasi(String idReservasi, Tamu tamu, Kamar kamar, LocalDate tanggalCheckIn, LocalDate tanggalCheckOut) {
        this.idReservasi = idReservasi;
        this.tamu = tamu;
        this.kamar = kamar;
        this.tanggalCheckIn = tanggalCheckIn;
        this.tanggalCheckOut = tanggalCheckOut;
        this.statusReservasi = StatusReservasi.DIBOOKING;
        hitungDurasiMenginap();
        hitungTotalBiaya();
    }

    public String getIdReservasi() { return idReservasi; }
    public Tamu getTamu() { return tamu; }
    public Kamar getKamar() { return kamar; }
    public LocalDate getTanggalCheckIn() { return tanggalCheckIn; }
    public LocalDate getTanggalCheckOut() { return tanggalCheckOut; }
    public int getDurasiMenginap() { return durasiMenginap; }
    public double getTotalBiaya() { return totalBiaya; }
    public StatusReservasi getStatusReservasi() { return statusReservasi; }
    public void setStatusReservasi(StatusReservasi statusReservasi) { this.statusReservasi = statusReservasi; }

    public void hitungDurasiMenginap() {
        long hari = ChronoUnit.DAYS.between(tanggalCheckIn, tanggalCheckOut);
        this.durasiMenginap = (int) Math.max(1, hari);
    }

    public void hitungTotalBiaya() {
        this.totalBiaya = kamar.hitungTotalHarga(durasiMenginap);
    }

    public void konfirmasiReservasi() {
        this.statusReservasi = StatusReservasi.DIBOOKING;
    }

    public void batalkanReservasi() {
        this.statusReservasi = StatusReservasi.DIBATALKAN;
        this.kamar.setStatusKamar(StatusKamar.TERSEDIA);
    }
}
