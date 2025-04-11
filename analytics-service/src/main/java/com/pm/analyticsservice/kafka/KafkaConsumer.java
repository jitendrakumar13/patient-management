package com.pm.analyticsservice.kafka;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-group")
    public void consumerEvent(byte[] event) {
        // Deserialize the event
        // Process the event
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // ... perform any business logic related to analytic event
            log.info("Received Patient Event:[PatientId={},PatientName={},PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error Deserialize the event: {}", e.getMessage());
        }

    }
}
