package einstein.recipebook_api;

import einstein.recipebook_api.api.CategorizedRecipe;
import einstein.recipebook_api.api.RecipeBookCategoryHolder;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(RecipeBookAPI.MOD_ID)
public class RecipeBookAPIForge {

    public RecipeBookAPIForge() {
        RecipeBookAPI.init();

        RecipeBookRegistryImpl.CATEGORY_REGISTRY.forEach((id, holder) -> {
            RecipeBookCategories category = RecipeBookCategories.create(RecipeBookAPI.enumName(id), holder.getIconStacks());
            holder.setCategory(category);
        });
        RecipeBookRegistryImpl.TYPE_REGISTRY.forEach((id, holder) -> {
            RecipeBookType type = RecipeBookType.create(RecipeBookAPI.enumName(id));
            holder.setType(type);
        });
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener((RegisterRecipeBookCategoriesEvent event) -> {
            RecipeBookRegistryImpl.TYPE_REGISTRY.forEach((id, holder) -> {
                event.registerBookCategories(holder.getType(), holder.getGroup().getAllCategories());
            });
            RecipeBookRegistryImpl.CATEGORY_GROUP_REGISTRY.forEach((id, holder) -> {
                event.registerAggregateCategory(holder.getSearchCategory().getCategory(), holder.getCategories().stream().map(RecipeBookCategoryHolder::getCategory).toList());
            });
            RecipeBookRegistryImpl.CATEGORY_REGISTRY.forEach((id, holder) -> {
                // TODO remove null
                event.registerRecipeCategoryFinder(null, recipe -> {
                    if (recipe instanceof CategorizedRecipe categorizedRecipe) {
                        return categorizedRecipe.getRecipeBookCategory().getCategory();
                    }
                    return null;
                });
            });
        });
    }
}