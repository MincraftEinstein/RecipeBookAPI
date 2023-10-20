package einstein.recipebook_api.api;

import net.minecraft.client.RecipeBookCategories;

import java.util.ArrayList;
import java.util.List;

public record RecipeBookCategoryGroup(RecipeBookCategoryHolder mainCategory,
                                      List<RecipeBookCategoryHolder> categories) {

    public List<RecipeBookCategories> getAllCategories() {
        List<RecipeBookCategories> allCategories = new ArrayList<>();
        allCategories.add(mainCategory.getCategory());
        allCategories.addAll(categories.stream().map(RecipeBookCategoryHolder::getCategory).toList());
        return allCategories;
    }
}
