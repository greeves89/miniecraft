package de.plots.generation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.IEEE754rUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.netty.util.internal.chmv8.ConcurrentHashMapV8.Action;
import net.md_5.bungee.api.ChatColor;

public class GenerationGUI implements Listener {

	public static Inventory inv;
	
	//Weg - Item
	//Begrenzung - Item (unclaimed)
	//Begrenzung - Item (claimed)
	//Weggröße
	//Plotgröße
	
	public static void openInventory(Player p) {
		
		inv = Bukkit.createInventory(null, 6*9, "Plotadmin");
		
		ItemStack glass = ItemCreation.createItem(Material.THIN_GLASS, "[]");
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, glass);
		}
		
		List<String> waylore = new ArrayList<>();
		waylore.add("§9Wähle aus, aus welchem Item der Weg bestehen soll!");
		ItemStack wayItem = ItemCreation.createItem(Material.QUARTZ_BLOCK, "§cWeg Item", waylore);
		
		List<String> borderlore = new ArrayList<>();
		borderlore.add("§9Wähle aus, aus welchem Item die Begrenzung bestehen soll!");
		ItemStack borderitem = ItemCreation.createItem(Material.STONE_SLAB2, "§cBegrenzungs-Item", borderlore);
		
		List<String> claimedborderlore = new ArrayList<>();
		claimedborderlore.add("§9Wähle aus, aus welchem Item die geclaimte Begrenzung bestehen soll!");
		ItemStack claimedborderitem = ItemCreation.createItem(Material.WOOD_STEP, "§cGeclaimte Begrenzung Itemen", claimedborderlore);
		
		List<String> waysizeItemLore = new ArrayList<>();
		waysizeItemLore.add("§9Wähle aus, wie groß der Weg zwischen den Plots sein soll!");
		ItemStack waysizeItem = ItemCreation.createItem(Material.BEACON, "3", waysizeItemLore);
		
		List<String> plotsizeItemLore = new ArrayList<>();
		plotsizeItemLore.add("§9Wähle aus, wie groß jedes einzelne Plot sein soll!");
		ItemStack plotsizeItem = ItemCreation.createItem(Material.GRASS, "16", plotsizeItemLore);
		
		ItemStack upWay = ItemCreation.createItem(Material.REDSTONE_BLOCK, "+");
		ItemStack downWay = ItemCreation.createItem(Material.REDSTONE_BLOCK, "-");
		
		ItemStack upPlot = ItemCreation.createItem(Material.DIAMOND_BLOCK, "+");
		ItemStack downPlot = ItemCreation.createItem(Material.DIAMOND_BLOCK, "-");
		
		List<String> plotamountLore = new ArrayList<>();
		plotamountLore.add("§9Klicke um die Anzahl der Plots die generiert werden zu ändern!");
		plotamountLore.add("§cDiese Zahl multipliziert sich mit sich selbst!");
		ItemStack plotamountItem = ItemCreation.createItem(Material.ANVIL, "1", plotamountLore);
		
		List<String> continueItemLore = new ArrayList<>();
		continueItemLore.add("§9Generieren der Plots starten!");
		ItemStack continueItem = ItemCreation.createItem(Material.EMERALD_BLOCK, "§l§2Weiter", continueItemLore);
		
		
		inv.setItem(9+2, wayItem);
		inv.setItem(9+4, borderitem);
		inv.setItem(9+6, claimedborderitem);
		inv.setItem(18+4, waysizeItem);
		inv.setItem(18+3, upWay);
		inv.setItem(18+5, downWay);
		inv.setItem(9*3+4, plotsizeItem);
		inv.setItem(9*3+3, upPlot);
		inv.setItem(9*3+5, downPlot);
		inv.setItem(9*4+4, plotamountItem);
		
		inv.setItem(6*9 - 1, continueItem);
		
		
		
		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory().equals(inv)) {
			if (e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				if (p.hasPermission("server.admin")) {
					if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
						ItemStack wayItem = e.getClickedInventory().getItem(8+3);
						ItemStack borderitem = e.getClickedInventory().getItem(8+5);
						ItemStack claimedborderitem = e.getClickedInventory().getItem(8+7);
						int waysize = Integer.parseInt(e.getClickedInventory().getItem(18+4).getItemMeta().getDisplayName());
						int plotsize = Integer.parseInt(e.getClickedInventory().getItem(9*3+4).getItemMeta().getDisplayName()) * 2;
						int plotamount = Integer.parseInt(e.getClickedInventory().getItem(9*4+4).getItemMeta().getDisplayName());
						
						
						PlotGeneration.generatePlots(p.getWorld(), p.getLocation(), plotsize, plotamount, borderitem.getType(), wayItem.getType(), waysize);
						p.closeInventory();
						e.setCancelled(true);
					} else if (e.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK)) {
						ItemStack item = e.getClickedInventory().getItem(18+4);
						String name = item.getItemMeta().getDisplayName();
						int size = Integer.parseInt(name);
						
						if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+")) {
							if (size < 15) {
								size++;
							} else {
								p.sendMessage("max. reached!");
							}
						} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-")) {
							if (size > 0) {
								size--;
							} else {
								p.sendMessage("minimum reached!");
							}
						} else {
							p.sendMessage("Error!");
						}
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("" + size);
						item.setItemMeta(meta);
						inv.setItem(18+4, item);
						e.setCancelled(true);
					} else if (e.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)) {
						if (e.getCurrentItem().getItemMeta().getDisplayName().equals("+") || e.getCurrentItem().getItemMeta().getDisplayName().equals("-")) {
							ItemStack item = e.getClickedInventory().getItem(9*3+4);
							String name = item.getItemMeta().getDisplayName();
							int size = Integer.parseInt(name);
							
							if (e.getCurrentItem().getItemMeta().getDisplayName().contains("+")) {
								if (size < 256) {
									size = size + 8;
								} else {
									p.sendMessage("max. reached!");
								}
							} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("-")) {
								if (size > 8) {
									size = size - 8;
								} else {
									p.sendMessage("minimum reached!");
								}
							} else {
								p.sendMessage("Error!");
							}
							ItemMeta meta = item.getItemMeta();
							meta.setDisplayName("" + size);
							item.setItemMeta(meta);
							inv.setItem(9*3+4, item);
							e.setCancelled(true);
						}
						
					} else if (e.getCurrentItem().getType().equals(Material.ANVIL)) {
						ItemStack item = e.getCurrentItem();
						int current = Integer.parseInt(item.getItemMeta().getDisplayName());
						
						if (current < 10) {
							current++;
						} else {
							current = 1;
						}
						
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(current + "");
						item.setItemMeta(meta);
						e.setCurrentItem(item);
						e.setCancelled(true);
					} else if (e.getCurrentItem().getType() == Material.THIN_GLASS) {
						e.setCancelled(true);
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
