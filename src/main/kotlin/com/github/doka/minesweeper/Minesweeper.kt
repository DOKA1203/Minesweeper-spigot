package com.github.doka.minesweeper

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

open class Minesweeper(val player : Player,val level:Int) : ArrayInventory("지뢰찾기",6) {

    val landMines : ArrayList<Int> = arrayListOf()

    private val landMine = ItemStack(Material.TNT)
    private val glass = ItemStack(Material.STAINED_GLASS_PANE)
    private val flag = ItemStack(Material.REDSTONE_TORCH_ON)

    var isFail = false
    var isGameover = false

    var startTime = 0L

    init {
        var meta = landMine.itemMeta
        meta.displayName = "§c지뢰"
        landMine.itemMeta = meta

        meta = glass.itemMeta
        meta.displayName = "???"
        meta.lore = arrayListOf("§f좌클릭 : 확인하기","§f우클릭 : 깃발 올리기")
        glass.itemMeta = meta

        meta = flag.itemMeta
        meta.displayName = "§c깃발"
        meta.lore = arrayListOf("§f클릭 : 깃발 해제하기")
        flag.itemMeta = meta

        creation()
    }

    fun start() {
        player.openInventory(inventory)
        startTime = System.currentTimeMillis()
        object : BukkitRunnable() {
            override fun run() {
                if(isGameover){
                    unregister()
                    cancel()
                    return
                }
                copyInventory(player.openInventory.topInventory,inventory)
            }
        }.runTaskTimer(MinesweeperPlugin.Instance,0,1)
    }

    private fun creation(){
        for(i in 1 .. (level * 3)){
            val a = rand(0,maxSlot)
            if(!landMines.contains(a))landMines.add(a)
        }
        for (i in 0 .. maxSlot){
            inventory.setItem(i,glass)
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null) return
        if (player.uniqueId != event.whoClicked.uniqueId)return
        if (event.inventory.title!="지뢰찾기")return
        event.isCancelled = true

        if(isFail||isGameover)return

        if(event.currentItem.isSimilar(flag)){
            inventory.setItem(event.slot,glass)
            return
        }
        if(event.isRightClick){
            if(event.currentItem.isSimilar(glass)){
                inventory.setItem(event.slot,flag)
            }
            return
        }
        if(landMines.contains(event.slot)){ //지뢰를 누르면
            if (event.isLeftClick){ // 지뢰를 터뜨리면
                isFail = true //실패
                inventory.setItem(event.slot,landMine)

                object : BukkitRunnable() {
                    var index = 0
                    override fun run() {
                        if(index >= landMines.size){
                            cancel();
                            player.sendMessage("Lose")
                            isGameover = true
                            return
                        }
                        inventory.setItem(landMines[index],landMine)
                        index++
                    }
                }.runTaskTimer(MinesweeperPlugin.Instance,5,5)
            }
            return
        }

        remove(event.slot / 9,((event.slot - (event.slot / 9 ) * 9) ) % 9)
        for (i in 0 .. maxSlot){
            if(inventory.getItem(i)?.isSimilar(glass) == true){ if(!landMines.contains(i))return }
            if(inventory.getItem(i)?.isSimilar(flag) == true){ if(!landMines.contains(i))return }
        }

        isFail = true



        object : BukkitRunnable() {
            var index = 0
            override fun run() {
                if(index >= landMines.size){
                    cancel();
                    player.sendMessage("Win")
                    isGameover = true
                    player.closeInventory()
                    return
                }
                inventory.setItem(landMines[index],landMine)
                index++
            }
        }.runTaskTimer(MinesweeperPlugin.Instance,5,5)
    }

    @EventHandler
    private fun close(event: InventoryCloseEvent){
        if (event.inventory == null) return
        if (player.uniqueId != event.player.uniqueId)return
        if (!event.inventory.title.equals("지뢰찾기"))return
        if(isGameover)return

        player.sendMessage("Lose")
        isGameover = true;
    }

    private fun remove(y:Int,x:Int){
        if (y < 0 || y >= line || x < 0 || x >= 9)return

        val nb = getNearbyMines(y,x)
        if(nb == 0 ){
            if(getItem(y,x).type == Material.AIR){
                return
            }
            setItem(y,x, ItemStack(Material.AIR))
            remove(y+1,x+1)
            remove(y+1,x)
            remove(y+1,x-1)
            remove(y,x+1)
            remove(y,x-1)
            remove(y-1,x+1)
            remove(y-1,x)
            remove(y-1,x-1)

        }else{
            when(nb){
                1 ->{
                    setItem(y,x,MinesweeperPlugin.one)
                }
                2 ->{
                    setItem(y,x,MinesweeperPlugin.two)
                }
                3 ->{
                    setItem(y,x,MinesweeperPlugin.three)
                }
                4 ->{
                    setItem(y,x,MinesweeperPlugin.four)
                }
                5 ->{
                    setItem(y,x,MinesweeperPlugin.five)
                }
                6 ->{
                    setItem(y,x,MinesweeperPlugin.six)
                }
            }
        }
    }
    private fun getNearbyMines(y:Int,x:Int) : Int{
        var c = 0
        c+=if(landMines.contains(yxtoslot(y+1,x))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y+1,x-1))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y+1,x+1))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y,x+1))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y,x-1))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y-1,x))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y-1,x+1))){ 1 }else{ 0 }
        c+=if(landMines.contains(yxtoslot(y-1,x-1))){ 1 }else{ 0 }
        return c
    }
    private fun yxtoslot(y: Int,x: Int) : Int{
        if (y < 0 || y >= line || x < 0 || x >= 9)return -1

        var slot = 0
        slot += y * 9
        slot += x
        return slot;
    }
}