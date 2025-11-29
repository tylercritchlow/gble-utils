package com.gble.hud

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.player.Player
import com.gble.config.GbleConfig

object HudRenderingEntrypoint : HudRenderCallback {
    private var currentTime = 0.0

    fun initialize() {
        HudRenderCallback.EVENT.register(this)
    }

    override fun onHudRender(context: GuiGraphics, tickCounter: DeltaTracker) {
        val minecraft = Minecraft.getInstance()
        val player = minecraft.player ?: return

        if (!GbleConfig.isHudEnabled()) return

        currentTime = net.minecraft.Util.getMillis() / 1000.0

        val color = 0x80404040.toInt()

        val window = minecraft.window
        val screenWidth = window.guiScaledWidth
        val screenHeight = window.guiScaledHeight

        val hotbarHeight = 20
        val itemSize = 16
        val padding = 1
        val itemsPerRow = 9
        val rectanglePadding = 4
        val rectangleWidth = itemsPerRow * itemSize + (itemsPerRow - 1) * padding + rectanglePadding * 2
        val rectangleHeight = hotbarHeight * 3
        val centerX = (screenWidth / 2) - (rectangleWidth / 2)
        val aboveHotbarY = screenHeight - 120

        context.fill(centerX, aboveHotbarY, centerX + rectangleWidth, aboveHotbarY + rectangleHeight, color)

        renderInventoryItems(context, player, centerX, aboveHotbarY)
    }

    private fun renderInventoryItems(context: GuiGraphics, player: Player, rectX: Int, rectY: Int) {
        val minecraft = Minecraft.getInstance()
        val inventory = player.inventory

        val itemSize = 16
        val padding = 1
        val itemsPerRow = 9
        val maxRows = 3
        val rectanglePadding = 4

        val startX = rectX + rectanglePadding
        val startY = rectY + rectanglePadding

        val hotbarSize = 9
        val mainInventoryStart = hotbarSize

        for (row in 0 until maxRows) {
            for (col in 0 until itemsPerRow) {
                val slotIndex = mainInventoryStart + (row * itemsPerRow) + col
                if (slotIndex >= inventory.containerSize) break

                val itemStack = inventory.getItem(slotIndex)
                val x = startX + col * (itemSize + padding)
                val y = startY + row * (itemSize + padding)

                if (!itemStack.isEmpty) {
                    context.renderItem(itemStack, x, y)

                    if (itemStack.count > 1) {
                        val countText = itemStack.count.toString()
                        context.drawString(
                            minecraft.font,
                            countText,
                            x + itemSize - minecraft.font.width(countText),
                            y + itemSize - minecraft.font.lineHeight + 1,
                            0xFFFFFF,
                            true
                        )
                    }
                }
            }
        }
    }
}