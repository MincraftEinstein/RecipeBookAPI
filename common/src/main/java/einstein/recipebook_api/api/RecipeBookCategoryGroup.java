package einstein.recipebook_api.api;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static einstein.recipebook_api.api.RecipeBookRegistry.INSTANCE;

public class RecipeBookCategoryGroup {

    private final ResourceLocation id;
    private final RecipeBookCategoryHolder searchCategory;
    private final List<RecipeBookCategoryHolder> categories;

    public RecipeBookCategoryGroup(ResourceLocation id, List<RecipeBookCategoryHolder> categories) {
        this.id = id;
        this.categories = categories;
        searchCategory = INSTANCE.registerCategory(new RecipeBookCategoryHolder(id.withSuffix("_search"), new ItemStack(Items.COMPASS)));
    }

    public static Builder create(ResourceLocation id) {
        return new Builder(id);
    }

    public ResourceLocation getId() {
        return id;
    }

    public RecipeBookCategoryHolder getSearchCategory() {
        return searchCategory;
    }

    public List<RecipeBookCategoryHolder> getCategories() {
        return categories;
    }

    public List<RecipeBookCategories> getAllCategories() {
        List<RecipeBookCategories> allCategories = new ArrayList<>();
        allCategories.add(searchCategory.getCategory());
        allCategories.addAll(categories.stream().map(RecipeBookCategoryHolder::getCategory).toList());
        return allCategories;
    }

    public static class Builder {

        private final ResourceLocation id;
        private final List<RecipeBookCategoryHolder> categories = new ArrayList<>();
        private String prefix;

        private Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder addCategory(String name, ItemStack... iconStacks) {
            ResourceLocation categoryId = new ResourceLocation(id.getNamespace(), (prefix == null ? "" : prefix + "_") + name);
            categories.add(new RecipeBookCategoryHolder(categoryId, iconStacks));
            return this;
        }

        public Builder namePrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public RecipeBookCategoryGroup build() {
            for (RecipeBookCategoryHolder category : categories) {
                INSTANCE.registerCategory(category);
            }
            return INSTANCE.registerCategoryGroup(new RecipeBookCategoryGroup(id, categories));
        }
    }
}
