package com.artempvp.client.ui;

import com.artempvp.client.ArtemPvPClient;
import com.artempvp.client.ModuleManager;
import com.artempvp.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ArtemScreen extends Screen {
    private final Module.Category[] cats = Module.Category.values();
    private int selected = 0;
    private int panelX, panelY;

    public ArtemScreen() {
        super(Text.literal("ArtemPvP"));
    }

    @Override
    protected void init() {
        panelX = (width - 760) / 2;
        panelY = (height - 440) / 2;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
      context.fill(0, 0, this.width, this.height, 0x99000000);
        int w = 760, h = 440;
        ctx.fill(panelX, panelY, panelX + w, panelY + h, 0xF00B0D12);
        ctx.fill(panelX, panelY, panelX + 190, panelY + h, 0xFF10121B);
        ctx.fill(panelX + 190, panelY, panelX + w, panelY + 58, 0xFF141722);

        ctx.drawTextWithShadow(textRenderer, "ARTEMPVP", panelX + 24, panelY + 20, 0xFF8B6CFF);
        ctx.drawTextWithShadow(textRenderer, "2.0  •  1.21.4", panelX + 105, panelY + 20, 0xFF777B8A);

        int cy = panelY + 80;
        for (int i = 0; i < cats.length; i++) {
            int color = i == selected ? 0xFF6D54D9 : 0x00101010;
            ctx.fill(panelX + 14, cy - 8, panelX + 176, cy + 28, color);
            ctx.drawTextWithShadow(textRenderer, cats[i].name(), panelX + 28, cy + 4,
                    i == selected ? 0xFFFFFFFF : 0xFF9EA2B0);
            cy += 44;
        }

        int x = panelX + 220;
        int y = panelY + 82;
        int count = 0;
        for (Module module : ModuleManager.all()) {
            if (module.category() != cats[selected]) continue;

            int bx = x + (count % 2) * 245;
            int by = y + (count / 2) * 76;
            int accent = module.enabled() ? 0xFF7658E8 : 0xFF262A37;

            ctx.fill(bx, by, bx + 225, by + 58, 0xFF191C27);
            ctx.fill(bx, by, bx + 4, by + 58, accent);
            ctx.drawTextWithShadow(textRenderer, module.name(), bx + 16, by + 12, 0xFFFFFFFF);
            ctx.drawTextWithShadow(textRenderer, module.enabled() ? "ON" : "OFF",
                    bx + 16, by + 32, module.enabled() ? 0xFFB8FFA8 : 0xFF7F8494);
            count++;
        }

        ctx.drawTextWithShadow(textRenderer, "Click module • Esc close",
                panelX + 220, panelY + h - 28, 0xFF6F7380);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button != 0) return super.mouseClicked(mx, my, button);

        int cy = panelY + 72;
        for (int i = 0; i < cats.length; i++) {
            if (mx >= panelX + 14 && mx <= panelX + 176 && my >= cy && my <= cy + 40) {
                selected = i;
                return true;
            }
            cy += 44;
        }

        int x = panelX + 220, y = panelY + 82, count = 0;
        for (Module module : ModuleManager.all()) {
            if (module.category() != cats[selected]) continue;
            int bx = x + (count % 2) * 245;
            int by = y + (count / 2) * 76;
            if (mx >= bx && mx <= bx + 225 && my >= by && my <= by + 58) {
                module.toggle();
                ArtemPvPClient.saveConfig();
                return true;
            }
            count++;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
