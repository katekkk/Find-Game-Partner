package com.fgp.model;

public class User {

    private String id;
    private int icon;
    private String label;
    private String password;

    public User() {
    }

    public User(String id, int icon, String label, String password) {
        this.id = id;
        this.icon = icon;
        this.label = label;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", icon=" + icon +
                ", label='" + label + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
