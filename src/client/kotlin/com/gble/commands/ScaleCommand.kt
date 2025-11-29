package com.gble.commands

import com.gble.config.ItemScalingConfig
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.FloatArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component

object ScaleCommand {
    
    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(ClientCommandManager.literal("itemscale")
            .executes { context ->
                val currentScale = ItemScalingConfig.getHeldItemScale()
                context.source.sendFeedback(Component.literal("Current item scale: ${String.format("%.2f", currentScale)}"))
                1
            }
            .then(ClientCommandManager.argument("scale", FloatArgumentType.floatArg(0.1f, 5.0f))
                .executes { context ->
                    val scale = FloatArgumentType.getFloat(context, "scale")
                    ItemScalingConfig.setHeldItemScale(scale)
                    context.source.sendFeedback(Component.literal("Set item scale to ${String.format("%.2f", scale)}"))
                    1
                }
            )
            .then(ClientCommandManager.literal("reset")
                .executes { context ->
                    ItemScalingConfig.resetScale()
                    context.source.sendFeedback(Component.literal("Reset item scale to default (1.0)"))
                    1
                }
            )
        )
    }
}