package rip.kits.ruby.command.event.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kits.ruby.profiles.Profile;
import rip.kits.rutilities.utils.CCUtil;

import java.util.concurrent.TimeUnit;

public class ServerWhitelistKickTask extends BukkitRunnable {
    private int secondsRemaining;

    public ServerWhitelistKickTask(int seconds, TimeUnit timeUnit) {
        this.secondsRemaining = (int) timeUnit.toSeconds(seconds);
    }

    @Override
    public void run() {
        if (secondsRemaining == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = Profile.getByUuid(player.getUniqueId());

                if (!(profile.isWhitelisted())) player.kickPlayer(CCUtil.s("&cServer is going into whitelist mode."));
            }
        }

        switch (secondsRemaining) {
            case 1:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Profile profile = Profile.getByUuid(player.getUniqueId());
                    if (profile.isWhitelisted()) continue;

                    player.sendMessage(CCUtil.s("&cKicking you because server is going into whitelist mode."));
                }
                break;
        }


        --this.secondsRemaining;
    }
}
