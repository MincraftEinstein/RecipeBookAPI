package einstein.recipebook_api.api.recipe;

import einstein.recipebook_api.api.category.RecipeBookCategoryEnum;

public interface CategorizedRecipe<T extends Enum<?> & RecipeBookCategoryEnum> {

    T getRecipeBookCategory();
}
