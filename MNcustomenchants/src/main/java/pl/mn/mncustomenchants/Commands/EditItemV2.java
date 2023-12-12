package pl.mn.mncustomenchants.Commands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.ItemMethods.ItemClassRegister;

import java.lang.management.PlatformLoggingMXBean;

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


                        /*
                        if (meta.getPersistentDataContainer().has(key)){
                            if (meta.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN).booleanValue()){
                                bool = false;
                            } else {
                                bool = true;
                            }
                        }
                        else {
                            bool = true;
                        } */

                        bool = args[2].equalsIgnoreCase("true");


                        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, bool);

                        itemStack.setItemMeta(meta);



                    }
                }
            }

            if (args[0].equalsIgnoreCase("setFlag")){
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
        }
        return true;
    }
}
