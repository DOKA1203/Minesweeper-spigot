package com.github.doka.minesweeper

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

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
        Instance = this;
        Bukkit.getPluginManager().registerEvents(this, Instance);

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

    override fun onCommand(sender: CommandSender,command: Command,label: String,args: Array<out String>): Boolean {
        if(!label.equals("지뢰찾기"))return false
        if (sender !is Player)return false
        val player = sender
        val m =  Minesweeper(player)
        m.start()
        return false
    }
}