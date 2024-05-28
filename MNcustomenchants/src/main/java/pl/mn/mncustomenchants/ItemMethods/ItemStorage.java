package pl.mn.mncustomenchants.ItemMethods;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.Commands.UpdateItem;
import pl.mn.mncustomenchants.ItemMethods.ItemData;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.Misc.Keys;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemStorage {

    public static Map<String, ItemData> itemDataMap = new HashMap<>();

    public static String getItemDataFile(ItemStack itemStack){

        return Bukkit.getPluginsFolder().getAbsoluteFile() + "/WoolSMP/items/items.yml";

        /*
        Bukkit.getPlayer("MN_128").sendMessage(ItemUtils.getIdentifier(itemStack) + "   :(");

        if(ItemUtils.getIdentifier(itemStack) == null){
            return null;
        }

        return Bukkit.getPluginsFolder().getAbsoluteFile() + "/WoolSMP/item/" + ItemUtils.getIdentifier(itemStack) + ".yml";


         */
    }

    public static void updateItemData(ItemStack itemStack){


        ItemData itemData = new ItemData();

        File file = new File(getItemDataFile(itemStack));

        if (file.exists()){
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            itemData.setMaterial(cfg.getString(ItemUtils.getIdentifier(itemStack) + ".material"));
            itemData.setEnchantments(cfg.getStringList(ItemUtils.getIdentifier(itemStack) + ".enchantments"));
            itemData.setPersistentB(cfg.getStringList(ItemUtils.getIdentifier(itemStack) + ".persistentB"));
            itemData.setPersistentD(cfg.getStringList(ItemUtils.getIdentifier(itemStack) +".persistentD"));
            itemData.setPersistentS(cfg.getStringList(ItemUtils.getIdentifier(itemStack) +".persistentS"));
            itemData.setUnbreakable(cfg.getString(ItemUtils.getIdentifier(itemStack) +".unbreakable"));
            itemData.setName(cfg.getString(ItemUtils.getIdentifier(itemStack) +".name"));
            itemData.setTexture(cfg.getString(ItemUtils.getIdentifier(itemStack) +".texture"));
        } else {
            return;
        }

        setItemData(itemStack, itemData);

    }

    public static void storeItemData(ItemStack itemStack){
        /*
        if (getItemDataFile(itemStack) == null){
            return;
        }

         */

        ItemData itemData = getItemData(itemStack);

        File file = new File(getItemDataFile(itemStack));
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set(ItemUtils.getIdentifier(itemStack) +".material", itemData.getMaterial());
        cfg.set(ItemUtils.getIdentifier(itemStack) + ".enchantments", itemData.getEnchantments());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".persistentB", itemData.getPersistentB());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".persistentD", itemData.getPersistentD());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".persistentS", itemData.getPersistentS());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".unbreakable", itemData.getUnbreakable());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".name", itemData.getName());
        cfg.set(ItemUtils.getIdentifier(itemStack) +".texture", itemData.getTexture());

        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the items stats based on its identifier and the identifier's corresponding ItemData
     * @param itemStack the itemStack getting updated
     */
    public static void updateItemFromData(ItemStack itemStack){

        if(!itemDataMap.containsKey(ItemUtils.getIdentifier(itemStack))){
            return;
        }

        ItemData itemData = itemDataMap.get(ItemUtils.getIdentifier(itemStack));
        ItemMeta itemMeta = itemStack.getItemMeta();

        //Texture NEED to be done first otherwise all other changes gets wiped
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        i.setTag(itemData.texture);
        itemMeta = CraftItemStack.asBukkitCopy(i).getItemMeta();

        //Material
        itemStack.setType(itemData.material);

        //Unbreakable
        itemMeta.setUnbreakable(itemData.unbreakable);

        //Name
        itemMeta.displayName(itemData.name);


        //Enchantments
        for (Map.Entry<Enchantment, Integer> entry : itemMeta.getEnchants().entrySet()){
            itemMeta.removeEnchant(entry.getKey());
        }
        for (Map.Entry<Enchantment, Integer> current : itemData.enchantments.entrySet()) {
            itemMeta.addEnchant(current.getKey(), current.getValue(), true);
        }

        //PersistentData
        for (NamespacedKey key : itemMeta.getPersistentDataContainer().getKeys()){

            //Ignore shattered when updating item
            if (key.equals(Keys.SHATTERED)){

                continue;
            }

            itemMeta.getPersistentDataContainer().remove(key);
        }
        for (Map.Entry<NamespacedKey, Boolean> entry : itemData.persistentB.entrySet()){
            itemMeta.getPersistentDataContainer().set(entry.getKey(), PersistentDataType.BOOLEAN, entry.getValue());
        }
        for (Map.Entry<NamespacedKey, Double> entry : itemData.persistentD.entrySet()){
            itemMeta.getPersistentDataContainer().set(entry.getKey(), PersistentDataType.DOUBLE, entry.getValue());
        }
        for (Map.Entry<NamespacedKey, String > entry : itemData.persistentS.entrySet()){
            itemMeta.getPersistentDataContainer().set(entry.getKey(), PersistentDataType.STRING, entry.getValue());
        }

        itemStack.setItemMeta(itemMeta);
    }

    /**
     * creates ItemData from itemStack
     * @param itemStack   itemStack
     * @return a itemData object with the itemData of itemStack
     */
    public static ItemData createItemData(ItemStack itemStack){

        ItemData itemData = new ItemData();


        itemData.material = itemStack.getType();

        itemData.enchantments = itemStack.getItemMeta().getEnchants();

        itemData.unbreakable = itemStack.getItemMeta().isUnbreakable();

        itemData.name = itemStack.getItemMeta().displayName();

        //Texture
        net.minecraft.world.item.ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        itemData.texture = i.getTag().getCompound("plain");

        //PersistentData
        PersistentDataContainer pdc = itemStack.getItemMeta().getPersistentDataContainer();


        itemData.persistentB = new HashMap<>();
        itemData.persistentD = new HashMap<>();
        itemData.persistentS = new HashMap<>();
        for (NamespacedKey key : pdc.getKeys()){

            if(pdc.has(key, PersistentDataType.BOOLEAN)){
                itemData.persistentB.put(key, pdc.get(key, PersistentDataType.BOOLEAN));
            }
            if(pdc.has(key, PersistentDataType.DOUBLE)){
                itemData.persistentD.put(key, pdc.get(key, PersistentDataType.DOUBLE));
            }
            if(pdc.has(key, PersistentDataType.STRING)){
                itemData.persistentS.put(key, pdc.get(key, PersistentDataType.STRING));
            }
        }

        return itemData;
    }


    //SetItemIdentifier => CreateItemData(from item) => setItemData(set data to item) => storeItemData(store item's data)
    //SetItemIdentifier(To the identifier of the item we want) => UpdateItemData(set data to item in map) =>  UpdateItemFromData(with data from map)

    /**
     * takes an item and stores it in config under its identifier
     * @param item       the template item
     * @param identifier the items identifier, its name
     */
    public static void createNewItem(ItemStack item, String identifier){

        ItemUtils.setIdentifier(item, identifier);
        ItemData data = createItemData(item);
        setItemData(item, data);
        storeItemData(item);

        //Waste clearing
        removeItemData(item);

    }

    /**
     * Sets an item to a stored item, some stats will be merged instead of overwritten
     * @param item          the item that the data is loaded to
     * @param identifier    identifier for item that it's loaded from
     */
    public static void loadItem(ItemStack item, String identifier){

        ItemUtils.setIdentifier(item, identifier);
        updateItemData(item);
        updateItemFromData(item);
        UpdateItem.updateItem(item);
        //Waste Clearing
        removeItemData(item);

    }



    public static ItemData getItemData(ItemStack itemStack){

        if(ItemUtils.getIdentifier(itemStack) == null){
            return null;
            //return new ItemData();
        }

        if(!itemDataMap.containsKey(ItemUtils.getIdentifier(itemStack))){

            ItemData itemData = new ItemData();
            itemDataMap.put(ItemUtils.getIdentifier(itemStack), itemData);
            return itemData;

        }

        return itemDataMap.get(ItemUtils.getIdentifier(itemStack));
    }

    public static void setItemData(ItemStack itemStack, ItemData itemData){

        if (ItemUtils.getIdentifier(itemStack) == null){
            return;
        }

        itemDataMap.put(ItemUtils.getIdentifier(itemStack), itemData);
    }

    public static void removeItemData(ItemStack itemStack){
        itemDataMap.remove(ItemUtils.getIdentifier(itemStack));
    }




}
