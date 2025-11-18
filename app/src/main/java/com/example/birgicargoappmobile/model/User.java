package com.example.birgicargoappmobile.model;

public class User {
    private Integer id;
    private String login;
    private String email;
    private String phone;
    private String password;
    private String created_at;
    private boolean status;

    public User() {
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public String getCreated_at() { return created_at; }
    public boolean getStatus() { return status; }

    public void setId(Integer id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    public void setStatus(Boolean status) { this.status = status; }
}
