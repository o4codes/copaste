package com.o4codes.copaste.models;

public class Config {
    private String name;
    private int port;
    private boolean darkMode;

    public Config(String name, int port, boolean darkMode) {
        this.name = name;
        this.port = port;
        this.darkMode = darkMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
