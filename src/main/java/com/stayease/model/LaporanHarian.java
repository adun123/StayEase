package com.stayease.model;

import java.time.LocalDate;
import java.util.List;

public class LaporanHarian {
    private String idLaporan;
    private LocalDate tanggalLaporan;
    private int jumlahKamarTersedia;
    private int jumlahKamarTerisi;
    private int jumlahCheckIn;
    private int jumlahCheckOut;
    private double totalPendapatan;

    public LaporanHarian(String idLaporan, LocalDate tanggalLaporan) {
        this.idLaporan = idLaporan;
        this.tanggalLaporan = tanggalLaporan;
    }

    public String getIdLaporan() { return idLaporan; }
    public LocalDate getTanggalLaporan() { return tanggalLaporan; }
    public int getJumlahKamarTersedia() { return jumlahKamarTersedia; }
    public int getJumlahKamarTerisi() { return jumlahKamarTerisi; }
    public int getJumlahCheckIn() { return jumlahCheckIn; }
    public int getJumlahCheckOut() { return jumlahCheckOut; }
    public double getTotalPendapatan() { return totalPendapatan; }

    public void generateLaporan(List<Kamar> daftarKamar, List<CheckIn> daftarCheckIn, List<CheckOut> daftarCheckOut) {
        hitungJumlahKamarTersedia(daftarKamar);
        hitungJumlahKamarTerisi(daftarKamar);
        hitungJumlahCheckIn(daftarCheckIn);
        hitungJumlahCheckOut(daftarCheckOut);
        hitungTotalPendapatan(daftarCheckOut);
    }

    public void hitungJumlahKamarTersedia(List<Kamar> daftarKamar) {
        jumlahKamarTersedia = (int) daftarKamar.stream().filter(k -> k.getStatusKamar() == StatusKamar.TERSEDIA).count();
    }

    public void hitungJumlahKamarTerisi(List<Kamar> daftarKamar) {
        jumlahKamarTerisi = (int) daftarKamar.stream().filter(k -> k.getStatusKamar() == StatusKamar.TERISI).count();
    }

    public void hitungJumlahCheckIn(List<CheckIn> daftarCheckIn) {
        jumlahCheckIn = (int) daftarCheckIn.stream().filter(c -> c.getTanggalMasuk().equals(tanggalLaporan)).count();
    }

    public void hitungJumlahCheckOut(List<CheckOut> daftarCheckOut) {
        jumlahCheckOut = (int) daftarCheckOut.stream().filter(c -> c.getTanggalKeluar().equals(tanggalLaporan)).count();
    }

    public void hitungTotalPendapatan(List<CheckOut> daftarCheckOut) {
        totalPendapatan = daftarCheckOut.stream()
                .filter(c -> c.getTanggalKeluar().equals(tanggalLaporan))
                .mapToDouble(CheckOut::getTotalTagihan)
                .sum();
    }

    public void tampilkanLaporan() { System.out.println("Laporan " + tanggalLaporan); }
}
