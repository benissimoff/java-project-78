package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    public MapSchema<K, V> required() {
        addCheck("required", Objects::nonNull);

        return this;
    }

    public MapSchema<K, V> sizeof(int size) {
        addCheck("sizeof", (map) -> map.size() == size);

        return this;
    }

    public MapSchema<K, V> shape(Map<K, BaseSchema<V>> schemas) {
        schemas.forEach((itemKey, itemSchema) -> {
            Predicate<Map<K, V>> check = param -> itemSchema.isValid(param.get(itemKey));
            addCheck("shape", check);
        });

        return this;
    }

    @Override
    protected void addCheck(String checkName, Predicate<Map<K, V>> checkFunction) {
        super.addCheck(checkName, checkFunction);
    }

}
