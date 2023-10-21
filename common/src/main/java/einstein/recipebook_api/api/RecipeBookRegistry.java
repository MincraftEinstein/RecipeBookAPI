package einstein.recipebook_api.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

import static einstein.recipebook_api.impl.RecipeBookRegistryImpl.CATEGORY_REGISTRY;

public interface RecipeBookRegistry {

    RecipeBookRegistryImpl INSTANCE = new RecipeBookRegistryImpl();

    static Codec<RecipeBookCategoryHolder> recipeBookCategoryCodec(RecipeBookTypeHolder type) {
        return new Codec<>() {

            private final Codec<RecipeBookCategoryHolder> codec = ExtraCodecs.stringResolverCodec(
                    holder -> holder.getId().toString(),
                    string -> CATEGORY_REGISTRY.values().stream()
                            .filter(holder -> type.equals(holder.getType()) && holder.toString().equals(string))
                            .findFirst().orElse(null)
            );

            @Override
            public <T> DataResult<Pair<RecipeBookCategoryHolder, T>> decode(DynamicOps<T> ops, T input) {
                return codec.decode(ops, input);
            }

            @Override
            public <T> DataResult<T> encode(RecipeBookCategoryHolder input, DynamicOps<T> ops, T prefix) {
                return codec.encode(input, ops, prefix);
            }
        };
    }

    RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group);

    RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks);

    RecipeBookCategoryHolder registerCategory(RecipeBookCategoryHolder category);

    RecipeBookCategoryGroup registerCategoryGroup(ResourceLocation id, RecipeBookCategoryHolder... categories);

    RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryGroup group);
}
