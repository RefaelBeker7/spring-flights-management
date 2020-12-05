package com.refaelbe.springflightsmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String destinationName;
    private Double longitude;
    private Double latitude;


    public Destination() {

    }

    public Destination(String destinationName, Double longitude, Double latitude) {
        this.destinationName = destinationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "{\"id\": " + this.id + ", \"destinationName\": \"" + this.destinationName +
                "\", \"longitude\": " + this.latitude + ", \"latitude\": " + this.latitude + "}";
    }

}
