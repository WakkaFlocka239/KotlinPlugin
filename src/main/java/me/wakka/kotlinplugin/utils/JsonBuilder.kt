@file:Suppress("MemberVisibilityCanBePrivate")

package me.wakka.kotlinplugin.utils

import me.wakka.kotlinplugin.utils.StringUtils.colorize
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.md_5.bungee.api.ChatColor
import org.jetbrains.annotations.Contract
import java.awt.Color
import java.net.URL
import java.util.function.Consumer

@Suppress("unused", "DEPRECATION", "FunctionName")
class JsonBuilder : ComponentLike {
    /**
     * Creates a new builder with its color set
     */
    constructor(color: TextColor?) {
        color(color)
    }

    /**
     * Creates a new builder with its color set
     */
    constructor(color: ChatColor?) {
        color(color)
    }

    /**
     * Creates a new builder with its color set
     */
    constructor(color: Color?) {
        color(color)
    }

    /**
     * Creates a new builder with its color set
     */
    constructor(color: org.bukkit.Color?) {
        color(color)
    }

    /**
     * Creates a new builder with its color and text decorations set
     */
    constructor(color: TextColor?, vararg decorations: TextDecoration?) {
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its color and text decorations set
     */
    constructor(color: ChatColor?, vararg decorations: TextDecoration?) {
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its color and text decorations set
     */
    constructor(color: Color?, vararg decorations: TextDecoration?) {
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its color and text decorations set
     */
    constructor(color: org.bukkit.Color?, vararg decorations: TextDecoration?) {
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its text decorations set
     */
    constructor(vararg decorations: TextDecoration?) {
        decorate(*decorations)
    }

    /**
     * Converts the input text to a component and appends it to the internal builder
     *
     * Note: this does not apply any text, color, or formatting changes to the builder itself
     */
    constructor(formattedText: String?) {
        next(formattedText)
    }

    /**
     * Converts the input to a component and appends it to the internal builder. Also copies [.loreize] and [.initialized].
     *
     * Note: this does not apply any text, color, or formatting changes to the builder itself
     */
    constructor(json: JsonBuilder) {
        next(json)
        loreize = json.loreize
        initialized = json.initialized
    }

    /**
     * Converts the input to a component and appends it to the internal builder
     *
     * Note: this does not apply any text, color, or formatting changes to the builder itself
     */
    constructor(component: ComponentLike?) {
        next(component)
    }

    /**
     * Creates a new builder with its raw text set and the provided color applied
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, vararg decorations: TextDecoration?) {
        content(rawText)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its raw text set and the provided color applied
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: TextColor?) {
        content(rawText)
        color(color)
    }

    /**
     * Creates a new builder with its raw text set and the provided color applied
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: ChatColor?) {
        content(rawText)
        color(color)
    }

    /**
     * Creates a new builder with its raw text set and the provided color applied
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: Color?) {
        content(rawText)
        color(color)
    }

    /**
     * Creates a new builder with its raw text set and the provided color applied
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: org.bukkit.Color?) {
        content(rawText)
        color(color)
    }

    /**
     * Creates a new builder with its raw text set, provided color applied, and text decorations set
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: TextColor?, vararg decorations: TextDecoration?) {
        content(rawText)
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its raw text set, provided color applied, and text decorations set
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: ChatColor?, vararg decorations: TextDecoration?) {
        content(rawText)
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its raw text set, provided color applied, and text decorations set
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: Color?, vararg decorations: TextDecoration?) {
        content(rawText)
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its raw text set, provided color applied, and text decorations set
     * @param rawText raw text, meaning formatting codes are ignored
     */
    constructor(rawText: String, color: org.bukkit.Color?, vararg decorations: TextDecoration?) {
        content(rawText)
        color(color)
        decorate(*decorations)
    }

    /**
     * Creates a new builder with its raw text set and provided style applied
     * @param rawText raw text, meaning formatting codes are ignored
     * @param style style that overwrites saved colors and text decorations
     */
    constructor(rawText: String, style: Style) {
        content(rawText)
        style(style)
    }

    /**
     * Creates a new builder with a prefix at the start and the color set to DARK_AQUA.
     * @param prefix prefix text
     * @param contents component to append after the prefix, or null for nothing
     */
    private constructor(prefix: String, contents: ComponentLike?) {
        color(NamedTextColor.DARK_AQUA)
            .next("&8&l[")
            .next(prefix, NamedTextColor.YELLOW)
            .next("&8&l]")
            .next(" ")
            .next(contents)
    }


    private var builder: TextComponent.Builder = Component.text()
    private val result: TextComponent.Builder = Component.text()

    private var lore: MutableList<String> = mutableListOf()
    private var loreize = true

    /**
     * Helper boolean that does not affect anything within the builder itself.
     * Can be used by external methods to determine if the builder has been setup.
     * To set this boolean to true, use [.initialize]
     */
    private var initialized = false


    /**
     * Creates a new builder with a prefix.
     * @param prefix prefix text
     * @return a new builder
     */
    @Contract("_ -> new")
    fun fromPrefix(prefix: String): JsonBuilder {
        return fromPrefix(prefix, null as ComponentLike?)
    }

    /**
     * Creates a new builder with a prefix.
     * @param prefix prefix text
     * @param contents contents to append to the prefix
     * @return a new builder
     */
    @Contract("_, _ -> new")
    fun fromPrefix(prefix: String, contents: ComponentLike?): JsonBuilder {
        return JsonBuilder(prefix, contents)
    }

    /**
     * Creates a new builder with a prefix. Converts the input text to a component and appends it to the internal builder
     *
     *
     * Note: this does not apply any text, color, or formatting changes to the builder itself
     * @param prefix prefix text
     * @param contents contents to append to the prefix
     * @return a new builder
     */
    @Contract("_, _ -> new")
    fun fromPrefix(prefix: String, contents: String?): JsonBuilder {
        return JsonBuilder(prefix, JsonBuilder(contents))
    }

    /**
     * Creates a new builder formatted as an error.
     * @param prefix prefix text
     * @param error error to display
     * @return a new builder
     */
    @Contract("_, _ -> new")
    fun fromError(prefix: String, error: ComponentLike): JsonBuilder {
        return JsonBuilder(prefix, error).color(NamedTextColor.RED)
    }

    /**
     * Creates a new builder formatted as an error.
     * @param prefix prefix text
     * @param error error to display
     * @return a new builder
     */
    @Contract("_, _ -> new")
    fun fromError(prefix: String, error: String): JsonBuilder {
        return JsonBuilder(prefix, Component.text(error)).color(NamedTextColor.RED)
    }

    /**
     * Converts the input text to a colored and formatted component and appends it to the internal builder
     * @param formattedText text formatted with ampersands or section symbols
     * @return this builder
     */
    @Contract("_ -> this")
    fun next(formattedText: String?): JsonBuilder {
        if (formattedText != null) builder.append(AdventureUtils.fromLegacyText(colorize(formattedText)))
        return this
    }

    /**
     * Appends text to the internal builder.
     * Ampersands and section symbols will **not** be parsed.
     * @param text text formatted with ampersands or section symbols
     * @return this builder
     */
    @Contract("_ -> this")
    fun rawNext(text: String?): JsonBuilder {
        if (text != null) builder.append(Component.text(text))
        return this
    }

    /**
     * Creates a component with its text and color set and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun next(rawText: String?, color: TextColor?): JsonBuilder {
        if (rawText != null) builder.append(Component.text(rawText, color))
        return this
    }

    /**
     * Creates a component with its text and color set and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun next(rawText: String?, color: ChatColor?): JsonBuilder {
        return next(rawText, AdventureUtils.textColorOf(color))
    }

    /**
     * Creates a component with its text and color set and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun next(rawText: String?, color: Color?): JsonBuilder {
        return next(rawText, AdventureUtils.textColorOf(color))
    }

    /**
     * Creates a component with its text and color set and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun next(rawText: String?, color: org.bukkit.Color?): JsonBuilder {
        return next(rawText, AdventureUtils.textColorOf(color))
    }

    /**
     * Creates a component with its text, color, and decorations set, and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _, _ -> this")
    fun next(rawText: String?, color: TextColor?, vararg decorations: TextDecoration?): JsonBuilder {
        if (rawText != null) builder.append(Component.text(rawText, color, *decorations))
        return this
    }

    /**
     * Creates a component with its text, color, and decorations set, and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
//    @Contract("_, _, _ -> this")
//    fun next(rawText: String?, color: ChatColor?, vararg decorations: TextDecoration?): JsonBuilder {
//        return next(rawText, AdventureUtils.textColorOf(color), decorations)
//    }

    /**
     * Creates a component with its text, color, and decorations set, and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
//    @Contract("_, _, _ -> this")
//    fun next(rawText: String?, color: Color?, vararg decorations: TextDecoration?): JsonBuilder {
//        return next(rawText, AdventureUtils.textColorOf(color), decorations)
//    }

    /**
     * Creates a component with its text, color, and decorations set, and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
//    @Contract("_, _, _ -> this")
//    fun next(rawText: String?, color: org.bukkit.Color?, vararg decorations: TextDecoration?): JsonBuilder {
//        return next(rawText, AdventureUtils.textColorOf(color), decorations)
//    }

    /**
     * Creates a component with its text, color, and decorations set, and appends it to the internal builder
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun next(rawText: String?, vararg decorations: TextDecoration?): JsonBuilder {
        if (rawText != null) builder.append(JsonBuilder(rawText, *decorations))
        return this
    }

    /**
     * Sets the raw text for the base text component
     * @param rawText raw text, color codes are ignored
     * @return this builder
     */
    @Contract("_ -> this")
    fun content(rawText: String): JsonBuilder {
        builder.content(rawText)
        return this
    }

    /**
     * Appends a component to the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun next(component: ComponentLike?): JsonBuilder {
        if (component != null) builder.append(component)
        return this
    }

    fun group(): JsonBuilder {
        if (lore.isNotEmpty()) {
            val lines: MutableList<String> = ArrayList()

            lore.forEach(Consumer { line: String? ->
                if (line != null) {
                    colorize(line).let { lines.add(it) }
                }
            })

            val hover = Component.text()
            val iterator: Iterator<String> = lines.iterator()

            while (iterator.hasNext()) {
                hover.append(AdventureUtils.fromLegacyText(iterator.next()))
                if (iterator.hasNext()) hover.append(Component.newline())
            }

            builder.hoverEvent(hover.build().asHoverEvent())
            lore.clear()
        }

        result.append(builder)
        builder = Component.text()
        return this
    }

    /**
     * Adds a new line and creates a new [.group]
     * @return this builder
     */
    @Contract("-> this")
    fun newline(): JsonBuilder {
        return newline(false)
    }

    /**
     * Adds a new line and optionally creates a new [.group]
     * @return this builder
     */
    @Contract("_ -> this")
    fun newline(newGroup: Boolean): JsonBuilder {
        builder.append(Component.text(System.lineSeparator()))
        if (newGroup) group()
        return this
    }

    /**
     * Creates an empty line (2x [.newline])
     */
    @Contract("-> this")
    fun line(): JsonBuilder {
        newline()
        newline()
        return this
    }

    /**
     * Sets the color for the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun color(color: TextColor?): JsonBuilder {
        builder.color(color)
        return this
    }

    /**
     * Sets the color for the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun color(color: ChatColor?): JsonBuilder {
        return color(AdventureUtils.textColorOf(color))
    }

    /**
     * Sets the color for the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun color(color: Color?): JsonBuilder {
        return color(AdventureUtils.textColorOf(color))
    }

    /**
     * Sets the color for the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun color(color: org.bukkit.Color?): JsonBuilder {
        return color(AdventureUtils.textColorOf(color))
    }

    /**
     * Parses a hexadecimal number
     * @param color number in the format "#FFFFFF" (# optional)
     * @throws IllegalArgumentException string contained an invalid hexadecimal number
     * @return this builder
     */
    @Contract("_ -> this")
    @Throws(IllegalArgumentException::class)
    fun color(color: String?): JsonBuilder {
        return color(AdventureUtils.textColorOf(color))
    }

    /**
     * Enables the provided text decorations
     * @param decorations varargs list of decorations
     * @return this builder
     */
    @Contract("_ -> this")
    fun decorate(vararg decorations: TextDecoration?): JsonBuilder {
        return decorate(true, *decorations)
    }

    /**
     * Sets the state of text decorations
     * @param state whether to enable or disable the decorations
     * @param decorations collection of decorations
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun decorate(state: Boolean, decorations: Collection<TextDecoration>): JsonBuilder {
        return decorate(TextDecoration.State.byBoolean(state), HashSet(decorations))
    }

    /**
     * Sets the state of text decorations
     * @param state whether to enable or disable the decorations
     * @param decorations varargs list of decorations
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun decorate(state: Boolean, vararg decorations: TextDecoration?): JsonBuilder {
        return decorate(TextDecoration.State.byBoolean(state), HashSet(listOf(*decorations)))
    }

    /**
     * Sets the state of text decorations
     * @param state state to set the decorations to
     * @param decorations varargs list of decorations
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun decorate(state: TextDecoration.State, vararg decorations: TextDecoration?): JsonBuilder {
        return decorate(state, listOf(*decorations))
    }

    /**
     * Sets the state of text decorations
     * @param state state to set the decorations to
     * @param decorations collection of decorations
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun decorate(state: TextDecoration.State, decorations: Collection<TextDecoration?>): JsonBuilder {
        decorations.forEach(Consumer { decoration: TextDecoration? -> builder.decoration(decoration!!, state) })
        return this
    }

    /**
     * Sets the state of text decorations
     * @param decorations map of decorations to states
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun decorate(decorations: Map<TextDecoration?, TextDecoration.State?>): JsonBuilder {
        decorations.forEach { (decoration: TextDecoration?, state: TextDecoration.State?) ->
            builder.decoration(
                decoration!!,
                state!!
            )
        }
        return this
    }

    /**
     * Enables bold on the internal builder
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun bold(): JsonBuilder {
        return bold(true)
    }

    /**
     * Sets the state of bold on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun bold(state: Boolean): JsonBuilder {
        return bold(TextDecoration.State.byBoolean(state))
    }

    /**
     * Sets the state of bold on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun bold(state: TextDecoration.State): JsonBuilder {
        return decorate(state, TextDecoration.BOLD)
    }

    /**
     * Enables italicization on the internal builder
     * @return this builder
     */
    @Contract("-> this")
    fun italic(): JsonBuilder {
        return italic(true)
    }

    /**
     * Sets the state of italicization on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun italic(state: Boolean): JsonBuilder {
        return italic(TextDecoration.State.byBoolean(state))
    }

    /**
     * Sets the state of italicization on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun italic(state: TextDecoration.State): JsonBuilder {
        return decorate(state, TextDecoration.ITALIC)
    }

    /**
     * Enables strikethrough on the internal builder
     * @return this builder
     */
    @Contract("-> this")
    fun strikethrough(): JsonBuilder {
        return strikethrough(true)
    }

    /**
     * Sets the state of strikethrough on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun strikethrough(state: Boolean): JsonBuilder {
        return strikethrough(TextDecoration.State.byBoolean(state))
    }

    /**
     * Sets the state of strikethrough on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun strikethrough(state: TextDecoration.State): JsonBuilder {
        return decorate(state, TextDecoration.STRIKETHROUGH)
    }

    /**
     * Enables underlines on the internal builder
     * @return this builder
     */
    @Contract("-> this")
    fun underline(): JsonBuilder {
        return underline(true)
    }

    /**
     * Sets the state of underlines on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun underline(state: Boolean): JsonBuilder {
        return underline(TextDecoration.State.byBoolean(state))
    }

    /**
     * Sets the state of underlines on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun underline(state: TextDecoration.State): JsonBuilder {
        return decorate(state, TextDecoration.UNDERLINED)
    }

    /**
     * Enables obfuscation (random gibberish text) on the internal builder
     * @return this builder
     */
    @Contract("-> this")
    fun obfuscate(): JsonBuilder {
        return obfuscate(true)
    }

    /**
     * Sets the state of obfuscation (random gibberish text) on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun obfuscate(state: Boolean): JsonBuilder {
        return obfuscate(TextDecoration.State.byBoolean(state))
    }

    /**
     * Sets the state of obfuscation (random gibberish text) on the internal builder
     * @return this builder
     */
    @Contract("_ -> this")
    fun obfuscate(state: TextDecoration.State): JsonBuilder {
        return decorate(state, TextDecoration.OBFUSCATED)
    }

    /**
     * Sets the style of the internal builder, replacing existing colors and decorations
     * @return this builder
     */
    @Contract("_ -> this")
    fun style(style: Style): JsonBuilder {
        builder.style(style)
        return this
    }

    /**
     * Sets the style of the internal builder, replacing existing colors and decorations
     * @return this builder
     */
    @Contract("_ -> this")
    fun style(style: Consumer<Style.Builder?>): JsonBuilder {
        builder.style(style)
        return this
    }

    /**
     * Adds an action to run on click
     * @return this builder
     */
    @Contract("_ -> this")
    fun clickEvent(clickEvent: ClickEvent?): JsonBuilder {
        builder.clickEvent(clickEvent)
        return this
    }

    /**
     * Prompts the player to open a URL when clicked
     * @return this builder
     */
    @Contract("_ -> this")
    fun url(url: String): JsonBuilder {
        return clickEvent(ClickEvent.openUrl(url))
    }

    /**
     * Prompts the player to open a URL when clicked
     * @return this builder
     */
    @Contract("_ -> this")
    fun url(url: URL): JsonBuilder {
        return clickEvent(ClickEvent.openUrl(url))
    }

    /**
     * Makes the player run a command when clicked
     * @param command a command, forward slash not required
     * @return this builder
     */
    @Contract("_ -> this")
    fun command(command: String): JsonBuilder {
        var command = command
        if (!command.startsWith("/")) command = "/$command"
        return clickEvent(ClickEvent.runCommand(command))
    }

    /**
     * Suggests a command to a player on click by typing it into their chat window
     * @param text some text, usually a command
     * @return this builder
     */
    @Contract("_ -> this")
    fun suggest(text: String): JsonBuilder {
        return clickEvent(ClickEvent.suggestCommand(text))
    }

    /**
     * Copies text to the user's clipboard on click
     * @param text text to copy
     * @return this builder
     */
    @Contract("_ -> this")
    fun copy(text: String): JsonBuilder {
        return clickEvent(ClickEvent.copyToClipboard(text))
    }

    /**
     * Toggles loreize, which automatically splits text and replaces || with newlines using [StringUtils.loreize]
     * @return this builder
     */
    @Contract("_ -> this")
    fun loreize(loreize: Boolean): JsonBuilder {
        this.loreize = loreize
        return this
    }

    /**
     * Clears this builder's hover text
     * @return this builder
     */
    @Contract("-> this")
    fun hover(): JsonBuilder {
        lore.clear()
        builder.hoverEvent(null)
        return this
    }

    /**
     * Adds lines of text to this builder's hover text
     * <br></br>Note: this is not computed until [.build] which will override any other hover set
     * @param lines lines of text
     * @return this builder
     */
    @Contract("_ -> this")
    fun hover(vararg lines: String): JsonBuilder {
        return hover(listOf(*lines))
    }

    /**
     * Adds lines of text to this builder's hover text
     * <br></br>Note: this is not computed until [.build] which will override any other hover set
     * @param lines lines of text
     * @return this builder
     */
    @Contract("_ -> this")
    fun hover(lines: List<String>): JsonBuilder {
        if (lines.size > 1) loreize(false)
        lore.addAll(lines)
        return this
    }

    /**
     * Adds lines of text to this builder's hover text in the specified color
     * <br></br>Note: Other included colors will override specified color
     * <br></br>Note: this is not computed until [.build] which will override any other hover set
     * @param lines lines of text
     * @param color color
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun hover(lines: List<String>, color: String): JsonBuilder {
        return hover(lines.stream().map { line: String -> color + line }.toList())
    }

    /**
     * Adds lines of text to this builder's hover text in the specified color
     * <br></br>Note: Other included colors will override specified color
     * <br></br>Note: this is not computed until [.build] which will override any other hover set
     * @param lines lines of text
     * @param color color
     * @return this builder
     */
    @Contract("_, _ -> this")
    fun hover(lines: List<String>, color: ChatColor): JsonBuilder {
        return hover(lines, color.toString())
    }

    /**
     * Gets a copy of the current pending lines of hover text
     * @return list of not-yet-formatted strings
     */
    @Contract("-> this")
    fun getHoverLines(): List<String?> {
        return java.util.ArrayList(lore)
    }

    /**
     * Sets the text shown on hover
     * @return this builder
     */
    @Contract("_ -> this")
    fun <V> hover(hoverEvent: HoverEventSource<V>?): JsonBuilder {
        builder.hoverEvent(hoverEvent)
        return this
    }

    /**
     * Sets a component to display on hover
     * @return this builder
     */
    @Contract("_ -> this")
    fun hover(component: ComponentLike?): JsonBuilder {
        if (component != null) builder.hoverEvent(component.asComponent().asHoverEvent())
        return this
    }

    /**
     * Sets a string that will be inserted in chat when this component is shift-clicked
     * @return this builder
     */
    @Contract("_ -> this")
    fun insert(insertion: String?): JsonBuilder {
        builder.insertion(insertion)
        return this
    }

    /**
     * "Initializes" the builder. This does nothing on its own. See [.isInitialized] for more information.
     * @return this builder
     */
    @Contract("-> this")
    fun initialize(): JsonBuilder {
        this.initialized = true
        return this
    }

    /**
     * Runs [.build] and sends the resulting component to a recipient.
     * @param recipient a player object (see [PlayerUtils#send][PlayerUtils.send] for valid objects)
     */
    fun send(recipient: Any?) {
        PlayerUtils.send(recipient, this)
    }

    /**
     * Executes [.group] and returns the final result
     * @return resultant component
     */
    fun build(): TextComponent {
        group()
        return result.build()
    }

    /**
     * Alias of [.build]. Executes [.group] and returns the final result
     * @return resultant component
     */
    override fun asComponent(): Component {
        return build()
    }

    /**
     * Builds this component ([.build]) and formats it as plain text using Paper's serializer
     * @return plain human text (no color codes)
     */
    override fun toString(): String {
        return AdventureUtils.asPlainText(build())
    }

    /**
     * [Builds][.build] this component and converts it to JSON using Paper's serializer
     * @return Minecraft json string
     */
    fun serialize(): String {
        return GsonComponentSerializer.gson().serialize(build())
    }

}
