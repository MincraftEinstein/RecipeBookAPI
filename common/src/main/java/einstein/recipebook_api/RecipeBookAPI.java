package einstein.recipebook_api;

import einstein.recipebook_api.api.category.RecipeBookCategoryHolder;
import einstein.recipebook_api.api.recipe.CategorizedRecipe;
import einstein.recipebook_api.api.recipe.RecipeBookTypeHolder;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeBookAPI {

    public static final String MOD_ID = "recipebook_api";
    public static final String MOD_NAME = "RecipeBook API";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String categoryName(RecipeBookCategoryHolder<?> category) {
        if (category.isSearch()) {
            return category.getName();
        }
        return category.getType().getName() + "$" + category.getName();
    }

    public static String enumName(String modId, String name) {
        return (MOD_ID + "$" + modId + "$" + name).toUpperCase();
    }

    public static RecipeBookCategories getCategory(Recipe<?> recipe, RecipeBookTypeHolder<?, ?> typeHolder) {
        if (recipe instanceof CategorizedRecipe<?> categorizedRecipe) {
            for (RecipeBookCategoryHolder<?> categoryHolder : typeHolder.getCategoryHolders()) {
                if (!categoryHolder.isSearch() && categorizedRecipe.getRecipeBookCategory().equals(categoryHolder.getEnumType())) {
                    return categoryHolder.getCategory();
                }
            }
        }
        return null;
    }
}
