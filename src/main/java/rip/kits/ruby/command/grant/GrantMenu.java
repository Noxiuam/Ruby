package rip.kits.ruby.command.grant;

import com.google.common.collect.Maps;
import net.frozenorb.qlib.menu.Button;
import net.frozenorb.qlib.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.kits.ruby.ranks.Rank;

import java.util.Map;
import java.util.UUID;

public class GrantMenu extends Menu {
    private UUID targetUUID;

    public String getTitle(Player player) {
        String title = ChatColor.WHITE + ChatColor.BOLD.toString() + "Grant " + Bukkit.getPlayer(targetUUID).getName() + " a rank.";

        if (Bukkit.getPlayer(targetUUID).getName().length() > 12) title = ChatColor.WHITE + ChatColor.BOLD.toString() +
                "Grant " + Bukkit.getPlayer(targetUUID).getName().substring(0, 12) + " a rank.";


        return title;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (int i = 0; i < 36; i++) {
            if (i == 0) {
                buttons.put(0, new RankButton(Rank.OWNER, (byte) 14, targetUUID));
            } else if (i == 1) {
                buttons.put(i, new RankButton(Rank.DEVELOPER, (byte) 3, targetUUID));
            } else if (i == 2) {
                buttons.put(i, new RankButton(Rank.HEADADMIN, (byte) 14, targetUUID));
            } else if (i == 3) {
                buttons.put(i, new RankButton(Rank.ADMIN, (byte) 14, targetUUID));
            } else if (i == 4) {
                buttons.put(i, new RankButton(Rank.MOD, (byte) 10, targetUUID));
            } else if (i == 5) {
                buttons.put(i, new RankButton(Rank.TRIALMOD, (byte) 4, targetUUID));
            } else if (i == 6) {
                buttons.put(i, new RankButton(Rank.CHATMOD, (byte) 10, targetUUID));
            } else if (i == 7) {
                buttons.put(i, new RankButton(Rank.PARTNER, (byte) 6, targetUUID));
            } else if (i == 8) {
                buttons.put(i, new RankButton(Rank.FAMOUS, (byte) 6, targetUUID));
            } else if (i == 9) {
                buttons.put(i, new RankButton(Rank.YOUTUBE, (byte) 6, targetUUID));
            } else if (i == 10) {
                buttons.put(i, new RankButton(Rank.STREAMER, (byte) 6, targetUUID));
            } else if (i == 11) {
                buttons.put(i, new RankButton(Rank.LEGEND, (byte) 10, targetUUID));
            } else if (i == 12) {
                buttons.put(i, new RankButton(Rank.ULTRA, (byte) 4, targetUUID));
            } else if (i == 13) {
                buttons.put(i, new RankButton(Rank.VIP, (byte) 13, targetUUID));
            } else if (i == 14) {
                buttons.put(i, new RankButton(Rank.DEFAULT, (byte) 7, targetUUID));
            }
        }

        // Original

        /*
                for (int i = 0; i < 36; i++) {
            if (i == 0) {
                buttons.put(0, new RankButton(Rank.OWNER, (byte) 14, targetUUID));
            } else if (i == 1) {
                buttons.put(i, new RankButton(Rank.MANAGER, (byte) 14, targetUUID));
            } else if (i == 2) {
                buttons.put(i, new RankButton(Rank.DEVELOPER, (byte) 3, targetUUID));
            } else if (i == 3) {
                buttons.put(i, new RankButton(Rank.SRADMIN, (byte) 14, targetUUID));
            } else if (i == 4) {
                buttons.put(i, new RankButton(Rank.ADMIN, (byte) 14, targetUUID));
            } else if (i == 5) {
                buttons.put(i, new RankButton(Rank.JRADMIN, (byte) 3, targetUUID));
            } else if (i == 6) {
                buttons.put(i, new RankButton(Rank.SRMOD, (byte) 10, targetUUID));
            } else if (i == 7) {
                buttons.put(i, new RankButton(Rank.MOD, (byte) 10, targetUUID));
            } else if (i == 8) {
                buttons.put(i, new RankButton(Rank.TRIALMOD, (byte) 4, targetUUID));
            } else if (i == 9) {
                buttons.put(i, new RankButton(Rank.PARTNER, (byte) 6, targetUUID));
            } else if (i == 10) {
                buttons.put(i, new RankButton(Rank.FAMOUS, (byte) 6, targetUUID));
            } else if (i == 11) {
                buttons.put(i, new RankButton(Rank.YOUTUBE, (byte) 6, targetUUID));
            } else if (i == 12) {
                buttons.put(i, new RankButton(Rank.DONOR, (byte) 3, targetUUID));
            } else if (i == 13) {
                buttons.put(i, new RankButton(Rank.DEFAULT, (byte) 7, targetUUID));
            }
        }
         */

        return buttons;
    }

    public GrantMenu(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }
}
