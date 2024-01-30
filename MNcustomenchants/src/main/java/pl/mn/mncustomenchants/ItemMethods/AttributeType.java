package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.NamespacedKey;
import pl.mn.mncustomenchants.main;

public class AttributeType {

    private String name;
    private String showName;

    private NamespacedKey key;

    public AttributeType(String name, String showName){
        this.name = name;
        this.showName = showName;
        key = new NamespacedKey(main.getInstance(), this.name);
    }

    public String getName(){
        return name;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public String getShowName() {
        return showName;
    }

    public String ToString(){
        return name;
    }

}
