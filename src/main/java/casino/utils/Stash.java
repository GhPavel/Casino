package casino.utils;

import java.util.HashMap;
import java.util.Map;

public class Stash {
    private static final ThreadLocal<Map<String,Object>> STASH = new ThreadLocal<>();

    private static Map<String,Object> getStash() {
        Map<String, Object> vault = STASH.get();
        if (vault == null) {
            vault = new HashMap<>();
            STASH.set(vault);
        }
        return vault;
    }

    public static void put(String key,Object value){
        getStash().put(key, value);
    }

    public static <T> T getValue(String key) {
        return (T) getStash().get(key);
    }

    public static void remove(String key) {
        getStash().remove(key);
    }

    public static void clearStash() {
        getStash().clear();
    }
}
