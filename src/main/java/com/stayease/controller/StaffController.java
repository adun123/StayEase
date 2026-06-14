package com.stayease.controller;

import com.stayease.model.*;
import com.stayease.service.AuthService;
import com.stayease.service.DataStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {
    private final DataStore dataStore;

    public StaffController(AuthService authService, DataStore dataStore) {
        super(authService);
        this.dataStore = dataStore;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!addCommon(model, session)) return "redirect:/login";
        if (!can(session, "STAFF")) return deniedRedirect();
        model.addAttribute("daftarStaff", dataStore.getDaftarStaff());
        return "staff";
    }

    @PostMapping("/add")
    public String add(@RequestParam("nama") String nama, @RequestParam("username") String username, @RequestParam("email") String email,
                      @RequestParam("password") String password, @RequestParam("roleName") String roleName, HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "STAFF")) return deniedRedirect();
        Role role = new Role(roleName);
        if (roleName.equalsIgnoreCase("ADMIN")) {
            for (String p : java.util.List.of("ROOM_VIEW", "ROOM_MANAGE", "HOUSEKEEPING_UPDATE", "GUEST", "RESERVATION", "CHECKIN", "CHECKOUT", "REPORT", "STAFF")) role.tambahPermission(new Permission(p));
            dataStore.getDaftarStaff().add(new Admin(dataStore.nextStaffId(), nama, username, email, password, role));
        } else if (roleName.equalsIgnoreCase("HOUSEKEEPING")) {
            role.tambahPermission(new Permission("ROOM_VIEW"));
            role.tambahPermission(new Permission("HOUSEKEEPING_UPDATE"));
            dataStore.getDaftarStaff().add(new Housekeeping(dataStore.nextStaffId(), nama, username, email, password, role));
        } else {
            for (String p : java.util.List.of("ROOM_VIEW", "GUEST", "RESERVATION", "CHECKIN", "CHECKOUT")) role.tambahPermission(new Permission(p));
            dataStore.getDaftarStaff().add(new Resepsionis(dataStore.nextStaffId(), nama, username, email, password, role));
        }
        return "redirect:/staff";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("idUser") String idUser, HttpSession session) {
        if (currentStaff(session) == null) return "redirect:/login";
        if (!can(session, "STAFF")) return deniedRedirect();
        dataStore.getDaftarStaff().removeIf(s -> s.getIdUser().equals(idUser));
        return "redirect:/staff";
    }
}
