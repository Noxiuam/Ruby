package rip.kits.ruby.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import rip.kits.ruby.Ruby;
import rip.kits.ruby.command.event.ShutdownEvent;
import rip.kits.ruby.command.event.WhitelistEvent;
import rip.kits.ruby.command.event.task.ServerShutdownKickTask;
import rip.kits.ruby.command.event.task.ServerShutdownTask;
import rip.kits.ruby.command.event.task.ServerWhitelistKickTask;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;
import rip.kits.ruby.ranks.event.RankChangeEvent;
import rip.kits.rutilities.utils.CCUtil;

import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void AsyncPlayerLogin(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId(), event.getName());
        if (Bukkit.hasWhitelist()) event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);

        if (profile.getRank().isAboveOrEqual(Rank.TRIALMOD)) event.allow();

        if (event.getLoginResult().equals(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST)) {
            event.setKickMessage(CCUtil.s("&cThe server is currently whitelisted.\nAdditonal info may be found in the Discord.\n\ndiscord.gg/hvGDqYPZzt"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void AsyncChatEvent(AsyncPlayerChatEvent event) {
        Profile profile = Profile.getByUuid(event.getPlayer().getUniqueId());

        event.setFormat(CCUtil.s(profile.getRank().getPrefix() + event.getPlayer().getName() + "&7: &r" + event.getMessage()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerQuit(PlayerQuitEvent event) {
        Profile profile = Profile.getByUuid(event.getPlayer().getUniqueId());

        Ruby.getMongoManager().saveProfile(profile, callback -> {
            if (callback) {
                Bukkit.getConsoleSender().sendMessage("Successfully saved " + event.getPlayer().getName() + "'s Profile.");
            } else {
                Bukkit.getConsoleSender().sendMessage("Failed to save " + event.getPlayer().getName() + "'s Profile.");
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRankChange(final RankChangeEvent event) {
        event.getPlayer().sendMessage(CCUtil.s("&aYour rank has been set to " + event.getRank().getColor() + event.getRank().getRankName()));

        Ruby.getMongoManager().saveProfile(event.getProfile(), callbackBoolean -> {
            if (callbackBoolean) {
                Bukkit.getConsoleSender().sendMessage("Successfully saved " + event.getProfile().getName() + " profile");
            } else {
                Bukkit.getConsoleSender().sendMessage("Failed to save " + event.getProfile().getName() + " profile");
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void ShutdownEvent(ShutdownEvent event) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            Profile profile = Profile.getByUuid(players.getUniqueId());

            Ruby.getMongoManager().saveProfile(profile, callback -> {
                if (callback) {
                    Bukkit.getConsoleSender().sendMessage("Successfully saved " + profile.getName() + " profile");
                } else {
                    Bukkit.getConsoleSender().sendMessage("Failed to save " + profile.getName() + " profile");
                }
            });
        }
        new ServerShutdownKickTask(10, TimeUnit.SECONDS).runTaskTimer(Ruby.getInstance(), 20L, 20L);
        new ServerShutdownTask(20, TimeUnit.SECONDS).runTaskTimer(Ruby.getInstance(), 20L, 20L);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void WhitelistEvent(WhitelistEvent event) {

        if (!(Bukkit.hasWhitelist())) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(players.getUniqueId());

                Ruby.getMongoManager().saveProfile(profile, callback -> {
                    if (callback) {
                        Bukkit.getConsoleSender().sendMessage("Successfully saved " + profile.getName() + " profile");
                    } else {
                        Bukkit.getConsoleSender().sendMessage("Failed to save " + profile.getName() + " profile");
                    }
                });
            }
            new ServerWhitelistKickTask(3, TimeUnit.SECONDS).runTaskTimer(Ruby.getInstance(), 20L, 20L);
            Bukkit.setWhitelist(true);
        } else {
            Bukkit.setWhitelist(false);
        }
    }
}
