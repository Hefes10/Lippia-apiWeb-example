package com.example.model.apiModel.character;

import java.util.ArrayList;

public class CharacterResponse {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Origin OriginObject;
    private Location LocationObject;
    private String image;
    private ArrayList<Object> episode = new ArrayList<>();
    private String url;
    private String created;


// Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public Origin getOrigin() {
        return OriginObject;
    }

    public Location getLocation() {
        return LocationObject;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Object> getEpisode() {
        return episode;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOrigin(Origin originObject) {
        this.OriginObject = originObject;
    }

    public void setLocation(Location locationObject) {
        this.LocationObject = locationObject;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEpisode(ArrayList<Object> episode) {
        this.episode = episode;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
