package rip.kits.ruby.command.grant;

import net.frozenorb.qlib.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;
import rip.kits.rutilities.utils.CCUtil;

import java.util.List;
import java.util.UUID;

public class RankButton extends Button {

    private Rank rank;
    private byte damageValue;
    private UUID targetUUID;

    @Override
    public String getName(Player player) {
        return rank.getColor() + rank.getRankName();
    }

    @Override
    public List<String> getDescription(Player player) {
        return null;
    }

    @Override
    public byte getDamageValue(Player player) {
        return damageValue;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.WOOL;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        Profile profile = Profile.getByUuid(this.targetUUID);
        Profile menuPlayerProfile = Profile.getByUuid(player.getUniqueId());

        if (rank.getLevel() >= menuPlayerProfile.getRank().getLevel() ) {
            player.sendMessage(CCUtil.s("&cYou cannot give a rank that is higher then yours!"));
            return;
        } else if (profile.getRank() == rank) {
            player.sendMessage(CCUtil.s("&cYou cannot give a rank they already have."));
            return;
        } else {
            profile.setRank(rank, false); // So we can get the message....
        }
    }

    public RankButton(Rank rank, byte damageValue, UUID targetUUID) {
        this.rank = rank;
        this.damageValue = damageValue;
        this.targetUUID = targetUUID;
    }
}
