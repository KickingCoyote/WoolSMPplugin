package pl.mn.mncustomenchants.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
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



                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    ItemMeta meta = itemStack.getItemMeta();

                    //if (meta.getPersistentDataContainer().has())
                    meta.getPersistentDataContainer().set(ItemClassRegister.custom_item, PersistentDataType.BOOLEAN, true);

                    itemStack.setItemMeta(meta);

                }
            }
        }
        return true;
    }
}
