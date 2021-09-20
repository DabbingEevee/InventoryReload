package com.existingeevee.inventoryreload.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;


public final class InventoryComponents implements EntityComponentInitializer {
    public static final ComponentKey<InventoryComponent> INVENTORIES = ComponentRegistry.getOrCreate(new Identifier("invreload", "inventories"), InventoryComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(INVENTORIES, InventoryComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
	}
}