package ejb.analyse;

import ejb.model.IoTDevice;
import ejb.model.dao.IoTDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class DataAnalyticsBean {

    @EJB
    IoTDAO ioTDAO;

    public Map<String, Double> fetchSensorDataAndAnalytics() {
        List<IoTDevice> allSensorData = ioTDAO.getAllSensorData();

        double mean = calculateMean(allSensorData);
        double deviation = calculateStandardDeviation(allSensorData);
        double averageSpeed = calculateAverageSpeed(allSensorData);
        double trafficFlow = calculateTrafficFlow(allSensorData);
        double congestionLevel = calculateCongestionLevel(allSensorData);
        double environmentalImpact = calculateEnvironmentalImpact(allSensorData);
        double trafficLightEfficiency = calculateTrafficLightEfficiency(allSensorData);

        Map<String, Double> result = new HashMap<>();
        result.put("mean", mean);
        result.put("standardDeviation", deviation);
        result.put("averageSpeed", averageSpeed);
        result.put("trafficFlow", trafficFlow);
        result.put("congestionLevel", congestionLevel);
        result.put("environmentalImpact", environmentalImpact);
        result.put("trafficLightEfficiency", trafficLightEfficiency);


        return result;
    }

    public double calculateMean(List<IoTDevice> allSensorData) {
        if (allSensorData == null || allSensorData.isEmpty()) {
            return 0.0; // Return 0 if the list is empty
        }

        double sum = 0.0;
        for (IoTDevice data : allSensorData) {
            sum += data.getSpeed();
        }

        return sum / allSensorData.size();
    }

    public double calculateStandardDeviation(List<IoTDevice> allSensorData) {
        if (allSensorData == null || allSensorData.isEmpty()) {
            return 0.0; // Return 0 if the list is empty
        }

        double mean = calculateMean(allSensorData);
        double sumOfSquaredDifferences = 0.0;

        for (IoTDevice data : allSensorData) {
            double difference = data.getSpeed() - mean;
            sumOfSquaredDifferences += difference * difference;
        }

        double variance = sumOfSquaredDifferences / (allSensorData.size() - 1);
        return Math.sqrt(variance);
    }

    public double calculateAverageSpeed(List<IoTDevice> devices) {
        double totalSpeed = devices.stream().mapToDouble(IoTDevice::getSpeed).sum();
        if (totalSpeed == 0) {
            return 0;
        } else {
            return totalSpeed / devices.size();
        }
    }

    public double calculateTrafficFlow(List<IoTDevice> devices) {
        // Assuming traffic flow is inversely proportional to traffic congestion
        double totalSpeed = devices.stream().mapToDouble(IoTDevice::getSpeed).sum();
        if (totalSpeed == 0) {
            return 0;
        } else {
            return totalSpeed / devices.size();
        }

    }

    public double calculateCongestionLevel(List<IoTDevice> devices) {
        // Assuming congestion level is inversely proportional to traffic flow
        double trafficFlow = calculateTrafficFlow(devices);
        return 1 - trafficFlow; // Inverse the traffic flow to get congestion level
    }

    public double calculateEnvironmentalImpact(List<IoTDevice> devices) {
        // Assuming environmental impact is related to environmental conditions
        double totalEnvironmentalImpact = devices.stream()
                .mapToDouble(device -> {
                    if (device.getEnvironmentalConditions().equalsIgnoreCase("polluted")) {
                        return 1.0; // High impact for polluted conditions
                    } else {
                        return 0.0; // Low impact for non-polluted conditions
                    }
                })
                .sum();
        if(devices.size()==0){
            return 0;
        }else {
            return totalEnvironmentalImpact / devices.size();
        }
    }

    public double calculateTrafficLightEfficiency(List<IoTDevice> devices) {
        // Assuming traffic light efficiency is related to the duration of traffic light status
        double totalDuration = devices.stream()
                .mapToDouble(IoTDevice::getTrafficLightDuration)
                .sum();

        if (totalDuration == 0.0) {
            return 0;
        } else {
            return totalDuration / devices.size();
        }

    }

}
