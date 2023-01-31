package rip.kits.ruby.command;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kits.ruby.command.event.ShutdownEvent;
import rip.kits.ruby.command.event.WhitelistEvent;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;
import rip.kits.rutilities.utils.CCUtil;

public class PlayerCommands {

    private static Rank rank;

    @Command(names={"message", "msg", "m", "t", "tell"}, permission = "")
    public static void onMessage(Player sender, @Param(name = "Player") Player player, @Param(name = "Message", wildcard = true) String message) {
        Profile target = Profile.getByUuid(player.getUniqueId());
        Profile messageSender = Profile.getByUuid(sender.getUniqueId());

        if (sender == player) {
            sender.sendMessage(CCUtil.s("&cYou cannot message yourself."));
        } else if (!(target.isPrivateMessagesEnabled()) && !(messageSender.getRank().isAboveOrEqual(Rank.TRIALMOD))) {
            sender.sendMessage(target.getRank().getColor() + player.getName() + " has messages turned off.");
        } else if (!(messageSender.isPrivateMessagesEnabled())) {
            sender.sendMessage(CCUtil.s("&cYou have messages toggled off."));
        } else {
            player.sendMessage(CCUtil.s("&7(From " + messageSender.getRank().getColor() + messageSender.getName() + "&7) " + message));
            sender.sendMessage(CCUtil.s("&7(To " + target.getRank().getColor() + target.getName() + "&7) " + message));
            if (target.isSoundsEnabled()) player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
            target.setMessagingPlayer(sender.getUniqueId());
            messageSender.setMessagingPlayer(player.getUniqueId());
        }
    }

    @Command(names={"reply", "r"}, permission = "")
    public static void onReply(Player sender, @Param(name = "Message", wildcard = true, defaultValue = "whoamitalkingto?") String message) {
        Profile messageSender = Profile.getByUuid(sender.getUniqueId());
        Profile target = Profile.getByUuid(messageSender.getMessagingPlayer());

        if (target == null || Bukkit.getPlayer(target.getUuid()) == null) {
            sender.sendMessage(CCUtil.s("&cThat player has logged out or your not messaging anyone."));
        } else if (message.toLowerCase().equals("whoamitalkingto?")) {
            sender.sendMessage(CCUtil.s("&bYou are in a conversation with " + target.getRank().getColor() + target.getName() + "&b."));
        } else if (!(target.isPrivateMessagesEnabled()) && !(messageSender.getRank().isAboveOrEqual(Rank.TRIALMOD))) {
            sender.sendMessage(target.getName() + " has messages turned off.");
        } else if (!(messageSender.isPrivateMessagesEnabled()) && !(target.getRank().isAboveOrEqual(Rank.TRIALMOD))) {
            sender.sendMessage(CCUtil.s("&cYou have messages toggled off."));
        } else {
            Player player = Bukkit.getPlayer(target.getUuid());

            player.sendMessage(CCUtil.s("&7(From " + messageSender.getRank().getColor() + messageSender.getName() + " &7) " + message));
            sender.sendMessage(CCUtil.s("&7(To " + target.getRank().getColor() + target.getName() + " &7) " + message));
            if (target.isSoundsEnabled()) player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
        }
    }

    @Command(names = {"togglepm", "tpm"}, permission = "")
    public static void togglePM(Player sender) {
        Profile profile = Profile.getByUuid(sender.getUniqueId());

        profile.setPrivateMessagesEnabled(!(profile.isPrivateMessagesEnabled()));
        sender.sendMessage(CCUtil.s((profile.isPrivateMessagesEnabled() ? "&aPrivate messages have been enabled" : "&cPrivate messages have been disabled")));
    }

    @Command(names = {"togglesounds", "togglesound"}, permission = "")
    public static void toggleSounds(Player sender) {
        Profile profile = Profile.getByUuid(sender.getUniqueId());

        profile.setSoundsEnabled(!(profile.isSoundsEnabled()));
        sender.sendMessage(CCUtil.s((profile.isSoundsEnabled() ? "&aMessaging sounds have been enabled" : "&cMessaging sounds have been disabled")));
    }

    @Command(names = {"stop", "shutdown"}, permission = "op")
    public static void stop(CommandSender sender) {
        Bukkit.getPluginManager().callEvent(new ShutdownEvent(sender));

        sender.sendMessage(CCUtil.s("&cStopping the server..."));
    }

    @Command(names = {"whitelist"}, permission = "op")
    public static void whitelist(CommandSender sender) {
        if (!(Bukkit.hasWhitelist())) {
            Bukkit.getPluginManager().callEvent(new WhitelistEvent(sender));
            sender.sendMessage(CCUtil.s("&cWhitelisting the server..."));
        } else {
            Bukkit.getPluginManager().callEvent(new WhitelistEvent(sender));
            sender.sendMessage(CCUtil.s("&aUnwhitelisting the server..."));
        }
    }

    @Command(names = {"whitelist add"}, permission = "op")
    public static void whitelistAdd(CommandSender sender, @Param(name = "player") Player player) {
        Profile playerProfile = Profile.getByUuid(player.getUniqueId());

        if (playerProfile.isWhitelisted()) {
            sender.sendMessage(CCUtil.s("&c" + player.getName() + " is already whitelisted."));
            return;
        } else {
            playerProfile.setWhitelisted(true);
        }
    }
}
