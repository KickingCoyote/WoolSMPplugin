package pl.mn.mncustomenchants.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import pl.mn.mncustomenchants.ItemMethods.ItemStorage;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.Misc.Keys;

public class UpdateItem implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)){
            return true;
        }

        if(!s.equalsIgnoreCase("updateItem")){
            return true;
        }

        if(((Player) commandSender).getInventory().getItemInMainHand().isEmpty()) {
            commandSender.sendMessage(Component.text("No item found.", TextColor.color(255, 85, 85)));
            return true;
        }

        ItemStack item = ((Player) commandSender).getInventory().getItemInMainHand();

        updateItem(item);

        return true;
    }


    public static void updateItem(ItemStack item){

        if (item.hasItemMeta()){
            if (!ItemUtils.hasDataContainer(item, Keys.SHATTERED)){
                ItemUtils.setShattered(item, 0);
            }

            ItemUtils.fixBrokenName(item);
        }

        /*
        if (ItemUtils.hasDataContainer(item, Keys.IDENTIFIER)){
            ItemStorage.loadItem(item, item.getItemMeta().getPersistentDataContainer().get(Keys.IDENTIFIER, PersistentDataType.STRING));
        }

         */

        ItemUtils.UpdateLore(item);
    }
}
