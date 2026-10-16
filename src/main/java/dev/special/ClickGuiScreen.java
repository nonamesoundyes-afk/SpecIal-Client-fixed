package dev.special;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    private static final int PW = 150, ROW = 16;

    public ClickGuiScreen() { super(Text.literal("SpecIal")); }

    @Override
    protected void init() {
        int total = PW * 2 + 10;
        int x1 = width / 2 - total / 2, x2 = x1 + PW + 10;
        int y = height / 2 - 70;

        int n1 = Hud.ITEMS.size(), n2 = 4;
        panel(x1, y, "Visuals", n1);
        panel(x2, y, "Client", n2);

        int i = 0;
        for (HudItem it : Hud.ITEMS) {
            addDrawableChild(new RowButton(x1 + 4, y + 22 + i * ROW, PW - 8, ROW - 1, it.label, it::enabled, null, b -> it.toggle()));
            i++;
        }
        addDrawableChild(new RowButton(x2 + 4, y + 22, PW - 8, ROW - 1, "Theme", null, Theme::name, b -> Theme.nextTheme()));
        addDrawableChild(new RowButton(x2 + 4, y + 22 + ROW, PW - 8, ROW - 1, "Opacity", null, () -> Config.I.opacity + "%", b -> Theme.nextOpacity()));
        addDrawableChild(new RowButton(x2 + 4, y + 22 + 2 * ROW, PW - 8, ROW - 1, "HUD Editor", null, null, b -> client.setScreen(new HudEditorScreen())));
        addDrawableChild(new RowButton(x2 + 4, y + 22 + 3 * ROW, PW - 8, ROW - 1, "Login", null, () -> client.getSession().getUsername(), b -> client.setScreen(new AccountScreen(this))));
    }

    private void panel(int x, int y, String title, int rows) {
        int h = 24 + rows * ROW + 4;
        addDrawable((c, mx, my, d) -> {
            c.fill(x, y, x + PW, y + h, 0xF013151B);
            c.drawText(textRenderer, title, x + 8, y + 6, 0xFFFFFFFF, true);
            c.fill(x + 6, y + 18, x + PW - 6, y + 19, 0xFF3A3D48);
        });
    }

    @Override
    public void removed() { Config.save(); }
}
