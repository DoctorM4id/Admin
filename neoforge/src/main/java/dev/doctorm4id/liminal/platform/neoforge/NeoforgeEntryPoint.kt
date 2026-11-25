package dev.doctorm4id.liminal.platform.neoforge

import dev.doctorm4id.liminal.Liminal
import dev.doctorm4id.m4id.M4id
import net.neoforged.fml.common.Mod
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_CONTEXT
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

@Mod(M4id.MOD_ID)
class NeoforgeEntryPoint {

	init {
		Liminal.onInitialize()

		runForDist(
			clientTarget = {
				// Register mod event bus
				MOD_CONTEXT.getKEventBus().register(NeoforgeClientEventSubscriber::class.java)
			},
			serverTarget = { }
		)
	}
}
