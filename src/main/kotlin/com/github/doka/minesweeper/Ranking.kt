package com.github.doka.minesweeper

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.util.*

class Ranking(val player: OfflinePlayer, val time: Int) {
    val minute: Int = time/60
    val second: Int = time%60
    override fun toString():String{
        val jsonObject = JSONObject()
        jsonObject["uuid"] = player.uniqueId.toString()
        jsonObject["time"] = "$time"
        return jsonObject.toJSONString()
    }
    companion object{
        val landMines : ArrayList<Ranking> = arrayListOf()
    }
}
fun RankingfromString(s:String) : Ranking{
    val p = JSONParser()
    val json = p.parse(s) as? JSONObject
    return Ranking(Bukkit.getOfflinePlayer(UUID.fromString(json?.get("uuid") as? String)),Integer.parseInt(json?.get("time") as String))
}