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
public class OperasionalController extends BaseController {
    private final DataStore dataStore;

    public OperasionalController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping("/checkin")
    public String checkInPage(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "CHECKIN")) return deniedRedirect();
        model.addAttribute("daftarReservasi", dataStore.getDaftarReservasi().stream().filter(r -> r.getStatusReservasi() == StatusReservasi.DIBOOKING).toList());
        model.addAttribute("daftarCheckIn", dataStore.getDaftarCheckIn());
        return "checkin";
    }

    @PostMapping("/checkin")
    public String prosesCheckIn(@RequestParam("idReservasi") String idReservasi, HttpSession session) {
        Staff staff = currentStaff(session);
        if (staff == null) return "redirect:/login";
        if (!can(session, "CHECKIN")) return deniedRedirect();
        Reservasi reservasi = dataStore.cariReservasi(idReservasi);
        if (reservasi != null) {
            CheckIn checkIn = new CheckIn(dataStore.nextCheckInId(), reservasi, LocalDate.now(), staff);
            checkIn.prosesCheckIn();
            dataStore.getDaftarCheckIn().add(checkIn);
        }
        return "redirect:/checkin";
    }

    @GetMapping("/checkout")
    public String checkOutPage(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "CHECKOUT")) return deniedRedirect();
        model.addAttribute("daftarReservasi", dataStore.getDaftarReservasi().stream().filter(r -> r.getStatusReservasi() == StatusReservasi.CHECK_IN).toList());
        model.addAttribute("daftarCheckOut", dataStore.getDaftarCheckOut());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String prosesCheckOut(@RequestParam("idReservasi") String idReservasi, HttpSession session) {
        Staff staff = currentStaff(session);
        if (staff == null) return "redirect:/login";
        if (!can(session, "CHECKOUT")) return deniedRedirect();
        Reservasi reservasi = dataStore.cariReservasi(idReservasi);
        if (reservasi != null) {
            CheckOut checkOut = new CheckOut(dataStore.nextCheckOutId(), reservasi, LocalDate.now(), staff);
            checkOut.prosesCheckOut();
            dataStore.getDaftarCheckOut().add(checkOut);
        }
        return "redirect:/checkout";
    }
}
