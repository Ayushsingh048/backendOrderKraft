package com.controller;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UptimeController {
	private final Instant jvmStartTime = Instant.ofEpochMilli(
            ManagementFactory.getRuntimeMXBean().getStartTime()
    );

    @GetMapping("/api/uptime")
    public Map<String, Object> getUptime() {
        Duration uptime = Duration.between(jvmStartTime, Instant.now());

        Map<String, Object> response = new HashMap<>();
        response.put("startTime", jvmStartTime.toString());
        response.put("uptimeSeconds", uptime.toSeconds());
        response.put("uptimeFormatted", formatDuration(uptime));

        return response;
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, seconds);
    }
}
