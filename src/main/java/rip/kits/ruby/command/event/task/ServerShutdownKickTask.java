package rip.kits.ruby.command.event.task;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kits.rutilities.utils.CCUtil;

import java.util.concurrent.TimeUnit;

public class ServerShutdownKickTask extends BukkitRunnable {
    private int secondsRemaining;

    public ServerShutdownKickTask(int seconds, TimeUnit timeUnit) {
        this.secondsRemaining = (int) timeUnit.toSeconds(seconds);
    }

    @Override
    public void run() {
        if (secondsRemaining == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(CCUtil.s("&cServer is shutting down."));
            }
        }

        switch (secondsRemaining) {
            case 5:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(CCUtil.s("&cServer shutting down in 5 seconds."));
                }
                break;
            case 4:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(CCUtil.s("&cServer shutting down in 4 seconds."));
                }
                break;
            case 3:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(CCUtil.s("&cServer shutting down in 3 seconds."));
                }
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(CCUtil.s("&cServer shutting down in 2 seconds."));
                }
                break;
            case 1:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(CCUtil.s("&cServer shutting down..."));
                }
                break;
        }


        --this.secondsRemaining;
    }
}
