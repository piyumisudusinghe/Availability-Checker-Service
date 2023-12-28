package org.availabilitycheckerservice.validator;

import org.apache.commons.lang3.StringUtils;
import org.availabilitycheckerservice.model.AvailabilityCheckCriteria;
import org.enactor.commonlibrary.exception.InvalidRequestException;

public class BusCriteriaValidator
{
    public static void validateBusAvailabilityCheckCriteria(AvailabilityCheckCriteria busAvailabilityCheckCriteria)
    {
        if (StringUtils.isBlank(busAvailabilityCheckCriteria.getOrigin()))
        {
            throw new InvalidRequestException("Mandatory parameter origin is not specified");
        }
        if (StringUtils.isBlank(busAvailabilityCheckCriteria.getDestination()))
        {
            throw new InvalidRequestException("Mandatory parameter destination is not specified");
        }
        if ( busAvailabilityCheckCriteria.getPaxCount() == null )
        {
            throw new InvalidRequestException("Mandatory parameter passenger count is not specified");
        }
        if ( busAvailabilityCheckCriteria.getPaxCount() <= 0 )
        {
            throw new InvalidRequestException("Mandatory parameter passenger count should be a positive number");
        }
        if ( busAvailabilityCheckCriteria.getVehicleType() == null )
        {
            throw new InvalidRequestException("Mandatory parameter vehicle type is not specified");
        }
        if ( StringUtils.isBlank(busAvailabilityCheckCriteria.getVehicleNum()))
        {
            throw new InvalidRequestException("Mandatory parameter vehicle number is not specified");
        }
        if ( busAvailabilityCheckCriteria.getDate() == null)
        {
            throw new InvalidRequestException("Mandatory parameter travelling date is not specified");
        }
        if ( busAvailabilityCheckCriteria.getTurn() == null || busAvailabilityCheckCriteria.getTurn() <= 0 )
        {
            throw new InvalidRequestException("Mandatory parameter turn of the vehicle within the day is not specified");
        }
    }
}
