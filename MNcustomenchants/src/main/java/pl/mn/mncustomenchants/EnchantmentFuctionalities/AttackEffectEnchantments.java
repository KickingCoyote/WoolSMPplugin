package pl.mn.mncustomenchants.EnchantmentFuctionalities;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.EntityMethods.Classifications.EntityUtils;
import pl.mn.mncustomenchants.EntityMethods.EntityEffects.CustomEffects;

import java.util.Random;

public class AttackEffectEnchantments {


    public static void CheckAttackEffects(EntityDamageByEntityEvent event, Player sender){

        CheckThunderAspect(event, sender);
        CheckFireAspect(event, sender);
        CheckIceAspect(event, sender);
        CheckDecay(event, sender);

    }



    private static void CheckThunderAspect(EntityDamageByEntityEvent event, Player sender) {

        Enchantment ench = CustomEnchantments.thunder_aspect;

        Random random = new Random();
        int nrn = random.nextInt(20);
        int enchLvl;


        boolean fullyChargedWeapon = event.getDamager() instanceof Projectile ? EntityUtils.bowCharge(sender, (Projectile) event.getDamager()) == 1 : sender.getAttackCooldown() == 1;


        if (EntityUtils.isPlayerWithEnch(ench, sender, EquipmentSlot.HAND) && fullyChargedWeapon) {

            enchLvl = sender.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);


            LivingEntity entity = (LivingEntity) event.getEntity();

            //if pvp
            if (event.getEntity() instanceof Player && nrn < enchLvl) {

                CustomEffects.stun(entity, 35);

            }

            //if pve
            else if (event.getEntity() instanceof Mob && nrn < enchLvl * 2) {

                CustomEffects.stun(entity, 35);

            }


        }
    }


    private static void CheckFireAspect (EntityDamageByEntityEvent event, Player sender){


        if (!EntityUtils.isPlayerWithEnch(CustomEnchantments.true_fire_aspect, sender, EquipmentSlot.HAND)){ return; }

        int enchLvl = sender.getInventory().getItemInMainHand().getEnchantmentLevel(CustomEnchantments.true_fire_aspect);
        int infernoLvl = EntityUtils.combinedEnchantLvl(sender, CustomEnchantments.inferno);

        CustomEffects.burn((LivingEntity) event.getEntity(), 80 * enchLvl, infernoLvl);


    }


    private static void CheckIceAspect(EntityDamageByEntityEvent event, Player sender){


        Enchantment ench = CustomEnchantments.ice_aspect;


        boolean fullyChargedWeapon = event.getDamager() instanceof Projectile ? EntityUtils.bowCharge(sender, (Projectile) event.getDamager()) == 1 : sender.getAttackCooldown() == 1;

        if (EntityUtils.isPlayerWithEnch(ench, sender, EquipmentSlot.HAND) && fullyChargedWeapon){

            int enchLvl = sender.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);



            LivingEntity entity = (LivingEntity) event.getEntity();
            //if pvp
            if(event.getEntity() instanceof Player) {

                CustomEffects.freeze(entity, 80, enchLvl);

            }

            //if pve
            else if (event.getEntity() instanceof Mob){
                CustomEffects.freeze(entity, 80, enchLvl);

            }


            sender.playSound(entity, Sound.BLOCK_GLASS_BREAK, SoundCategory.HOSTILE, 0.5f, 2);




        }



    }



    private static void CheckDecay(EntityDamageByEntityEvent event, Player sender){

        Enchantment ench = CustomEnchantments.decay;


        if(!EntityUtils.isPlayerWithEnch(ench, sender, EquipmentSlot.HAND)) { return; }


        int enchLvl = sender.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(ench);


        LivingEntity entity = (LivingEntity) event.getEntity();

        if(event.getEntity() instanceof Player){

            CustomEffects.decay(entity, 80, enchLvl);

        }
        else {


            CustomEffects.decay(entity, 80, enchLvl);

        }




    }



}
