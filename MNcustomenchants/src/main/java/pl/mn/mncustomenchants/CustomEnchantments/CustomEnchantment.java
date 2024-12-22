package pl.mn.mncustomenchants.CustomEnchantments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchantment {

    private final String namespace;
    private final String name;
    private final int maxLevel;
    private final boolean curse;


    public CustomEnchantment(String namespace, String name){
        this(namespace, name, 1, false);
    }

    public CustomEnchantment(String namespace, String name, int maxLevel){
        this(namespace, name, maxLevel, false);
    }

    public CustomEnchantment(String namespace, String name, int maxLevel, boolean curse){

        this.namespace = namespace;
        this.name = name;
        this.maxLevel = maxLevel;
        this.curse = curse;
    }


    public int getMaxLevel() {
        return maxLevel;
    }

    public Component getName(int level) {

        if (level == 0) { return Component.text(""); }

        Component component;
        String number;
        if (maxLevel == 1){ number = ""; }
        else {
            number = ItemUtils.getRomanNumber(level);
        }

        if (this.curse){
            component = Component.text(name + " " + number, TextColor.color(255, 85,85));
        } else {
            component = Component.text(name + " " + number, TextColor.color(169,169,169));
        }

        component = component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        return component;
    }


    public NamespacedKey getKey(){
        return new NamespacedKey(main.getInstance(), "enchantment/" + namespace);
    }

    public static CustomEnchantment valueOf(NamespacedKey key){
        return valueOf(key.asString().split("/")[1].toLowerCase());
    }

    public static void addEnchantment(ItemStack item, CustomEnchantment enchantment, int level){
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(enchantment.getKey(), PersistentDataType.INTEGER, level);
        item.setItemMeta(itemMeta);
    }
    public static void removeEnchantment(ItemStack item, CustomEnchantment enchantment){
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        if (!item.getItemMeta().getPersistentDataContainer().has(enchantment.getKey())){
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(enchantment.getKey());
        item.setItemMeta(itemMeta);
    }

    public static List<CustomEnchantment> getAllItemEnchantments(ItemStack item){

        List<CustomEnchantment> enchantments = new ArrayList<>();

        if (item == null || !item.hasItemMeta()) {
            return enchantments;
        }

        for (NamespacedKey key : item.getItemMeta().getPersistentDataContainer().getKeys()) {
            if (key.asString().contains("enchantment")){
                enchantments.add(valueOf(key));
            }
        }

        return enchantments;
    }


    public static CustomEnchantment valueOf(String namespace){
        return switch (namespace) {
            case "radiant" -> radiant;
            case "thunder_aspect" -> thunder_aspect;
            case "decay" -> decay;
            case "recoil" -> recoil;
            case "two_handed" -> two_handed;
            case "quake" -> quake;
            case "magic_protection" -> magic_protection;
            case "melee_protection" -> melee_protection;
            case "true_infinity" -> true_infinity;
            case "regeneration" -> regeneration;
            case "arcane_strike" -> arcane_strike;
            case "ice_aspect" -> ice_aspect;
            case "curse_of_corruption" -> curse_of_corruption;
            case "excavator" -> excavator;
            case "true_fire_aspect" -> true_fire_aspect;
            case "inferno" -> inferno;
            case "sustenance" -> sustenance;
            case "curse_of_anemia" -> curse_of_anemia;
            case "regicide" -> regicide;
            case "rocket_crossbow" -> rocket_crossbow;
            case "teleportation" -> teleportation;
            case "dragon_blade" -> dragon_blade;
            case "advancing_shadows" -> advancing_shadows;
            case "sweeping_edge" -> sweeping_edge;
            case "aerial_strike" -> aerial_strike;
            case "weightless" -> weightless;
            case "protection" -> protection;
            case "blast_protection" -> blast_protection;
            case "fire_protection" -> fire_protection;
            case "fall_protection" -> fall_protection;
            case "projectile_protection" -> projectile_protection;
            case "material" -> material;
            default -> error;
        };
    } 

    public static final List<String> enchantmentArgs = List.of(
            "radiant",
            "thunder_aspect",
            "decay", "recoil",
            "two_handed",
            "quake", "magic_protection",
            "melee_protection",
            "true_infinity",
            "regeneration",
            "ice_aspect",
            "curse_of_corruption",
            "excavator",
            "true_fire_aspect",
            "inferno",
            "sustenance",
            "curse_of_anemia",
            "regicide",
            "rocket_crossbow",
            "teleportation",
            "dragon_blade",
            "advancing_shadows",
            "sweeping_edge",
            "aerial_strike",
            "weightless",
            "arcane_strike",
            "protection",
            "blast_protection",
            "fire_protection",
            "fall_protection",
            "projectile_protection",
            "material"
    );

    //All enchantments go here
    public static final CustomEnchantment error = new CustomEnchantment("error", "Error", 1);

    public static final CustomEnchantment material = new CustomEnchantment("material", "Material", 1);

    public static final CustomEnchantment radiant = new CustomEnchantment("radiant", "Radiant", 2);

    public static final CustomEnchantment thunder_aspect = new CustomEnchantment("thunder_aspect", "Thunder Aspect", 20);

    public static final CustomEnchantment decay = new CustomEnchantment("decay", "Decay", 40);

    public static final CustomEnchantment recoil = new CustomEnchantment("recoil", "Recoil", 2);

    public static final CustomEnchantment two_handed = new CustomEnchantment("two_handed", "Two Handed", 1, true);

    public static final CustomEnchantment weightless = new CustomEnchantment("weightless", "Weightless", 1);

    public static final CustomEnchantment quake = new CustomEnchantment("quake", "Quake", 2);

    public static final CustomEnchantment magic_protection = new CustomEnchantment("magic_protection", "Magic Protection", 2);

    public static final CustomEnchantment melee_protection = new CustomEnchantment("melee_protection", "Melee Protection", 2);

    public static final CustomEnchantment true_infinity = new CustomEnchantment("true_infinity", "Infinity", 1);

    public static final CustomEnchantment rocket_crossbow = new CustomEnchantment("rocket_crossbow", "Rocket Bow", 2);


    //5 blocks per lvl
    public static final CustomEnchantment teleportation = new CustomEnchantment("teleportation", "Teleportation", 2);

    //having more than lvl 143 total regen breaks the game
    public static final CustomEnchantment regeneration = new CustomEnchantment("regeneration", "Regeneration", 2);

    public static final CustomEnchantment ice_aspect = new CustomEnchantment("ice_aspect", "Ice Aspect", 2);

    public static final CustomEnchantment curse_of_corruption = new CustomEnchantment("curse_of_corruption", "Curse of Corruption", 1, true);

    public static final CustomEnchantment excavator = new CustomEnchantment("excavator", "Excavator", 1);

    public static final CustomEnchantment true_fire_aspect = new CustomEnchantment("true_fire_aspect", "Fire Aspect", 2);

    public static final CustomEnchantment inferno = new CustomEnchantment("inferno", "Inferno", 2);

    public static final CustomEnchantment sustenance = new CustomEnchantment("sustenance", "Sustenance", 2);
    public static final CustomEnchantment curse_of_anemia = new CustomEnchantment("curse_of_anemia", "Curse of Anemia", 2, true);

    //ENCHANTMENTS HANDLED IN CUSTOM DAMAGE CLASS
    //10% extra damage to players
    public static final CustomEnchantment regicide = new CustomEnchantment("regicide", "Regicide", 2);
    //1 + damage * lvl / (lvl + 1)
    public static final CustomEnchantment sweeping_edge = new CustomEnchantment("sweeping_edge", "Sweeping Edge", 2);
    //If fall_distance > 1 => Critical damage * (1 + lvl * fall_distance^0.5 / 10)
    public static final CustomEnchantment aerial_strike = new CustomEnchantment("aerial_strike", "Aerial Strike", 2);

    public static final CustomEnchantment protection = new CustomEnchantment("protection", "Protection", 2);
    public static final CustomEnchantment blast_protection = new CustomEnchantment("blast_protection", "Blast Protection", 2);
    public static final CustomEnchantment fire_protection = new CustomEnchantment("fire_protection", "Fire Protection", 2);
    public static final CustomEnchantment fall_protection = new CustomEnchantment("fall_protection", "Feather Falling", 2);
    public static final CustomEnchantment projectile_protection = new CustomEnchantment("projectile_protection", "Projectile Protection", 2);

    //SPELLS
    public static final CustomEnchantment arcane_strike = new CustomEnchantment("arcane_strike", "Arcane Thrust", 2);
    public static final CustomEnchantment dragon_blade = new CustomEnchantment("dragon_blade", "Dragonblade", 1);
    public static final CustomEnchantment advancing_shadows = new CustomEnchantment("advancing_shadows", "Advancing Shadows", 1);


}
