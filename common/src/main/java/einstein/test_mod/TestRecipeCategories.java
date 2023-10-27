package einstein.test_mod;

import einstein.recipebook_api.api.RecipeBookCategoryEnum;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public enum TestRecipeCategories implements RecipeBookCategoryEnum {
    CATEGORY("test_category", new ItemStack(Blocks.COMMAND_BLOCK)),
    CATEGORY2("test_category2", new ItemStack(Blocks.CHAIN_COMMAND_BLOCK)),
    CATEGORY3("test_category3", new ItemStack(Blocks.REPEATING_COMMAND_BLOCK)),
    CATEGORY4("test_category4", new ItemStack(Blocks.STRUCTURE_BLOCK)),
    CATEGORY5("test_category5", new ItemStack(Blocks.LIGHT));

    public static final EnumCodec<TestRecipeCategories> CODEC = StringRepresentable.fromEnum(TestRecipeCategories::values);

    private final String name;
    private final ItemStack[] iconStacks;

    TestRecipeCategories(String name, ItemStack... iconStacks) {
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
