package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting(
                "Hello, " + name + "!",
                owner,
                LocalDateTime.now()
        );
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(
                owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors()
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam String numbers) {

        if (numbers == null || numbers.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "numbers parameter must not be empty"
                    ));
        }

        try {
            double[] values = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .mapToDouble(Double::parseDouble)
                    .toArray();

            double minimum = Arrays.stream(values)
                    .min()
                    .orElse(0);

            double maximum = Arrays.stream(values)
                    .max()
                    .orElse(0);

            double average = Arrays.stream(values)
                    .average()
                    .orElse(0);

            return ResponseEntity.ok(
                    Map.of(
                            "minimum", minimum,
                            "maximum", maximum,
                            "average", average
                    )
            );

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "All values must be numbers"
                    ));
        }
    }

    public record Greeting(
            String message,
            String owner,
            LocalDateTime timestamp
    ) {
    }

    public record Info(
            String owner,
            String javaVersion,
            int cpuCores
    ) {
    }
}