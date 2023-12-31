package einstein.recipebook_api.api.category;

import einstein.recipebook_api.api.recipe.RecipeBookTypeHolder;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RecipeBookCategoryHolder<T extends Enum<?> & RecipeBookCategoryEnum> {

    @Nullable
    private RecipeBookCategories category;
    private final String name;
    private final boolean isSearch;
    private final ItemStack[] iconStacks;
    private final T enumType;
    private final RecipeBookTypeHolder<T, ?> type;

    public RecipeBookCategoryHolder(T enumType, RecipeBookTypeHolder<T, ?> type) {
        this.name = enumType.name();
        this.isSearch = false;
        this.type = type;
        this.iconStacks = enumType.getIconStacks();
        this.enumType = enumType;
    }

    public RecipeBookCategoryHolder(String name, boolean isSearch, RecipeBookTypeHolder<T, ?> type, ItemStack... iconStacks) {
        this.name = name;
        this.isSearch = isSearch;
        this.type = type;
        this.iconStacks = iconStacks;
        this.enumType = null;
    }

    public String getName() {
        return name;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public ItemStack[] getIconStacks() {
        return iconStacks;
    }

    public RecipeBookCategories getCategory() {
        return Objects.requireNonNull(category, "Recipe Book Category not present: " + name);
    }

    public void setCategory(RecipeBookCategories category) {
        this.category = Objects.requireNonNull(category, "Failed to assign Recipe Book Category to: " + name);
    }

    public T getEnumType() {
        return enumType;
    }

    public RecipeBookTypeHolder<T, ?> getType() {
        return type;
    }
}
