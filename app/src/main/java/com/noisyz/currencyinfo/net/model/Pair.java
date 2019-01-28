package com.noisyz.currencyinfo.net.model;

import com.noisyz.bindlibrary.annotation.Bind;
import com.noisyz.currencyinfo.data.SharedManager;

import java.util.Locale;


@Bind(generateAdapters = true, filterable = true)
public class Pair {

    private static final int DEFAULT_NAME_LENGTH = 6;

    private String name;
    private Double price;

    public Pair(String name) {
        this.name = name;
    }

    public Pair(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getNameInApiFormat() {
        return name;
    }

    public String getName() {
        return name.length() >= DEFAULT_NAME_LENGTH ? getValidName() : name;
    }

    private String getValidName() {
        return name.substring(0, 3) + "/" + name.substring(3, name.length());
    }

    public String getPrice() {
        return String.format(Locale.getDefault(), "%.2f", price);
    }

    public boolean isEnabled() {
        return SharedManager.isInfoEnabled(name);
    }

    public void setEnabled(boolean enabled) {
        SharedManager.setInfoEnabled(name, enabled);
    }

}
