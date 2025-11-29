package com.gble

import com.gble.commands.ScaleCommand
import com.gble.commands.InventoryHudCommand
import com.gble.config.ItemScalingConfig
import com.gble.hud.HudRenderingEntrypoint
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

object GbleutilsClient : ClientModInitializer {
	override fun onInitializeClient() {
		// Load configuration
		ItemScalingConfig.loadConfig()

		// Register commands
		ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			ScaleCommand.register(dispatcher)
			InventoryHudCommand.register(dispatcher)
		}

		// Initialize HUD rendering
		HudRenderingEntrypoint.initialize()
	}
}
