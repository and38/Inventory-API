package com.teamxserver.classes.listener;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.teamxserver.classes.GUI.GUIs;
import com.teamxserver.classes.GUI.InventoryItem;
import com.teamxserver.classes.GUI.InventoryItem.ClickResponse;
import com.teamxserver.classes.GUI.InventoryPage;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryInteract(InventoryDragEvent e) {
		Player pl = (Player) e.getWhoClicked();
		if (GUIs.getCurrentInventory(pl) != null && pl.getOpenInventory() != null) {
			for (int x : e.getRawSlots()) {
				if (x < e.getView().getTopInventory().getSize()) {
					e.setCancelled(true);
					GUIs.playClickSound(pl, ClickResponse.DENY);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player pl = (Player) e.getWhoClicked();
		if (GUIs.getCurrentInventory(pl) != null && pl.getOpenInventory() != null) {
			if (e.getRawSlot() >= e.getView().getTopInventory().getSize() 
					&& GUIs.getCurrentInventory(pl).isClickOwnInventory()) {
				return;
			} else if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
				GUIs.playClickSound(pl, ClickResponse.DENY);
				e.setCancelled(true);
				return;
			}
			e.setCancelled(true);
			InventoryPage page = GUIs.getCurrentInventory(pl);
			if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				ItemStack stack = e.getCurrentItem();
				if (stack.isSimilar(InventoryPage.BED)) {
					GUIs.getLastInventory(pl).open(pl);
					GUIs.playClickSound(pl, ClickResponse.ACCEPT);
				} else if (!page.getCustomItems().keySet().contains(e.getSlot())) {
					GUIs.playClickSound(pl, ClickResponse.DENY);
				}
			} else {
				GUIs.playClickSound(pl, ClickResponse.DENY);
			}
			for (int slot : page.getCustomItems().keySet()) {
				if (e.getSlot() == slot) {
					InventoryItem item = page.getCustomItems().get(slot);
					if (item.isDisabled()) 
						continue;
					ClickResponse success = item.click(pl, e.getClick(), e.getSlot());
					GUIs.playClickSound(pl, success);
				}
			}
		}
	}

	@EventHandler
	public void onOpenInv(InventoryOpenEvent e) {
		if (GUIs.isOpeneing((Player) e.getPlayer()) == true) {
			GUIs.stopOpeneing((Player) e.getPlayer());
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (GUIs.getCurrentInventory(p) != null && GUIs.isOpeneing(p) == false) {
			GUIs.getCurrentInventory(p).close(p);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		GUIs.removePlayer(e.getPlayer());
	}

}
