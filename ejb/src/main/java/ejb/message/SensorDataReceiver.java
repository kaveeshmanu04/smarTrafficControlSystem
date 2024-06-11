package ejb.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ejb.model.IoTDevice;
import ejb.util.DBUtil;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "vehicleSensorQueue")})
public class SensorDataReceiver implements MessageListener {

    @EJB
    DBUtil dbUtil;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String deviceJson = textMessage.getText();

                ObjectMapper objectMapper = new ObjectMapper();
                IoTDevice ioTDevice = objectMapper.readValue(deviceJson, IoTDevice.class);


                dbUtil.insertVehicleSensorData(ioTDevice);
                message.acknowledge();
                System.out.println("Data saved");
            }

        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
