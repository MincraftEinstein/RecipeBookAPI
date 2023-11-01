package einstein.recipebook_api;

import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(RecipeBookAPI.MOD_ID)
public class RecipeBookAPIForge {

    public RecipeBookAPIForge() {
        RecipeBookAPI.init();
    }

    public static void registerRecipeBooks(IEventBus modEventBus) {
        modEventBus.addListener((RegisterRecipeBookCategoriesEvent event) -> {
            RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
                registry.getTypes().forEach((recipeType, typeHolder) -> {
                    event.registerRecipeCategoryFinder(recipeType.get(), recipe -> RecipeBookAPI.getCategory(recipe, typeHolder));
                    event.registerBookCategories(typeHolder.getType(), typeHolder.getAllCategories());
                    event.registerAggregateCategory(typeHolder.getSearchCategory().getCategory(), typeHolder.getCategories());
                });
            });
        });
    }
}