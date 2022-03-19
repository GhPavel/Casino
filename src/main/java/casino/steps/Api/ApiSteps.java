package casino.steps.Api;

import casino.model.RegDataPlayerDTO;
import casino.utils.Stash;
import io.cucumber.java.ru.Когда;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

import static casino.hooks.Hooks.confReader;
import static casino.model.DtoChecks.checkRegisterDTO;
import static casino.steps.Api.ApiRequest.*;
import static casino.utils.StringHelper.*;

@Slf4j
public class ApiSteps {


    @Когда("Получить и проверить гостевой токен")
    public void getAndCheckGuestToken() {
        String baseAuth = getBaseAuthBase64(confReader.getValue("basic.authentication.username"), "");
        String body = "{\"grant_type\":\"client_credentials\",\"scope\":\"guest:default\"}";
        Response response = getToken(Method.POST, 200, baseAuth, body);
        Assertions.assertAll(() -> {
            Assertions.assertEquals("Bearer", response.jsonPath().getObject("token_type", String.class), "Неверный тип токена!");
            Assertions.assertNotNull(response.jsonPath().getObject("expires_in", String.class), "Отсутствует срок действия токена!");
            Assertions.assertNotNull(response.jsonPath().getObject("access_token", String.class), "Отсутствует токен!");
            log.info("Гостевой токен успешно получен");
        });
    }

    @Когда("Получить и сохранить гостевой токен в stash с ключом {string}")
    public void getGuestTokenAndSaveToStash(String stashKey) {
        String baseAuth = getBaseAuthBase64(confReader.getValue("basic.authentication.username"), "");
        String body = "{\"grant_type\":\"client_credentials\",\"scope\":\"guest:default\"}";
        String token = getToken(Method.POST, 200, baseAuth, body).jsonPath().getObject("access_token", String.class);
        log.info("Гостевой токен успешно получен");
        Stash.put(stashKey, token);
    }

    @Когда("Сгенерировать нового игрока и сохранить в stash с ключом {string} со следующими параметрами:")
    public void generateNewPlayer(String stashKey, List<String> playerParams) {
        Stash.put(stashKey, playerGenerator(playerParams));
    }

    @Когда("Зарегистрировать нового игрока {string} и сохранить ответ в stash с ключом {string}, гостевой токен авторизации {string}")
    public void registerNewPlayer(String credStashKey, String responseStashKey, String bearerStashKey) {
        Map<String, String> cred = Stash.getValue(credStashKey);
        Response response = registerOrLoginPlayer(Method.POST,201, new JSONObject(cred).toString(), Stash.getValue(bearerStashKey));
        log.info("Получен ответ со статусом: {}", response.getStatusCode());
        log.info("Ответ: {}", response.asString());
        RegDataPlayerDTO playerDTO = response.jsonPath().getObject("", RegDataPlayerDTO.class);
        cred.put("id", String.valueOf(playerDTO.getId()));
        Stash.put(responseStashKey, playerDTO);
    }

    @Когда("Ошибка при регистрации нового игрока {string}, гостевой токен авторизации {string}")
    public void errorWhileRegisterNewPlayer(String credStashKey, String bearerStashKey) {
        Map<String, String> cred = Stash.getValue(credStashKey);
        Response response = registerOrLoginPlayer(Method.POST, 422, new JSONObject(cred).toString(), Stash.getValue(bearerStashKey));
        log.info("Получен ответ со статусом: {}", response.getStatusCode());
        log.info("Ответ: {}", response.asString());
    }

    @Когда("Проверка ответа {string} при регистрации нового пользователя, stash ключ данных игрока {string}")
    public void checkRegisterPlayerResponse(String responseStashKey, String playerDataStashKey) {
        checkRegisterDTO(Stash.getValue(responseStashKey), Stash.getValue(playerDataStashKey));
    }

    @Когда("Авторизация зарегистрированным пользователем {string} и сохранить токен в stash с ключом {string}")
    public void loginToSite(String playerDataStashKey, String playerTokenStashKey) {
        String baseAuth = getBaseAuthBase64(confReader.getValue("basic.authentication.username"), "");
        String body = getLoginBody(Stash.getValue(playerDataStashKey));
        String token = getToken(Method.POST, 200, baseAuth, body).jsonPath().getObject("access_token", String.class);
        Stash.put(playerTokenStashKey, token);
        registerOrLoginPlayer(Method.GET, 200, "", token);
    }

    @Когда("Получить профиль игрока {string} и сохранить его в stash с ключом {string}, токен авторизации {string}")
    public void getPlayerProfile(String playerDataStashKey, String profileStashKey, String playerTokenStashKey) {
        String bearer = Stash.getValue(playerTokenStashKey);
        String id = ((Map<String, String>) Stash.getValue(playerDataStashKey)).get("id");
        Stash.put(profileStashKey, getProfile(Method.GET, 200, bearer, id).jsonPath().getObject("", RegDataPlayerDTO.class));
    }

    @Когда("Запрос профиля другого игрока, токен авторизации {string}")
    public void getAnotherPlayerProfile(String playerTokenStashKey) {
        String bearer = Stash.getValue(playerTokenStashKey);
        String id = "0000";
        Response response = getProfile(Method.GET, 404, bearer, id);
        log.info("Получен ответ со статусом: {}", response.getStatusCode());
        log.info("Ответ: {}", response.asString());
    }

    @Когда("Проверить профиль игрока {string} с данными при регистрации {string}")
    public void eqDTO(String playerDataStashKey, String profileStashKey) {
        Assertions.assertTrue(((RegDataPlayerDTO) Stash.getValue(playerDataStashKey)).equalsDTO(Stash.getValue(profileStashKey)), "Поученные данные не совпадают с данными при регистрации!");
    }
}
