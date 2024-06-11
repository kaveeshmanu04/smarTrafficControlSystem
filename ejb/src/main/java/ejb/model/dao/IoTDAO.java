package ejb.model.dao;

import ejb.model.IoTDevice;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class IoTDAO {

    @Resource(lookup = "jdbs/urbanTrafficDS")
    private DataSource dataSource;

    public List<IoTDevice> getAllSensorData() {
        List<IoTDevice> iotSensors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM iot_device";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Timestamp timestamp = resultSet.getTimestamp("timestamp");
                        String vehicleId = resultSet.getString("vehicle_id");
                        double latitude = resultSet.getDouble("latitude");
                        double longitude = resultSet.getDouble("longitude");
                        double speed = resultSet.getDouble("speed");
                        String trafficLightStatus = resultSet.getString("traffic_light_status");
                        String environmentalConditions = resultSet.getString("environmental_conditions");
                        int trafficLightDuration = resultSet.getInt("traffic_light_duration");

                        IoTDevice iotSensor = new IoTDevice(timestamp, vehicleId, latitude, longitude, speed, trafficLightStatus, environmentalConditions, trafficLightDuration);
                        iotSensors.add(iotSensor);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return iotSensors;
    }
}