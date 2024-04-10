package pl.mn.mncustomenchants.Abilities;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.item.Item;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.Abilities.Magic.ManaLance;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellBases.RadialAoeSpellBase;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.*;
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
