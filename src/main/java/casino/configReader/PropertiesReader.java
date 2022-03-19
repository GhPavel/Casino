package casino.configReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final Properties properties = new Properties();

    public PropertiesReader() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream config = contextClassLoader.getResourceAsStream("config/config.properties")) {
            properties.load(config);
            System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver"));
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
