package uwu.iloveutoo.neko.model.emitter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uwu.iloveutoo.neko.model.Endpoint;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EndpointText extends EndpointModel {

    /* Fields */

    private final String text;

    /* Constructor */

    public EndpointText(Endpoint endpoint, String text) {
        super(endpoint);
        this.text = text;
    }

    /* Implements */

    /* Overrides */

    /* Methods */

}
