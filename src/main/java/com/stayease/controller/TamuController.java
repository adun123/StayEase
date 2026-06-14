package com.stayease.controller;

import com.stayease.model.Tamu;
import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tamu")
public class TamuController extends BaseController {
    private final DataStore dataStore;

    public TamuController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "GUEST")) return deniedRedirect();
        model.addAttribute("daftarTamu", dataStore.getDaftarTamu());
        return "tamu";
    }

    @PostMapping("/add")
    public String add(@RequestParam("nama") String nama, @RequestParam("noTelepon") String noTelepon, HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "GUEST")) return deniedRedirect();
        dataStore.getDaftarTamu().add(new Tamu(dataStore.nextTamuId(), nama, noTelepon));
        return "redirect:/tamu";
    }
}
