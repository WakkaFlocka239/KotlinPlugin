package me.wakka.kotlinplugin.features.menus.providers

import fr.minuskube.inv.content.InventoryContents
import me.wakka.kotlinplugin.features.menus.CustomMenu
import me.wakka.kotlinplugin.utils.EnumUtils.nextWithLoop
import me.wakka.kotlinplugin.utils.StringUtils
import me.wakka.kotlinplugin.utils.Tasks
import org.bukkit.entity.Player

class AdventMenu(private var frameTicks: Int) : CustomMenu() {
    private var animatedTitle: AnimatedTitle = AnimatedTitle.FRAME_1

    override fun open(viewer: Player, page: Int) {
        getInventory(this).open(viewer, page)
    }

    override fun getTitle(): String {
        return animatedTitle.getTitle()
    }

    override fun getRows(): Int {
        return 6
    }

    enum class AnimatedTitle(private val title: String) {
        FRAME_1("ꈉ盆"),
        FRAME_2("ꈉ鉊"),
        ;

        fun getTitle(): String {
            return StringUtils.colorize("&f$title")
        }
    }

    override fun update(viewer: Player, contents: InventoryContents) {}

    override fun init(viewer: Player, contents: InventoryContents) {
        Tasks.wait(frameTicks) {
            if (!isOpen(viewer, this))
                return@wait

            this.animatedTitle = animatedTitle.nextWithLoop()

            open(viewer, contents.pagination().page)
        }
    }




}