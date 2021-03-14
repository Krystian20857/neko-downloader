package uwu.iloveutoo.neko.emitter.provider;

import uwu.iloveutoo.neko.model.emitter.EndpointModel;

public interface OutputProvider<V extends EndpointModel> {

    void accept(Class<? extends EndpointModel> clazz, V object) throws Throwable;

}
