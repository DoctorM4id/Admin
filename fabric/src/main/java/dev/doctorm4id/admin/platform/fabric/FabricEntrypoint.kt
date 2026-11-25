package dev.doctorm4id.admin.platform.fabric

//? fabric {

import dev.doctorm4id.admin.Admin
import net.fabricmc.api.ModInitializer

class FabricEntrypoint : ModInitializer {

	override fun onInitialize() {
		Admin.onInitialize()
		FabricEventSubscriber().registerEvents()
	}
}

//? }
