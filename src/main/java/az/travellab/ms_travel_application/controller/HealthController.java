package az.travellab.ms_travel_application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(path = {"/health", "/readiness"})
    public ResponseEntity<Void> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
