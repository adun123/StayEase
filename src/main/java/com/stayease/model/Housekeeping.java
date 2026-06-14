package com.stayease.model;

public class Housekeeping extends Staff {
    public Housekeeping(String idStaff, String nama, String username, String email, String password, Role role) {
        super(idStaff, nama, username, email, password, "Housekeeping", role);
    }
    @Override public void tampilkanMenu() { System.out.println("Menu Housekeeping"); }
}
