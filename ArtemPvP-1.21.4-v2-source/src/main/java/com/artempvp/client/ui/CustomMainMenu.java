package com.artempvp.client.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomMainMenu extends Screen {

    public CustomMainMenu() {
        super(Text.literal("ArtemPvP Main Menu"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Кнопка: Одиночная игра
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Singleplayer"), button -> {
            if (this.client != null) this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(centerX - 100, centerY - 10, 200, 20).build());

        // Кнопка: Сетевая игра
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Multiplayer"), button -> {
            if (this.client != null) this.client.setScreen(new MultiplayerScreen(this));
        }).dimensions(centerX - 100, centerY + 15, 200, 20).build());

        // Кнопка: Настройки
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Options"), button -> {
            if (this.client != null) this.client.setScreen(new OptionsScreen(this, this.client.options));
        }).dimensions(centerX - 100, centerY + 40, 200, 20).build());

        // Кнопка: Выход из игры
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Quit Game"), button -> {
            if (this.client != null) this.client.scheduleStop();
        }).dimensions(centerX - 100, centerY + 65, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Тёмно-фиолетовый фон
        context.fill(0, 0, this.width, this.height, 0xFF0D0A14);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Фиолетовая плашка под логотип
        context.fill(centerX - 120, centerY - 85, centerX + 120, centerY - 25, 0xEE161724);
        
        // Заголовок ARTEMPVP
        context.drawTextWithShadow(this.textRenderer, "ARTEMPVP CLIENT", centerX - 50, centerY - 70, 0xFFA822FF);
        context.drawTextWithShadow(this.textRenderer, "1.21.4 • Premium Edition", centerX - 60, centerY - 50, 0xFF6C6E7D);

        super.render(context, mouseX, mouseY, delta);
    }
}
