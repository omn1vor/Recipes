package recipes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1)
    private List<String> ingredients;
    @NotNull
    @Size(min = 1)
    private List<String> directions;
    @NotBlank
    private String category;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;
}
