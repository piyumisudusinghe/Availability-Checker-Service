package org.availabilitycheckerservice.model;

import org.enactor.commonlibrary.model.TicketPrice;
import org.enactor.commonlibrary.model.VehicleType;

public class AvailabilityCheckResult
{
    private String origin;
    private String destination;
    private TicketPrice price;
    private Integer paxCount;
    private VehicleType vehicleType;
    private String vehicleNum;
    private String date;


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

    public TicketPrice getPrice() {
        return price;
    }

    public void setPrice(TicketPrice price) {
        this.price = price;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
