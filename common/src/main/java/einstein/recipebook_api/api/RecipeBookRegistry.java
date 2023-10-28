package einstein.recipebook_api.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RecipeBookRegistry {

    public static final Map<String, RecipeBookRegistry> RECIPE_BOOK_REGISTRY = new HashMap<>();
    private final Map<Supplier<? extends RecipeType<?>>, RecipeBookTypeHolder<?, ?>> types = new HashMap<>();

    public static Codec<RecipeBookCategoryHolder<?>> recipeBookCategoryCodec(RecipeBookRegistry registry, RecipeType<?> recipeType) {
        return new Codec<>() {

            private final Codec<RecipeBookCategoryHolder<?>> codec = ExtraCodecs.stringResolverCodec(
                    RecipeBookCategoryHolder::getName,
                    string -> {
                        RecipeBookTypeHolder<?, ?> typeHolder = registry.types.values().stream()
                                .filter(holder -> holder.getRecipeType().get().equals(recipeType))
                                .findFirst().orElse(null);

                        if (typeHolder != null) {
                            return typeHolder.getCategoryHolders().stream()
                                    .filter(category -> category.getName().equals(string))
                                    .findFirst().orElse(null);
                        }
                        return null;
                    }
            );

            @Override
            public <T> DataResult<Pair<RecipeBookCategoryHolder<?>, T>> decode(DynamicOps<T> ops, T input) {
                return codec.decode(ops, input);
            }

            @Override
            public <T> DataResult<T> encode(RecipeBookCategoryHolder<?> input, DynamicOps<T> ops, T prefix) {
                return codec.encode(input, ops, prefix);
            }
        };
    }

    public static RecipeBookRegistry create(String modId) {
        if (RECIPE_BOOK_REGISTRY.containsKey(modId)) {
            throw new IllegalArgumentException("Mod id already registered");
        }

        RecipeBookRegistry registry = new RecipeBookRegistry();
        RECIPE_BOOK_REGISTRY.put(modId, registry);
        return registry;
    }

    public <T extends Enum<?> & RecipeBookCategoryEnum, V extends RecipeType<?>> RecipeBookTypeHolder<T, V> registerType(String name, Supplier<V> recipeType, T[] categories) {
        return registerType(name, recipeType, categories, categories[0]);
    }

    public <T extends Enum<?> & RecipeBookCategoryEnum, V extends RecipeType<?>> RecipeBookTypeHolder<T, V> registerType(String name, Supplier<V> recipeType, T[] categories, T fallbackCategory) {
        if (types.containsKey(recipeType)) {
            throw new IllegalArgumentException("Duplicate type registration: " + name);
        }

        RecipeBookTypeHolder<T, V> type = new RecipeBookTypeHolder<>(name, recipeType, categories, fallbackCategory);
        validateCategories(type, categories);
        types.put(recipeType, type);
        return type;
    }

    private static <T extends Enum<?> & RecipeBookCategoryEnum> void validateCategories(RecipeBookTypeHolder<T, ?> type, T[] categories) {
        if (categories.length > 5) {
            throw new IllegalArgumentException("Too many categories for type: " + type.getName() + " - the max is 5");
        }

        for (T category : categories) {
            if (category.getIconStacks().length > 2) {
                throw new IllegalArgumentException("Too many icons for category: " + category.getName() + " - the max is 2");
            }
        }
    }

    public Map<Supplier<? extends RecipeType<?>>, RecipeBookTypeHolder<?, ?>> getTypes() {
        return types;
    }
}
