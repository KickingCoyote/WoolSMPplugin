package pl.mn.mncustomenchants.EnchantmentFuctionalities.EnchantmentSpells;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;
import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;
import pl.mn.mncustomenchants.Spells.Spells.ArcaneBlastSpell;

import java.util.ArrayList;
import java.util.List;

public class Arcane_Strike implements Listener {

    Plugin plugin;

    public Arcane_Strike (Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMeleeHit (EntityDamageByEntityEvent event){


        if (!(event.getDamager() instanceof Player)) {return;}

        int lvl = EntityUtils.itemEnchLvl(CustomEnchantments.arcane_strike, ((Player) event.getDamager()).getInventory().getItemInMainHand());


        if (lvl > 0){

            if(!(event.getEntity() instanceof LivingEntity)){return;}

            if(((Player) event.getDamager()).getAttackCooldown() != 1){return;}
            //Hinders sweep damage from casting arcane
            if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){return;}



            Location castLocation = event.getDamager().getLocation();

            double weaponDamage = ItemUtils.getEntityAttribute((LivingEntity) event.getDamager(), AttributeType.ATTACK_DAMAGE, ItemUtils.AttributeOperator.ITEM_STAT);

            if (event.isCritical()) {weaponDamage *= 1.5;}


            ParticleData particleData = new ParticleData(
                    1,
                    Particle.REDSTONE,
                    Particles.cone(castLocation, castLocation.getDirection().setY(0), 100, 4, 0.2, 0.4),
                    1,
                    castLocation.toVector()
            );

            particleData.isDust = true;
            particleData.color = Color.AQUA;
            particleData.particleSize = 0.5f;


            Spell spell = new ArcaneBlastSpell(
                    (Player)event.getDamager(),
                    castLocation,
                    4, 0.8, 2,
                    weaponDamage * lvl / (lvl + 1),
                    particleData,
                    List.of((LivingEntity) event.getEntity(), (LivingEntity)event.getDamager())
            );

            SpellManager.castSpell(spell);

        }
    }


}
