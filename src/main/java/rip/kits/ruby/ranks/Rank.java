package rip.kits.ruby.ranks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.kits.ruby.profiles.Profile;

@AllArgsConstructor
@Getter
public enum Rank {
    CONSOLE(10000, "Console", "", "", ChatColor.DARK_RED, true, 500655),
    SUPERRANK(1000, "Super Level", "&7[&4Owner&7]&4", "", ChatColor.DARK_RED, true, 11),
    OWNER(100, "Owner", "&7[&4Owner&7]&4", "", ChatColor.DARK_RED, false, 10),
//    MANAGER(95, "Manager", "&7[&4Manager&7]&4&o", "", ChatColor.DARK_RED, false, 9),
    DEVELOPER(90, "Developer", "&7[&b&oDeveloper&7]&b&o", "", ChatColor.AQUA, false, 8),
    HEADADMIN(85, "HeadAdmin", "&7[&cHead Admin&7]&c&o", "", ChatColor.RED, false, 7),
    ADMIN(80, "Admin", "&7[&cAdmin&7]&c", "", ChatColor.RED, false, 6),
//    JRADMIN(75, "JrAdmin", "&7[&bJr Admin&7]&b&o", "", ChatColor.AQUA, false, 5),
//    SRMOD(70, "SrMod", "&7[&5Senior Mod&7]&5&o", "", ChatColor.DARK_PURPLE, false, 4),
    MOD(65, "Mod", "&7[&5Mod&7]&5&o", "", ChatColor.DARK_PURPLE, false, 3),
    TRIALMOD(60, "Trial Mod", "&7[&eTrial Mod&7]&e", "", ChatColor.YELLOW, false, 2),

    CHATMOD(65, "ChatMod", "&7[&5ChatMod&7]&5", "", ChatColor.DARK_PURPLE, false, 61),

    PARTNER(55, "Partner", "&7[&d&oPartner&7]&d&o", "", ChatColor.LIGHT_PURPLE, false, 15),
    FAMOUS(50, "Famous", "&7[&dFamous&7]&d&o", "", ChatColor.LIGHT_PURPLE, false, 12),
    YOUTUBE(45, "YouTube", "&7[&dYouTube&7]&d", "", ChatColor.LIGHT_PURPLE, false, 13),
    STREAMER(44, "Streamer", "&7[&dStreamer&7]&d", "", ChatColor.LIGHT_PURPLE, false, 14),

    LEGEND(42, "Legend", "&7[&5Legend&7]&6", "", ChatColor.DARK_PURPLE, false, 57),
    ULTRA(41, "Ultra", "&7[&6&lUltra&7]&6", "", ChatColor.GOLD, false, 58),
    VIP(40, "VIP", "&7[&aVIP&7]&a", "", ChatColor.GREEN, false, 59),

    DEFAULT(1, "Default", "", "", ChatColor.GRAY, false, 1);

    public int level;
    public String rankName;
    public String prefix;
    public String desc;
    public ChatColor color;
    public boolean hidden;
    public int id;

    public boolean isAboveOrEqual(Rank rank) {
        return rank.ordinal() >= this.ordinal();
    }

    public static Rank getByID(int id) {
        Rank rank = DEFAULT;

        for (Rank value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }
        return rank;
    }

    public static Rank getByName(String name) {
        for (Rank value : values()) {
            Bukkit.getConsoleSender().sendMessage(String.valueOf(value.getRankName()));
            Bukkit.getConsoleSender().sendMessage(String.valueOf(name));
            if (value.getRankName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}
