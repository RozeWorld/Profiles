package com.roseworld.Profiles.profiles.GUI;

import com.rosekingdom.rosekingdom.Core.Database.Main_Statements.UserStatement;
import com.rosekingdom.rosekingdom.Core.Items.InvsibleItem;
import com.rosekingdom.rosekingdom.Core.Utils.MillisToTime;
import com.rosekingdom.rosekingdom.Core.gui.InventoryButton;
import com.rosekingdom.rosekingdom.Core.gui.InventoryTypes.InventoryGUI;
import com.rosekingdom.rosekingdom.RoseKingdom;
import com.roseworld.Profiles.profiles.GUI.Items.ActivityIndicator;
import com.roseworld.Profiles.profiles.GUI.Items.ProfilePlayTime;
import com.roseworld.Profiles.profiles.GUI.Items.StreakScore;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class UserGUI extends InventoryGUI{

    OfflinePlayer target;

    public UserGUI(OfflinePlayer player){
        super(player);
        this.target = player;
    }

    @Override
    protected Inventory createInventory() {
        return Bukkit.createInventory(null, 3*9, Component.text("\u00A7f\uDAFF\uDFF8\uEFB0"));
    }

    @Override
    protected Inventory createInventory(Object... params) {
        if (params.length > 0 && params[0] instanceof OfflinePlayer) {
            OfflinePlayer player = (OfflinePlayer) params[0];
            String playerRank = UserStatement.getRank(player.getUniqueId());
            Rank rank = Rank.valueOf(playerRank);
            Component title = Component.text("\u00A7f\uDAFF\uDFF8\uEFB0" + rank.prefix);
            return Bukkit.createInventory(null, 3*9, title);
        }
        return createInventory();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void decorate(Player player) {
        int[] playerHead = {0,1,2,9,10,11,18,19,20};
        for(int i : playerHead){
            this.addButton(i, createButton(new InvsibleItem(Component.text(target.getName(), TextColor.fromHexString("#9c9c9c")))));
        }

        var profile = Bukkit.createProfile(target.getUniqueId());
        int rawTime = target.getStatistic(Statistic.PLAY_ONE_MINUTE)/20;
        Timestamp timestamp = UserStatement.getJoinDate(UserStatement.getId(target.getUniqueId()));
        Instant rawDate = timestamp.toInstant();
        String date = rawDate.toString();
        date = date.replace('-', '/');
        String time = String.format("Total time spent on the server: %s.", MillisToTime.withSymbol(rawTime));
        String joinDate = String.format("First joined on %s %s (UTC).", date.substring(0, 10), date.substring(11, 19));
        profile.update().thenAcceptAsync(
                updatedProfile -> {
                    var head = ItemStack.of(Material.PLAYER_HEAD);
                    head.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addString("profile"));
                    var meta = (SkullMeta) head.getItemMeta();
                    meta.displayName(Component.text(time, TextColor.fromHexString("#17fc32")).decoration(TextDecoration.ITALIC, false));
                    meta.lore(List.of(Component.text(joinDate, TextColor.fromHexString("#17fc32")).decoration(TextDecoration.ITALIC, false)));
                    meta.setPlayerProfile(updatedProfile);
                    head.setItemMeta(meta);
                    this.addButton(21, createButton(head));
                    super.decorate(player);
                },
                runnable -> Bukkit. getScheduler().runTask(RoseKingdom.getPlugin(RoseKingdom.class), runnable));
        this.addButton(8, createButton(new ActivityIndicator(target)));
        this.addButton(22, createButton(new StreakScore(target)));
        super.decorate(player);
    }

    private InventoryButton createButton(ItemStack item) {
        return new InventoryButton()
                .creator(player -> item)
                .consumer(event -> {});
    }
}
