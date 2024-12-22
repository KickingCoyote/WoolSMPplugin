package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantment;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;
import pl.mn.mncustomenchants.Particles.ParticleData;
import pl.mn.mncustomenchants.Particles.Particles;

import java.util.Random;

public class AttackEffectEnchantments {


    public static void CheckAttackEffects(LivingEntity target, Player sender, Entity directDamager){

        CheckThunderAspect(target, sender, directDamager);
        CheckFireAspect(target, sender);
        CheckIceAspect(target, sender, directDamager);
        CheckDecay(target, sender);

    }



    private static void CheckThunderAspect(LivingEntity target, Player sender, Entity directDamager) {

        CustomEnchantment ench = CustomEnchantment.thunder_aspect;

        Random random = new Random();
        int nrn = random.nextInt(20);
        int enchLvl;


        boolean fullyChargedWeapon = directDamager instanceof Projectile ? EntityUtils.bowCharge(sender, (Projectile) directDamager) == 1 : sender.getAttackCooldown() == 1;


        if (EntityUtils.isPlayerWithEnchantment(ench, sender, EquipmentSlot.HAND) && fullyChargedWeapon) {

            enchLvl = EntityUtils.itemEnchantmentLvl(ench, sender.getInventory().getItemInMainHand());



            //if pvp
            if (target instanceof Player && nrn < enchLvl) {

                CustomEffects.stun(target, 35);

            }

            //if pve
            else if (target instanceof Mob && nrn < enchLvl * 2) {

                CustomEffects.stun(target, 35);

            }


        }
    }


    private static void CheckFireAspect (LivingEntity target, Player sender){


        if (!EntityUtils.isPlayerWithEnchantment(CustomEnchantment.true_fire_aspect, sender, EquipmentSlot.HAND)){ return; }

        int enchLvl = EntityUtils.itemEnchantmentLvl(CustomEnchantment.true_fire_aspect, sender.getInventory().getItemInMainHand());
        int infernoLvl = EntityUtils.combinedEnchantLvl(sender, CustomEnchantment.inferno);

        CustomEffects.burn(target, 80 * enchLvl, infernoLvl);


    }


    private static void CheckIceAspect(LivingEntity target, Player sender, Entity directDamager){


        CustomEnchantment ench = CustomEnchantment.ice_aspect;


        boolean fullyChargedWeapon = directDamager instanceof Projectile ? EntityUtils.bowCharge(sender, (Projectile) directDamager) == 1 : sender.getAttackCooldown() == 1;

        if (EntityUtils.isPlayerWithEnchantment(ench, sender, EquipmentSlot.HAND) && fullyChargedWeapon){

            int enchLvl = EntityUtils.itemEnchantmentLvl(ench, sender.getInventory().getItemInMainHand());


            //if pvp
            if(target instanceof Player) {

                CustomEffects.freeze(target, 80, enchLvl);

            }

            //if pve
            else if (target instanceof Mob){
                CustomEffects.freeze(target, 80, enchLvl);

            }


            //
            ParticleData pD = new ParticleData(
                    20,
                    Particle.REDSTONE,
                    Particles.sphereExplosion(target.getLocation().toVector().getMidpoint(target.getEyeLocation().toVector()).toLocation(target.getWorld()), 10, 1.5),
                    1,
                    target.getLocation().toVector()
            );

            pD.isDust = true;
            pD.color = Color.fromBGR(255,200,200);


            Particles.RenderParticles(pD, target.getWorld());

            sender.getWorld().playSound(target, Sound.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 0.5f, 2);


        }


    }



    private static void CheckDecay(LivingEntity target, Player sender){

        CustomEnchantment ench = CustomEnchantment.decay;


        if(!EntityUtils.isPlayerWithEnchantment(ench, sender, EquipmentSlot.HAND)) { return; }


        int enchLvl = EntityUtils.itemEnchantmentLvl(ench, sender.getInventory().getItemInMainHand());


        if(target instanceof Player){

            CustomEffects.decay(target, 80, enchLvl);

        }
        else {

            CustomEffects.decay(target, 80, enchLvl);

        }


    }



}
