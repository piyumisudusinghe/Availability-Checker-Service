package org.availabilitycheckerservice.validator;

import org.availabilitycheckerservice.model.AvailabilityCheckCriteria;
import org.availabilitycheckerservice.model.BusAvailabilityCheckCriteria;
import org.enactor.commonlibrary.exception.InvalidRequestException;
import org.enactor.commonlibrary.model.VehicleType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BusCriteriaValidatorTest
{
    @Test
    void validateBusAvailabilityCheckCriteria_invalidVehicleType()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(null,"1","A", "D", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter vehicle type is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidVehicleNum_1()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS," ","A", "D", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter vehicle number is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidVehicleNum_2()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,null,"A", "D", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter vehicle number is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidOrigin_1()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1"," ", "D", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter origin is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidOrigin_2()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1",null, "D", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter origin is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidDest_1()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", " ", LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter destination is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidDest_2()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", null, LocalDate.of(2024,01,01),3,1);
        assertInvalidRequestException("Mandatory parameter destination is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidDate()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", "D", null,3,1);
        assertInvalidRequestException("Mandatory parameter travelling date is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidPaxCount_1()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", "D", LocalDate.of(2024,01,01),0,1);
        assertInvalidRequestException("Mandatory parameter passenger count should be a positive number", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidPaxCount_2()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", "D", LocalDate.of(2024,01,01),-1,1);
        assertInvalidRequestException("Mandatory parameter passenger count should be a positive number", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidTurn_1()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", "D", LocalDate.of(2024,01,01),3,0);
        assertInvalidRequestException("Mandatory parameter turn of the vehicle within the day is not specified", criteria);
    }

    @Test
    void validateBusAvailabilityCheckCriteria_invalidTurn_2()
    {
        AvailabilityCheckCriteria criteria = getBusAvailabilityCheckCriteria(VehicleType.BUS,"1","A", "D", LocalDate.of(2024,01,01),3,0);
        assertInvalidRequestException("Mandatory parameter turn of the vehicle within the day is not specified", criteria);
    }

    private BusAvailabilityCheckCriteria getBusAvailabilityCheckCriteria(VehicleType vehicleType, String vehicleNum, String origin, String destintaion, LocalDate date, Integer paxCount, Integer turn)
    {
        BusAvailabilityCheckCriteria busAvailabilityCheckCriteria = new BusAvailabilityCheckCriteria();
        busAvailabilityCheckCriteria.setVehicleType(vehicleType);
        busAvailabilityCheckCriteria.setVehicleNum(vehicleNum);
        busAvailabilityCheckCriteria.setDestination(destintaion);
        busAvailabilityCheckCriteria.setOrigin(origin);
        busAvailabilityCheckCriteria.setDate(date);
        busAvailabilityCheckCriteria.setPaxCount(paxCount);
        busAvailabilityCheckCriteria.setTurn(turn);
        return busAvailabilityCheckCriteria;
    }

    private void assertInvalidRequestException(String expectedMessage, AvailabilityCheckCriteria criteria) {
        try {
            BusCriteriaValidator.validateBusAvailabilityCheckCriteria(criteria);
            fail("Expected InvalidRequestException, but no exception was thrown");
        } catch (InvalidRequestException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}