package com.refaelbe.springflightsmanagement.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Double budget;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //
    private Destination baseLocation;

    @ElementCollection
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<Integer> aircraftKeys = new ArrayList<>();

    public Airline(String name, Double budget, Destination baseLocation, List<Integer> aircraftKeys) {
        this.name = name;
        this.budget = budget;
        this.baseLocation = baseLocation;
        this.aircraftKeys = aircraftKeys;
    }

    public Airline() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getBudget() {
        return budget;
    }

    public Destination getBaseLocation() {
        return baseLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(Double initialBudget) {
        this.budget = initialBudget;
    }

    public void setBaseLocation(Destination baseLocation) {
        this.baseLocation = baseLocation;
    }

    public List<Integer> getAircraftKeys() {
        return aircraftKeys;
    }

    public void setAircraftKeys(List<Integer> aircraftKeys) {this.aircraftKeys = aircraftKeys;}

    @Override
    public String toString() {
        return "{\"id\": " + this.id + ", \"budget\": " + this.budget
                + ", \"baseLocation\": " + this.baseLocation.toString() +
                ", \"aircraftKeys\": " + this.aircraftKeys.toString() + "}";
    }

}
