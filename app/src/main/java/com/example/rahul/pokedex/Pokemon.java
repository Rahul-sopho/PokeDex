package com.example.rahul.pokedex;

/**
 * Created by Rahul on 06-07-2017.
 */

public class Pokemon {

    public Pokemon(String name, String height, String mUrl, String type, String weight, String ability) {
        this.name = name;
        this.height = height;
        this.mUrl = mUrl;
        this.type = type;
        this.weight = weight;
        this.ability = ability;
    }

    private String name;
    private String height;
    private String mUrl;
    private String type;
    private String weight;
    private String ability;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }


}
