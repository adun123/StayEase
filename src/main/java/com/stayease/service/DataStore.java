package com.stayease.service;

import com.stayease.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataStore {
    private final List<Kamar> daftarKamar = new ArrayList<>();
    private final List<Tamu> daftarTamu = new ArrayList<>();
    private final List<Reservasi> daftarReservasi = new ArrayList<>();
    private final List<CheckIn> daftarCheckIn = new ArrayList<>();
    private final List<CheckOut> daftarCheckOut = new ArrayList<>();
    private final List<Staff> daftarStaff = new ArrayList<>();
    private Hotel hotel;
    private final AtomicInteger counterTamu = new AtomicInteger(2);
    private final AtomicInteger counterReservasi = new AtomicInteger(1);
    private final AtomicInteger counterCheckIn = new AtomicInteger(1);
    private final AtomicInteger counterCheckOut = new AtomicInteger(1);
    private final AtomicInteger counterStaff = new AtomicInteger(4);

    @PostConstruct
    public void init() {
        daftarKamar.add(new KamarStandard("101", 350000, StatusKamar.TERSEDIA));
        daftarKamar.add(new KamarStandard("102", 350000, StatusKamar.TERSEDIA));
        daftarKamar.add(new KamarVIP("201", 750000, StatusKamar.TERSEDIA, 150000));
        daftarKamar.add(new KamarVIP("202", 850000, StatusKamar.MAINTENANCE, 200000));
        daftarTamu.add(new Tamu("T001", "Audrey Lestari", "081234567890"));
        daftarTamu.add(new Tamu("T002", "Muhammad Raihan", "081298765432"));

        Role adminRole = new Role("ADMIN");
        for (String p : List.of("ROOM_VIEW", "ROOM_MANAGE", "HOUSEKEEPING_UPDATE", "GUEST", "RESERVATION", "CHECKIN", "CHECKOUT", "REPORT", "STAFF")) adminRole.tambahPermission(new Permission(p));
        Role resepsionisRole = new Role("RESEPSIONIS");
        for (String p : List.of("ROOM_VIEW", "GUEST", "RESERVATION", "CHECKIN", "CHECKOUT")) resepsionisRole.tambahPermission(new Permission(p));
        Role housekeepingRole = new Role("HOUSEKEEPING");
        housekeepingRole.tambahPermission(new Permission("ROOM_VIEW"));
        housekeepingRole.tambahPermission(new Permission("HOUSEKEEPING_UPDATE"));

        daftarStaff.add(new Admin("S001", "Admin StayEase", "admin", "admin@stayease.com", "admin123", adminRole));
        daftarStaff.add(new Resepsionis("S002", "Resepsionis StayEase", "resepsionis", "resepsionis@stayease.com", "resepsionis123", resepsionisRole));
        daftarStaff.add(new Housekeeping("S003", "Housekeeping StayEase", "housekeeping", "housekeeping@stayease.com", "housekeeping123", housekeepingRole));

        hotel = new Hotel("StayEase Hotel", daftarKamar, daftarReservasi);
    }

    public List<Kamar> getDaftarKamar() { return daftarKamar; }
    public List<Tamu> getDaftarTamu() { return daftarTamu; }
    public List<Reservasi> getDaftarReservasi() { return daftarReservasi; }
    public List<CheckIn> getDaftarCheckIn() { return daftarCheckIn; }
    public List<CheckOut> getDaftarCheckOut() { return daftarCheckOut; }
    public List<Staff> getDaftarStaff() { return daftarStaff; }
    public Hotel getHotel() { return hotel; }

    public String nextTamuId() { return String.format("T%03d", counterTamu.incrementAndGet()); }
    public String nextReservasiId() { return String.format("R%03d", counterReservasi.incrementAndGet()); }
    public String nextCheckInId() { return String.format("CI%03d", counterCheckIn.incrementAndGet()); }
    public String nextCheckOutId() { return String.format("CO%03d", counterCheckOut.incrementAndGet()); }
    public String nextStaffId() { return String.format("S%03d", counterStaff.incrementAndGet()); }

    public Kamar cariKamar(String nomorKamar) { return hotel.cariKamar(nomorKamar); }
    public Tamu cariTamu(String idTamu) { return daftarTamu.stream().filter(t -> t.getIdTamu().equals(idTamu)).findFirst().orElse(null); }
    public Reservasi cariReservasi(String idReservasi) { return daftarReservasi.stream().filter(r -> r.getIdReservasi().equals(idReservasi)).findFirst().orElse(null); }
    public Staff cariStaff(String idStaff) { return daftarStaff.stream().filter(s -> s.getIdUser().equals(idStaff)).findFirst().orElse(null); }

    public LaporanHarian buatLaporan(LocalDate tanggal) {
        LaporanHarian laporan = new LaporanHarian("LH-" + tanggal, tanggal);
        laporan.generateLaporan(daftarKamar, daftarCheckIn, daftarCheckOut);
        return laporan;
    }
}
