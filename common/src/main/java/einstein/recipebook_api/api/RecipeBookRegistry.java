package einstein.recipebook_api.api;

import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface RecipeBookRegistry {

    RecipeBookRegistryImpl INSTANCE = new RecipeBookRegistryImpl();

    RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group);

    RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks);

    RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryHolder mainCategory, RecipeBookCategoryHolder... categories);
}
