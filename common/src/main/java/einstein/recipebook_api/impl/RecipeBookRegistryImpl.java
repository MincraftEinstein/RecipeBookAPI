package einstein.recipebook_api.impl;

import einstein.recipebook_api.api.RecipeBookCategoryGroup;
import einstein.recipebook_api.api.RecipeBookCategoryHolder;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeBookRegistryImpl implements RecipeBookRegistry {

    public static final Map<ResourceLocation, RecipeBookTypeHolder> TYPE_REGISTRY = new HashMap<>();
    public static final Map<ResourceLocation, RecipeBookCategoryHolder> CATEGORY_REGISTRY = new HashMap<>();
    public static final List<RecipeBookCategoryGroup> CATEGORY_GROUP_REGISTRY = new ArrayList<>();

    @Override
    public RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group) {
        RecipeBookTypeHolder holder = new RecipeBookTypeHolder(id, group);
        TYPE_REGISTRY.put(id, holder);
        return holder;
    }

    @Override
    public RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks) {
        if (iconStacks.length > 2) {
            throw new IllegalArgumentException("Too many icons for category: " + id + " - the max is 2");
        }
        RecipeBookCategoryHolder holder = new RecipeBookCategoryHolder(id, iconStacks);
        CATEGORY_REGISTRY.put(id, holder);
        return holder;
    }

    @Override
    public RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryHolder mainCategory, RecipeBookCategoryHolder... categories) {
        if (categories.length > 5) {
            throw new IllegalArgumentException("Too many categories for group the max is 5");
        }
        RecipeBookCategoryGroup group = new RecipeBookCategoryGroup(mainCategory, List.of(categories));
        CATEGORY_GROUP_REGISTRY.add(group);
        return group;
    }
}
