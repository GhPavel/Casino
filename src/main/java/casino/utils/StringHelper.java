package casino.utils;

import casino.hooks.Hooks;
import nl.flotsam.xeger.Xeger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringHelper {

    public static String generateRandomSiring(String prefix, int range) {
        return prefix + new Xeger("[a-z]{" + range + "}").generate();
    }

    public static Map<String, String> playerGenerator(List<String> params) {
        Map<String, String> credMap = new HashMap<>();
        params.forEach(param -> {
            switch (param.toLowerCase()) {
                case ("username"): {
                    credMap.put("username", generateRandomSiring("username", 10));
                    break;
                }
                case ("password"): {
                    String password = new String(Base64.getEncoder()
                            .encode(generateRandomSiring("pass", 10).getBytes(StandardCharsets.UTF_8)));
                    credMap.put("password_change", password);
                    credMap.put("password_repeat", password);
                    break;
                }
                case ("email"): {
                    credMap.put("email", generateRandomSiring("email@", 10) + ".com");
                    break;
                }
                case ("name"): {
                    credMap.put("name", generateRandomSiring("TestName", 10));
                    break;
                }
                case ("surname"): {
                    credMap.put("surname", generateRandomSiring("TestSurname", 10));
                    break;
                }
                case ("currency_code"): {
                    credMap.put("currency_code", "RUB");
                    break;
                }
                default: {
                    throw new RuntimeException("Неверный параметр!");
                }
            }
        });
        return credMap;
    }

    public static String getBaseAuthBase64(String login, String password) {
        return new String(Base64.getEncoder()
                .encode((login + ":" + password)
                        .getBytes(StandardCharsets.UTF_8)));
    }

    public static String getLoginBody(Map<String, String> playerData) {
        return "{\"grant_type\":\"password\",\"username\":\"playerName\",\"password\":\"playerPass\"}"
                .replace("playerName", playerData.get("username"))
                .replace("playerPass", playerData.get("password_change"));
    }
}
