package einstein.recipebook_api.api;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RecipeBookTypeHolder<T extends Enum<?> & RecipeBookCategoryEnum, V extends RecipeType<?>> {

    private RecipeBookType type;
    private final String name;
    private final Supplier<V> recipeType;
    private final RecipeBookCategoryHolder<T> searchCategory;
    private final List<RecipeBookCategoryHolder<T>> categories;
    private final RecipeBookCategoryHolder<T> fallbackCategory;

    public RecipeBookTypeHolder(String name, Supplier<V> recipeType, T[] categories, T fallbackCategory) {
        this.name = name;
        this.recipeType = recipeType;
        this.fallbackCategory = new RecipeBookCategoryHolder<>(fallbackCategory);
        searchCategory = new RecipeBookCategoryHolder<>(name + "_search", true, new ItemStack(Items.COMPASS));

        List<RecipeBookCategoryHolder<T>> holders = new ArrayList<>();
        for (T category : categories) {
            holders.add(new RecipeBookCategoryHolder<>(category));
        }
        this.categories = holders;
    }

    public String getName() {
        return name;
    }

    public Supplier<V> getRecipeType() {
        return recipeType;
    }

    public RecipeBookCategoryHolder<T> getSearchCategory() {
        return searchCategory;
    }

    public List<RecipeBookCategoryHolder<T>> getCategoryHolders() {
        return categories;
    }

    public List<RecipeBookCategoryHolder<T>> getAllCategoryHolders() {
        List<RecipeBookCategoryHolder<T>> allCategories = new ArrayList<>();
        allCategories.add(searchCategory);
        allCategories.addAll(getCategoryHolders());
        return allCategories;
    }

    public List<RecipeBookCategories> getCategories() {
        return categories.stream().map(RecipeBookCategoryHolder::getCategory).toList();
    }

    public List<RecipeBookCategories> getAllCategories() {
        return getAllCategoryHolders().stream().map(RecipeBookCategoryHolder::getCategory).toList();
    }

    public RecipeBookCategoryHolder<T> getFallbackCategory() {
        return fallbackCategory;
    }

    public RecipeBookType getType() {
        return type;
    }

    public void setType(RecipeBookType type) {
        this.type = type;
    }
}
