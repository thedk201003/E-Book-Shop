package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class DatabaseConfig {

    static Properties prop = new Properties();
    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");

        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch from env variable first, if not found then use properties file
    public final static String DRIVER_NAME = getEnvOrProperty("db.driver");
    public final static String DB_HOST = getEnvOrProperty("db.host");
    public final static String DB_PORT = getEnvOrProperty("db.port");
    public final static String DB_NAME = getEnvOrProperty("db.name");
    public final static String DB_USER_NAME = getEnvOrProperty("db.username");
    public final static String DB_PASSWORD = getEnvOrProperty("db.password");
    public final static String CONNECTION_STRING = DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private static String getEnvOrProperty(String key) {
        return System.getenv().getOrDefault(key.toUpperCase().replace(".", "_"), prop.getProperty(key));
    }
}