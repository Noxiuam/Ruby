package rip.kits.ruby;

import lombok.Getter;
import net.frozenorb.qlib.command.FrozenCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.kits.ruby.listener.PlayerListener;
import rip.kits.ruby.mongo.MongoManager;


public final class Ruby extends JavaPlugin {

    @Getter private static Ruby instance;
    @Getter private static MongoManager mongoManager;

    @Override
    public void onEnable() {
       long start = System.currentTimeMillis();
       instance = this;

       mongoManager = new MongoManager();

       FrozenCommandHandler.registerAll(this);

       Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

       Bukkit.getConsoleSender().sendMessage("[Ruby] Finished in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
    }
}
