package com.teamxserver.classes.GUI;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class InventoryItem {

	private ItemStack itemStack;
	private boolean disabled;

	public InventoryItem(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.disabled = false;
	}

	public static enum ClickResponse {
		NOTHING, ACCEPT, DENY, REMOVE, ERROR;
	}

	public abstract ClickResponse click(Player player, ClickType click, int slot);

	public static InventoryItem makeItem(ItemStack stack, Clicker onClick) {
		return new InventoryItem(stack) {
			@Override
			public ClickResponse click(Player player, ClickType click, int slot) {
				return onClick.click(stack, player, click, slot);
			}
		};
	}

	public static ItemStack makeStack(Material material) {
		return makeStack(material, 1);
	}

	public static ItemStack makeStack(Material material, int count) {
		return makeStack(material, count, null);
	}
	
	public static ItemStack makeStack(Material material, int count, String name) {
		return makeStack(material, count, name, (short) 0);
	}
	
	public static ItemStack makeStack(Material material, int count, String name, short durability) {
		return makeStack(material, count, name, durability, null);
	}

	public static ItemStack makeStack(Material material, String name, List<String> lore) {
		return makeStack(material, 1, name, (short) 0, lore); 
	}

	public static ItemStack makeStack(Material material, String name) {
		return makeStack(material, 1, name);
	}
	
	public static ItemStack makeStack(Material material, int count, String name, short durability, List<String> lore) {
		ItemStack stack = new ItemStack(material, count, durability);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		if (lore != null)
			meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	public ItemStack getItemStack() {
		return itemStack.clone();
	}
	
	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


}
