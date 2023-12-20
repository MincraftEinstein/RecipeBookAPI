package einstein.recipebook_api.examples;

import com.mojang.blaze3d.platform.InputConstants;
import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.api.RecipeBookRegistry;
import einstein.recipebook_api.api.recipe.RecipeBookTypeHolder;
import einstein.recipebook_api.examples.menus.ExampleMenu;
import einstein.recipebook_api.examples.recipes.ExampleRecipe;
import einstein.recipebook_api.examples.recipes.ExampleRecipeSerializer;
import einstein.recipebook_api.examples.screens.ExampleRecipeContextOption;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static einstein.recipebook_api.platform.Services.REGISTRY;

public class ModExamples {

    public static final ResourceLocation EMPTY = RecipeBookAPI.loc("empty");

    public static final Supplier<RecipeSerializer<ExampleRecipe>> EXAMPLE_RECIPE_SERIALIZER = REGISTRY.register("example_recipe_serializer", BuiltInRegistries.RECIPE_SERIALIZER, ExampleRecipeSerializer::new);
    public static final Supplier<RecipeType<ExampleRecipe>> EXAMPLE_RECIPE_TYPE = REGISTRY.register("example_recipe_type", BuiltInRegistries.RECIPE_TYPE, () -> new RecipeType<>() {

        @Override
        public String toString() {
            return "example_recipe_type";
        }
    });
    public static final Supplier<MenuType<ExampleMenu>> EXAMPLE_MENU = REGISTRY.register("example_menu", BuiltInRegistries.MENU, () -> REGISTRY.createMenuType((id, inventory, buf) -> new ExampleMenu(id, inventory)));

    public static final RecipeBookRegistry EXAMPLE_REGISTRY = RecipeBookRegistry.create(RecipeBookAPI.MOD_ID);
    public static final RecipeBookTypeHolder<?, ?> EXAMPLE_TYPE = EXAMPLE_REGISTRY.registerType("example_type", EXAMPLE_RECIPE_TYPE, ExampleRecipeCategories.values());

    public static final KeyMapping OPEN_EXAMPLE_MENU = REGISTRY.registerKeyMapping(() -> new KeyMapping("open_example_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KeyMapping.CATEGORY_INVENTORY));

    public static void init() {
        EXAMPLE_REGISTRY.registerRecipeContextMenuOption(EXAMPLE_RECIPE_TYPE, ExampleRecipeContextOption::new);
    }
}
