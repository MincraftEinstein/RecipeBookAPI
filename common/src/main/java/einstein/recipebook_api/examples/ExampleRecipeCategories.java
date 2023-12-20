package einstein.recipebook_api.examples;

import com.mojang.serialization.Codec;
import einstein.recipebook_api.api.category.RecipeBookCategoryEnum;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public enum ExampleRecipeCategories implements RecipeBookCategoryEnum {
    EXAMPLE_CATEGORY("example_category", new ItemStack(Blocks.COMMAND_BLOCK)),
    EXAMPLE_CATEGORY2("example_category2", new ItemStack(Blocks.CHAIN_COMMAND_BLOCK)),
    EXAMPLE_CATEGORY3("example_category3", new ItemStack(Blocks.REPEATING_COMMAND_BLOCK)),
    EXAMPLE_CATEGORY4("example_category4", new ItemStack(Blocks.STRUCTURE_BLOCK)),
    EXAMPLE_CATEGORY5("example_category5", new ItemStack(Blocks.LIGHT));

    public static final Codec<ExampleRecipeCategories> CODEC = RecipeBookCategoryEnum.codec(ExampleRecipeCategories::values);

    private final String name;
    private final ItemStack[] iconStacks;

    ExampleRecipeCategories(String name, ItemStack... iconStacks) {
        this.name = name;
        this.iconStacks = iconStacks;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemStack[] getIconStacks() {
        return iconStacks;
    }
}
