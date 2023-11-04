package einstein.recipebook_api.dev_extras;

import com.mojang.blaze3d.platform.InputConstants;
import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.recipe.RecipeBookTypeHolder;
import einstein.recipebook_api.dev_extras.menus.TestMenu;
import einstein.recipebook_api.dev_extras.recipes.TestRecipe;
import einstein.recipebook_api.dev_extras.recipes.TestRecipeSerializer;
import einstein.recipebook_api.dev_extras.screens.TestRecipeContextOption;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static einstein.recipebook_api.platform.Services.REGISTRY;

public class DevelopmentExtras {

    public static final ResourceLocation EMPTY = RecipeBookAPI.loc("empty");

    public static final Supplier<RecipeSerializer<TestRecipe>> TEST_RECIPE_SERIALIZER = REGISTRY.register("test_recipe_serializer", BuiltInRegistries.RECIPE_SERIALIZER, TestRecipeSerializer::new);
    public static final Supplier<RecipeType<TestRecipe>> TEST_RECIPE_TYPE = REGISTRY.register("test_recipe_type", BuiltInRegistries.RECIPE_TYPE, () -> new RecipeType<>() {

        @Override
        public String toString() {
            return "test_recipe_type";
        }
    });
    public static final Supplier<MenuType<TestMenu>> TEST_MENU = REGISTRY.register("test_menu", BuiltInRegistries.MENU, () -> REGISTRY.createMenuType((id, inventory, buf) -> new TestMenu(id, inventory)));

    public static final RecipeBookRegistry TEST_REGISTRY = RecipeBookRegistry.create(RecipeBookAPI.MOD_ID);
    public static final RecipeBookTypeHolder<?, ?> TEST_TYPE = TEST_REGISTRY.registerType("test_type", TEST_RECIPE_TYPE, TestRecipeCategories.values());

    public static final KeyMapping OPEN_TEST_MENU = REGISTRY.registerKeyMapping(() -> new KeyMapping("open_test_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KeyMapping.CATEGORY_INVENTORY));

    public static void init() {
        TEST_REGISTRY.registerRecipeContextMenuOption(TEST_RECIPE_TYPE, TestRecipeContextOption::new);
    }
}
