package einstein.recipebook_api.impl;

import einstein.recipebook_api.api.RecipeBookCategoryGroup;
import einstein.recipebook_api.api.RecipeBookCategoryHolder;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RecipeBookRegistryImpl implements RecipeBookRegistry {

    public static final List<RecipeBookTypeHolder> TYPE_REGISTRY = new ArrayList<>();
    public static final List<RecipeBookCategoryHolder> CATEGORY_REGISTRY = new ArrayList<>();
    public static final List<RecipeBookCategoryGroup> CATEGORY_GROUP_REGISTRY = new ArrayList<>();

    @Override
    public RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group) {
        RecipeBookTypeHolder holder = new RecipeBookTypeHolder(id, group);
        TYPE_REGISTRY.add(holder);
        return holder;
    }

    @Override
    public RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks) {
        RecipeBookCategoryHolder holder = new RecipeBookCategoryHolder(id, iconStacks);
        CATEGORY_REGISTRY.add(holder);
        return holder;
    }

    @Override
    public RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryHolder mainCategory, RecipeBookCategoryHolder... categories) {
        RecipeBookCategoryGroup group = new RecipeBookCategoryGroup(mainCategory, List.of(categories));
        CATEGORY_GROUP_REGISTRY.add(group);
        return group;
    }
}
