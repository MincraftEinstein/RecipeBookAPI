package einstein.recipebook_api;

import einstein.recipebook_api.dev_extras.DevelopmentExtrasForge;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import einstein.recipebook_api.platform.Services;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(RecipeBookAPI.MOD_ID)
public class RecipeBookAPIForge {

    public RecipeBookAPIForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RecipeBookAPI.init();
        modEventBus.addListener(this::registerCustomRecipeBooks);

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            DevelopmentExtrasForge.loadDevelopmentExtrasEvents(modEventBus);
        }
    }

    void registerCustomRecipeBooks(NewRegistryEvent event) {
        RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY.forEach((modId, registry) -> {
            registry.getTypes().forEach((recipeType, typeHolder) -> {
                typeHolder.setType(RecipeBookType.create(RecipeBookAPI.enumName(modId, typeHolder.getName())));

                typeHolder.getAllCategoryHolders().forEach(categoryHolder -> {
                    RecipeBookCategories category = RecipeBookCategories.create(RecipeBookAPI.enumName(modId, RecipeBookAPI.categoryName(categoryHolder)), categoryHolder.getIconStacks());
                    categoryHolder.setCategory(category);
                });
            });
        });
    }

    public static void registerRecipeBooks(String modId, IEventBus modEventBus) {
        modEventBus.addListener((RegisterRecipeBookCategoriesEvent event) -> {
            RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY.forEach((registeredModId, registry) -> {
                if (modId.equals(registeredModId)) {
                    registry.getTypes().forEach((recipeType, typeHolder) -> {
                        event.registerRecipeCategoryFinder(recipeType.get(), recipe -> RecipeBookAPI.getCategory(recipe, typeHolder));
                        event.registerBookCategories(typeHolder.getType(), typeHolder.getAllCategories());
                        event.registerAggregateCategory(typeHolder.getSearchCategory().getCategory(), typeHolder.getCategories());
                    });
                }
            });
        });
    }
}