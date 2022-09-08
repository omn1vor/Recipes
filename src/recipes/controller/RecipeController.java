package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.RecipeDto;
import recipes.security.UserService;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addRecipe(@Valid @RequestBody RecipeDto recipeDto,
                                                       Principal principal) {
        Long id = recipeService.addRecipe(recipeDto, userService.getByEmail(principal.getName()));
        return ResponseEntity.ok(Map.of(
                "id", id
        ));
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipe(@PathVariable Long id) {
        return recipeService.getRecipe(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteRecipe(@PathVariable Long id, Principal principal) {
        if (!recipeService.deleteRecipe(id, userService.getByEmail(principal.getName()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                "status", "successfully deleted recipe"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRecipe(@PathVariable Long id,
                                                            @Valid @RequestBody RecipeDto recipeDto,
                                                            Principal principal) {
        if (!recipeService.updateRecipe(id, recipeDto, userService.getByEmail(principal.getName()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                "status", "successfully update recipe"
        ));
    }

    @GetMapping("/search")
    public List<RecipeDto> searchRecipes(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) String name) {
        if ((category == null) == (name == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (category != null) {
            return recipeService.getRecipesByCategory(category);
        } else {
            return recipeService.findRecipesByName(name);
        }
    }
}
