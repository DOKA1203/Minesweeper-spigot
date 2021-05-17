package com.github.doka.minesweeper

import net.minecraft.server.v1_12_R1.IChatBaseComponent
import net.minecraft.server.v1_12_R1.PacketPlayOutChat
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MinesweeperPlugin : JavaPlugin() , Listener{
    companion object {
        lateinit var Instance : MinesweeperPlugin
        lateinit var one:ItemStack
        lateinit var two:ItemStack
        lateinit var three:ItemStack
        lateinit var four:ItemStack
        lateinit var five:ItemStack
        lateinit var six:ItemStack
    }

    override fun onEnable() {
        Instance = this
        Bukkit.getPluginManager().registerEvents(this, Instance)
        creation()

        val rankfile = YamlConfiguration.loadConfiguration(File(dataFolder,"rank.yml"))
        for (s in rankfile.getStringList("ranks")) {
            Ranking.landMines.add(RankingfromString(s))
        }

    }

    override fun onCommand(sender: CommandSender,command: Command,label: String,args: Array<out String>): Boolean {
        if(label != "지뢰찾기")return false
        if (sender !is Player)return false

        val player:Player = sender

        if(args.isEmpty()){
            val component = IChatBaseComponent.ChatSerializer.a("[\"\",{\"text\":\"=================================\",\"color\":\"blue\"},{\"text\":\"\\n\\n           [ \"},{\"text\":\"\\uc9c0\\ub8b0\\ucc3e\\uae30\",\"color\":\"red\"},{\"text\":\" ]\\n\\n    \"},{\"text\":\"[ \\uc26c\\uc6c0 ]\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/\\uc9c0\\ub8b0\\ucc3e\\uae30 1\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"\\uc26c\\uc6b4 \\ub09c\\uc774\\ub3c4\\uc758 \\uc9c0\\ub8b0\\ucc3e\\uae30\\ub97c \\uc2dc\\uc791\\ud569\\ub2c8\\ub2e4.\"}},{\"text\":\" \"},{\"text\":\"[ \\ubcf4\\ud1b5 ]\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/\\uc9c0\\ub8b0\\ucc3e\\uae30 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"\\ubcf4\\ud1b5 \\ub09c\\uc774\\ub3c4\\uc758 \\uc9c0\\ub8b0\\ucc3e\\uae30\\ub97c \\uc2dc\\uc791\\ud569\\ub2c8\\ub2e4.\"}},{\"text\":\" \"},{\"text\":\"[ \\uc5b4\\ub824\\uc6c0 ]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/\\uc9c0\\ub8b0\\ucc3e\\uae30 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"\\uc5b4\\ub824\\uc6b4 \\ub09c\\uc774\\ub3c4\\uc758 \\uc9c0\\ub8b0\\ucc3e\\uae30\\ub97c \\uc2dc\\uc791\\ud569\\ub2c8\\ub2e4.\"}},{\"text\":\"\\n\\n\\n\\n           \"},{\"text\":\"[ \\ub7ad\\ud0b9\\ubcf4\\uae30 ]\",\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/\\uc9c0\\ub8b0\\ucc3e\\uae30 \\uc21c\\uc704\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"\\ud074\\ub9ad\\ud558\\uc5ec \\ub7ad\\ud0b9\\uc744 \\ubd05\\ub2c8\\ub2e4.\"}},{\"text\":\"\\n\\n\"},{\"text\":\"=================================\",\"color\":\"blue\"}]")
            (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutChat(component))
            return false
        }else{
            when(args[0]){
                "1" -> {
                    Minesweeper(sender,1).start()
                }
                "2" -> {
                    Minesweeper(sender,2).start()
                }
                "3" -> {
                    Minesweeper(sender,3).start()
                }
            }
        }
        return false
    }




    private fun creation(){
        one = ItemStack(Material.STAINED_GLASS_PANE)
        one.durability = 3
        var meta = one.itemMeta
        meta.displayName = "§9 1 "
        one.itemMeta = meta

        two = ItemStack(Material.STAINED_GLASS_PANE)
        two.durability = 5
        meta = two.itemMeta
        meta.displayName = "§2 2 "
        two.itemMeta = meta

        three = ItemStack(Material.STAINED_GLASS_PANE)
        three.durability = 14
        meta = three.itemMeta
        meta.displayName = "§c 3 "
        three.itemMeta = meta

        four = ItemStack(Material.STAINED_GLASS_PANE)
        four.durability = 11
        meta = four.itemMeta
        meta.displayName = "§1 4 "
        four.itemMeta = meta

        five = ItemStack(Material.STAINED_GLASS_PANE)
        five.durability = 12
        meta = five.itemMeta
        meta.displayName = "§6 5 "
        five.itemMeta = meta

        six = ItemStack(Material.STAINED_GLASS_PANE)
        six.durability = 13
        meta = six.itemMeta
        meta.displayName = "§b 6 "
        six.itemMeta = meta
    }
}