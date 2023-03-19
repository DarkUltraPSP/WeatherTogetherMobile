package app.hesias.weathertogether.Model;

import java.time.LocalDateTime;

public class Report {
    private int id;
    private LocalDateTime dateReport;
    private double latitude;
    private double longitude;
    private double temperature;
    private Weather weather;
    private Utilisateur utilisateur;

    public Report(LocalDateTime dateReport, double latitude, double longitude, double temperature, Weather weather, Utilisateur utilisateur) {
        this.dateReport = dateReport;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.weather = weather;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateReport() {
        return dateReport;
    }

    public void setDateReport(LocalDateTime dateReport) {
        this.dateReport = dateReport;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}