package dev.doctorm4id.admin.platform.fabric

//? fabric {

import com.mojang.blaze3d.platform.InputConstants
import dev.doctorm4id.admin.ImGuiScreen
import dev.doctorm4id.admin.Admin
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.lwjgl.sdl.SDLScancode

class FabricClientEntrypoint : ClientModInitializer {

	val CATEGORY = KeyMapping.Category.register(
		Identifier.fromNamespaceAndPath(Admin.MOD_ID, ".admin")
	)

	val openAdminGUI = KeyMappingHelper.registerKeyMapping(
		KeyMapping(
			"key.admin.openadmingui",
			InputConstants.Type.KEYBOARD,
			SDLScancode.SDL_SCANCODE_G,
			this.CATEGORY
		)
	)

	override fun onInitializeClient() {
		Admin.onInitializeClient()

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: Minecraft? ->
			while (this.openAdminGUI.consumeClick()) {
				if (client!!.player != null) {
					client.player!!.sendOverlayMessage(Component.literal("Opened TeamByte: The Backrooms Admin Panel"))
					Minecraft.getInstance().setScreenAndShow(ImGuiScreen())
				}
			}
		})
	}
}

//? }

