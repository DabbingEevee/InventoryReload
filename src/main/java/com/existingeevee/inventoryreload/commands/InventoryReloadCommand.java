package com.existingeevee.inventoryreload.commands;

import com.existingeevee.inventoryreload.components.InventoryComponents;
import com.existingeevee.inventoryreload.utils.InventorySerializationUtils;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class InventoryReloadCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("invreload").requires(s -> s.hasPermissionLevel(2))
				.then(CommandManager.argument("inventoryID", StringArgumentType.string()).executes((commandContext) -> {
					return executeEntity((ServerCommandSource) commandContext.getSource(),
							StringArgumentType.getString(commandContext, "inventoryID"));
				})));
	}

	private static int executeEntity(ServerCommandSource source, String id) {
		try {
		    if (!(source.getEntity() instanceof PlayerEntity)) {
				source.sendError(new TranslatableText("commands.invreload.fail.notplayer", new Object[] {id, source}));
				return 0;
		    }
		    if (!(InventoryComponents.INVENTORIES.get(source.getPlayer()).retrieveInventoryCode(id) != null)) {
				source.sendError(new TranslatableText("commands.invreload.fail.nosuchsavedinventory", new Object[] {id}));
				return 0;
		    }
		    InventorySerializationUtils.serializeToInventory(source.getPlayer().getInventory(), InventoryComponents.INVENTORIES.get(source.getPlayer()).retrieveInventoryCode(id));
		    source.sendFeedback(new TranslatableText("commands.invreload.success", new Object[] {id, source}), true);
		    return 1;
		} catch (IllegalArgumentException | JsonSyntaxException t) {
			source.sendError(new TranslatableText("commands.invreload.fail.corruptedinventory", new Object[] {id}));
			return 0;
		} catch (Throwable t) {
			source.sendError(new TranslatableText("commands.invreload.fail.unknown", new Object[] {id}));
			return 0;
		}
	}
}
