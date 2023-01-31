package rip.kits.ruby.command.event.task;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kits.ruby.Ruby;

import java.util.concurrent.TimeUnit;

@Getter
public class ServerShutdownTask extends BukkitRunnable {
    private int secondsRemaining;
    private boolean wasWhitelisted;

    public ServerShutdownTask(int timeUnitAmount, TimeUnit timeUnit) {
        this.secondsRemaining = (int) timeUnit.toSeconds(timeUnitAmount);
        this.wasWhitelisted = Ruby.getInstance().getServer().hasWhitelist();
    }

    @Override
    public void run() {
        if (this.secondsRemaining == 5) Ruby.getInstance().getServer().setWhitelist(true);
        if (this.secondsRemaining == 0) {
            Ruby.getInstance().getServer().setWhitelist(this.wasWhitelisted);
            Ruby.getInstance().getServer().shutdown();
        }

        --this.secondsRemaining;
    }
}
