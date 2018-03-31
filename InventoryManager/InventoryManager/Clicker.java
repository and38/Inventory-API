package com.teamxserver.classes.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.teamxserver.classes.GUI.InventoryItem.ClickResponse;

public interface Clicker {
	public ClickResponse click(ItemStack stack, Player player, ClickType click, int slot);
}
