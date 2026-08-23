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

    private static boolean draggingWidget = false;
    private static int dragOffsetX = 0;
    private static int dragOffsetY = 0;

    public ArtemScreen() {
        super(Text.literal("ArtemPvP Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x80000000);

        int mainX = this.width / 2 - 260;
        int mainY = this.height / 2 - 160;
        int mainWidth = 520;
        int mainHeight = 320;

        context.fill(mainX, mainY, mainX + mainWidth, mainY + mainHeight, 0xEE11121C);
        context.fill(mainX, mainY, mainX + 110, mainY + mainHeight, 0xEE161724);

        context.drawTextWithShadow(this.textRenderer, "ARTEM", mainX + 15, mainY + 15, 0xFFA822FF);
        context.drawTextWithShadow(this.textRenderer, "PVP  •  1.21.4", mainX + 60, mainY + 15, 0xFF6C6E7D);

        int catY = mainY + 55;
        for (String cat : categories) {
            boolean isSelected = cat.equals(selectedCategory);
            if (isSelected) {
                context.fill(mainX + 10, catY - 5, mainX + 100, catY + 20, 0xFFA822FF);
            }
            context.drawTextWithShadow(this.textRenderer, cat, mainX + 20, catY, isSelected ? 0xFFFFFFFF : 0xFF8A8C9E);
            catY += 35;
        }

        List<Module> modules = ModuleManager.getInstance().getModulesByCategory(selectedCategory);
        int modX = mainX + 125;
        int modY = mainY + 30;
        int col = 0;

        for (Module module : modules) {
            int currentX = modX + (col % 2) * 190;
            int currentY = modY + (col / 2) * 45;

            context.fill(currentX, currentY, currentX + 180, currentY + 38, 0xFF191A29);
            if (module.isEnabled()) {
                context.fill(currentX, currentY, currentX + 4, currentY + 38, 0xFFA822FF);
            }

            context.drawTextWithShadow(this.textRenderer, module.getName(), currentX + 12, currentY + 8, 0xFFFFFFFF);
            context.drawTextWithShadow(this.textRenderer, module.isEnabled() ? "ON" : "OFF", currentX + 12, currentY + 22, module.isEnabled() ? 0xFF55FF55 : 0xFF777777);

            col++;
        }

        context.drawTextWithShadow(this.textRenderer, "Зажми ЛКМ на плашках HUD чтобы переместить их!", mainX + 125, mainY + mainHeight - 20, 0xFF6C6E7D);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mainX = this.width / 2 - 260;
        int mainY = this.height / 2 - 160;

        if (button == 0 && mouseX >= HudOverlay.keybindsX && mouseX <= HudOverlay.keybindsX + 110 &&
            mouseY >= HudOverlay.keybindsY && mouseY <= HudOverlay.keybindsY + 60) {
            draggingWidget = true;
            dragOffsetX = (int) mouseX - HudOverlay.keybindsX;
            dragOffsetY = (int) mouseY - HudOverlay.keybindsY;
            return true;
        }

        int catY = mainY + 55;
        for (String cat : categories) {
            if (mouseX >= mainX + 10 && mouseX <= mainX + 100 && mouseY >= catY - 5 && mouseY <= catY + 20) {
                selectedCategory = cat;
                return true;
            }
            catY += 35;
        }

        List<Module> modules = ModuleManager.getInstance().getModulesByCategory(selectedCategory);
        int modX = mainX + 125;
        int modY = mainY + 30;
        int col = 0;

        for (Module module : modules) {
            int currentX = modX + (col % 2) * 190;
            int currentY = modY + (col / 2) * 45;

            if (mouseX >= currentX && mouseX <= currentX + 180 && mouseY >= currentY && mouseY <= currentY + 38) {
                module.toggle();
                return true;
            }
            col++;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingWidget && button == 0) {
            HudOverlay.keybindsX = (int) mouseX - dragOffsetX;
            HudOverlay.keybindsY = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            draggingWidget = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
