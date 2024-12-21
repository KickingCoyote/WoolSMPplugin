package pl.mn.mncustomenchants.Abilities;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.Abilities.Magic.ManaLance;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AbilityManager implements Listener {

    public AbilityManager(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event){

        if(!(event.getPlayer().isSneaking() && event.getAction().isRightClick() && event.getHand() == EquipmentSlot.HAND)){
            return;
        }



        /*
        Iterator<Map.Entry<Enchantment, Integer>> iterator = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchants().entrySet().iterator();
        while (iterator.hasNext()){event.getPlayer().sendMessage(iterator.next().getKey().getKey().asString());}
         */


        ManaLance.temp(event.getPlayer());

        /*
        List<Entity> t = event.getPlayer().getNearbyEntities(10, 10, 10);
        t.remove(event.getPlayer());
        for (Entity e : new ArrayList<>(t)){
            if (e instanceof LivingEntity){continue;}
            t.remove(e);
        }
        if(t.isEmpty()){return;}
        Spell s2 = new HomingBoltSpell(*8
                (LivingEntity) t.get(0), ((LivingEntity)t.get(0)).getEyeLocation(),
                event.getPlayer(), 0.2,
                particleData, 30, 0.5,
                List.of((LivingEntity) t.get(0)), 10
        );

         */








    }

}
