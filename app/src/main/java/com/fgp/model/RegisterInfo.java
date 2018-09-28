package com.fgp.model;

public class RegisterInfo {

    private String username;
    private String password;
    private int icon;
    private String label;

    public RegisterInfo(String username, String password, int icon, String label) {
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.label = label;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
