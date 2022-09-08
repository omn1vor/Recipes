package recipes.entity;

import lombok.*;
import recipes.dto.RecipeDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter @Setter
public class Recipe {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();
    @ElementCollection
    private List<String> directions = new ArrayList<>();
    private String category;
    private LocalDateTime date;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Recipe from(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.fill(dto);
        return recipe;
    }

    public static Recipe from(RecipeDto dto, User user) {
        Recipe recipe = from(dto);
        recipe.setUser(user);
        return recipe;
    }

    public void fill(RecipeDto dto) {
        date = LocalDateTime.now();

        setName(dto.getName());
        setDescription(dto.getDescription());
        setIngredients(dto.getIngredients());
        setDirections(dto.getDirections());
        setCategory(dto.getCategory());
    }

    public RecipeDto dto() {
        return new RecipeDto(name, description, ingredients, directions,
                category, date);
    }
}
