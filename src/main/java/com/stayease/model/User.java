package com.stayease.model;

public abstract class User {
    protected String idUser;
    protected String nama;
    protected String email;
    protected String password;

    public User(String idUser, String nama, String email, String password) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public String getIdUser() { return idUser; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public abstract void login();
}
