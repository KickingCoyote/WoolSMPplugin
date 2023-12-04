package pl.mn.mncustomenchants.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(s.equalsIgnoreCase("edititemv2")){
            if(args.length == 1){
                List<String> list = new ArrayList<>();
                list.add("setcustomtag");

                return list;

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
