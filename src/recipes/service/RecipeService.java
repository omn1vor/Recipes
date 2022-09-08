package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RecipeDto;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepo;

    public Long addRecipe(RecipeDto recipeDto, User user) {
        Recipe recipe = Recipe.from(recipeDto, user);
        recipeRepo.save(recipe);
        return recipe.getId();
    }

    public Optional<RecipeDto> getRecipe(Long id) {
        Optional<Recipe> recipeQuery = recipeRepo.findById(id);
        if (recipeQuery.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(recipeQuery.get().dto());
    }

    public boolean deleteRecipe(Long id, User user) {
        Optional<Recipe> recipeQuery = recipeRepo.findById(id);
        if (recipeQuery.isEmpty()) {
            return false;
        }
        Recipe recipe = recipeQuery.get();
        checkRecipeOwnership(recipe, user);
        recipeRepo.delete(recipe);
        return true;
    }

    public boolean updateRecipe(Long id, RecipeDto recipeDto, User user) {
        Optional<Recipe> recipeQuery = recipeRepo.findById(id);
        if (recipeQuery.isEmpty()) {
            return false;
        }
        Recipe recipe = recipeQuery.get();
        checkRecipeOwnership(recipe, user);
        recipe.fill(recipeDto);
        recipeRepo.save(recipe);
        return true;
    }

    public List<RecipeDto> getRecipesByCategory(String category) {
        return recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category).stream()
                .map(Recipe::dto)
                .toList();
    }

    public List<RecipeDto> findRecipesByName(String name) {
        return recipeRepo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name).stream()
                .map(Recipe::dto)
                .toList();
    }

    private void checkRecipeOwnership(Recipe recipe, User user) {
        if (!user.equals(recipe.getUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Recipe does not belong to user with email " + user.getEmail());
        }
    }
}
