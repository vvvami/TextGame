package net.vami.game.interactables.ai.tasks;

import com.google.gson.*;
import net.vami.util.ClassUtil;

import java.lang.reflect.Type;

public class TaskAdapter implements JsonSerializer, JsonDeserializer {

    @Override
    public Task deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String simpleName = jsonElement.getAsString();
        String interfaceName = Task.class.getName();
        String fullName = interfaceName.substring(0, interfaceName.lastIndexOf(".") + 1) + simpleName;
        Class klass = ClassUtil.getObjectClass(fullName);
        return (Task) new Gson().fromJson("{}", klass);
    }

    @Override
    public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(o.getClass().getSimpleName());
    }
}
