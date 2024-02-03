package pl.mn.mncustomenchants.Commands;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.ItemMethods.Keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(s.equalsIgnoreCase("edititemv2")){
            if(args.length == 1){

                return List.of("setCustomTag", "setFlag", "Remove_Attribute", "Add_Attribute");

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
            } else if (args[0].equalsIgnoreCase("Remove_Attribute")){
                if (args.length == 2){

                    List<String> attrTypes = new ArrayList<>();
                    for (AttributeType attributeType : AttributeType.values){
                        attrTypes.add(attributeType.ToString());
                    }
                    return attrTypes;
                } else if (args.length == 3){
                    return List.of(
                            ItemUtils.AttributeOperator.ADD.toString(),
                            ItemUtils.AttributeOperator.ADD_PROCENT.toString(),
                            ItemUtils.AttributeOperator.ITEM_STAT.toString()
                    );
                } else if (args.length == 4){
                    return List.of(
                            EquipmentSlot.HAND.toString(), EquipmentSlot.OFF_HAND.toString(),
                            EquipmentSlot.FEET.toString(), EquipmentSlot.LEGS.toString(),
                            EquipmentSlot.CHEST.toString(), EquipmentSlot.HEAD.toString()
                    );
                }
            } else if (args[0].equalsIgnoreCase("Add_Attribute")){
                if (args.length == 2){
                    List<String> attrTypes = new ArrayList<>();
                    for (AttributeType attributeType : AttributeType.values){
                        attrTypes.add(attributeType.ToString());
                    }
                    return attrTypes;
                } else if (args.length == 3){
                    return List.of("0","1", "2");
                } else if (args.length == 4){
                    return List.of(
                            ItemUtils.AttributeOperator.ADD.toString(),
                            ItemUtils.AttributeOperator.ADD_PROCENT.toString(),
                            ItemUtils.AttributeOperator.ITEM_STAT.toString()
                            );

                } else if (args.length == 5){
                    return List.of(
                            EquipmentSlot.HAND.toString(), EquipmentSlot.OFF_HAND.toString(),
                            EquipmentSlot.FEET.toString(), EquipmentSlot.LEGS.toString(),
                            EquipmentSlot.CHEST.toString(), EquipmentSlot.HEAD.toString()
                    );
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
