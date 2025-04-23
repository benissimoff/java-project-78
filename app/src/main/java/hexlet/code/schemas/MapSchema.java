package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
//    public MapSchema<K, V> required() {
//        Function<Map<K, V>, Boolean> check = Objects::nonNull;
//        super.addCheck(check);
//        return this;
//    }

    public MapSchema<K, V> sizeOf(int size) {
        Function<Map<K, V>, Boolean> check = (map) -> map.size() == size;
        super.addCheck(check);
        return this;
    }
}
