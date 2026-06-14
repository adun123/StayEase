package com.stayease.model;

import java.util.List;

public interface ManageStaff {
    void tambahStaff(Staff staff);
    void hapusStaff(String idUser);
    void updateStaff(Staff staff);
    List<Staff> getDaftarStaff();
}
