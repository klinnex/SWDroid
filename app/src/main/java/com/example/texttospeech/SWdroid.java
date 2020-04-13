package com.example.texttospeech;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;

public class SWdroid {

    List<String> directions = new ArrayList<>();
    List<String> actions = new ArrayList<>();
    List<String> zones = new ArrayList<>();


    public SWdroid() {
        directions.add("Gauche");
        directions.add("Droite");

        actions.add("Défendre");
        actions.add("Attaquer");
        actions.add("Esquiver");


       // zones.add("Main");
        zones.add("Tête");
       // zones.add("Epaule");
        zones.add("Bras");
        //zones.add("Ventre");
        zones.add("Jambe");
        //zones.add("Pied");


    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public List<String> getZones() {
        return zones;
    }

    public void setZones(List<String> zones) {
        this.zones = zones;
    }

    public String randomPhrase() {
        return format("%s %s %s", getRandomItem(actions), getRandomItem(zones), getRandomItem(directions));
    }


    private String getRandomItem(List<String> list) {
        if (!list.isEmpty()) {
            return list.get(new Random().nextInt(list.size()));
        }
        return "";
    }
}
