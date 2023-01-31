package rip.kits.ruby.mongo;

import com.mongodb.*;
import com.mongodb.client.model.DBCollectionUpdateOptions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import rip.kits.ruby.Ruby;
import rip.kits.ruby.profiles.Profile;
import rip.kits.ruby.ranks.Rank;
import rip.kits.rutilities.utils.callback.GeneralCallback;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MongoManager {
    @Getter private MongoClient mongoClient;
    @Getter private DB database;
    @Getter private DBCollection playersCollection;
    @Getter private DBCollection serverCollection;

    public MongoManager() {
        String link = "mongodb://localhost:27017";
        mongoClient = new MongoClient(new MongoClientURI(link));

        try {
            database = mongoClient.getDB("ruby");
            playersCollection = database.getCollection("players");
            serverCollection = database.getCollection("servers");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getProfilesInDB(GeneralCallback<Set<UUID>> callback) {
        Set<UUID> profiles = new HashSet<>();

        DBCursor cursor = playersCollection.find();
        cursor.forEach(dbObject -> profiles.add(UUID.fromString(dbObject.get("uuid").toString())));
        callback.call(profiles);
    }

    public void getProfileFromDB(String username, UUID uuid, GeneralCallback<Profile> callback) {
        // Use This method to get a offline players Profile from the database to change their rank.
    }

    public void loadProfile(Profile profile, GeneralCallback<Profile> callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                DBObject query = new BasicDBObject("uuid", profile.getUuid().toString());
                DBCursor cursor = Ruby.getMongoManager().getPlayersCollection().find(query);
                DBObject storedProfile = cursor.one();

                if (storedProfile == null) {
                    callback.call(null);
                    return;
                }

                int rankID = (int) storedProfile.get("rank");
                boolean privateMessages = (boolean) storedProfile.get("privateMessages");
                boolean sounds = (boolean) storedProfile.get("sounds");

                if (rankID == 500655) rankID = 1; // Prevent people from having the console rank.

                Rank rank = Rank.getByID(rankID);

                profile.setRank(rank, true);
                profile.setPrivateMessagesEnabled(privateMessages);
                profile.setSoundsEnabled(sounds);

                Bukkit.getConsoleSender().sendMessage(String.valueOf(rank));

                callback.call(profile);
            }
        }.runTaskAsynchronously(Ruby.getInstance());
    }

    public void saveProfile(Profile profile, GeneralCallback<Boolean> callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                DBObject rankObject = new BasicDBObjectBuilder().add("uuid", profile.getUuid().toString())
                        .add("name", profile.getName())
                        .add("rank", profile.getRank().getId())
                        .add("privateMessages", profile.isPrivateMessagesEnabled())
                        .add("sounds", profile.isSoundsEnabled()).get();
                Ruby.getMongoManager().getPlayersCollection().update(new BasicDBObject("uuid", profile.getUuid().toString()), rankObject, new DBCollectionUpdateOptions().upsert(true));
                callback.call(true);
            }
        }.runTaskAsynchronously(Ruby.getInstance());
    }
}
