package com.artempvp.client.ui;

import com.artempvp.client.module.Module;
import com.artempvp.client.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ArtemScreen extends Screen {
    private String selectedCategory = "HUD";
    private final String[] categories = {"HUD", "VISUAL", "PLAYER", "CLIENT"};

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Обычная темная заливка БЕЗ размытия/блюра
        context.fill(0, 0, this.width, this.height, 0x99000000);

        int mainX = this.width / 2 - 250;
        int mainY = this.height / 2 - 150;
        int mainWidth = 500;
        int mainHeight = 300;

        // Основное окно
        context.fill(mainX, mainY, mainX + mainWidth, mainY + mainHeight, 0xEE11121C);

        // Левая панель категорий
        context.fill(mainX, mainY, mainX + 110, mainY + mainHeight, 0xEE161724);

        // Заголовок
        context.drawTextWithShadow(this.textRenderer, "ARTEM", mainX + 15, mainY + 15, 0xFF7C5CFC);
        context.drawTextWithShadow(this.textRenderer, "DP  •  1.21.4", mainX + 60, mainY + 15, 0xFF6C6E7D);

        // Список категорий слева
        int catY = mainY + 55;
        for (String cat : categories) {
            boolean isSelected = cat.equals(selectedCategory);
            if (isSelected) {
                context.fill(mainX + 10, catY - 5, mainX + 100, catY + 20, 0xFF5C3BFC);
            }
            context.drawTextWithShadow(this.textRenderer, cat, mainX + 20, catY, isSelected ? 0xFFFFFFFF : 0xFF8A8C9E);
            catY += 35;
        }

        // Модули выбранной категории
        List<Module> modules = ModuleManager.getInstance().getModulesByCategory(selectedCategory);
        int modX = mainX + 125;
        int modY = mainY + 30;
        int col = 0;

        for (Module module : modules) {
            int currentX = modX + (col % 2) * 175;
            int currentY = modY + (col / 2) * 50;

            // Карточка модуля
            context.fill(currentX, currentY, currentX + 165, currentY + 42, 0xFF191A29);

            // Индикатор включения
            if (module.isEnabled()) {
                context.fill(currentX, currentY, currentX + 3, currentY + 42, 0xFF5C3BFC);
            }

            context.drawTextWithShadow(this.textRenderer, module.getName(), currentX + 12, currentY + 10, 0xFFFFFFFF);
            context.drawTextWithShadow(this.textRenderer, module.isEnabled() ? "ON" : "OFF", currentX + 12, currentY + 24, module.isEnabled() ? 0xFF55FF55 : 0xFF777777);

            col++;
        }

        // Подпись снизу
        context.drawTextWithShadow(this.textRenderer, "Click module • Esc close", mainX + 125, mainY + mainHeight - 20, 0xFF6C6E7D);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mainX = this.width / 2 - 250;
        int mainY = this.height / 2 - 150;

        // Клик по категориям
        int catY = mainY + 55;
        for (String cat : categories) {
            if (mouseX >= mainX + 10 && mouseX <= mainX + 100 && mouseY >= catY - 5 && mouseY <= catY + 20) {
                selectedCategory = cat;
                return true;
            }
            catY += 35;
        }

        // Клик по модулям
        List<Module> modules = ModuleManager.getInstance().getModulesByCategory(selectedCategory);
        int modX = mainX + 125;
        int modY = mainY + 30;
        int col = 0;

        for (Module module : modules) {
            int currentX = modX + (col % 2) * 175;
            int currentY = modY + (col / 2) * 50;

            if (mouseX >= currentX && mouseX <= currentX + 165 && mouseY >= currentY && mouseY <= currentY + 42) {
                module.toggle();
                return true;
            }
            col++;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
