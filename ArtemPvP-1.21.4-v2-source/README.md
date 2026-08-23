# ArtemPvP 2.0 — Minecraft 1.21.4

Современный клиентский Fabric-мод с упором на визуалы, HUD и QoL.

## Вдохновение по набору функций

Набор сделан по актуальным публичным клиентским/визуальным модам 1.21.4:
- HUD: FPS, CPS, ping, armor, potions, keystrokes, coordinates, speed, direction, time, server info.
- Visual: custom crosshair, FullBright, zoom-заготовка, no hurt camera, low fire, held item info.
- Player/QoL: toggle sprint, toggle sneak, auto respawn.
- Client: notifications, module list, theme/client colors, performance HUD.
- GUI: категории HUD / VISUAL / PLAYER / CLIENT.

## Управление

- Right Shift — ClickGUI
- C — подготовленная клавиша Zoom

## Сборка

Требуется JDK 21.

Windows:
`gradlew.bat build`

Linux/macOS:
`./gradlew build`

Jar будет в `build/libs/`.

## Важно

Это client-side мод. Некоторые продвинутые визуалы вроде настоящего freelook, smooth zoom, low-fire и view-model требуют mixin-слоя; архитектура уже разделяет модули, поэтому их можно добавлять без переделки ClickGUI.
