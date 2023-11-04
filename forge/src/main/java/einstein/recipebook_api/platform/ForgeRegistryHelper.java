package einstein.recipebook_api.platform;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.platform.services.RegistryHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeRegistryHelper implements RegistryHelper {

    public static final Map<Registry<?>, DeferredRegister<?>> REGISTRIES = new HashMap<>();
    public static final List<Supplier<KeyMapping>> KEY_MAPPINGS = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> Supplier<T> register(String name, Registry<?> registry, Supplier<T> supplier) {
        return ((DeferredRegister<T>) getOrCreateRegistry(registry)).register(name, supplier);
    }

    @SuppressWarnings("unchecked")
    private static <T> DeferredRegister<T> getOrCreateRegistry(Registry<T> registry) {
        if (REGISTRIES.containsKey(registry)) {
            return (DeferredRegister<T>) REGISTRIES.get(registry);
        }

        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry.key(), RecipeBookAPI.MOD_ID);
        REGISTRIES.put(registry, deferredRegister);
        return deferredRegister;
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuTypeSupplier<T> supplier) {
        return IForgeMenuType.create(supplier::create);
    }

    @Override
    public KeyMapping registerKeyMapping(Supplier<KeyMapping> keyMapping) {
        KEY_MAPPINGS.add(keyMapping);
        return keyMapping.get();
    }
}
