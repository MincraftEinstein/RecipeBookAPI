package einstein.recipebook_api.api.category;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface RecipeBookCategoryEnum {

    String getName();

    ItemStack[] getIconStacks();

    static <T extends Enum<?> & RecipeBookCategoryEnum> Codec<T> codec(Supplier<T[]> valuesSupplier) {
        Map<String, T> byName = Arrays.stream(valuesSupplier.get()).collect(Collectors.toMap(T::getName, t -> t));
        return Codec.either(Codec.STRING, Codec.INT).comapFlatMap(either -> either.map(
                name -> {
                    T t = byName.get(name);
                    return t != null ? DataResult.success(t) : DataResult.error(() -> "Unknown enum name: " + name);
                },
                index -> {
                    T[] values = valuesSupplier.get();
                    return index >= 0 && index < values.length ? DataResult.success(values[index]) : DataResult.error(() -> "Unknown enum index:" + index);
                }
        ), t -> Either.left(t.getName()));
    }
}
