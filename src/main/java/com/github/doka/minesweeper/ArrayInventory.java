package com.github.doka.minesweeper;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArrayInventory extends InventoryBase{
    public ArrayInventory(String name, int line) {
        super(name, line);
    }

    public void setItem(int y, int x, ItemStack item){
        if(y<0||y>=line||x<0||x>=9)return;

        int slot = 0;
        slot += y*9;
        slot += x;

        if(slot < 0 || slot > maxSlot)return;
        inventory.setItem(slot,item);
    }
    public ItemStack getItem(int y,int x){
        if(y<0||y>=line||x<0||x>=9)return new ItemStack(Material.AIR);
        int slot = 0;
        slot += y*9;
        slot += x;
        if(slot < 0 || slot > maxSlot)return new ItemStack(Material.AIR);
        return inventory.getItem(slot) == null ? new ItemStack(Material.AIR):inventory.getItem(slot);
    }
}
