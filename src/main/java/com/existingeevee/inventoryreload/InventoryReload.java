package com.existingeevee.inventoryreload;

import com.existingeevee.inventoryreload.commands.InventoryReloadCommand;
import com.existingeevee.inventoryreload.commands.InventorySaveCommand;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class InventoryReload implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Initalizing Inventory Reload");
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			InventoryReloadCommand.register(dispatcher);
			InventorySaveCommand.register(dispatcher);
		});
	}
}
