package uwu.iloveutoo.neko.emitter.provider;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import uwu.iloveutoo.neko.model.emitter.EndpointModel;

import java.util.HashMap;
import java.util.Map;

public final class OutputProviderRegister {

    /* Fields */

    private static final Map<Class<? extends EndpointModel>, OutputProvider<?>> providers = new HashMap<>();

    /* Constructor */

    /* Implements */

    /* Overrides */

    /* Methods */

    public static<V extends EndpointModel> void register(Class<V> clazz, OutputProvider<V> outputProvider) {
        providers.put(clazz, outputProvider);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static<V extends EndpointModel> void accept(V object) {
        Class<? extends EndpointModel> clazz = object.getClass();
        OutputProvider<?> provider = providers.get(clazz);
        if(provider != null){
            ((OutputProvider<V>) provider).accept(clazz, object);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static<V extends EndpointModel> OutputProvider<V> get(Class<? extends EndpointModel> clazz){
        return (OutputProvider<V>) providers.get(clazz);
    }

}
