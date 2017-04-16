package com.example.antoine.knowyourgovernment;

/**
 * Created by antoine on 4/01/17.
 */

import java.io.Serializable;

public class Office implements Serializable {
    //var String Declaration
    private String name;

    //Var Official Declaration
    private Official official;

    public Office(String name, Official official) {
        this.name = name;
        this.official = official;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    @Override
    public String toString() {
        return "Office{" +
                "name='" + name + '\'' +
                ", official=" + official +
                '}';
    }
}
