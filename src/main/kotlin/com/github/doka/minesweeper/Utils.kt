package com.github.doka.minesweeper

import org.bukkit.inventory.Inventory
import java.util.*
import kotlin.math.min

val random = Random()
fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
}
fun copyInventory(i1 : Inventory,i2 : Inventory){
    for(i in 0 until min(i1.size,i2.size)){
        i1.setItem(i,i2.getItem(i))
    }
}