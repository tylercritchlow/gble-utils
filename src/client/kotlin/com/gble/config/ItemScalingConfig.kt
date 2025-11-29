package com.gble.config

import com.google.gson.GsonBuilder
import com.google.gson.Gson
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object ItemScalingConfig {
    // Default scale value for held items
    private var heldItemScale: Float = 1.0f
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val configFile: File = File(FabricLoader.getInstance().configDir.toFile(), "gble-utils-item-scale.json")

    /**
     * Get the configured scale for held items
     * @return scale value (1.0 = normal size, 0.5 = half size, 2.0 = double size)
     */
    fun getHeldItemScale(): Float {
        return heldItemScale
    }

    /**
     * Set the scale for held items
     * @param scale new scale value (recommended range: 0.5-2.0)
     */
    fun setHeldItemScale(scale: Float) {
        // Clamp the scale to a reasonable range to prevent issues
        heldItemScale = scale.coerceIn(0.1f, 5.0f)
        saveConfig()
    }

    /**
     * Reset the scale to default value
     */
    fun resetScale() {
        heldItemScale = 1.0f
        saveConfig()
    }

    /**
     * Load configuration from file
     */
    fun loadConfig() {
        try {
            if (configFile.exists()) {
                FileReader(configFile).use { reader ->
                    val configData = gson.fromJson(reader, ConfigData::class.java)
                    heldItemScale = configData?.heldItemScale ?: 1.0f
                }
            }
        } catch (e: Exception) {
            // If loading fails, keep the default value
            heldItemScale = 1.0f
        }
    }

    /**
     * Save configuration to file
     */
    private fun saveConfig() {
        try {
            configFile.parentFile.mkdirs()
            FileWriter(configFile).use { writer ->
                val configData = ConfigData(heldItemScale)
                gson.toJson(configData, writer)
            }
        } catch (e: Exception) {
            // If saving fails, continue without crashing
        }
    }

    /**
     * Data class for JSON serialization
     */
    private data class ConfigData(
        val heldItemScale: Float = 1.0f
    )
}