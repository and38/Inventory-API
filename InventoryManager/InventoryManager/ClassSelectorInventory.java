package com.teamxserver.classes.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.teamxserver.classes.GUI.InventoryItem.ClickResponse;
import com.teamxserver.classes.abilities.Class;
import com.teamxserver.classes.manager.GameManagement;
public class ClassSelectorInventory extends InventoryPage {

	public ClassSelectorInventory(InventoryPage lastInventory) {
		super(lastInventory, 36, "Select Class");
	}

	@Override
	protected ItemStack[] make(ItemStack[] currentItems) {
		int looped = 0;
		for (Class clazz : GameManagement.getClassManager().getClasses()) {
			for (int i = 0; i < 4; i++) {
				int slotToPut = (9*i)+looped;
				Material mat = materialFromArmorPosition(clazz.getArmorMaterial(), i);
				ItemStack icon = new ItemStack(mat);
				ItemMeta meta = icon.getItemMeta();
				meta.setDisplayName(ChatColor.GRAY + clazz.getName());
				icon.setItemMeta(meta);
				addCustomItem(InventoryItem.makeItem(icon, (stack, player, clickType, slot) -> {
					BuildSelectorInventory inventory = 
							new BuildSelectorInventory(this, player, stack.getItemMeta().getDisplayName());
					inventory.open(player);
					return ClickResponse.ACCEPT;
				}), slotToPut);
			}
			looped += 2;
		}
		
		
		return currentItems;
	}
	
	private Material materialFromArmorPosition(String armorMaterial, int pos) {
		String str = "HELMET";
		switch (pos) {
		case 1:
			str = "CHESTPLATE";
			break;
		case 2:
			str = "LEGGINGS";
			break;
		case 3:
			str = "BOOTS";
			break;
		}
		return Material.valueOf(armorMaterial + "_" + str);
	}

}
