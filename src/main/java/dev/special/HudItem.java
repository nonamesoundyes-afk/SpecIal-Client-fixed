package dev.special;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class HudItem {
    public final String id;
    public final String label;
    public int w = 10, h = 10;

    protected HudItem(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public boolean enabled() { return Config.I.enabled.getOrDefault(id, true); }

    public void toggle() {
        Config.I.enabled.put(id, !enabled());
        Config.save();
    }

    public int x(MinecraftClient mc) {
        int[] p = Config.I.pos.get(id);
        return p == null ? defX(mc) : p[0];
    }

    public int y(MinecraftClient mc) {
        int[] p = Config.I.pos.get(id);
        return p == null ? defY(mc) : p[1];
    }

    public void setPos(int x, int y) { Config.I.pos.put(id, new int[]{x, y}); }

    protected abstract int defX(MinecraftClient mc);
    protected abstract int defY(MinecraftClient mc);

    public abstract void render(DrawContext c, MinecraftClient mc, int x, int y);
}
