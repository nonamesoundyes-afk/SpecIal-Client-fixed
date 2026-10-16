package dev.special.mixin;

import dev.special.AccountScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen {
    protected MultiplayerScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void special$addLogin(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Login"),
                        b -> this.client.setScreen(new AccountScreen(this)))
                .dimensions(this.width - 66, 6, 60, 20).build());
    }
}
