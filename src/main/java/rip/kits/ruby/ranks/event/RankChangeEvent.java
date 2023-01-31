package rip.kits.ruby.ranks.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;

@AllArgsConstructor @Getter
public class RankChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Rank rank;
    private Profile profile;



    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
