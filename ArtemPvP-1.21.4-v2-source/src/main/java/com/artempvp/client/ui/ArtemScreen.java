package com.artempvp.client.ui;

import com.artempvp.client.modules.Module;
import com.artempvp.client.modules.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ArtemScreen extends Screen {

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Вместо этого (который делал блюр):
        // this.renderBackground(context, mouseX, mouseY, delta);

        // Ставим обычную темную подложку БЕЗ размытия:
        context.fill(0, 0, this.width, this.height, 0x80000000);

        // Отрисовка всех твоих стандартных виджетов/кнопок
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
