package pl.mn.mncustomenchants.ItemMethods;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class LoreComponentDataType implements PersistentDataType<byte[], LoreComponent> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<LoreComponent> getComplexType() {
        return LoreComponent.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull LoreComponent loreComponent, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return SerializationUtils.serialize(loreComponent);
    }

    @Override
    public @NotNull LoreComponent fromPrimitive(byte @NotNull [] bytes, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {

        try {
            InputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(is);
            return (LoreComponent) o.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;

        //return new LoreComponent(s, 1, 1, 1);
        //return SerializationUtils.deserialize(bytes);
    }
}
