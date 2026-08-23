package com.artempvp.client;

import com.artempvp.client.module.FullBrightModule;
import com.artempvp.client.module.Module;
import com.artempvp.client.module.SimpleModule;
import com.artempvp.client.ui.ArtemScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public final class ArtemPvPClient implements ClientModInitializer {
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private static final Map<String, Integer> cps = new HashMap<>();
    private static KeyBinding openGui;
    private static KeyBinding zoom;

    public static Module watermark, fps, coords, ping, cpsHud, armor, potions, keystrokes;
    public static Module speed, direction, time, server, crosshair, fullBright, zoomModule;
    public static Module noHurtCam, lowFire, itemInfo, scoreboardClean;

    @Override
    public void onInitializeClient() {
        registerModules();

        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.artempvp.open_gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "ArtemPvP"));
        zoom = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.artempvp.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "ArtemPvP"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGui.wasPressed()) client.setScreen(new ArtemScreen());
            if (MC.player != null && zoomModule.enabled()) {
                // Visual placeholder: a real FOV mixin can be added without changing the module API.
            }
            updateCps();
        });

        HudRenderCallback.EVENT.register((ctx, tickCounter) -> renderHud(ctx));
    }

    private void registerModules() {
        watermark = add("Watermark", Module.Category.HUD, true);
        fps = add("FPS", Module.Category.HUD, true);
        coords = add("Coordinates", Module.Category.HUD, true);
        ping = add("Ping", Module.Category.HUD, false);
        cpsHud = add("CPS", Module.Category.HUD, false);
        armor = add("Armor Status", Module.Category.HUD, true);
        potions = add("Potion Effects", Module.Category.HUD, false);
        keystrokes = add("Keystrokes", Module.Category.HUD, true);
        speed = add("Speed", Module.Category.HUD, false);
        direction = add("Direction", Module.Category.HUD, false);
        time = add("Time", Module.Category.HUD, false);
        server = add("Server Info", Module.Category.HUD, false);

        crosshair = add("Custom Crosshair", Module.Category.VISUAL, true);
        fullBright = new FullBrightModule(MC); ModuleManager.register(fullBright);
        zoomModule = add("Zoom", Module.Category.VISUAL, false);
        noHurtCam = add("No Hurt Camera", Module.Category.VISUAL, false);
        lowFire = add("Low Fire", Module.Category.VISUAL, false);
        itemInfo = add("Held Item Info", Module.Category.VISUAL, false);
        scoreboardClean = add("Clean Scoreboard", Module.Category.VISUAL, false);

        add("Toggle Sprint", Module.Category.PLAYER, false);
        add("Toggle Sneak", Module.Category.PLAYER, false);
        add("Auto Respawn", Module.Category.PLAYER, false);

        add("Notifications", Module.Category.CLIENT, true);
        add("Module List", Module.Category.CLIENT, false);
        add("Client Colors", Module.Category.CLIENT, true);
        add("Performance HUD", Module.Category.CLIENT, false);
    }

    private static Module add(String name, Module.Category category, boolean enabled) {
        Module m = new SimpleModule(name, category, enabled);
        ModuleManager.register(m);
        return m;
    }

    private static void updateCps() {
        if (MC.getWindow() == null) return;
        cps.put("LMB", mouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) ? 1 : 0);
        cps.put("RMB", mouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT) ? 1 : 0);
    }

    private static boolean mouseDown(int button) {
        return GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), button) == GLFW.GLFW_PRESS;
    }

    private static void renderHud(net.minecraft.client.gui.DrawContext ctx) {
        if (MC.player == null || MC.options.hudHidden) return;
        int x = 8, y = 8;

        if (watermark.enabled()) {
            box(ctx, x - 4, y - 4, 112, 22);
            text(ctx, "ARTEMPVP", x + 6, y + 3, 0xFF9A7BFF);
            y += 26;
        }
        if (fps.enabled()) { line(ctx, "FPS  " + MC.getCurrentFps(), x, y); y += 14; }
        if (coords.enabled()) {
            line(ctx, String.format("XYZ  %.1f  %.1f  %.1f", MC.player.getX(), MC.player.getY(), MC.player.getZ()), x, y); y += 14;
        }
        if (ping.enabled()) {
            var entry = MC.player.networkHandler.getPlayerListEntry(MC.player.getUuid());
            line(ctx, "PING  " + (entry == null ? "-" : entry.getLatency()) + " ms", x, y); y += 14;
        }
        if (speed.enabled()) {
            double s = Math.sqrt(MC.player.getVelocity().x * MC.player.getVelocity().x +
                    MC.player.getVelocity().z * MC.player.getVelocity().z) * 20.0;
            line(ctx, String.format("SPEED  %.2f m/s", s), x, y); y += 14;
        }
        if (direction.enabled()) { line(ctx, "DIR  " + MC.player.getHorizontalFacing().asString().toUpperCase(), x, y); y += 14; }
        if (time.enabled() && MC.world != null) {
            long t = (MC.world.getTimeOfDay() % 24000 + 6000) % 24000;
            int hour = (int)(t / 1000), minute = (int)((t % 1000) * 60 / 1000);
            line(ctx, String.format("TIME  %02d:%02d", hour, minute), x, y); y += 14;
        }
        if (server.enabled()) {
            String s = MC.getCurrentServerEntry() == null ? "Singleplayer" : MC.getCurrentServerEntry().address;
            line(ctx, "SERVER  " + s, x, y); y += 14;
        }

        if (armor.enabled()) renderArmor(ctx);
        if (keystrokes.enabled()) renderKeys(ctx);
        if (cpsHud.enabled()) renderCps(ctx);
        if (potions.enabled()) renderPotions(ctx);
        if (itemInfo.enabled() && MC.player.getMainHandStack() != null) {
            ItemStack stack = MC.player.getMainHandStack();
            line(ctx, stack.getName().getString() + " x" + stack.getCount(), 8, MC.getWindow().getScaledHeight() - 44);
        }
        if (crosshair.enabled()) renderCrosshair(ctx);
    }

    private static void renderArmor(net.minecraft.client.gui.DrawContext ctx) {
        int x = MC.getWindow().getScaledWidth() / 2 - 80;
        int y = MC.getWindow().getScaledHeight() - 52;
        for (int i = 0; i < 4; i++) {
            ItemStack stack = MC.player.getInventory().getArmorStack(3 - i);
            ctx.drawItem(stack, x + i * 20, y);
            if (!stack.isEmpty()) {
                int d = stack.getMaxDamage() - stack.getDamage();
                ctx.drawTextWithShadow(MC.textRenderer, String.valueOf(d), x + i * 20, y - 10, 0xFFFFFFFF);
            }
        }
    }

    private static void renderKeys(net.minecraft.client.gui.DrawContext ctx) {
        int x = MC.getWindow().getScaledWidth() - 86;
        int y = MC.getWindow().getScaledHeight() - 78;
        key(ctx, "W", x + 28, y, MC.options.forwardKey.isPressed());
        key(ctx, "A", x, y + 25, MC.options.leftKey.isPressed());
        key(ctx, "S", x + 28, y + 25, MC.options.backKey.isPressed());
        key(ctx, "D", x + 56, y + 25, MC.options.rightKey.isPressed());
        key(ctx, "L", x, y + 50, mouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT));
        key(ctx, "R", x + 56, y + 50, mouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT));
    }

    private static void renderCps(net.minecraft.client.gui.DrawContext ctx) {
        int x = MC.getWindow().getScaledWidth() - 180;
        int y = MC.getWindow().getScaledHeight() - 42;
        line(ctx, "LMB " + cps.getOrDefault("LMB", 0) + " CPS   RMB " + cps.getOrDefault("RMB", 0) + " CPS", x, y);
    }

    private static void renderPotions(net.minecraft.client.gui.DrawContext ctx) {
        int x = MC.getWindow().getScaledWidth() - 160;
        int y = 8;
        for (StatusEffectInstance effect : MC.player.getStatusEffects()) {
            String name = effect.getEffectType().value().getName().getString();
            line(ctx, name + "  " + effect.getDuration(), x, y);
            y += 14;
        }
    }

    private static void renderCrosshair(net.minecraft.client.gui.DrawContext ctx) {
        int cx = MC.getWindow().getScaledWidth() / 2;
        int cy = MC.getWindow().getScaledHeight() / 2;
        ctx.fill(cx - 5, cy, cx + 6, cy + 1, 0xFFFFFFFF);
        ctx.fill(cx, cy - 5, cx + 1, cy + 6, 0xFFFFFFFF);
    }

    private static void key(net.minecraft.client.gui.DrawContext ctx, String k, int x, int y, boolean pressed) {
        ctx.fill(x, y, x + 26, y + 22, pressed ? 0xFF7658E8 : 0xAA11131B);
        text(ctx, k, x + 9, y + 6, 0xFFFFFFFF);
    }

    private static void line(net.minecraft.client.gui.DrawContext ctx, String s, int x, int y) {
        ctx.drawTextWithShadow(MC.textRenderer, s, x, y, 0xFFE8EAF2);
    }

    private static void text(net.minecraft.client.gui.DrawContext ctx, String s, int x, int y, int color) {
        ctx.drawTextWithShadow(MC.textRenderer, s, x, y, color);
    }

    private static void box(net.minecraft.client.gui.DrawContext ctx, int x, int y, int w, int h) {
        ctx.fill(x, y, x + w, y + h, 0xB80B0D12);
        ctx.fill(x, y, x + 2, y + h, 0xFF7658E8);
    }

    public static void saveConfig() {
        // Config persistence is intentionally kept as the next upgrade.
    }
}
