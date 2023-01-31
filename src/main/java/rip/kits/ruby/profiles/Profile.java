package rip.kits.ruby.profiles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.kits.ruby.Ruby;
import rip.kits.ruby.ranks.Rank;
import rip.kits.ruby.ranks.event.RankChangeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Profile {
    private static Map<UUID, Profile> profiles = new HashMap<>();

     private UUID uuid;
     private String name;
     private Rank rank = Rank.DEFAULT;
     private boolean soundsEnabled = true, privateMessagesEnabled = true, whitelisted = false;
     private UUID messagingPlayer = null;

     public Profile(UUID uuid, String username) {
         this.uuid = uuid;
         this.name = username;

         Ruby.getMongoManager().loadProfile(this, callback -> {
             if (callback == null) {
                 Ruby.getMongoManager().saveProfile(this, callbackBoolean -> {
                     if (callbackBoolean) {
                         Bukkit.getConsoleSender().sendMessage("Successfully saved " + this.name + " profile");
                     } else {
                         Bukkit.getConsoleSender().sendMessage("Failed to save " + this.name + " profile");
                     }
                 });
             } else {
                 Bukkit.getConsoleSender().sendMessage("Successfully loaded " + this.name + " profile (Rank: " + this.rank + "), (Sounds: " + this.soundsEnabled + ") (Messages: " + this.isPrivateMessagesEnabled() + ")");
             }
         });

         profiles.put(this.uuid, this);
     }

     public void setRank(Rank rank, boolean join) {
         if (this.rank == rank) return;
         this.rank = rank;


         if (!(join)) {
             Bukkit.getPluginManager().callEvent(new RankChangeEvent(getPlayer(), rank, this));
         }
     }

     public Player getPlayer() {
         return Ruby.getInstance().getServer().getPlayer(this.uuid);
     }

    public static Profile getByUuid(UUID uuid) {
         Profile profile = profiles.get(uuid);

         if (profile == null) {

             return new Profile(uuid, Ruby.getInstance().getServer().getPlayer(uuid).getName());
         } else {
             return profile;
         }
    }
}
