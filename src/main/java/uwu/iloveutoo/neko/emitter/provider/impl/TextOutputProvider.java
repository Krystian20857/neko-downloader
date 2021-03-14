package uwu.iloveutoo.neko.emitter.provider.impl;

import uwu.iloveutoo.neko.emitter.provider.OutputProvider;
import uwu.iloveutoo.neko.model.emitter.EndpointModel;
import uwu.iloveutoo.neko.model.emitter.EndpointText;
import uwu.iloveutoo.neko.util.VisualHelper;

public class TextOutputProvider implements OutputProvider<EndpointText> {

    /* Fields */

    /* Constructor */

    /* Implements */

    @Override
    public void accept(Class<? extends EndpointModel> clazz, EndpointText object) {
        System.out.println(VisualHelper.formatTextEndpoint(object));
    }

    /* Overrides */

    /* Methods */

}
