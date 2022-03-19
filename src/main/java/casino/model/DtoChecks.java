package casino.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

@Slf4j
public class DtoChecks {

    public static void checkRegisterDTO(RegDataPlayerDTO dto, Map<String, String> playerData) {
        log.info("<---------Проверка DTO началась--------->");
        playerData.forEach((key, value) -> {
            switch (key) {
                case ("username"): {
                    Assertions.assertEquals(value, dto.getUsername(), "Неверное значение в поле " + key);
                    break;
                }
                case ("email"): {
                    Assertions.assertEquals(value, dto.getEmail(), "Неверное значение в поле " + key);
                    break;
                }
                case ("name"): {
                    Assertions.assertEquals(value, dto.getName(), "Неверное значение в поле " + key);
                    break;
                }
                case ("surname"): {
                    Assertions.assertEquals(value, dto.getSurname(), "Неверное значение в поле " + key);
                    break;
                }
            }
        });
        Assertions.assertNotEquals(0, dto.getId(), "Отсутствует Id");
        log.info("<---------DTO успешно проверена--------->");
    }
}
