package org.sayandevelopment.bukkit.utils

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.sayandevelopment.bukkit.plugin

object AdventureUtils {

    @JvmStatic
    val audience = BukkitAudiences.create(plugin)

    @JvmStatic
    val miniMessage = MiniMessage.miniMessage()

    @JvmStatic
    fun sendMessage(player: Player, message: Component) {
        audience.player(player).sendMessage(message)
    }

    @JvmStatic
    fun sendMessage(sender: CommandSender, message: Component) {
        audience.sender(sender).sendMessage(message)
    }

    fun Player.sendMessage(message: Component) {
        sendMessage(this, message)
    }

    fun CommandSender.sendMessage(message: Component) {
        sendMessage(this, message)
    }

    @JvmStatic
    fun toComponent(content: String, vararg placeholder: TagResolver): Component {
        return miniMessage.deserialize(content, *placeholder)
    }

    fun String.component(vararg placeholder: TagResolver): Component {
        return toComponent(this, *placeholder)
    }
}