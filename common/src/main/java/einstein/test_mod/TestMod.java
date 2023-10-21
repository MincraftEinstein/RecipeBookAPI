package einstein.test_mod;

import com.mojang.blaze3d.platform.InputConstants;
import einstein.recipebook_api.api.RecipeBookCategoryGroup;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import einstein.recipebook_api.platform.Services;
import einstein.test_mod.menus.TestMenu;
import einstein.test_mod.recipes.TestRecipe;
import einstein.test_mod.recipes.TestRecipeSerializer;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class TestMod {

    public static final String MOD_ID = "test_mod";

    private static final RegistryHelper REGISTRY = Services.load(RegistryHelper.class);

    public static final Supplier<RecipeSerializer<TestRecipe>> TEST_RECIPE_SERIALIZER = REGISTRY.register("test_recipe_serializer", BuiltInRegistries.RECIPE_SERIALIZER, TestRecipeSerializer::new);
    public static final Supplier<RecipeType<TestRecipe>> TEST_RECIPE_TYPE = REGISTRY.register("test_recipe_type", BuiltInRegistries.RECIPE_TYPE, () -> new RecipeType<>() {

        @Override
        public String toString() {
            return "test_recipe_type";
        }
    });
    public static final Supplier<MenuType<TestMenu>> TEST_MENU = REGISTRY.register("test_menu", BuiltInRegistries.MENU, () -> REGISTRY.createMenuType((id, inventory, buf) -> new TestMenu(id, inventory)));
    public static final RecipeBookTypeHolder TEST_TYPE = RecipeBookRegistry.INSTANCE.registerType(loc("test_type"), RecipeBookCategoryGroup.create(loc("test_group"))
            .namePrefix("test")
            .addCategory("category", new ItemStack(Blocks.COMMAND_BLOCK))
            .addCategory("category2", new ItemStack(Blocks.CHAIN_COMMAND_BLOCK))
            .addCategory("category3", new ItemStack(Blocks.REPEATING_COMMAND_BLOCK))
            .addCategory("category4", new ItemStack(Blocks.STRUCTURE_BLOCK))
            .addCategory("category5", new ItemStack(Blocks.LIGHT))
            .build()
    );
    public static final KeyMapping OPEN_TEST_MENU = REGISTRY.registerKeyMapping(() -> new KeyMapping("open_test_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KeyMapping.CATEGORY_INVENTORY));

    public static void init() {
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
