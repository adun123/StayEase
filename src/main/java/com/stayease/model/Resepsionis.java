package com.stayease.model;

public class Resepsionis extends Staff {
    public Resepsionis(String idStaff, String nama, String username, String email, String password, Role role) {
        super(idStaff, nama, username, email, password, "Resepsionis", role);
    }
    @Override public void tampilkanMenu() { System.out.println("Menu Resepsionis"); }
}
