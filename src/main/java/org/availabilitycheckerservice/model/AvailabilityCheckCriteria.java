package org.availabilitycheckerservice.model;

import org.enactor.commonlibrary.model.VehicleType;

import java.time.LocalDate;

public class AvailabilityCheckCriteria
{
    private String origin;
    private String destination;
    private Integer paxCount;
    private VehicleType vehicleType;
    private String vehicleNum;
    private  Integer turn;
    private LocalDate date;

    public AvailabilityCheckCriteria(){}

    public AvailabilityCheckCriteria(String origin, String destination, Integer paxCount, VehicleType vehicleType, String vehicleNum, Integer turn, LocalDate date)
    {
        this.origin = origin;
        this.destination = destination;
        this.paxCount = paxCount;
        this.vehicleType = vehicleType;
        this.vehicleNum = vehicleNum;
        this.turn = turn;
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getPaxCount() {
        return paxCount;
    }

    public void setPaxCount(Integer paxCount) {
        this.paxCount = paxCount;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
