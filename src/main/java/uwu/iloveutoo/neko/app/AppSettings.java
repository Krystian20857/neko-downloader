package uwu.iloveutoo.neko.app;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uwu.iloveutoo.neko.model.ContentType;

import java.io.File;
import java.util.Set;

@Getter
@Setter
@Builder(buildMethodName = "create")
public class AppSettings {

    /* Fields */

    private int amount;
    private boolean saveImages;
    private File saveDirectory;
    private Set<String> categories;
    private ContentType contentType;

    /* Constructor */

    /* Implements */

    /* Overrides */

    /* Methods */

}
