package einstein.recipebook_api.mixin;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.impl.RecipeBookRegistryImpl;
import einstein.recipebook_api.api.RecipeBookTypeHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(RecipeBookType.class)
public class RecipeBookTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static RecipeBookType[] $VALUES;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clInit(CallbackInfo ci) {
        RecipeBookRegistryImpl.TYPE_REGISTRY.forEach((id, holder) ->
                holder.setType(justMoreCakes$register(id)));
    }

    @Invoker("<init>")
    private static RecipeBookType invokeInit(String name, int id) {
        throw new AssertionError();
    }

    @Unique
    private static RecipeBookType justMoreCakes$register(ResourceLocation id) {
        ArrayList<RecipeBookType> values = new ArrayList<>(Arrays.asList($VALUES));
        RecipeBookType type = invokeInit(RecipeBookAPI.enumName(id), values.get(values.size() - 1).ordinal() + 1);
        values.add(type);
        $VALUES = values.toArray(new RecipeBookType[]{});
        return type;
    }
}
