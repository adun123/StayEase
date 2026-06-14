package com.stayease.service;

import com.stayease.model.Staff;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final DataStore dataStore;

    public AuthService(DataStore dataStore) { this.dataStore = dataStore; }

    public Staff login(String usernameOrEmail, String password) {
        return dataStore.getDaftarStaff().stream()
                .filter(staff -> staff.login(usernameOrEmail, password))
                .findFirst()
                .orElse(null);
    }

    public boolean cekPermission(Staff staff, String namaPermission) {
        if (staff == null || staff.getRole() == null) return false;
        return staff.getRole().punyaPermission(namaPermission);
    }
}
