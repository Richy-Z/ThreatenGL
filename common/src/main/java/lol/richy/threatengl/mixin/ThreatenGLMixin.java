package lol.richy.threatengl.mixin;

import lol.richy.threatengl.ThreatenGL;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// In Minecraft 26.1, window hints are no longer set in Window.<init>.
// They moved to GlBackend.setWindowHints() which is called during window creation.
// GLFW hint 139266 = GLFW_CONTEXT_VERSION_MAJOR, 139267 = GLFW_CONTEXT_VERSION_MINOR
import com.mojang.blaze3d.opengl.GlBackend;
import org.lwjgl.glfw.GLFW;

@Mixin(value = GlBackend.class, remap = false)
public class ThreatenGLMixin {
    private static final Logger LOGGER = ThreatenGL.LOGGER;

    @Redirect(method = "setWindowHints", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwWindowHint(II)V", remap = false))
    private void hintOverride(int hint, int value) {
        boolean isMacOS = System.getProperty("os.name").toLowerCase().contains("mac");

        if (hint == GLFW.GLFW_CONTEXT_VERSION_MAJOR) {
            value = 4;
            LOGGER.info("Threatened Minecraft to use GLFW_CONTEXT_VERSION_MAJOR {}", value);
        } else if (hint == GLFW.GLFW_CONTEXT_VERSION_MINOR) {
            // macOS dropped OpenGL support in 2013 in favor of Metal, caps at 4.1.
            if (isMacOS) LOGGER.info("Detected macOS - capping at OpenGL 4.1 since Apple deprecated OpenGL support.");
            value = isMacOS ? 1 : 6;
            LOGGER.info("Threatened Minecraft to use GLFW_CONTEXT_VERSION_MINOR {}", value);
        }

        GLFW.glfwWindowHint(hint, value);
    }
}