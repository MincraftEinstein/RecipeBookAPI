package einstein.recipebook_api.platform;

import einstein.recipebook_api.RecipeBookAPI;
import einstein.recipebook_api.platform.services.IPlatformHelper;
import einstein.recipebook_api.platform.services.RegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final RegistryHelper REGISTRY = Services.load(RegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        RecipeBookAPI.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}