package com.existingeevee.inventoryreload.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

import com.existingeevee.inventoryreload.utils.InventorySerializationUtils;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class InventoryComponent implements AutoSyncedComponent {

	private PlayerEntity player;
	
	private Map<String,String> map = new HashMap<String,String>();
	
    public InventoryComponent(PlayerEntity player) {
        this.player = player;
    }
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		for (String key : tag.getKeys()) {
			map.put(key, tag.getString(key));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		for (Entry<String, String> invEntry : map.entrySet()) {
			tag.putString(invEntry.getKey(), invEntry.getValue());
		}
	}

	@Nullable	
	public String retrieveInventoryCode(String string) {
		return map.get(string);
	}
	
	public boolean saveInventory(String id) {
		try {
			String inventory = InventorySerializationUtils.deserializeInventory(player.getInventory());
			map.put(id, inventory);
			return true;
		} catch (Throwable t) { 
			return false;
		}
	}
	
}
