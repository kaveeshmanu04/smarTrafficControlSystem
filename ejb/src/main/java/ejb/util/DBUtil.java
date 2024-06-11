package ejb.util;

import ejb.model.IoTDevice;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Stateless
public class DBUtil {
    @Resource(lookup = "jdbs/urbanTrafficDS")
    private DataSource dataSource;

    public void insertVehicleSensorData(IoTDevice ioTDevice) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO iot_device (timestamp, vehicle_id, latitude, longitude, speed, traffic_light_status, environmental_conditions, traffic_light_duration) VALUES (?,?,?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, (Timestamp) ioTDevice.getTimestamp());
                statement.setString(2, ioTDevice.getVehicleId());
                statement.setDouble(3, ioTDevice.getLatitude());
                statement.setDouble(4, ioTDevice.getLongitude());
                statement.setDouble(5, ioTDevice.getSpeed());
                statement.setString(6, ioTDevice.getTrafficLightStatus());
                statement.setString(7, ioTDevice.getEnvironmentalConditions());
                statement.setInt(8, ioTDevice.getTrafficLightDuration());
                statement.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
