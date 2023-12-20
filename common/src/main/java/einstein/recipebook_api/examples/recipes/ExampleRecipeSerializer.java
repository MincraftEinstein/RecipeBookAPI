package einstein.recipebook_api.examples.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import einstein.recipebook_api.examples.ExampleRecipeCategories;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExampleRecipeSerializer implements RecipeSerializer<ExampleRecipe> {

    @Override
    public Codec<ExampleRecipe> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                    Ingredient[] ingredients1 = ingredients.stream().filter(i -> !i.isEmpty()).toArray(Ingredient[]::new);
                    if (ingredients1.length == 0) {
                        return DataResult.error(() -> "No Ingredients for example recipe");
                    }
                    return ingredients1.length > 2
                            ? DataResult.error(() -> "Too many ingredients for example recipe")
                            : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients1));
                }, DataResult::success).forGetter(recipe -> recipe.ingredients),
                BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter(recipe -> recipe.result),
                ExampleRecipeCategories.CODEC.fieldOf("category").orElse(ExampleRecipeCategories.EXAMPLE_CATEGORY).forGetter(recipe -> recipe.categories),
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> recipe.group)
        ).apply(instance, ExampleRecipe::new));
    }

    @Override
    public ExampleRecipe fromNetwork(FriendlyByteBuf buf) {
        ExampleRecipeCategories category = buf.readEnum(ExampleRecipeCategories.class);
        ItemStack resultStack = buf.readItem();
        String group = buf.readUtf();
        int ingredientCount = buf.readByte();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

        for (int i = 0; i < ingredientCount; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buf));
        }

        return new ExampleRecipe(ingredients, resultStack, category, group);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ExampleRecipe recipe) {
        buf.writeEnum(recipe.getCategory());
        buf.writeItem(recipe.result);
        buf.writeUtf(recipe.getGroup());
        buf.writeByte(recipe.ingredients.size());
        recipe.ingredients.forEach(ingredient -> ingredient.toNetwork(buf));
    }
}
