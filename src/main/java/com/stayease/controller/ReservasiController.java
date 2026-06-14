package com.stayease.controller;

import com.stayease.model.*;
import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservasi")
public class ReservasiController extends BaseController {
    private final DataStore dataStore;

    public ReservasiController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "RESERVATION")) return deniedRedirect();
        model.addAttribute("daftarReservasi", dataStore.getDaftarReservasi());
        model.addAttribute("daftarTamu", dataStore.getDaftarTamu());
        model.addAttribute("daftarKamar", dataStore.getDaftarKamar().stream()
                .filter(k -> k.getStatusKamar() == StatusKamar.TERSEDIA)
                .filter(k -> dataStore.getDaftarReservasi().stream()
                        .noneMatch(r -> r.getKamar().getNomorKamar().equals(k.getNomorKamar())
                                && (r.getStatusReservasi() == StatusReservasi.DIBOOKING || r.getStatusReservasi() == StatusReservasi.CHECK_IN)))
                .toList());
        return "reservasi";
    }

    @PostMapping("/add")
    public String add(@RequestParam("idTamu") String idTamu, @RequestParam("nomorKamar") String nomorKamar,
                      @RequestParam("tanggalCheckIn") String tanggalCheckIn, @RequestParam("tanggalCheckOut") String tanggalCheckOut,
                      HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "RESERVATION")) return deniedRedirect();
        Tamu tamu = dataStore.cariTamu(idTamu);
        Kamar kamar = dataStore.cariKamar(nomorKamar);
        if (tamu != null && kamar != null && kamar.getStatusKamar() == StatusKamar.TERSEDIA) {
            Reservasi reservasi = new Reservasi(dataStore.nextReservasiId(), tamu, kamar, LocalDate.parse(tanggalCheckIn), LocalDate.parse(tanggalCheckOut));
            dataStore.getHotel().tambahReservasi(reservasi);
        }
        return "redirect:/reservasi";
    }

    @PostMapping("/batal")
    public String cancel(@RequestParam("idReservasi") String idReservasi, HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "RESERVATION")) return deniedRedirect();
        Reservasi reservasi = dataStore.cariReservasi(idReservasi);
        if (reservasi != null) reservasi.batalkanReservasi();
        return "redirect:/reservasi";
    }
}
