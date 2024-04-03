package pl.mn.mncustomenchants.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import pl.mn.mncustomenchants.main;

public class InventoryShulkers implements Listener {

    public InventoryShulkers(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    public Inventory inv;
    public ItemStack i;

    @EventHandler
    private void openInventory(InventoryClickEvent event){



        if (event.getClickedInventory().getItem(event.getSlot()) == null){
            return;
        }


        i = event.getClickedInventory().getItem(event.getSlot());

        if (i.getType() == Material.SHULKER_BOX){



            if (event.getClick() != ClickType.RIGHT){
                return;
            }


            BlockStateMeta bsm = (BlockStateMeta) i.getItemMeta();

            ShulkerBox box = (ShulkerBox) bsm.getBlockState();

            inv = box.getInventory();

            event.getWhoClicked().openInventory(inv);

        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event){



        saveInventory(event.getPlayer().getInventory().getItemInMainHand(), event.getInventory().getContents());


    }


    private void saveInventory(ItemStack inventoryItem, ItemStack[] items){

        //creates block state meta
        BlockStateMeta b = (BlockStateMeta) inventoryItem.getItemMeta();

        //Creates box from that meta
        ShulkerBox box = (ShulkerBox) b.getBlockState();

        //Set box inventory to items
        box.getInventory().setContents(items);

        //Set meta to box items
        b.setBlockState(box);

        //Set item meta to block meta
        inventoryItem.setItemMeta(b);
    }


}
