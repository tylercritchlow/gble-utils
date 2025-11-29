package com.gble

import com.gble.commands.ScaleCommand
import com.gble.config.ItemScalingConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback

object GbleutilsClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// Load configuration
		ItemScalingConfig.loadConfig()

		// Register the item scale command
		ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			ScaleCommand.register(dispatcher)
		}
	}
}
