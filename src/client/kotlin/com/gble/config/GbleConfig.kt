package com.gble.config

import com.google.gson.GsonBuilder
import com.google.gson.Gson
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object GbleConfig {
    private var heldItemScale: Float = 1.0f
    private var hudEnabled: Boolean = true
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile: File = File(FabricLoader.getInstance().configDir.toFile(), "gble-utils.json")

    fun getHeldItemScale(): Float {
        return heldItemScale
    }

    fun setHeldItemScale(scale: Float) {
        heldItemScale = scale.coerceIn(0.1f, 5.0f)
        saveConfig()
    }

    fun resetScale() {
        heldItemScale = 1.0f
        saveConfig()
    }

    fun isHudEnabled(): Boolean {
        return hudEnabled
    }

    fun setHudEnabled(enabled: Boolean) {
        hudEnabled = enabled
        saveConfig()
    }

    fun toggleHud(): Boolean {
        hudEnabled = !hudEnabled
        saveConfig()
        return hudEnabled
    }

    fun loadConfig() {
        try {
            if (configFile.exists()) {
                FileReader(configFile).use { reader ->
                    val configData = gson.fromJson(reader, ConfigData::class.java)
                    heldItemScale = configData?.heldItemScale ?: 1.0f
                    hudEnabled = configData?.hudEnabled ?: true
                }
            }
        } catch (e: Exception) {
            heldItemScale = 1.0f
            hudEnabled = true
        }
    }

    private fun saveConfig() {
        try {
            configFile.parentFile.mkdirs()
            FileWriter(configFile).use { writer ->
                val configData = ConfigData(heldItemScale, hudEnabled)
                gson.toJson(configData, writer)
            }
        } catch (e: Exception) {
        }
    }

    private data class ConfigData(
        val heldItemScale: Float = 1.0f,
        val hudEnabled: Boolean = true
    )
}