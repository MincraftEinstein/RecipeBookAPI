package einstein.test_mod.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import einstein.test_mod.TestRecipeCategories;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class TestRecipeSerializer implements RecipeSerializer<TestRecipe> {

    @Override
    public Codec<TestRecipe> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                    Ingredient[] ingredients1 = ingredients.stream().filter(i -> !i.isEmpty()).toArray(Ingredient[]::new);
                    if (ingredients1.length == 0) {
                        return DataResult.error(() -> "No Ingredients for test recipe");
                    }
                    return ingredients1.length > 2
                            ? DataResult.error(() -> "Too many ingredients for test recipe")
                            : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients1));
                }, DataResult::success).forGetter(recipe -> recipe.ingredients),
                BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter(recipe -> recipe.result),
                TestRecipeCategories.CODEC.fieldOf("category").forGetter(recipe -> recipe.categories)
        ).apply(instance, TestRecipe::new));
    }

    @Override
    public TestRecipe fromNetwork(FriendlyByteBuf buf) {
        TestRecipeCategories category = buf.readEnum(TestRecipeCategories.class);
        ItemStack resultStack = buf.readItem();
        int ingredientCount = buf.readByte();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

        for (int i = 0; i < ingredientCount; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buf));
        }

        return new TestRecipe(ingredients, resultStack, category);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, TestRecipe recipe) {
        buf.writeEnum(recipe.getCategory());
        buf.writeItem(recipe.result);
        buf.writeByte(recipe.ingredients.size());
        recipe.ingredients.forEach(ingredient -> ingredient.toNetwork(buf));
    }
}
