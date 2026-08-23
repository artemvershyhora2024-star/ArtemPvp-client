package com.artempvp.client.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ArtemScreen extends Screen {

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override.
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Отрисовка темного полупрозрачного фона вместо блюра
        context.fill(0, 0, this.width, this.height, 0x99000000);

        // Отрисовка заглавного текста
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
