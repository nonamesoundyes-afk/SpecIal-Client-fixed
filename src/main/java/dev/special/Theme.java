package dev.special;

public final class Theme {
    public static final String[] NAMES = {"Violet", "Blue", "Green", "Red", "Orange", "Aqua"};
    private static final int[] COLORS = {0xFFA58BFF, 0xFF6AA6FF, 0xFF5BE08A, 0xFFFF6B7F, 0xFFFFB04A, 0xFF4FD8D0};

    public static int accent() { return COLORS[Math.floorMod(Config.I.theme, COLORS.length)]; }
    public static String name() { return NAMES[Math.floorMod(Config.I.theme, NAMES.length)]; }

    public static int bg() {
        int a = Math.max(10, Math.min(100, Config.I.opacity)) * 255 / 100;
        return (a << 24) | 0x13151B;
    }

    public static void nextTheme() {
        Config.I.theme = (Config.I.theme + 1) % NAMES.length;
        Config.save();
    }

    public static void nextOpacity() {
        int o = Config.I.opacity - 10;
        Config.I.opacity = o < 20 ? 100 : o;
        Config.save();
    }
}
