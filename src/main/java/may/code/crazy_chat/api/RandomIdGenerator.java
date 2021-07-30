package may.code.crazy_chat.api;

import java.util.UUID;

public class RandomIdGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().substring(0, 4);
    }
}
