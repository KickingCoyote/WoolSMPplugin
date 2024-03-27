package pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.TeleportSpell;
import pl.mn.mncustomenchants.main;

public class Advancing_Shadows implements Listener {

    public Advancing_Shadows(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }



    @EventHandler
    public void ClickEvent(PlayerInteractEvent event){


        if (!event.getAction().isRightClick()) { return; }
        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.advancing_shadows, event.getPlayer(), EquipmentSlot.HAND)) { return; }


       // Bukkit.getPlayer("MN_128").sendMessage(event.getPlayer().rayTraceEntities(10).toString() + "");

        if (event.getPlayer().rayTraceEntities(10) == null) {return; }

        Entity entity = event.getPlayer().rayTraceEntities(10).getHitEntity();


        if (entity == null) {return;}

        SpellManager.castSpell(new TeleportSpell(event.getPlayer(), entity.getLocation().subtract(event.getPlayer().getLocation().getDirection().clone().setY(0)), 0, 100));


    }

}
