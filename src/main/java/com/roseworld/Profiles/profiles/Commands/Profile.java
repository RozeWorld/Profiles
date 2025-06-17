package com.roseworld.Profiles.profiles.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.rosekingdom.rosekingdom.Core.Utils.Message;
import com.rosekingdom.rosekingdom.RoseKingdom;
import com.roseworld.Profiles.profiles.GUI.UserGUI;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

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
                    sender.sendMessage(Component.text("Your profile: " + sender.getName()));
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
                            try {
                                OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(name);
                                if(player.hasPlayedBefore()) {
                                    RoseKingdom.getGuiManager().openGUI(new UserGUI(player), sender);
                                }else{
                                    sender.sendMessage(Message.Warning(player.getName() + " hasn't played before!"));
                                }
                            }catch (Exception e){
                                sender.sendMessage(Component.text("Player with this name does not exist!"));
                            }
                            return Command.SINGLE_SUCCESS;
                        }))
                .build(), "Player Profile", List.of("p", "pf"));
    }
}
