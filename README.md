# MaxGL

Forces Minecraft to use OpenGL 4.6 instead of the default 3.3 cap.

Fork of [ThreatenGL](https://github.com/Richy-Z/ThreatenGL) by Richy Z., updated for Minecraft 26.1.

## Why This Exists
ThreatenGL stopped working on 26.1 because Mojang moved the `glfwWindowHint` 
calls from `Window` to `GlBackend.setWindowHints()`. The old Mixin target no 
longer existed so it was silently doing nothing. Also dropped Yarn mappings 
since they don't exist for 26.1.

## Requirements
- Minecraft 26.1
- Fabric Loader 0.18.5+
- Java 25+

## Why Would You Need This
Minecraft 26.1 caps OpenGL at 3.3 by default. Some mods require OpenGL 4.x 
features like compute shaders. This mod removes that cap.

## License
LGPL-3.0 — same as the original ThreatenGL.