package org.availabilitycheckerservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.availabilitycheckerservice.mapper.BusAvailabilityCheckResponseMapper;
import org.availabilitycheckerservice.model.AvailabilityCheckCriteria;
import org.availabilitycheckerservice.model.AvailabilityCheckResult;
import org.availabilitycheckerservice.util.AvailServiceConstant;
import org.availabilitycheckerservice.util.ConfigReader;
import org.availabilitycheckerservice.validator.BusCriteriaValidator;
import org.enactor.commonlibrary.exception.InvalidRequestException;
import org.enactor.commonlibrary.model.ApiError;
import org.enactor.commonlibrary.model.ApiResponse;
import org.enactor.commonlibrary.model.SeatAvailability;
import org.enactor.commonlibrary.util.Constant;
import org.enactor.commonlibrary.util.CustomObjectMapper;
import org.enactor.commonlibrary.util.RequestGenerator;

import java.util.logging.Level;

public class BusAvailabilityCheckService extends AvailabilityService
{
    /**
     * This method is used to check the availability of seats of the bus for the given criteria
     *
     * @param busAvailabilityCheckCriteria details to check seat availability
     * @return the availability response
     */
    public static ApiResponse<AvailabilityCheckResult> getAvailability(AvailabilityCheckCriteria busAvailabilityCheckCriteria)
    {
        try
        {
            String resServiceBaseUrl = ConfigReader.availServiceAppConfigs.getProperty(AvailServiceConstant.RES_SERVICE_BASE_URL);
            if (resServiceBaseUrl == null)
            {
                logger.log(Level.SEVERE, () -> "Base URL of the Reservation service is not specified in the property file");
                return new ApiResponse<>(500, Constant.FAILED,new ApiError(501,"Reservation Service base Url is not specified"));
            }
            //validate the criteria according to requirements
            BusCriteriaValidator.validateBusAvailabilityCheckCriteria(busAvailabilityCheckCriteria);
            String url = buildApiUrl(resServiceBaseUrl,busAvailabilityCheckCriteria);
            String jsonResponse = RequestGenerator.makeGetRequest(url);
            //convert json response to ApiResponse<SeatAvailability> object
            ApiResponse<SeatAvailability> seatAvailability = CustomObjectMapper.getObjectMapper().readValue(jsonResponse, new TypeReference<>() {});

           //check seats are available or not
            if( seatAvailability.getData() != null && seatAvailability.getData().getAvailableSeats() >= busAvailabilityCheckCriteria.getPaxCount())
            {
                AvailabilityCheckResult busAvailabilityCheckResult = BusAvailabilityCheckResponseMapper.mapAvailabilityResult(seatAvailability.getData(),busAvailabilityCheckCriteria);
                return new ApiResponse<>(200,Constant.SUCCESS,busAvailabilityCheckResult);
            }
            else
            {
                //There are no available seats for the given criteria
                logger.log(Level.INFO, () -> "No available seats for the specified criteria");
                return new ApiResponse<>(200,"No available seats for the specified criteria",null);
            }
        }
        catch (InvalidRequestException e)
        {
            logger.log(Level.SEVERE, () -> "Invalid query parameters are given" + e.getMessage());
            return new ApiResponse<>(400, Constant.FAILED,new ApiError(402,e.getMessage()));
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, () -> "Error occurred while calling the availabilityCheck endpoint" + e);
            return new ApiResponse<>(400, Constant.FAILED,new ApiError(403,"Error occurred while making the request to availabilityCheck endpoint"));
        }
    }

    /**
     * This method is used to build the API Url relevant to availability endpoint with relevant query parameters
     *
     * @param baseUrl base url with domain
     * @return complete url for get endpoint with query parameter values
     */
    private static String buildApiUrl(String baseUrl, AvailabilityCheckCriteria criteria)
    {
        return String.format("%s/v1/reserveSeats?vehicleType=%s&vehicleNum=%s&origin=%s&destination=%s&paxCount=%s&turn=%s&date=%s",
                baseUrl,
                criteria.getVehicleType().getName(),
                criteria.getVehicleNum(),
                criteria.getOrigin(),
                criteria.getDestination(),
                criteria.getPaxCount(),
                criteria.getTurn(),
                criteria.getDate());
    }
}
