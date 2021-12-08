package com.o4codes.copaste.models;

public class Config {
    private String name;
    private int port;
    private boolean dark_mode;

    public Config(String name, int port, boolean dark_mode) {
        this.name = name;
        this.port = port;
        this.dark_mode = dark_mode;
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

    public boolean isDark_mode() {
        return dark_mode;
    }

    public void setDark_mode(boolean dark_mode) {
        this.dark_mode = dark_mode;
    }
}
