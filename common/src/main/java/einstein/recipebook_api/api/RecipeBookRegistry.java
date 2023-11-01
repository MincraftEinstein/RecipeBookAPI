package einstein.recipebook_api.api;

import einstein.recipebook_api.api.category.RecipeBookCategoryEnum;
import einstein.recipebook_api.api.recipe.RecipeBookTypeHolder;
import einstein.recipebook_api.api.screen.RecipeContextMenuOption;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

import static einstein.recipebook_api.impl.RecipeBookRegistryImpl.RECIPE_BOOK_REGISTRY;

public interface RecipeBookRegistry {

    static RecipeBookRegistryImpl create(String modId) {
        if (RECIPE_BOOK_REGISTRY.containsKey(modId)) {
            throw new IllegalArgumentException("Mod id already registered");
        }

        RecipeBookRegistryImpl registry = new RecipeBookRegistryImpl();
        RECIPE_BOOK_REGISTRY.put(modId, registry);
        return registry;
    }

    <T extends RecipeContextMenuOption, V extends RecipeType<?>> void registerRecipeContextMenuOption(Supplier<V> recipeType, Supplier<T> contextMenuOption);

    <T extends Enum<?> & RecipeBookCategoryEnum, V extends RecipeType<?>> RecipeBookTypeHolder<T, V> registerType(String name, Supplier<V> recipeType, T[] categories);
}
