package dev.special;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HudEditorScreen extends Screen {
    private HudItem drag;
    private int ox, oy;
    private boolean wasDown;

    public HudEditorScreen() { super(Text.literal("HUD Editor")); }

    @Override
    public void render(DrawContext c, int mx, int my, float delta) {
        super.render(c, mx, my, delta);

        for (HudItem it : Hud.ITEMS) {
            if (!it.enabled()) continue;
            int x = it.x(client), y = it.y(client);
            it.render(c, client, x, y);
            int col = it == drag ? Theme.accent() : 0x66FFFFFF;
            c.fill(x - 1, y - 1, x + it.w + 1, y, col);
            c.fill(x - 1, y + it.h, x + it.w + 1, y + it.h + 1, col);
            c.fill(x - 1, y, x, y + it.h, col);
            c.fill(x + it.w, y, x + it.w + 1, y + it.h, col);
        }

        boolean down = GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        if (down && !wasDown) {
            for (int i = Hud.ITEMS.size() - 1; i >= 0; i--) {
                HudItem it = Hud.ITEMS.get(i);
                if (!it.enabled() || it.w <= 0) continue;
                int x = it.x(client), y = it.y(client);
                if (mx >= x && mx <= x + it.w && my >= y && my <= y + it.h) {
                    drag = it;
                    ox = mx - x;
                    oy = my - y;
                    break;
                }
            }
        }
        if (down && drag != null) {
            int nx = Math.max(0, Math.min(width - drag.w, mx - ox));
            int ny = Math.max(0, Math.min(height - drag.h, my - oy));
            drag.setPos(nx, ny);
        }
        if (!down && wasDown) {
            drag = null;
            Config.save();
        }
        wasDown = down;

        c.drawCenteredTextWithShadow(textRenderer, Text.literal("HUD Editor: drag blocks with the mouse, ESC to save"), width / 2, height / 2, 0xFFFFFFFF);
    }

    @Override
    public void removed() { Config.save(); }
}
