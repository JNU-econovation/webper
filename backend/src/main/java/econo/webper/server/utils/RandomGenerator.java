package econo.webper.server.utils;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerator {
    public int generateRandomInt(int bound) {
        return (int) (Math.random() * bound);
    }
}
