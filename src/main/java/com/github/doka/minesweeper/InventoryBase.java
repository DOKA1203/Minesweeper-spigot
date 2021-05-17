package com.github.doka.minesweeper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class InventoryBase implements Listener{
    public Inventory inventory;
    public final String name;
    public final int maxSlot;
    public final int line;

    public final UUID inventoryID = UUID.randomUUID();

    public InventoryBase(String name, int line){
        inventory = Bukkit.createInventory(null,line*9, ChatColor.translateAlternateColorCodes('&',name));
        this.name = ChatColor.translateAlternateColorCodes('&',name);
        this.maxSlot = line*9-1;
        this.line = line;

        Bukkit.getPluginManager().registerEvents(this,MinesweeperPlugin.Instance);
    }

    public void unregister(){
        HandlerList.unregisterAll(this);
    }
}
