package com.teamxserver.classes.GUI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.teamxserver.classes.GUI.InventoryItem.ClickResponse;

public class GUIs {

	private static HashMap<UUID, InventoryPage> lastInventory = new HashMap<UUID, InventoryPage>();
	private static HashMap<UUID, InventoryPage> currentInventory = new HashMap<UUID, InventoryPage>();
	private static ArrayList<UUID> openeing = new ArrayList<UUID>();

	public static void openInventory(Player p, InventoryPage inventory) {
		inventory.open(p);
	}

	public static void openLastInventory(Player p, InventoryPage page) {
		page.open(p);
	}

	public static InventoryPage getCurrentInventory(Player p) {
		return currentInventory.get(p.getUniqueId());
	}

	public static void setCurrentInventory(HashMap<UUID, InventoryPage> currentInventoryy) {
		currentInventory = currentInventoryy;
	}

	public static InventoryPage getLastInventory(Player p) {
		return lastInventory.get(p.getUniqueId());
	}

	public static void setLastInventory(HashMap<UUID, InventoryPage> lastInventoryy) {
		lastInventory = lastInventoryy;
	}

	public static void setLastInventory(Player p, InventoryPage inventory) {
		lastInventory.put(p.getUniqueId(), inventory);
	}

	public static void setInventory(Player p, InventoryPage inventory) {
		currentInventory.put(p.getUniqueId(), inventory);
	}
	
	public static void removePlayer(Player player) {
		currentInventory.remove(player.getUniqueId());
		lastInventory.remove(player.getUniqueId());
	}
	
	public static void removeCurrentInventory(Player player) {
		currentInventory.remove(player.getUniqueId());
	}
	
	public static void closed(Player player) {
		lastInventory.remove(player.getUniqueId());
		currentInventory.remove(player.getUniqueId());
	}

	public static void playClickSound(Player player, ClickResponse click) {
		switch (click) {
		case ACCEPT:
			player.playSound(player.getLocation(), Sound.valueOf("NOTE_PLING"), 1, 1.6f);
			return;
		case DENY:
			player.playSound(player.getLocation(), Sound.valueOf("ITEM_BREAK"), 1, .6f);
			return;
		case REMOVE:
			player.playSound(player.getLocation(), Sound.valueOf("NOTE_PLING"), 1, 0.6f);
			return;
		case ERROR:
			player.playSound(player.getLocation(), Sound.valueOf("NOTE_BASS_GUITAR"), 1f, 0.5f);
			return;
		default:
			return;
		}
	}

	public static void setOpeneing(Player p, boolean open) {
		if (open) {
			openeing.add(p.getUniqueId());
		} else {
			openeing.remove(p.getUniqueId());
		}
	}

	public static void stopOpeneing(Player p) {
		openeing.remove(p.getUniqueId());
	}
	
	public static boolean isOpeneing(Player p) {
		return openeing.contains(p.getUniqueId());
	}

}
