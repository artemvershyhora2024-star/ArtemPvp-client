package com.artempvp.client.module;

import net.minecraft.client.MinecraftClient;

public final class FullBrightModule extends Module {
    private final MinecraftClient client;
    private double oldGamma = 1.0;

    public FullBrightModule(MinecraftClient client) {
        super("FullBright", Category.VISUAL, false);
        this.client = client;
    }

    @Override
    protected void onEnable() {
        oldGamma = client.options.getGamma().getValue();
        client.options.getGamma().setValue(16.0);
    }

    @Override
    protected void onDisable() {
        client.options.getGamma().setValue(oldGamma);
    }
}
