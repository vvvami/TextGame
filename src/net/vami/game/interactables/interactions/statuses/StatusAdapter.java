package net.vami.game.interactables.interactions.statuses;

import com.google.gson.*;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.util.ClassUtil;

import java.lang.reflect.Type;

public class StatusAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Status deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String simpleName = jsonElement.getAsString();
        String interfaceName = Status.class.getName();
        String fullName = interfaceName.substring(0, interfaceName.lastIndexOf(".") + 1) + simpleName;
        Class klass = ClassUtil.getObjectClass(fullName);
        return (Status) new Gson().fromJson("{}", klass);
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(o.getClass().getSimpleName());
    }

}
