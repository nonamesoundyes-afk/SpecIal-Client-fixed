package dev.special;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class Hud {
    public static final List<HudItem> ITEMS = List.of(new Watermark(), new Info(), new Effects(), new Armor(), new Keys());

    public static void render(DrawContext c) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;
        for (HudItem i : ITEMS) {
            if (i.enabled()) i.render(c, mc, i.x(mc), i.y(mc));
        }
    }

    static void panel(DrawContext c, int x, int y, int w, int h) {
        c.fill(x, y, x + w, y + h, Theme.bg());
        c.fill(x, y, x + w, y + 2, Theme.accent());
    }

    static void text(DrawContext c, MinecraftClient mc, String s, int x, int y, int col) {
        c.drawText(mc.textRenderer, s, x, y, col, true);
    }

    static final class Watermark extends HudItem {
        Watermark() { super("watermark", "Watermark"); }
        protected int defX(MinecraftClient mc) { return mc.getWindow().getScaledWidth() / 2 - 60; }
        protected int defY(MinecraftClient mc) { return 4; }
        public void render(DrawContext c, MinecraftClient mc, int x, int y) {
            String a = "SpecIal", b = " | " + mc.getCurrentFps() + " fps";
            w = mc.textRenderer.getWidth(a + b) + 16;
            h = 16;
            panel(c, x, y, w, h);
            text(c, mc, a, x + 8, y + 5, Theme.accent());
            text(c, mc, b, x + 8 + mc.textRenderer.getWidth(a), y + 5, 0xFFFFFFFF);
        }
    }

    static final class Info extends HudItem {
        Info() { super("info", "Info"); }
        protected int defX(MinecraftClient mc) { return 6; }
        protected int defY(MinecraftClient mc) { return 6; }
        public void render(DrawContext c, MinecraftClient mc, int x, int y) {
            var p = mc.player;
            int ping = 0;
            if (mc.getNetworkHandler() != null) {
                PlayerListEntry e = mc.getNetworkHandler().getPlayerListEntry(p.getUuid());
                if (e != null) ping = e.getLatency();
            }
            String[] l = {
                    "XYZ " + (int) Math.floor(p.getX()) + " " + (int) Math.floor(p.getY()) + " " + (int) Math.floor(p.getZ()),
                    "Ping " + ping + " ms",
                    "FPS " + mc.getCurrentFps()
            };
            int mw = mc.textRenderer.getWidth("Info");
            for (String s : l) mw = Math.max(mw, mc.textRenderer.getWidth(s));
            w = mw + 16;
            h = 8 + 10 * (l.length + 1) + 2;
            panel(c, x, y, w, h);
            text(c, mc, "Info", x + 8, y + 6, Theme.accent());
            for (int i = 0; i < l.length; i++) text(c, mc, l[i], x + 8, y + 16 + i * 10, 0xFFFFFFFF);
        }
    }

    static final class Effects extends HudItem {
        Effects() { super("effects", "Effects"); }
        protected int defX(MinecraftClient mc) { return 6; }
        protected int defY(MinecraftClient mc) { return 64; }
        public void render(DrawContext c, MinecraftClient mc, int x, int y) {
            var list = mc.player.getStatusEffects();
            if (list.isEmpty()) { w = 0; h = 0; return; }
            String[] l = new String[list.size()];
            int i = 0, mw = mc.textRenderer.getWidth("Effects");
            for (StatusEffectInstance e : list) {
                String name = e.getEffectType().value().getName().getString();
                String t;
                if (e.isInfinite()) t = "inf";
                else { int s = e.getDuration() / 20; t = (s / 60) + ":" + String.format("%02d", s % 60); }
                l[i] = name + " " + (e.getAmplifier() + 1) + "  " + t;
                mw = Math.max(mw, mc.textRenderer.getWidth(l[i]));
                i++;
            }
            w = mw + 16;
            h = 8 + 10 * (l.length + 1) + 2;
            panel(c, x, y, w, h);
            text(c, mc, "Effects", x + 8, y + 6, Theme.accent());
            for (int k = 0; k < l.length; k++) text(c, mc, l[k], x + 8, y + 16 + k * 10, 0xFFFFFFFF);
        }
    }

    static final class Armor extends HudItem {
        private static final EquipmentSlot[] SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        Armor() { super("armor", "Armor"); }
        protected int defX(MinecraftClient mc) { return mc.getWindow().getScaledWidth() / 2 - 36; }
        protected int defY(MinecraftClient mc) { return mc.getWindow().getScaledHeight() - 72; }
        public void render(DrawContext c, MinecraftClient mc, int x, int y) {
            w = 4 * 18;
            h = 16;
            for (int i = 0; i < 4; i++) {
                ItemStack s = mc.player.getEquippedStack(SLOTS[i]);
                if (!s.isEmpty()) c.drawItem(s, x + i * 18, y);
            }
        }
    }

    static final class Keys extends HudItem {
        Keys() { super("keys", "Keystrokes"); }
        protected int defX(MinecraftClient mc) { return 6; }
        protected int defY(MinecraftClient mc) { return mc.getWindow().getScaledHeight() - 60; }
        public void render(DrawContext c, MinecraftClient mc, int x, int y) {
            w = 3 * 18 + 4;
            h = 2 * 18 + 2;
            var o = mc.options;
            key(c, mc, "W", o.forwardKey.isPressed(), x + 20, y);
            key(c, mc, "A", o.leftKey.isPressed(), x, y + 20);
            key(c, mc, "S", o.backKey.isPressed(), x + 20, y + 20);
            key(c, mc, "D", o.rightKey.isPressed(), x + 40, y + 20);
        }
        private void key(DrawContext c, MinecraftClient mc, String s, boolean on, int x, int y) {
            c.fill(x, y, x + 18, y + 18, on ? Theme.accent() : Theme.bg());
            text(c, mc, s, x + 9 - mc.textRenderer.getWidth(s) / 2, y + 5, 0xFFFFFFFF);
        }
    }
}
