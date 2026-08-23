package com.artempvp.client.ui;

import com.artempvp.client.modules.Module;
import com.artempvp.client.modules.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ArtemScreen extends Screen {

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Отрисовка темного полупрозрачного фона (0x99000000) вместо блюра.
        context.fill(0, 0, this.width, this.height, 0x99000000);

        // Отрисовка заглавного текста.
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        // --- ВОТ ЭТО МЫ ПРОПУСТИЛИ ---
        // Отрисовка всех модулей и кнопок клиента.
        renderModules(context, mouseX, mouseY);
        // -----------------------------

        super.render(context, mouseX, mouseY, delta);
    }

    // Метод, который мы удалили по ошибке. Он отрисовывает кнопки модулей.
    private void renderModules(DrawContext context, int mouseX, int mouseY) {
        List<Module> modules = ModuleManager.getInstance().getModules();
        int yOffset = 50;
        int xOffset = 50;
        int count = 0;

        for (Module module : modules) {
            // Очень простая отрисовка кнопок для теста.
            int color = module.isEnabled() ? 0xFF00FF00 : 0xFFFF0000; // Зеленый если вкл, красный если выкл.
            context.fill(xOffset, yOffset, xOffset + 100, yOffset + 20, 0x60000000); // Фон кнопки
            context.drawTextWithShadow(this.textRenderer, module.getName(), xOffset + 5, yOffset + 5, color);

            count++;
            yOffset += 25;
            if (count % 10 == 0) { // Сдвигаем колонку каждые 10 модулей
                xOffset += 110;
                yOffset = 50;
            }
        }
    }

    // Мы также должны вернуть логику клика по модулям.
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<Module> modules = ModuleManager.getInstance().getModules();
        int yOffset = 50;
        int xOffset = 50;
        int count = 0;

        for (Module module : modules) {
            // Проверяем, попал ли клик в область кнопки модуля.
            if (mouseX >= xOffset && mouseX <= xOffset + 100 && mouseY >= yOffset && mouseY <= yOffset + 20) {
                module.toggle(); // Включаем/выключаем модуль.
                return true;
            }

            count++;
            yOffset += 25;
            if (count % 10 == 0) {
                xOffset += 110;
                yOffset = 50;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
