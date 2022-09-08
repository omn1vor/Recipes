package recipes.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserDto {
    @Pattern(regexp = ".+@.+\\..+")
    public String email;
    @NotBlank @Size(min = 8)
    public String password;
}
