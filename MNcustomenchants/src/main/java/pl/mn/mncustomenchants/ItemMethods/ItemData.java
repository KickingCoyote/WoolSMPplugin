package pl.mn.mncustomenchants.ItemMethods;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.*;


public class ItemData {


    public Material material;

    public Boolean unbreakable;

    public Map<Enchantment, Integer> enchantments;

    public Map<NamespacedKey, Boolean> persistentB;
    public Map<NamespacedKey, Double> persistentD;
    public Map<NamespacedKey, String> persistentS;

    public Component name;

    public String texture;

    public String getMaterial() {
        return material.toString();
    }

    public void setMaterial(String string) {
        this.material = Material.valueOf(string);
    }

    public List<String> getEnchantments() {

        List<String> s = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> current : enchantments.entrySet()) {
            s.add(current.getKey().getKey().asString() + "=" + current.getValue().toString());
        }
        return s;
    }

    public void setEnchantments(List<String> s) {

        Map<Enchantment, Integer> map = new HashMap<>();

        for (String string : s) {

            String[] args = string.split("=");

            Enchantment e = Enchantment.getByKey(NamespacedKey.fromString(args[0]));
            Integer lvl = Integer.parseInt(args[1]);

            map.put(e, lvl);

        }

        this.enchantments = map;

    }


    public List<String> getPersistentB() {
        List<String> containersStr = new ArrayList<>();

        for (Map.Entry<NamespacedKey, Boolean> entry : persistentB.entrySet()){
            containersStr.add(entry.getKey().asString() + "=" + entry.getValue().toString());
        }

        return containersStr;
    }

    public void setPersistentB(List<String> persistent) {

        this.persistentB = new HashMap<>();

        for (String s : persistent){

            String[] args = s.split("=");
            this.persistentB.put(NamespacedKey.fromString(args[0]), Boolean.valueOf(args[1]));

        }

    }

    public List<String> getPersistentD() {
        List<String> containersStr = new ArrayList<>();

        for (Map.Entry<NamespacedKey, Double> entry : persistentD.entrySet()){
            containersStr.add(entry.getKey().asString() + "=" + entry.getValue().toString());
        }

        return containersStr;
    }

    public void setPersistentD(List<String> persistent) {

        this.persistentD = new HashMap<>();

        for (String s : persistent){

            String[] args = s.split("=");
            this.persistentD.put(NamespacedKey.fromString(args[0]), Double.valueOf(args[1]));

        }
    }

    public List<String> getPersistentS() {
        List<String> containersStr = new ArrayList<>();

        for (Map.Entry<NamespacedKey, String> entry : persistentS.entrySet()){
            containersStr.add(entry.getKey().asString() + "=" + entry.getValue());
        }

        return containersStr;
    }

    public void setPersistentS(List<String> persistent) {

        this.persistentS = new HashMap<>();

        for (String s : persistent){

            String[] args = s.split("=");
            this.persistentS.put(NamespacedKey.fromString(args[0]), args[1]);

        }
    }

    public String getUnbreakable() {
        return unbreakable.toString();
    }

    public void setUnbreakable(String unbreakable) {
        this.unbreakable = Boolean.valueOf(unbreakable);
    }
}
