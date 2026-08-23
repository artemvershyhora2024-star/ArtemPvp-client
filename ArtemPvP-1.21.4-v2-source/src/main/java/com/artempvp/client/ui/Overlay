package com.artempvp.client.ui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudOverlay implements HudRenderCallback {
    public static int keybindsX = 10;
    public static int keybindsY = 50;

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        int centerX = client.getWindow().getScaledWidth() / 2;

        // 1. Верхний бар ArtemPVP (FPS / MS)
        drawBorderedRect(drawContext, centerX - 110, 10, centerX + 110, 32, 0xEE0B0B12, 0xFFA822FF);
        drawContext.drawTextWithShadow(client.textRenderer, "✦ ArtemPVP", centerX - 95, 17, 0xFFFFFFFF);
        int fps = client.getCurrentFps();
        drawContext.drawTextWithShadow(client.textRenderer, "⚙ " + fps + " FPS  ≡  12 MS", centerX + 10, 17, 0xFFA8A8B8);

        // 2. Виджет Keybinds (который двигается мышкой в меню)
        drawWidget(drawContext, client, keybindsX, keybindsY, 110, 60, "■ Keybinds");
        drawContext.drawTextWithShadow(client.textRenderer, "| ShiftTap", keybindsX + 8, keybindsY + 22, 0xFFA822FF);
        drawContext.drawTextWithShadow(client.textRenderer, "H", keybindsX + 85, keybindsY + 22, 0xFFA8A8B8);
    }

    private void drawWidget(DrawContext context, MinecraftClient client, int x, int y, int width, int height, String title) {
        drawBorderedRect(context, x, y, x + width, y + height, 0xEE0B0B12, 0xFFA822FF);
        context.drawTextWithShadow(client.textRenderer, title, x + 8, y + 6, 0xFFFFFFFF);
    }

    private void drawBorderedRect(DrawContext context, int x1, int y1, int x2, int y2, int fillColor, int borderColor) {
        context.fill(x1, y1, x2, y2, fillColor);
        context.fill(x1, y1, x2, y1 + 1, borderColor);
        context.fill(x1, y2 - 1, x2, y2, borderColor);
        context.fill(x1, y1, x1 + 1, y2, borderColor);
        context.fill(x2 - 1, y1, x2, y2, borderColor);
    }
}
