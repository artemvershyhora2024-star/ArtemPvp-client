package com.artempvp.client.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ArtemScreen extends Screen {

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Тёмный полупрозрачный фон БЕЗ размытия
        context.fill(0, 0, this.width, this.height, 0x80000000);

        int centerX = this.width / 2;

        // 1. Верхняя панель (ArtemPVP + FPS + MS)
        drawBorderedRect(context, centerX - 110, 15, centerX + 110, 40, 0xEE0B0B12, 0xFFA822FF);
        context.drawTextWithShadow(this.textRenderer, "✦ ArtemPVP", centerX - 95, 23, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "⚙  60 FPS  ≡  12 MS", centerX + 10, 23, 0xFFA8A8B8);

        // 2. Виджет: Cooldowns (слева)
        drawWidget(context, centerX - 320, 70, 150, 90, "● Cooldowns");
        context.drawTextWithShadow(this.textRenderer, "[?] Пласт", centerX - 305, 95, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "5.7c", centerX - 305, 107, 0xFFA8A8B8);
        context.drawTextWithShadow(this.textRenderer, "🍎 Чарка", centerX - 305, 125, 0xFFFFFFFF);

        // 3. Виджет: Keybinds
        drawWidget(context, centerX - 155, 70, 120, 70, "■ Keybinds");
        context.drawTextWithShadow(this.textRenderer, "| Hud", centerX - 145, 95, 0xFFA822FF);
        context.drawTextWithShadow(this.textRenderer, "1", centerX - 55, 95, 0xFFA8A8B8);
        context.drawTextWithShadow(this.textRenderer, "| JumpWave", centerX - 145, 112, 0xFFA822FF);
        context.drawTextWithShadow(this.textRenderer, "5", centerX - 55, 112, 0xFFA8A8B8);

        // 4. Виджет: Potions (центр)
        drawWidget(context, centerX - 20, 70, 130, 160, "⚗ Potions");
        context.drawTextWithShadow(this.textRenderer, "Свечение", centerX - 5, 95, 0xFFFF3333);
        context.drawTextWithShadow(this.textRenderer, "35.8c", centerX - 5, 107, 0xFFA8A8B8);
        context.drawTextWithShadow(this.textRenderer, "Поглощение IV", centerX - 5, 125, 0xFF3399FF);
        context.drawTextWithShadow(this.textRenderer, "1:37", centerX - 5, 137, 0xFFA8A8B8);
        context.drawTextWithShadow(this.textRenderer, "Сила III", centerX - 5, 155, 0xFFFF3333);
        context.drawTextWithShadow(this.textRenderer, "2:22", centerX - 5, 167, 0xFFA8A8B8);

        // 5. Виджет: Inventory (справа вверху)
        drawWidget(context, centerX + 125, 70, 160, 100, "⬡ Inventory");
        context.drawTextWithShadow(this.textRenderer, "[ Предметы ]", centerX + 175, 115, 0xFFA8A8B8);

        // 6. Виджет: Игрок (справа внизу)
        drawWidget(context, centerX + 125, 180, 160, 50, "");
        context.drawTextWithShadow(this.textRenderer, "ArtemPlayer", centerX + 140, 190, 0xFFFFFFFF);
        context.fill(centerX + 140, 205, centerX + 270, 210, 0xFF22FF88); // Полоска здоровья

        super.render(context, mouseX, mouseY, delta);
    }

    // Вспомогательный метод для отрисовки карточек
    private void drawWidget(DrawContext context, int x, int y, int width, int height, String title) {
        drawBorderedRect(context, x, y, x + width, y + height, 0xEE0B0B12, 0xFFA822FF);
        if (!title.isEmpty()) {
            context.drawTextWithShadow(this.textRenderer, title, x + 10, y + 8, 0xFFFFFFFF);
            context.fill(x + 10, y + 22, x + width - 10, y + 23, 0x44A822FF);
        }
    }

    // Вспомогательный метод для скруглённых/фиолетовых рамок
    private void drawBorderedRect(DrawContext context, int x1, int y1, int x2, int y2, int fillColor, int borderColor) {
        context.fill(x1, y1, x2, y2, fillColor);
        context.fill(x1, y1, x2, y1 + 1, borderColor);
        context.fill(x1, y2 - 1, x2, y2, borderColor);
        context.fill(x1, y1, x1 + 1, y2, borderColor);
        context.fill(x2 - 1, y1, x2, y2, borderColor);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
