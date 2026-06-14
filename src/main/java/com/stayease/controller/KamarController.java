package com.stayease.controller;

import com.stayease.model.*;
import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kamar")
public class KamarController extends BaseController {
    private final DataStore dataStore;

    public KamarController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "ROOM_VIEW")) return deniedRedirect();
        model.addAttribute("daftarKamar", dataStore.getDaftarKamar());
        model.addAttribute("statuses", StatusKamar.values());
        return "kamar";
    }

    @PostMapping("/add")
    public String add(@RequestParam("nomorKamar") String nomorKamar,
                      @RequestParam("tipeKamar") String tipeKamar,
                      @RequestParam("hargaKamar") double hargaKamar,
                      @RequestParam(value = "biayaTambahan", defaultValue = "0") double biayaTambahan,
                      HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "ROOM_MANAGE")) return deniedRedirect();
        Kamar kamar = tipeKamar.equalsIgnoreCase("VIP")
                ? new KamarVIP(nomorKamar, hargaKamar, StatusKamar.TERSEDIA, biayaTambahan)
                : new KamarStandard(nomorKamar, hargaKamar, StatusKamar.TERSEDIA);
        dataStore.getHotel().tambahKamar(kamar);
        return "redirect:/kamar";
    }

    @PostMapping("/status")
    public String updateStatus(@RequestParam("nomorKamar") String nomorKamar,
                               @RequestParam("statusKamar") StatusKamar statusKamar,
                               HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!canAny(session, "ROOM_MANAGE", "HOUSEKEEPING_UPDATE")) return deniedRedirect();
        Kamar kamar = dataStore.cariKamar(nomorKamar);
        if (kamar != null) kamar.setStatusKamar(statusKamar);
        return "redirect:/kamar";
    }
}
