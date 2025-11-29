package com.gble.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component

object InventoryHudCommand {
    private var isEnabled = true

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(ClientCommandManager.literal("inventoryhud")
            .executes { context ->
                val currentState = if (isEnabled) "enabled" else "disabled"
                context.source.sendFeedback(Component.literal("Inventory HUD is currently $currentState"))
                1
            }
            .then(ClientCommandManager.literal("enable")
                .executes { context ->
                    isEnabled = true
                    context.source.sendFeedback(Component.literal("Inventory HUD enabled"))
                    1
                }
            )
            .then(ClientCommandManager.literal("disable")
                .executes { context ->
                    isEnabled = false
                    context.source.sendFeedback(Component.literal("Inventory HUD disabled"))
                    1
                }
            )
            .then(ClientCommandManager.literal("toggle")
                .executes { context ->
                    isEnabled = !isEnabled
                    val newState = if (isEnabled) "enabled" else "disabled"
                    context.source.sendFeedback(Component.literal("Inventory HUD $newState"))
                    1
                }
            )
        )
    }

    fun isHudEnabled(): Boolean = isEnabled
}