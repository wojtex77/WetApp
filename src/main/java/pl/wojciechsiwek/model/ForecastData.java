package pl.wojciechsiwek.model;

public class ForecastData {

    private Double tempDay, tempNight;
    private Integer pressure, hummidity;
    private String description, date;

    public ForecastData() {
    }

    public Double getTempDay() {
        return tempDay;
    }

    public void setTempDay(Double tempDay) {
        this.tempDay = tempDay;
    }

    public Double getTempNight() {
        return tempNight;
    }

    public void setTempNight(Double tempNight) {
        this.tempNight = tempNight;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHummidity() {
        return hummidity;
    }

    public void setHummidity(Integer hummidity) {
        this.hummidity = hummidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
