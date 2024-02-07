package pl.mn.mncustomenchants.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.ItemMethods.Attribute;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.ItemMethods.Keys;
import pl.mn.mncustomenchants.ItemMethods.LoreComponent;

import java.security.Key;


public class EditItemV2 implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player)){
            return true;
        }

        Player player = (Player) commandSender;

        if(s.equalsIgnoreCase("edititemv2")){

            if(args[0].equalsIgnoreCase("SetCustomTag")){

                if (!player.getInventory().getItemInMainHand().isEmpty()){

                    boolean bool;
                    NamespacedKey key = NamespacedKey.fromString(args[1]);
                    if (key != null){


                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta meta = itemStack.getItemMeta();



                        bool = args[2].equalsIgnoreCase("true");


                        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, bool);

                        itemStack.setItemMeta(meta);



                    }
                }
            }

            else if (args[0].equalsIgnoreCase("Remove_Attribute")){


                ItemUtils.RemoveAttribute(player.getInventory().getItemInMainHand(), ItemUtils.getAttributeFromKey(args[2] + "/"+ args[3] + "/"+ args[1]));



            }
            else if (args[0].equalsIgnoreCase("Add_Attribute")){

                ItemUtils.RemoveAttribute(player.getInventory().getItemInMainHand(), ItemUtils.getAttributeFromKey(args[3] + "/" + args[4] + "/" + args[1]));



                ItemUtils.AddAttribute(player.getInventory().getItemInMainHand(), ItemUtils.getAttributeFromKey(args[3] + "/"+ args[4] + "/"+ args[1]), Double.valueOf(args[2]));

            }
            else if (args[0].equalsIgnoreCase("Edit_Lore")){
                ItemUtils.EditLore(player.getInventory().getItemInMainHand(), Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
            }

            else if (args[0].equalsIgnoreCase("setFlag")){
                if(!player.getInventory().getItemInMainHand().isEmpty()){

                    if (args[1].equalsIgnoreCase("HIDE_ITEM_SPECIFICS")){

                        if(player.getInventory().getItemInMainHand().hasItemFlag(ItemFlag.HIDE_ITEM_SPECIFICS)){
                            player.getInventory().getItemInMainHand().removeItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
                        } else {
                            player.getInventory().getItemInMainHand().addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
                        }

                    } else {
                        for (ItemFlag itemFlag : ItemFlag.values()){
                            if(itemFlag == ItemFlag.valueOf(args[1])){
                                if(player.getInventory().getItemInMainHand().hasItemFlag(itemFlag)){
                                    player.getInventory().getItemInMainHand().removeItemFlags(itemFlag);
                                } else {
                                    player.getInventory().getItemInMainHand().addItemFlags(itemFlag);
                                }
                            }
                        }
                    }
                }
            }


            ItemUtils.UpdateLore(player.getInventory().getItemInMainHand());
        }
        return true;
    }
}
