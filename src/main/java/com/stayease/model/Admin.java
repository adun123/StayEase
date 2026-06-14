package com.stayease.model;

public class Admin extends Staff {
    public Admin(String idStaff, String nama, String username, String email, String password, Role role) {
        super(idStaff, nama, username, email, password, "Admin", role);
    }
    @Override public void tampilkanMenu() { System.out.println("Menu Admin"); }
}
