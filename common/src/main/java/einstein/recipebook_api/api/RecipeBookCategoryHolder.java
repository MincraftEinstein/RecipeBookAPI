package einstein.recipebook_api.api;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;

public class RecipeBookCategoryHolder<T extends Enum<?> & RecipeBookCategoryEnum> {

    private RecipeBookCategories category;
    private final String name;
    private final boolean isSearch;
    private final ItemStack[] iconStacks;
    private final T enumType;

    public RecipeBookCategoryHolder(T enumType) {
        this.name = enumType.getName();
        this.isSearch = false;
        this.iconStacks = enumType.getIconStacks();
        this.enumType = enumType;
    }

    public RecipeBookCategoryHolder(String name, boolean isSearch, ItemStack... iconStacks) {
        this.name = name;
        this.isSearch = isSearch;
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
        return category;
    }

    public void setCategory(RecipeBookCategories category) {
        this.category = category;
    }

    public T getEnumType() {
        return enumType;
    }
}
