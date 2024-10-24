package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Enable CORS for requests from the specified origin
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
    private final Main main;

    @Autowired
    public SettingsController(Main main) {
        this.main = main;
    }



    @PostMapping("/settings")
    public String saveSettings(@RequestBody SettingsRequest settingsRequest) {
        try {
            int[] settings = settingsRequest.getSettings();
            String ip = settingsRequest.getIp();
            logger.info("Received graph data settings: {}", (Object) settings);
            logger.info(ip);
            main.GraphRun(settings,ip);
        } catch (Exception e) {
            logger.error("Error processing graph data settings: {}", e.getMessage());
            return "Error processing settings";
        }

        return "Settings saved and processed successfully";
    }

    @PostMapping("/settingsmap")
    public String saveSettingsMap(@RequestBody SettingsRequestMap settingsRequestMap) {
        try {
                double[] settings = settingsRequestMap.getSettings();
                String ip = settingsRequestMap.getIp();
                logger.info("Received graph data settings: {}", (Object) settings);
                logger.info(ip);
                main.MapRun(settings, ip);
            } catch (Exception e) {
                logger.error("Error processing graph data settings: {}", e.getMessage());
                return "Error processing settings";
            }

            return "Settings saved and processed successfully";

        }
    }
