package dev.special;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class RowButton extends ButtonWidget {
    private final Supplier<Boolean> checked;
    private final Supplier<String> value;

    public RowButton(int x, int y, int w, int h, String label, Supplier<Boolean> checked, Supplier<String> value, PressAction action) {
        super(x, y, w, h, Text.literal(label), action, DEFAULT_NARRATION_SUPPLIER);
        this.checked = checked;
        this.value = value;
    }

    @Override
    protected void renderWidget(DrawContext c, int mx, int my, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (isHovered()) c.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFF1D2029);
        int tx = getX() + 4, ty = getY() + (getHeight() - 8) / 2;
        if (checked != null) {
            if (checked.get()) c.drawText(mc.textRenderer, "\u2713", tx, ty, Theme.accent(), false);
            tx += 12;
        }
        c.drawText(mc.textRenderer, getMessage(), tx, ty, 0xFFF1F3FA, true);
        if (value != null) {
            String v = value.get();
            c.drawText(mc.textRenderer, v, getX() + getWidth() - 4 - mc.textRenderer.getWidth(v), ty, Theme.accent(), true);
        }
    }
}
