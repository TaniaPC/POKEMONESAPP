/*
 *   Creado por TaniaPC
 *   03/06/22
 * */

package com.example.pokes_app.models;

public class Pokemon {

    private int number;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
