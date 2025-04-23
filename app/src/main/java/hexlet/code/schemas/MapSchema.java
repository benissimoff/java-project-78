package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    public MapSchema<K, V> required() {
        Function<Map<K, V>, Boolean> check = Objects::nonNull;
        super.addCheck(check);
        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        Function<Map<K, V>, Boolean> check = (map) -> map.size() == size;
        super.addCheck(check);
        return this;
    }

    public MapSchema<K, V> shape(Map<K, BaseSchema<V>> schemas) {
        for (var line : schemas.entrySet()) {
            K entryFieldName = line.getKey();
            BaseSchema<V> entrySchema = line.getValue();
            Function<Map<K, V>, Boolean> check = param -> {
                return entrySchema.isValid(param.get(entryFieldName));

            };
            super.addCheck(check);
        }

        return this;
    }

}
