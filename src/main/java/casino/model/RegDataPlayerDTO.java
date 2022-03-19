package casino.model;

import lombok.Data;

import java.util.Objects;

@Data
public class RegDataPlayerDTO {
    private long id;
    private String country_id;
    private String timezone_id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String gender;
    private String phone_number;
    private String is_verified;
    private String birthdate;
    private boolean bonuses_allowed;

    public boolean equalsDTO(RegDataPlayerDTO dto) {
        if (this == dto) {
            return true;
        }
        if (dto == null || this.getClass() != dto.getClass()) {
            return false;
        }
        return this.id == dto.id &&
                Objects.equals(this.country_id, dto.country_id) &&
                Objects.equals(this.timezone_id, dto.timezone_id) &&
                Objects.equals(this.username, dto.username) &&
                Objects.equals(this.email, dto.email) &&
                Objects.equals(this.name, dto.name) &&
                Objects.equals(this.surname, dto.surname) &&
                Objects.equals(this.gender, dto.gender) &&
                Objects.equals(this.phone_number, dto.phone_number) &&
                Objects.equals(this.is_verified, dto.is_verified) &&
                Objects.equals(this.birthdate, dto.birthdate) &&
                this.bonuses_allowed == dto.bonuses_allowed;
    }
}
