package org.availabilitycheckerservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.availabilitycheckerservice.model.AvailabilityCheckCriteria;
import org.availabilitycheckerservice.model.AvailabilityCheckResult;
import org.availabilitycheckerservice.util.AvailServiceConstant;
import org.enactor.commonlibrary.model.ApiResponse;
import org.enactor.commonlibrary.model.VehicleType;
import org.enactor.commonlibrary.util.CustomObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(PowerMockRunner.class)
class BusAvailabilityCheckServiceTest
{
    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary directory for externalConfigDir
        Path tempDirPath = Files.createTempDirectory("externalConfigDir");

        // Create a temporary config.properties file with existing property
        File configFile = new File(tempDirPath.toFile(), "config.properties");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(AvailServiceConstant.RES_SERVICE_BASE_URL + "=" + "http://localhost:8080/reservation-service");
        }
        System.setProperty("externalConfigDir", tempDirPath.toString());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAvailability_ValidCriteria_SeatsAvailable() throws IOException
    {

        AvailabilityCheckCriteria validCriteria = getAvailableCheckCriteria();
        ApiResponse<AvailabilityCheckResult> expected = CustomObjectMapper.getObjectMapper().readValue(createJsonResponseWithAvailableSeats(), new TypeReference<>() {});
        ApiResponse<AvailabilityCheckResult> response = BusAvailabilityCheckService.getAvailability(validCriteria);

        // Assert
        assertEquals(expected.getStatus(), response.getStatus());
        assertEquals(expected.getMessage(), response.getMessage());
        assertNotNull(response.getData());
        assertEquals(expected.getData().getOrigin(), response.getData().getOrigin());
        assertEquals(expected.getData().getDestination(), response.getData().getDestination());
        assertEquals(expected.getData().getVehicleNum(), response.getData().getVehicleNum());
        assertEquals(expected.getData().getVehicleType(), response.getData().getVehicleType());
        assertEquals(expected.getData().getPaxCount(), response.getData().getPaxCount());
        assertEquals(expected.getData().getDate(), response.getData().getDate());
        assertNotNull(response.getData().getPrice());
        assertEquals(expected.getData().getPrice().getUnitPrice(), response.getData().getPrice().getUnitPrice());
        assertEquals(expected.getData().getPrice().getValue(), response.getData().getPrice().getValue());
        assertEquals(expected.getData().getPrice().getCurrency(), response.getData().getPrice().getCurrency());

    }

    @Test
    void testGetAvailability_ValidCriteria_SeatsNotAvailable() throws IOException
    {

        AvailabilityCheckCriteria validCriteria = getAvailableCheckCriteria();
        validCriteria.setPaxCount(50);
        ApiResponse<AvailabilityCheckResult> expected = CustomObjectMapper.getObjectMapper().readValue(createJsonResponseWithNoAvailableSeats(), new TypeReference<>() {});
        ApiResponse<AvailabilityCheckResult> response = BusAvailabilityCheckService.getAvailability(validCriteria);

        assertEquals(expected.getStatus(), response.getStatus());
        assertEquals(expected.getMessage(), response.getMessage());
        assertNull(response.getData());
    }


    private String createJsonResponseWithAvailableSeats()
    {
        return "{\n" +
                "  \"status\": 200,\n" +
                "  \"message\": \"SUCCESS\",\n" +
                "  \"data\": {\n" +
                "    \"origin\": \"A\",\n" +
                "    \"destination\": \"D\",\n" +
                "    \"price\": {\n" +
                "      \"unitPrice\": 150.0,\n" +
                "      \"value\": 450.0,\n" +
                "      \"currency\": \"LKR\"\n" +
                "    },\n" +
                "    \"paxCount\": 3,\n" +
                "    \"vehicleType\": \"BUS\",\n" +
                "    \"vehicleNum\": \"1\",\n" +
                "    \"date\": \"2024-01-01\"\n" +
                "  }\n" +
                "}";
    }

    private String createJsonResponseWithNoAvailableSeats()
    {
        return "{\n" +
                "  \"status\": 200,\n" +
                "  \"message\": \"No available seats for the specified criteria\"\n"
                +"\n}";
    }

    private AvailabilityCheckCriteria getAvailableCheckCriteria()
    {
        return new AvailabilityCheckCriteria("A","D",3, VehicleType.BUS,"1",1, LocalDate.of(2024,01,01));
    }
}

