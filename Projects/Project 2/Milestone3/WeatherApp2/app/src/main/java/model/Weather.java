package model;

/**
 * Created by SunnyBoy on 11/7/16.
 */

public class Weather {
    private int temperature;
    private int tempFeh;
    private int humidity;
    private int windSpeed;
    private int windDirection;
    private String description;
    private String location;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature - 273;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTempFeh() {
        double tempCel = (double)this.getTemperature();
        tempFeh = (int)((tempCel * 1.8) + 32);
        return tempFeh;
    }
}
