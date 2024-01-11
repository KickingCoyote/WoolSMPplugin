package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.SoundCategory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class DeleteContraband {


    public static void Anvil (InventoryOpenEvent event){


        if (event.getInventory().getType() == InventoryType.ANVIL){
            if (!event.getPlayer().getInventory().getItemInMainHand().isEmpty()){
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() instanceof Damageable){
                    if (((Damageable) event.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getDamage() > 0){

                        ItemMeta itemMeta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                        ((Damageable)itemMeta).setDamage(0);

                        event.getPlayer().getInventory().getItemInMainHand().setItemMeta(itemMeta);

                        Block block = event.getInventory().getLocation().getBlock();

                        if (block.getType() == Material.ANVIL){
                            block.setType(Material.CHIPPED_ANVIL);
                        }else if (block.getType() == Material.CHIPPED_ANVIL){
                            block.setType(Material.DAMAGED_ANVIL);
                        }else if (block.getType() == Material.DAMAGED_ANVIL){
                            block.setType(Material.AIR);
                        }



                        event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.BLOCK_ANVIL_PLACE,1, 1);
                    }
                }
            }
            event.setCancelled(true);

        }
    }

    public static void Enchanting (InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.ENCHANTING){
            event.setCancelled(true);
        }
    }

    public static void NonCustomItemsWithEnchants (Player player){


        for (EquipmentSlot e : EntityClassifications.equipmentSlots){
            if (!player.getInventory().getItem(e).isEmpty()){
                if (player.getInventory().getItem(e).hasItemMeta()){
                    if(!player.getInventory().getItem(e).getItemMeta().getPersistentDataContainer().has(Keys.custom_item)){

                        for (Enchantment enchantment : Enchantment.values()){
                            player.getInventory().getItem(e).removeEnchantment(enchantment);
                        }

                    } else if (!player.getInventory().getItem(e).getItemMeta().getPersistentDataContainer().get(Keys.custom_item, PersistentDataType.BOOLEAN).booleanValue()){

                        for (Enchantment enchantment : Enchantment.values()){
                            player.getInventory().getItem(e).removeEnchantment(enchantment);

                        }

                    }
                }
            }
        }

    }



}
