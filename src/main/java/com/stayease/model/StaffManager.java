package com.stayease.model;

import java.util.List;

public class StaffManager implements ManageStaff {
    private List<Staff> daftarStaff;

    public StaffManager(List<Staff> daftarStaff) { this.daftarStaff = daftarStaff; }
    @Override public void tambahStaff(Staff staff) { daftarStaff.add(staff); }
    @Override public void hapusStaff(String idUser) { daftarStaff.removeIf(s -> s.getIdUser().equals(idUser)); }
    @Override public void updateStaff(Staff staff) {
        hapusStaff(staff.getIdUser());
        tambahStaff(staff);
    }
    @Override public List<Staff> getDaftarStaff() { return daftarStaff; }
}
