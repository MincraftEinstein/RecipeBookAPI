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
    public static final Map<ResourceLocation, RecipeBookCategoryGroup> CATEGORY_GROUP_REGISTRY = new HashMap<>();

    @Override
    public RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group) {
        RecipeBookTypeHolder type = new RecipeBookTypeHolder(id, group);
        if (TYPE_REGISTRY.put(id, type) != null) {
            throw new IllegalArgumentException("Duplicate type registration: " + id);
        }

        for (RecipeBookCategoryHolder category : group.getCategories()) {
            category.setType(type);
        }
        return type;
    }

    @Override
    public RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks) {
        return registerCategory(new RecipeBookCategoryHolder(id, iconStacks));
    }

    @Override
    public RecipeBookCategoryHolder registerCategory(RecipeBookCategoryHolder category) {
        if (category.getIconStacks().length > 2) {
            throw new IllegalArgumentException("Too many icons for category: " + category.getId() + " - the max is 2");
        }

        if (CATEGORY_REGISTRY.put(category.getId(), category) != null) {
            throw new IllegalArgumentException("Duplicate category registration: " + category.getId());
        }
        return category;
    }

    @Override
    public RecipeBookCategoryGroup registerCategoryGroup(ResourceLocation id, RecipeBookCategoryHolder... categories) {
        return registerCategoryGroup(new RecipeBookCategoryGroup(id, List.of(categories)));
    }

    @Override
    public RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryGroup group) {
        if (group.getCategories().size() > 5) {
            throw new IllegalArgumentException("Too many categories for group: " + group.getId() + " - the max is 5");
        }

        if (CATEGORY_GROUP_REGISTRY.put(group.getId(), group) != null) {
            throw new IllegalArgumentException("Duplicate category group registration: " + group.getId());
        }
        return group;
    }
}
