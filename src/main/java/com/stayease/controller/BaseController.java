package com.stayease.controller;

import com.stayease.model.Staff;
import com.stayease.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public abstract class BaseController {
    protected final AuthService authService;

    protected BaseController(AuthService authService) { this.authService = authService; }

    protected Staff currentStaff(HttpSession session) {
        return (Staff) session.getAttribute("staff");
    }

    protected boolean addCommon(Model model, HttpSession session) {
        Staff staff = currentStaff(session);
        if (staff == null) return false;
        model.addAttribute("staff", staff);
        model.addAttribute("canRoomView", can(session, "ROOM_VIEW"));
        model.addAttribute("canRoomManage", can(session, "ROOM_MANAGE"));
        model.addAttribute("canHousekeeping", can(session, "HOUSEKEEPING_UPDATE"));
        model.addAttribute("canGuest", can(session, "GUEST"));
        model.addAttribute("canReservation", can(session, "RESERVATION"));
        model.addAttribute("canCheckIn", can(session, "CHECKIN"));
        model.addAttribute("canCheckOut", can(session, "CHECKOUT"));
        model.addAttribute("canReport", can(session, "REPORT"));
        model.addAttribute("canStaff", can(session, "STAFF"));
        return true;
    }

    protected boolean can(HttpSession session, String permission) {
        return authService.cekPermission(currentStaff(session), permission);
    }

    protected boolean canAny(HttpSession session, String... permissions) {
        for (String permission : permissions) {
            if (can(session, permission)) return true;
        }
        return false;
    }

    protected String deniedRedirect() {
        return "redirect:/dashboard?denied=1";
    }
}
