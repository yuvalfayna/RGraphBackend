package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://rgraph.onrender.com/")  // Enable CORS for requests from localhost:3000
public class SettingsController {

    private final Main main;

    @Autowired
    public SettingsController(Main main) {
        this.main = main;
    }

    @PostMapping("/settings")
    public String saveSettings(@RequestBody int[] settings) {
        int[]arr=settings;
        try {

            main.run(arr);
        } catch (Exception e) {
            return "Error processing settings";
        }

        return "Settings saved and processed successfully";
    }
}
