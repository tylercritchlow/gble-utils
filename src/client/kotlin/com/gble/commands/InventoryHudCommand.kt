package com.gble.commands

import com.gble.config.GbleConfig
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component

object InventoryHudCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(ClientCommandManager.literal("inventoryhud")
            .executes { context ->
                val currentState = if (GbleConfig.isHudEnabled()) "enabled" else "disabled"
                context.source.sendFeedback(Component.literal("Inventory HUD is currently $currentState"))
                1
            }
            .then(ClientCommandManager.literal("enable")
                .executes { context ->
                    GbleConfig.setHudEnabled(true)
                    context.source.sendFeedback(Component.literal("Inventory HUD enabled"))
                    1
                }
            )
            .then(ClientCommandManager.literal("disable")
                .executes { context ->
                    GbleConfig.setHudEnabled(false)
                    context.source.sendFeedback(Component.literal("Inventory HUD disabled"))
                    1
                }
            )
            .then(ClientCommandManager.literal("toggle")
                .executes { context ->
                    val newState = GbleConfig.toggleHud()
                    val stateText = if (newState) "enabled" else "disabled"
                    context.source.sendFeedback(Component.literal("Inventory HUD $stateText"))
                    1
                }
            )
        )
    }

    fun isHudEnabled(): Boolean = GbleConfig.isHudEnabled()
}