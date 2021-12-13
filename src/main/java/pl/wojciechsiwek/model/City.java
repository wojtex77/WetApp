package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class City {

    public Coordinates coordinates;
    private String country, name;
    private JsonObject coord;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public JsonObject getCoord() {
        return coord;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void convertCoordinatesToObject() {
        Gson gson = new Gson();
        coordinates = gson.fromJson(coord, Coordinates.class);
    }
}
