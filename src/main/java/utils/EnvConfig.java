package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().systemProperties().load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
