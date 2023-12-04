package pl.mn.mncustomenchants.ItemMethods;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityClassifications;

public class DeleteContraband {

    public static void Anvil (InventoryClickEvent event){
        if (event.getInventory().getType() == InventoryType.ANVIL){
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getType() != Material.NAME_TAG && event.getCurrentItem().getType() != Material.AIR){
                    event.setCancelled(true);

                    event.getInventory().close();

                }
            }
        }
    }

    public static void Enchanting (InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.ENCHANTING){
            event.setCancelled(true);
        }
    }

    public static void NonCustomItemsWithEnchants (Player player){

        Bukkit.getPlayer("MN_128").sendMessage("1");

        for (EquipmentSlot e : EntityClassifications.equipmentSlots){
            if (!player.getInventory().getItem(e).isEmpty()){
                if (player.getInventory().getItem(e).hasItemMeta()){
                    if(!player.getInventory().getItem(e).getItemMeta().getPersistentDataContainer().has(ItemClassRegister.custom_item)){

                        Bukkit.getPlayer("MN_128").sendMessage("1");
                        for (Enchantment enchantment : Enchantment.values()){
                            player.getInventory().getItem(e).removeEnchantment(enchantment);
                        }
                    }
                }
            }
        }

    }



}