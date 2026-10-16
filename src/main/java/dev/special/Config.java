package dev.special;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    public int theme = 0;
    public int opacity = 80;
    public Map<String, Boolean> enabled = new HashMap<>();
    public Map<String, int[]> pos = new HashMap<>();
    public List<String> accounts = new ArrayList<>();

    public static Config I = new Config();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("special.json");
    private static final Gson G = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        try {
            if (Files.exists(FILE)) I = G.fromJson(Files.readString(FILE), Config.class);
        } catch (Exception e) {
            I = new Config();
        }
        if (I == null) I = new Config();
        if (I.enabled == null) I.enabled = new HashMap<>();
        if (I.pos == null) I.pos = new HashMap<>();
        if (I.accounts == null) I.accounts = new ArrayList<>();
    }

    public static void save() {
        try {
            Files.writeString(FILE, G.toJson(I));
        } catch (Exception ignored) {
        }
    }
}
