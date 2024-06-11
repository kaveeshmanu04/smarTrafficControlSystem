package com.devkm.servlets;

import ejb.analyse.DataAnalyticsBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;

@WebServlet("/sensor_analytics")
public class AnalyticsServlet extends HttpServlet {
    @EJB
    DataAnalyticsBean dataAnalyticsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Double> analyticsData = dataAnalyticsBean.fetchSensorDataAndAnalytics();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mean", analyticsData.get("mean"));
        jsonObject.put("deviation", analyticsData.get("standardDeviation"));
        jsonObject.put("averageSpeed", analyticsData.get("averageSpeed"));
        jsonObject.put("trafficFlow", analyticsData.get("trafficFlow"));
        jsonObject.put("congestionLevel", analyticsData.get("congestionLevel"));
        jsonObject.put("environmentalImpact", analyticsData.get("environmentalImpact"));
        jsonObject.put("trafficLightEfficiency", analyticsData.get("trafficLightEfficiency"));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}