package einstein.recipebook_api.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

public interface RecipeBookRegistry {

    RecipeBookRegistryImpl INSTANCE = new RecipeBookRegistryImpl();
    Codec<RecipeBookCategoryHolder> RECIPE_BOOK_CATEGORY_CODEC = new Codec<>() {

        private static final Codec<RecipeBookCategoryHolder> CODEC = ExtraCodecs.stringResolverCodec(holder -> holder.getId().toString(), string -> {
            if (RecipeBookRegistryImpl.CATEGORY_REGISTRY.size() > StringRepresentable.PRE_BUILT_MAP_THRESHOLD) {
                return string == null || ResourceLocation.tryParse(string) == null
                        ? null
                        : RecipeBookRegistryImpl.CATEGORY_REGISTRY.get(new ResourceLocation(string));
            }
            for (RecipeBookCategoryHolder holder : RecipeBookRegistryImpl.CATEGORY_REGISTRY.values()) {
                if (holder.toString().equals(string)) {
                    return holder;
                }
            }

            return null;
        });

        @Override
        public <T> DataResult<Pair<RecipeBookCategoryHolder, T>> decode(DynamicOps<T> ops, T input) {
            return CODEC.decode(ops, input);
        }

        @Override
        public <T> DataResult<T> encode(RecipeBookCategoryHolder input, DynamicOps<T> ops, T prefix) {
            return CODEC.encode(input, ops, prefix);
        }
    };

    RecipeBookTypeHolder registerType(ResourceLocation id, RecipeBookCategoryGroup group);

    RecipeBookCategoryHolder registerCategory(ResourceLocation id, ItemStack... iconStacks);

    RecipeBookCategoryHolder registerCategory(RecipeBookCategoryHolder category);

    RecipeBookCategoryGroup registerCategoryGroup(ResourceLocation id, RecipeBookCategoryHolder... categories);

    RecipeBookCategoryGroup registerCategoryGroup(RecipeBookCategoryGroup group);
}
