package com.stayease.controller;

import com.stayease.model.LaporanHarian;
import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class LaporanController extends BaseController {
    private final DataStore dataStore;

    public LaporanController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping("/laporan")
    public String laporan(@RequestParam(value = "tanggal", required = false) String tanggal, Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "REPORT")) return deniedRedirect();
        LocalDate tanggalLaporan = tanggal == null || tanggal.isBlank() ? LocalDate.now() : LocalDate.parse(tanggal);
        LaporanHarian laporan = dataStore.buatLaporan(tanggalLaporan);
        model.addAttribute("laporan", laporan);
        model.addAttribute("tanggal", tanggalLaporan);
        return "laporan";
    }
}
