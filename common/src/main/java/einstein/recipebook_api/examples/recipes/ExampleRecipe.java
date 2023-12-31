package einstein.recipebook_api.examples.recipes;

import einstein.recipebook_api.api.recipe.CategorizedRecipe;
import einstein.recipebook_api.examples.ModExamples;
import einstein.recipebook_api.examples.ExampleRecipeCategories;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ExampleRecipe implements Recipe<Container>, CategorizedRecipe<ExampleRecipeCategories> {

    protected final NonNullList<Ingredient> ingredients;
    protected final ItemStack result;
    protected final ExampleRecipeCategories categories;
    protected final String group;

    public ExampleRecipe(NonNullList<Ingredient> ingredients, ItemStack result, ExampleRecipeCategories categories, String group) {
        this.ingredients = ingredients;
        this.result = result;
        this.categories = categories;
        this.group = group;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ingredients.get(0).test(container.getItem(0)) && ingredients.get(1).test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return true;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModExamples.EXAMPLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModExamples.EXAMPLE_RECIPE_TYPE.get();
    }

    public ExampleRecipeCategories getCategory() {
        return categories;
    }

    @Override
    public ExampleRecipeCategories getRecipeBookCategory() {
        return categories;
    }
}
