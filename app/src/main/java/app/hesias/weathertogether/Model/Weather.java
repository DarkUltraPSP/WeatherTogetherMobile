package app.hesias.weathertogether.Model;

public class Weather {
    private int id;
    private String weather;

    public Weather(int id, String weather) {
        this.id = id;
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return weather;
    }
}
