package de.plots.generation;

import java.util.ArrayList;
import java.util.List;

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
		
		List<String> waylore = new ArrayList<>();
		waylore.add("§9Wähle aus, aus welchem Item der Weg bestehen soll!");
		ItemStack wayItem = createItem(Material.QUARTZ_BLOCK, "§cWeg Item", waylore);
		
		List<String> borderlore = new ArrayList<>();
		borderlore.add("§9Wähle aus, aus welchem Item die Begrenzung bestehen soll!");
		ItemStack borderitem = createItem(Material.DIAMOND_BLOCK, "§cBegrenzungs-Item", borderlore);
		
		List<String> claimedborderlore = new ArrayList<>();
		claimedborderlore.add("§9Wähle aus, aus welchem Item die geclaimte Begrenzung bestehen soll!");
		ItemStack claimedborderitem = createItem(Material.QUARTZ_BLOCK, "§cGeclaimte Begrenzung Itemen", claimedborderlore);
		
		List<String> continueItemLore = new ArrayList<>();
		continueItemLore.add("§9Wähle aus, aus welchem Item die geclaimte Begrenzung bestehen soll!");
		ItemStack continueItem = createItem(Material.EMERALD_BLOCK, "§l§2Weiter", continueItemLore);
		
		inv.setItem(9+2, wayItem);
		inv.setItem(9+4, borderitem);
		inv.setItem(9+6, claimedborderitem);
		inv.setItem(4*9 - 1, continueItem);
		
		
		p.openInventory(inv);
	}
	public static ItemStack createItem(Material material, String displayname, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory().equals(inv)) {
			if (e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if (p.hasPermission("server.admin")) {
					if (e.getCurrentItem().getType() != Material.AIR) {
						e.setCancelled(true);
					}
					
					if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
						//TODO:
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