package einstein.recipebook_api.api;

public interface CategorizedRecipe<T extends Enum<?> & RecipeBookCategoryEnum> {

    T getRecipeBookCategory();
}
