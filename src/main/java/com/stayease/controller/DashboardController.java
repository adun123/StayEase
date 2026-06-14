package com.stayease.controller;

import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController extends BaseController {
    private final DataStore dataStore;

    public DashboardController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "denied", required = false) String denied, Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        model.addAttribute("jumlahKamar", dataStore.getDaftarKamar().size());
        model.addAttribute("jumlahTamu", dataStore.getDaftarTamu().size());
        model.addAttribute("jumlahReservasi", dataStore.getDaftarReservasi().size());
        model.addAttribute("jumlahCheckIn", dataStore.getDaftarCheckIn().size());
        if (denied != null) model.addAttribute("error", "Akses ditolak. Menu tersebut tidak tersedia untuk role Anda.");
        return "dashboard";
    }
}
