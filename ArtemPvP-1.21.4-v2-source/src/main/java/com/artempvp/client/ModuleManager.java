package com.artempvp.client;

import com.artempvp.client.module.Module;
import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();

    private ModuleManager() {}

    public static void register(Module module) { MODULES.add(module); }
    public static List<Module> all() { return MODULES; }
}
