package org.availabilitycheckerservice.mapper;

import org.availabilitycheckerservice.model.AvailabilityCheckCriteria;
import org.availabilitycheckerservice.model.AvailabilityCheckResult;
import org.availabilitycheckerservice.model.BusAvailabilityCheckResult;
import org.enactor.commonlibrary.model.Bus;
import org.enactor.commonlibrary.model.SeatAvailability;
import org.enactor.commonlibrary.model.TicketPrice;
import org.enactor.commonlibrary.util.Constant;

public class BusAvailabilityCheckResponseMapper
{
    /**
     * This method is used to map the bus availability result using bus availability criteria and availability result
     *
     * @param seatAvailability object which contains availability data
     * @param busAvailabilityCheckCriteria  object which contains bus availability criteria details
     * @return the object which contains BusAvailabilityCheckResult
     */
    public static AvailabilityCheckResult mapAvailabilityResult(SeatAvailability seatAvailability, AvailabilityCheckCriteria busAvailabilityCheckCriteria )
    {
        BusAvailabilityCheckResult busAvailabilityCheckResult = new BusAvailabilityCheckResult();
        busAvailabilityCheckResult.setOrigin(busAvailabilityCheckCriteria.getOrigin());
        busAvailabilityCheckResult.setDestination(busAvailabilityCheckCriteria.getDestination());
        busAvailabilityCheckResult.setPrice(mapPrice(seatAvailability,busAvailabilityCheckCriteria));
        busAvailabilityCheckResult.setPaxCount(busAvailabilityCheckCriteria.getPaxCount());
        busAvailabilityCheckResult.setVehicleType(busAvailabilityCheckCriteria.getVehicleType());
        busAvailabilityCheckResult.setVehicleNum(busAvailabilityCheckCriteria.getVehicleNum());
        busAvailabilityCheckResult.setDate(busAvailabilityCheckCriteria.getDate().toString());

        return  busAvailabilityCheckResult;
    }

    /**
     * This method is used to map the price object of the response
     *
     * @param seatAvailability object which contains availability data
     * @param busAvailabilityCheckCriteria  object which contains bus availability criteria details
     *
     * @return mapped TicketPrice object
     */
    private static TicketPrice mapPrice(SeatAvailability seatAvailability, AvailabilityCheckCriteria busAvailabilityCheckCriteria)
    {
        Bus bus = (Bus)seatAvailability.getVehicle();
        TicketPrice ticketPrice = new TicketPrice();
        String key = busAvailabilityCheckCriteria.getOrigin() + busAvailabilityCheckCriteria.getDestination();
        ticketPrice.setUnitPrice(bus.getPriceList().get(key));
        ticketPrice.setCurrency(Constant.LKR);
        ticketPrice.setValue(ticketPrice.getUnitPrice() * busAvailabilityCheckCriteria.getPaxCount());
        return ticketPrice;
    }
}
