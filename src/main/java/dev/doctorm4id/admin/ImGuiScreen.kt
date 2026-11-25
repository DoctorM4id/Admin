package dev.doctorm4id.admin

import cn.enaium.fabric.imgui.ImGuiRenderable
import cn.enaium.fabric.imgui.TextureBindings
import imgui.ImGui
import imgui.ImGuiIO
import imgui.flag.ImGuiKey
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import imgui.type.ImString
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.network.chat.Component
import org.lwjgl.sdl.SDLScancode
import imgui.ImGuiInputTextCallbackData
import imgui.callback.ImGuiInputTextCallback
import imgui.flag.ImGuiInputTextFlags
import net.minecraft.world.item.component.ResolvableProfile

class ImGuiScreen : Screen(Component.literal("Admin Screen")), ImGuiRenderable {

    private val username = ImString()

    enum class AdminTab {
        DASHBOARD,
        DEMO
    }

    private var selectedTab = AdminTab.DASHBOARD

    fun menuItem(name: String, tab: AdminTab, shortcut: String = "") {
        if (ImGui.menuItem(name, shortcut, selectedTab == tab))
            selectedTab = tab
    }

    override fun keyPressed(event: KeyEvent): Boolean {
        if (event.key == SDLScancode.SDL_SCANCODE_ESCAPE) {
            return super.keyPressed(event)
        }

        if (ImGui.getIO().wantCaptureKeyboard) {
            return true
        }

        val tabs = AdminTab.entries

        when (event.key) {
            SDLScancode.SDL_SCANCODE_D -> {
                selectedTab = tabs[(selectedTab.ordinal + 1) % tabs.size]
                return true
            }

            SDLScancode.SDL_SCANCODE_A -> {
                selectedTab = tabs[(selectedTab.ordinal - 1 + tabs.size) % tabs.size]
                return true
            }
        }

        return super.keyPressed(event)
    }

    override fun render(io: ImGuiIO?) {
        if (ImGui.beginMainMenuBar()) {

            menuItem("Dashboard", AdminTab.DASHBOARD)
            menuItem("Demo", AdminTab.DEMO)

            //selectedTab = AdminTab.DEMO

            ImGui.endMainMenuBar()
        }

        when (selectedTab) {
            AdminTab.DASHBOARD -> if (ImGui.begin("Screen")) { renderDashboard() }
            AdminTab.DEMO -> ImGui.showDemoWindow()
        }

        ImGui.end()

/*        ImGui.showDemoWindow()
        renderDashboard()*/
    }

    fun renderPlayer(player: String) {
        val client = Minecraft.getInstance()

        val playerInfo = client.connection
            ?.getPlayerInfo(player) ?: return

        val profile = ResolvableProfile.createResolved(playerInfo.profile)

        val texture = client.playerSkinRenderCache().getOrDefault(profile).textureView()
        val textureId = TextureBindings.INSTANCE.textureId(texture)

        ImGui.image(textureId,
            16f, 16f,
            8f / 64f, 8f / 64f,
            16f / 64f, 16f / 64f
        )
    }

    private fun renderDashboard() {
        ImGui.text("Admin Screen")

        val onlinePlayers = Minecraft.getInstance().player
            ?.connection
            ?.onlinePlayers
            ?.map { it.profile.name }
            ?.sorted()
            ?: emptyList()

        ImGui.inputText(
            "Username",
            username,
            ImGuiInputTextFlags.CallbackCompletion,
            object : ImGuiInputTextCallback() {
                override fun accept(data: ImGuiInputTextCallbackData) {
                    val query = data.buf

                    val match = onlinePlayers.firstOrNull {
                        it.startsWith(query, ignoreCase = true)
                    }

                    if (match != null) {
                        data.deleteChars(0, data.bufTextLen)
                        data.insertChars(0, match)
                    }
                }
            }
        )

        val query = username.get()

        val players = onlinePlayers.filter {
            query.isBlank() || it.startsWith(query, ignoreCase = true)
        }

        ImGui.separator()
        ImGui.text("Online Players (${players.size})")

        if (ImGui.beginListBox("##players")) {
            if (players.isEmpty()) {
                ImGui.text("No matching players")
            }

            for (player in players) {
                ImGui.pushID(player)

                renderPlayer(player)

                ImGui.sameLine()

                if (ImGui.selectable(player, username.get() == player)) {
                    username.set(player)
                }

                ImGui.popID()
            }

            ImGui.endListBox()
        }
    }
}