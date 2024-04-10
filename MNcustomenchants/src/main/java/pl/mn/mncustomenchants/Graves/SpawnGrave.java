package pl.mn.mncustomenchants.Graves;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.mn.mncustomenchants.Misc.Keys;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;

public class SpawnGrave implements Listener {

    public SpawnGrave(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event){

        //the game refuses to let stuff not die twice so this checks if they already are dead to prevent duped graves
        if (event.getPlayer().getPersistentDataContainer().has(Keys.DEAD) && Boolean.TRUE.equals(event.getPlayer().getPersistentDataContainer().get(Keys.DEAD, PersistentDataType.BOOLEAN))) {
            event.getPlayer().getPersistentDataContainer().set(Keys.DEAD, PersistentDataType.BOOLEAN, false);
            return;
        }


        spawnGrave(event.getPlayer());
    }

    @EventHandler
    private void interact(PlayerInteractAtEntityEvent event){

        if(!(event.getRightClicked() instanceof ArmorStand)){
            return;
        }
        if (event.getRightClicked().getPersistentDataContainer().has(Keys.GRAVE)){

            event.getRightClicked().remove();

        }

    }

    public void spawnGrave(Player player){


        List<ItemStack> items = new ArrayList<>();
        items.add(player.getEquipment().getItem(EquipmentSlot.HAND));


        ItemStack itemStack = new ItemStack(Material.CHEST);
        BlockStateMeta bsm = (BlockStateMeta) itemStack.getItemMeta();
        Chest chest = (Chest) bsm.getBlockState();

        for (int i = 0; i < items.size(); i++) {

            chest.getInventory().setItem(i, items.get(i));

        }

        bsm.setBlockState(chest);
        bsm.displayName(Component.text("Grave Box").decoration(TextDecoration.BOLD, true));

        itemStack.setItemMeta(bsm);


        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        armorStand.setArms(true);
        armorStand.setItem(EquipmentSlot.HAND, itemStack);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCanMove(false);
        armorStand.getPersistentDataContainer().set(Keys.GRAVE, PersistentDataType.BOOLEAN, true);

    }

}
