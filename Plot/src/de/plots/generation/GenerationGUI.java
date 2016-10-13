package de.plots.generation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.IEEE754rUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GenerationGUI implements Listener {

	public static Inventory inv;
	
	public static void openInventory(Player p) {
		
		inv = Bukkit.createInventory(null, 4*9, "Plotadmin");
		
		ItemStack glass = ItemCreation.createItem(Material.THIN_GLASS, "[]");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, glass);
		}
		
		List<String> waylore = new ArrayList<>();
		waylore.add("§9Wähle aus, aus welchem Item der Weg bestehen soll!");
		ItemStack wayItem = ItemCreation.createItem(Material.QUARTZ_BLOCK, "§cWeg Item", waylore);
		
		List<String> borderlore = new ArrayList<>();
		borderlore.add("§9Wähle aus, aus welchem Item die Begrenzung bestehen soll!");
		ItemStack borderitem = ItemCreation.createItem(Material.DIAMOND_BLOCK, "§cBegrenzungs-Item", borderlore);
		
		List<String> claimedborderlore = new ArrayList<>();
		claimedborderlore.add("§9Wähle aus, aus welchem Item die geclaimte Begrenzung bestehen soll!");
		ItemStack claimedborderitem = ItemCreation.createItem(Material.QUARTZ_BLOCK, "§cGeclaimte Begrenzung Itemen", claimedborderlore);
		
		List<String> continueItemLore = new ArrayList<>();
		continueItemLore.add("§9Wähle aus, aus welchem Item die geclaimte Begrenzung bestehen soll!");
		ItemStack continueItem = ItemCreation.createItem(Material.EMERALD_BLOCK, "§l§2Weiter", continueItemLore);
		
		inv.setItem(9+2, wayItem);
		inv.setItem(9+4, borderitem);
		inv.setItem(9+6, claimedborderitem);
		inv.setItem(4*9 - 1, continueItem);
		
		
		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory().equals(inv)) {
			if (e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if (p.hasPermission("server.admin")) {
					if (e.getCurrentItem().getType() != Material.THIN_GLASS) {
						e.setCancelled(true);
					} else if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
						ItemStack wayItem = e.getClickedInventory().getItem(9*2+2);
						ItemStack borderitem = e.getClickedInventory().getItem(9*2+2);
						ItemStack claimedborderitem = e.getClickedInventory().getItem(9*2+2);
						
						
						p.closeInventory();
					}
				} else {
					p.closeInventory();
				}
			}
		}
	}
	@EventHandler
	public void onInventoryFill(InventoryMoveItemEvent e) {
		
	}
}
