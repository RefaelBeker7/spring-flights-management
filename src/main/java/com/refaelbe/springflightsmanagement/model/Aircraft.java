package com.refaelbe.springflightsmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Calendar;


@Entity
public class Aircraft {
    @Id
    @GeneratedValue
    private long id;
    private Double maxDistanceKM;
    private Double price;
    private int createAtYear;
    private int createAtMonth;
    private long airlineId;


    public Aircraft(long airlineId, Double maxDistanceKM, Double price) {
        Calendar cal = Calendar.getInstance();
        this.airlineId = airlineId;
        this.maxDistanceKM = maxDistanceKM;
        this.price = price;
        this.setCreateAtMonth(cal.get(cal.MONTH));
        this.setCreateAtYear(cal.get(cal.YEAR));
    }

    public Aircraft() {

    }

    public long getId() {
        return id;
    }

    public long getAirlineId() {
        return airlineId;
    }

    public Double getMaxDistanceKM() {
        return maxDistanceKM;
    }

    public Double getPrice() {
        return price;
    }

    public int getCreateAtMonth() {
        return createAtMonth;
    }

    public int getCreateAtYear() {
        return createAtYear;
    }

    public void setMaxDistanceKM(Double maxDistanceKM) {
        this.maxDistanceKM = maxDistanceKM;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCreateAtYear(int createAtYear) {
        this.createAtYear = createAtYear;
    }

    public void setCreateAtMonth(int createAtMonth) {
        this.createAtMonth = createAtMonth;
    }

    public void setAirlineId(long airlineId) {
        this.airlineId = airlineId;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", maxDistanceKM=" + maxDistanceKM +
                ", price=" + price +
                ", createAtYear=" + createAtYear +
                ", createAtMonth=" + createAtMonth +
                ", airlineId=" + airlineId +
                '}';
    }
}
