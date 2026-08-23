package com.artempvp.client;

import com.artempvp.client.ui.ArtemScreen;
import com.artempvp.client.ui.Overlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ArtemPvPClient implements ClientModInitializer {
    public static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.artempvp.gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.artempvp.client"
        ));

        HudRenderCallback.EVENT.register(new Overlay());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new ArtemScreen());
            }
        });
    }
}
