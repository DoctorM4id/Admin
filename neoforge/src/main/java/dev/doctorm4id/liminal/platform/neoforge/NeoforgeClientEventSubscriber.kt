package dev.doctorm4id.liminal.platform.neoforge

import dev.doctorm4id.liminal.Liminal
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent

object NeoforgeClientEventSubscriber {

	@SubscribeEvent
	@JvmStatic
	fun onClientSetup(event: FMLClientSetupEvent) {
		Liminal.onInitializeClient()
	}
}

