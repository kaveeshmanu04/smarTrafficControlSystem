
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vehicle Sensor Analytics-UTMS</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>Vehicle Sensor Analytics</h1>
<div id="analyticsData">
    Mean: <span id="mean"></span><br>
    Standard Deviation: <span id="deviation"></span><br>
    Average Speed: <span id="averageSpeed"></span><br>
    Traffic Flow: <span id="trafficFlow"></span><br>
    Congestion Level: <span id="congestionLevel"></span><br>
    Environmental Impact: <span id="environmentalImpact"></span><br>
    Traffic Light Efficiency: <span id="trafficLightEfficiency"></span><br>
</div>

<script>
    function fetchDataAndDisplay() {
        $.ajax({
            url: 'sensor_analytics',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#mean').text(data.mean);
                $('#deviation').text(data.deviation);
                $('#averageSpeed').text(data.averageSpeed);
                $('#trafficFlow').text(data.trafficFlow);
                $('#congestionLevel').text(data.congestionLevel);
                $('#environmentalImpact').text(data.environmentalImpact);
                $('#trafficLightEfficiency').text(data.trafficLightEfficiency);
            },
            error: function () {
                console.error('Failed to fetch analytics data');
            }
        });
    }


    fetchDataAndDisplay();


    setInterval(fetchDataAndDisplay, 6000);
</script>
</body>
</html>

