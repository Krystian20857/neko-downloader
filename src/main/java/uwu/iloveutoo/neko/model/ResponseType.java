package uwu.iloveutoo.neko.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uwu.iloveutoo.neko.Bootstrapper;
import uwu.iloveutoo.neko.emitter.EndpointEmitter;
import uwu.iloveutoo.neko.emitter.impl.ImageEmitter;
import uwu.iloveutoo.neko.emitter.impl.TextEmitter;
import uwu.iloveutoo.neko.util.LazySupplier;

@RequiredArgsConstructor
public enum ResponseType {

    IMAGE(new LazySupplier<>(() -> new ImageEmitter(Bootstrapper.getInstance().getOkHttpClient()))),
    TEXT(new LazySupplier<>(TextEmitter::new))

    ;

    /* Fields */

    @Getter
    private final LazySupplier<EndpointEmitter<?>> emitterSupplier;

    /* Methods */

    public EndpointEmitter<?> getEmitter(){
        return emitterSupplier.get();
    }

}
