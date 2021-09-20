package com.existingeevee.inventoryreload.commands;

import com.existingeevee.inventoryreload.components.InventoryComponents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class InventorySaveCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("invsave").requires(s -> s.hasPermissionLevel(2))
				.then(CommandManager.argument("inventoryID", StringArgumentType.string()).executes((commandContext) -> {
					return executeEntity((ServerCommandSource) commandContext.getSource(),
							StringArgumentType.getString(commandContext, "inventoryID"));
				})));
	}

	private static int executeEntity(ServerCommandSource source, String id) {
		try {
		    if (!(source.getEntity() instanceof PlayerEntity)) {
				source.sendError(new TranslatableText("commands.invsave.fail.notplayer", new Object[] {id, source}));
				return 0;
		    }
		    InventoryComponents.INVENTORIES.get(source.getPlayer()).saveInventory(id);
		    source.sendFeedback(new TranslatableText("commands.invsave.success", new Object[] {id, source}), true);
		    return 1;
		} catch (Throwable t) {
			source.sendError(new TranslatableText("commands.invsave.fail.unknown", new Object[] {id}));
			return 0;
		}
	}
}
