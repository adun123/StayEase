package com.stayease.model;

public abstract class Staff extends User {
    private String username;
    private String jabatan;
    private Role role;

    public Staff(String idStaff, String nama, String username, String email, String password, String jabatan, Role role) {
        super(idStaff, nama, email, password);
        this.username = username;
        this.jabatan = jabatan;
        this.role = role;
    }

    public String getIdStaff() { return idUser; }
    public String getUsername() { return username; }
    public String getJabatan() { return jabatan; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean login(String usernameOrEmail, String password) {
        return (this.username.equalsIgnoreCase(usernameOrEmail) || this.email.equalsIgnoreCase(usernameOrEmail))
                && this.password.equals(password);
    }

    @Override
    public void login() { }

    public abstract void tampilkanMenu();
}
