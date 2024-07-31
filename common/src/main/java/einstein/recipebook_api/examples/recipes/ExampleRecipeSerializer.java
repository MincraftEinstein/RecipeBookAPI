package einstein.recipebook_api.examples.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import einstein.recipebook_api.examples.ExampleRecipeCategories;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExampleRecipeSerializer implements RecipeSerializer<ExampleRecipe> {

    private static final MapCodec<ExampleRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                Ingredient[] ingredients1 = ingredients.stream().filter(i -> !i.isEmpty()).toArray(Ingredient[]::new);
                if (ingredients1.length == 0) {
                    return DataResult.error(() -> "No Ingredients for example recipe");
                }
                return ingredients1.length > 2
                        ? DataResult.error(() -> "Too many ingredients for example recipe")
                        : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients1));
            }, DataResult::success).forGetter(recipe -> recipe.ingredients),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            ExampleRecipeCategories.CODEC.fieldOf("category").orElse(ExampleRecipeCategories.EXAMPLE_CATEGORY).forGetter(recipe -> recipe.categories),
            Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group)
    ).apply(instance, ExampleRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, ExampleRecipe> STREAM_CODEC = StreamCodec.of(ExampleRecipeSerializer::toNetwork, ExampleRecipeSerializer::fromNetwork);

    @Override
    public MapCodec<ExampleRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ExampleRecipe> streamCodec() {
        return STREAM_CODEC;
    }

    private static ExampleRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        ExampleRecipeCategories category = buf.readEnum(ExampleRecipeCategories.class);
        ItemStack resultStack = ItemStack.STREAM_CODEC.decode(buf);
        String group = buf.readUtf();
        int ingredientCount = buf.readByte();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

        for (int i = 0; i < ingredientCount; i++) {
            ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
        }

        return new ExampleRecipe(ingredients, resultStack, category, group);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buf, ExampleRecipe recipe) {
        buf.writeEnum(recipe.getCategory());
        ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        buf.writeUtf(recipe.getGroup());
        buf.writeByte(recipe.ingredients.size());
        recipe.ingredients.forEach(ingredient -> Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient));
    }
}
