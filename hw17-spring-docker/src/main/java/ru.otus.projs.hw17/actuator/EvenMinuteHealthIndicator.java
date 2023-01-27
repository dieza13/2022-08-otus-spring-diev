package ru.otus.projs.hw17.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EvenMinuteHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        boolean everyThingIsDead = LocalDateTime.now().getMinute() % 2 == 0;
        if (everyThingIsDead) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("alarm", "Ничего не работает!")
                    .build();
        } else {
            return Health.up().withDetail("alarm", "Все работает!").build();
        }
    }
}
