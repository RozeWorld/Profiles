package com.roseworld.Profiles.profiles.GUI.Items;

import com.rosekingdom.rosekingdom.Core.Utils.MillisToTime;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("UnstableApiUsage")
public class ActivityIndicator extends ItemStack {
    public ActivityIndicator(OfflinePlayer player){
        super(Material.PAPER);
        setAmount(1);
        if (player.isOnline()) {
            setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addString("pf_activity_online"));
        } else {
            setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addString("pf_activity_offline"));
        }
        ItemMeta meta = getItemMeta();
        if(player.isOnline()){
            meta.displayName(Component.text("Currently Online", TextColor.fromHexString("#17fc32"))
                    .decoration(TextDecoration.ITALIC, false));
        }else{
            long lastOnline = player.getLastSeen();
            int rawTime = (int) (System.currentTimeMillis()-lastOnline)/1000;
            String text = String.format("Last seen %s ago.", MillisToTime.withWord(rawTime));
            meta.displayName(Component.text(text, TextColor.fromHexString("#e80e0e"))
                    .decoration(TextDecoration.ITALIC, false));
        }
        setItemMeta(meta);
    }
}
