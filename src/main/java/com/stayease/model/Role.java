package com.stayease.model;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private String namaRole;
    private List<Permission> daftarPermission;

    public Role(String namaRole) {
        this.namaRole = namaRole;
        this.daftarPermission = new ArrayList<>();
    }

    public String getNamaRole() { return namaRole; }
    public List<Permission> getDaftarPermission() { return daftarPermission; }
    public void tambahPermission(Permission permission) { daftarPermission.add(permission); }

    public boolean punyaPermission(String namaPermission) {
        return daftarPermission.stream().anyMatch(p -> p.getNamaPermission().equalsIgnoreCase(namaPermission));
    }
}
