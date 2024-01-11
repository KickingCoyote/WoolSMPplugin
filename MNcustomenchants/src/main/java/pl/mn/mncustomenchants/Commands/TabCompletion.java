package pl.mn.mncustomenchants.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.ItemMethods.Keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(s.equalsIgnoreCase("edititemv2")){
            if(args.length == 1){

                return List.of("setCustomTag", "setFlag");

            }
            if (args[0].equalsIgnoreCase("setCustomTag")){
                if (args.length == 2){
                    return List.of(Keys.custom_item.asString(), Keys.material.asString());
                }
                else if (args.length == 3){
                    return List.of("true", "false");
                }
            } else if (args[0].equalsIgnoreCase("setFlag")){
                if (args.length == 2){
                    List<String> flags = new ArrayList<>();
                    for (ItemFlag itemFlag : ItemFlag.values()){
                        flags.add(itemFlag.name());
                    }
                    flags.add("HIDE_ITEM_SPECIFICS");

                    return flags;
                }
            }
        }


        if (s.equalsIgnoreCase("customenchant")){
            if(args.length == 1){
                List<String> e = new ArrayList<>(CustomEnchantments.enchantmentArgs);
                Collections.sort(e);
                return e;
            }

            if(args.length == 2){
                List<String> lvls = new ArrayList<>();
                int maxLvl = CustomEnchantments.valueOf(args[1]).getMaxLevel();
                for (int i = 0; i <= maxLvl; i++){

                    lvls.add(String.valueOf(i));
                }
                return lvls;
            }
        }



        return null;
    }
}
