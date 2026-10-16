package dev.special;

import dev.special.mixin.MinecraftClientAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AccountScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget nick;
    private String status = "";

    public AccountScreen(Screen parent) {
        super(Text.literal("SpecIal accounts"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = width / 2, y = height / 2 - 50;
        nick = new TextFieldWidget(textRenderer, cx - 100, y, 200, 20, Text.literal("Nick"));
        nick.setMaxLength(16);
        nick.setText(client.getSession().getUsername());
        addDrawableChild(nick);

        addDrawableChild(ButtonWidget.builder(Text.literal("Login (offline)"), b -> login(nick.getText().trim()))
                .dimensions(cx - 100, y + 26, 200, 20).build());

        int i = 0;
        for (String name : Config.I.accounts) {
            if (i >= 5) break;
            addDrawableChild(ButtonWidget.builder(Text.literal(name), b -> login(name))
                    .dimensions(cx - 100, y + 56 + i * 22, 200, 20).build());
            i++;
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> client.setScreen(parent))
                .dimensions(cx - 100, y + 56 + 5 * 22 + 6, 200, 20).build());
    }

    private void login(String name) {
        if (!name.matches("[A-Za-z0-9_]{3,16}")) {
            status = "Nick: 3-16 chars, letters, digits, underscore";
            return;
        }
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
        ((MinecraftClientAccessor) client).special$setSession(
                new Session(name, uuid, "", java.util.Optional.empty(), java.util.Optional.empty()));
        if (!Config.I.accounts.contains(name)) Config.I.accounts.add(name);
        Config.save();
        status = "Logged in as " + name;
        clearAndInit();
    }

    @Override
    public void render(DrawContext c, int mx, int my, float delta) {
        super.render(c, mx, my, delta);
        c.drawCenteredTextWithShadow(textRenderer, Text.literal("SpecIal | Accounts"), width / 2, height / 2 - 76, Theme.accent());
        c.drawCenteredTextWithShadow(textRenderer, Text.literal("Current: " + client.getSession().getUsername()), width / 2, height / 2 - 64, 0xFFAAAAAA);
        if (!status.isEmpty())
            c.drawCenteredTextWithShadow(textRenderer, Text.literal(status), width / 2, height / 2 + 130, 0xFFFFFFFF);
    }
}
