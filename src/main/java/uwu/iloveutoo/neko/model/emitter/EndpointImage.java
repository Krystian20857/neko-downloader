package uwu.iloveutoo.neko.model.emitter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uwu.iloveutoo.neko.model.Endpoint;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EndpointImage extends EndpointModel implements Closeable {

    /* Fields */

    private final URL imageURL;
    private final InputStream stream;

    public EndpointImage(Endpoint endpoint, URL imageURL, InputStream stream) {
        super(endpoint);
        this.imageURL = imageURL;
        this.stream = stream;
    }

    /* Constructor */

    /* Implements */

    @Override
    public void close() throws IOException {
        stream.close();
    }

    /* Overrides */

    /* Methods */

}
