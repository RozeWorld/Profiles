package com.roseworld.Profiles.profiles.Commands;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.rosekingdom.rosekingdom.Core.Utils.Message;
import com.rosekingdom.rosekingdom.RoseKingdom;
import com.roseworld.Profiles.profiles.GUI.UserGUI;
import com.roseworld.Profiles.profiles.Profiles;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("UnstableApiUsage")
public class Profile {
    Player sender;
    int id;

    public void register(Commands cm){
        cm.register(Commands.literal("profile")
                .requires(source -> {
                    if (source.getExecutor() instanceof Player player) {
                        this.sender = player;
                        return true;
                    }
                    return false;
                })
                .executes(context -> {
                    RoseKingdom.getGuiManager().openGUI(new UserGUI(sender), sender);
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.argument("Player", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            Arrays.stream(Bukkit.getOfflinePlayers())
                                    .map(OfflinePlayer::getName)
                                    .forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            String name = StringArgumentType.getString(context, "Player");
                            OfflinePlayer player = Bukkit.getServer().getOfflinePlayerIfCached(name);
                            if(player != null) {
                                RoseKingdom.getGuiManager().openGUI(new UserGUI(player), sender);
                            }else{
                                sender.sendMessage(Message.Warning(name + " hasn't played before!"));
                            }
                            return Command.SINGLE_SUCCESS;
                        }))
                .build(), "Player Profile", List.of("p", "pf"));
    }
}
