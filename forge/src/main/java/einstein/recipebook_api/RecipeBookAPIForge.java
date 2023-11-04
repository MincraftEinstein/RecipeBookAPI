package einstein.recipebook_api;

import einstein.recipebook_api.dev_extras.DevelopmentExtrasForge;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import einstein.recipebook_api.platform.Services;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RecipeBookAPI.MOD_ID)
public class RecipeBookAPIForge {

    public RecipeBookAPIForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RecipeBookAPI.init();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            DevelopmentExtrasForge.loadDevelopmentExtrasEvents(modEventBus);
        }
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