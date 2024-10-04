package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Enable CORS for requests from the specified origin
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class); // Add a logger for better logging
    private final Main main;

    @Autowired
    public SettingsController(Main main) {
        this.main = main;
    }

    @PostMapping("/settings")
    public String saveSettings(@RequestBody int[] settings) {
        try {
            // Log the incoming settings for better debugging
            logger.info("Received settings: {}", (Object) settings);

            main.run(settings); // Directly use settings
        } catch (Exception e) {
            logger.error("Error processing settings: {}", e.getMessage()); // Log the error
            return "Error processing settings";
        }

        return "Settings saved and processed successfully";
    }
}
