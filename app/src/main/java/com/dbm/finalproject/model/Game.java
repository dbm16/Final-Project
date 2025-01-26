package com.dbm.finalproject.model;

public class Game {

    private String id;
    private String name;
    private String developer;
    private String genre;
    private String platform;
    private String release_date;
    private String description;
    private String trailer_url;
    private String image_url;
    private int popularity;

    public Game() {
    }

    public Game(String id, String name, String developer, String genre, String platform,
                String release_date, String description, String trailer_url,
                String image_url, int popularity) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.genre = genre;
        this.platform = platform;
        this.release_date = release_date;
        this.description = description;
        this.trailer_url = trailer_url;
        this.image_url = image_url;
        this.popularity = popularity;

    }

    // Getters ו-Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }


}
