package rip.kits.ruby.command;

import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.kits.ruby.command.grant.GrantMenu;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;
import rip.kits.rutilities.utils.CCUtil;

public class GrantCommands {
    @Command(names = "consolegrant", permission = "op") // Gonna redo qLib to implement my system...
    public static void grantConsole(Player sender, @Param(name = "Player") Player player, @Param(name = "Rank", wildcard = true) String rank) {
        Profile playerProfile = Profile.getByUuid(player.getUniqueId());

        Rank rankName = Rank.getByName(rank);

            if (rankName == null) {
                sender.sendMessage(CCUtil.s("&cThe rank " + rank + " doesn't exist."));
            } else if (playerProfile.getRank() == rankName) {
                sender.sendMessage(CCUtil.s("&cThis player already contains this rank."));
                return;
            } else {
                playerProfile.setRank(rankName);
                sender.sendMessage(CCUtil.s("&aYou have successfully set " + player.getName() + "'s rank to " + rankName.getColor() + rankName.getRankName()));
            }

    }

    @Command(names = "grant", permission = "")
    public static void grant(Player sender, @Param(name = "Player") Player player) {
        Profile profile = Profile.getByUuid(sender.getUniqueId());

        if (profile.getRank().isAboveOrEqual(Rank.HEADADMIN)) {
            new GrantMenu(player.getUniqueId()).openMenu(sender);
        } else {
            sender.sendMessage(CCUtil.s("&cNo permission."));
        }
    }

}
