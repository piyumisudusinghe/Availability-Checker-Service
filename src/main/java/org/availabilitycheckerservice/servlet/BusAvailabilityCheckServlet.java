package org.availabilitycheckerservice.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.availabilitycheckerservice.model.AvailabilityCheckResult;
import org.availabilitycheckerservice.model.BusAvailabilityCheckCriteria;
import org.availabilitycheckerservice.service.BusAvailabilityCheckService;
import org.availabilitycheckerservice.util.AvailServiceConstant;
import org.enactor.commonlibrary.exception.InvalidRequestException;
import org.enactor.commonlibrary.model.ApiError;
import org.enactor.commonlibrary.model.ApiResponse;
import org.enactor.commonlibrary.model.VehicleType;
import org.enactor.commonlibrary.util.Constant;
import org.enactor.commonlibrary.util.DateUtil;
import org.enactor.commonlibrary.util.JsonMappingUtil;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BusAvailabilityCheckServlet extends HttpServlet
{
    /**
     * This method is used to call the relevant service and check availability of the seats for the given criteria
     *
     * @param req http request
     * @param resp response of the request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            BusAvailabilityCheckCriteria criteria = createBusAvailabilityCheckCriteria(req);
            ApiResponse<AvailabilityCheckResult> apiResponse = BusAvailabilityCheckService.getAvailability(criteria);
            //covert object to a string
            String jsonResponse = JsonMappingUtil.converObjectToString(apiResponse);
            resp.setContentType(Constant.JSON_CONTENT_TYPE);
            resp.getWriter().write(jsonResponse);
        }
        catch (InvalidRequestException e)
        {
            Logger.getLogger(AvailServiceConstant.LOG_CODE).log(Level.SEVERE, () -> "Mandatory arguments were given in invalid formats" + e.getMessage());
            ApiResponse<AvailabilityCheckResult> apiResponse = new ApiResponse<>(400,Constant.FAILED,new ApiError(400,e.getMessage()));
            String jsonResponse = JsonMappingUtil.converObjectToString(apiResponse);
            resp.setContentType(Constant.JSON_CONTENT_TYPE);
            resp.getWriter().write(jsonResponse);
        }
        catch (Exception e)
        {
            Logger.getLogger(AvailServiceConstant.LOG_CODE).log(Level.SEVERE, () -> "Error occurred while getting availability  of the bus" + e);
            ApiResponse<AvailabilityCheckResult> apiResponse = new ApiResponse<>(500,Constant.FAILED,new ApiError(500,e.getMessage()));
            String jsonResponse = JsonMappingUtil.converObjectToString(apiResponse);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType(Constant.JSON_CONTENT_TYPE);
            resp.getWriter().write(jsonResponse);
        }
    }

    /**
     * This method is used to map the request parameters and create the availability criteria
     *
     * @param req http request
     * @return the availability criteria
     */
    private BusAvailabilityCheckCriteria createBusAvailabilityCheckCriteria(HttpServletRequest req)
    {
        try
        {
            String origin = req.getParameter(Constant.ORIGIN);
            String destination = req.getParameter(Constant.DESTINATION);
            int paxCount = Integer.parseInt(req.getParameter(Constant.PAX_COUNT));
            String vehicleType = req.getParameter(Constant.VEHICLE_TYPE);
            String vehicleNum = req.getParameter(Constant.VEHICLE_NUM);
            int turn = Integer.parseInt(req.getParameter(Constant.TURN_OF_THE_DAY));
            String date = req.getParameter(Constant.TRAVELLING_DATE);

            BusAvailabilityCheckCriteria criteria = new BusAvailabilityCheckCriteria();
            criteria.setOrigin(origin);
            criteria.setDestination(destination);
            criteria.setPaxCount(paxCount);
            criteria.setVehicleType(VehicleType.getVehicleType(vehicleType));
            criteria.setVehicleNum(vehicleNum);
            criteria.setTurn(turn);
            criteria.setDate(DateUtil.convertStringToDate(date));
            return criteria;
        }
        catch (Exception exception)
        {
            throw new InvalidRequestException("Mandatory arguments were given in invalid formats");
        }
    }
}
