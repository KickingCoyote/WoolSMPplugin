package pl.mn.mncustomenchants.CustomEnchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;

import java.util.Set;

public class EnchatmentWrapper extends Enchantment {

    public final String name;
    private final int maxLvl;

    private final boolean cursed;

    public EnchatmentWrapper (String namespace, String name, int lvl){
        super(NamespacedKey.minecraft(namespace));
        this.maxLvl = lvl;
        this.name = name;
        cursed = false;
    }
    public EnchatmentWrapper (String namespace, String name, int lvl, boolean isCurse){
        super(NamespacedKey.minecraft(namespace));
        this.maxLvl = lvl;
        this.name = name;
        this.cursed = isCurse;
    }


    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return cursed;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public @NotNull Component displayName(int i) {

        if (i == 0) { return Component.text(""); }

        Component component;
        String number;
        if (maxLvl == 1){ number = ""; }
        else {
            number = ItemUtils.getRomanNumber(i);
        }

        if (this.isCursed()){
            component = Component.text(name + " " + number, TextColor.color(255, 85,85));
        } else {
            component = Component.text(name + " " + number, TextColor.color(169,169,169));
        }

        component = component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        return component;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return null;
    }

    @Override
    public @NotNull String translationKey() {
        return null;
    }
}
