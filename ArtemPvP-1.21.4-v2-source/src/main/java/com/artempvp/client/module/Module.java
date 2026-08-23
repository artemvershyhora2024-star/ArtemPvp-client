package com.artempvp.client.module;

public abstract class Module {
    public enum Category { HUD, VISUAL, PLAYER, CLIENT }

    private final String name;
    private final Category category;
    private boolean enabled;

    protected Module(String name, Category category, boolean enabled) {
        this.name = name;
        this.category = category;
        this.enabled = enabled;
    }

    public String name() { return name; }
    public Category category() { return category; }
    public boolean enabled() { return enabled; }

    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean value) {
        if (enabled == value) return;
        enabled = value;
        if (enabled) onEnable();
        else onDisable();
    }

    protected void onEnable() {}
    protected void onDisable() {}
}
