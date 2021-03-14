package uwu.iloveutoo.neko.util;

import java.util.function.Supplier;

public class LazySupplier<V> implements Supplier<V> {

    /* Fields */

    private final Supplier<V> supplier;
    private V value;

    /* Constructor */

    public LazySupplier(Supplier<V> supplier) {
        this.supplier = supplier;
    }

    /* Implements */

    @Override
    public V get() {
        if (this.value == null) {
            this.value = supplier.get();
        }
        return this.value;
    }

    /* Overrides */

    /* Methods */

}
