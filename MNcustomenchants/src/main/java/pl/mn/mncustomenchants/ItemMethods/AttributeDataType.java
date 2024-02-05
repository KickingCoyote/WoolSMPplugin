package pl.mn.mncustomenchants.ItemMethods;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.logging.Logger;

public class AttributeDataType implements PersistentDataType<byte[], Attribute> {
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<Attribute> getComplexType() {
        return Attribute.class;
    }

    @Override
    public byte[] toPrimitive(Attribute attribute, PersistentDataAdapterContext arg) {
        return SerializationUtils.serialize(attribute);
    }



    //When the funny tutorial guy gives you magic code, do not question it.
    @Override
    public Attribute fromPrimitive(byte[] bytes, PersistentDataAdapterContext persistentDataAdapterContext) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(is);
            return (Attribute) o.readObject();
        } catch (IOException | ClassNotFoundException e){
            Logger logger = Logger.getLogger("Minecraft");
            logger.warning(e.toString());
        }
        return null;
    }
}
