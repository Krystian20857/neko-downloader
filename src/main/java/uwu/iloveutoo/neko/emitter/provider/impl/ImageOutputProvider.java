package uwu.iloveutoo.neko.emitter.provider.impl;

import com.google.common.io.ByteStreams;
import uwu.iloveutoo.neko.emitter.provider.OutputProvider;
import uwu.iloveutoo.neko.model.emitter.EndpointImage;
import uwu.iloveutoo.neko.model.emitter.EndpointModel;
import uwu.iloveutoo.neko.util.Util;
import uwu.iloveutoo.neko.util.VisualHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageOutputProvider implements OutputProvider<EndpointImage> {

    /* Fields */

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), Util.namedFactory("image-io-pool-%d"));
    private final File saveDirectory;


    /* Constructor */

    public ImageOutputProvider(){
        this(null);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ImageOutputProvider(File saveDirectory) {
        this.saveDirectory = saveDirectory;
        if (saveDirectory != null) {
            if (!saveDirectory.exists()) {
                saveDirectory.mkdirs();
                saveDirectory.mkdir();
            }
            if (!saveDirectory.isDirectory()) {
                throw new IllegalArgumentException("Save directory must be directory");
            }
        }
    }

    /* Implements */

    @Override
    public void accept(Class<? extends EndpointModel> clazz, EndpointImage object) throws Throwable {
        if (this.saveDirectory != null) {
            this.executor.submit(() -> {
                try {
                    this.saveImage(object);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
            return;
        }
        System.out.println(VisualHelper.formatImage(object));
        object.close();
    }

    /* Overrides */

    /* Methods */

    private void saveImage(EndpointImage object) throws IOException {
        try {
            String path = object.getImageURL().getPath();
            String filename = path.substring(path.lastIndexOf("/") + 1);
            File file = new File(this.saveDirectory, filename);
            try (FileOutputStream fileWriter = new FileOutputStream(file)) {
                ByteStreams.copy(object.getStream(), fileWriter);
            }
            System.out.println(VisualHelper.formatImageDownload(object));
        } finally {
            object.close();
        }
    }

}
