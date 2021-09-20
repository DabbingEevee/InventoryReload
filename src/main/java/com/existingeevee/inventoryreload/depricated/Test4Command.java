package com.existingeevee.inventoryreload.depricated;

import com.existingeevee.inventoryreload.components.InventoryComponents;
import com.existingeevee.inventoryreload.utils.InventorySerializationUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

@Deprecated
public class Test4Command {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("test4command").requires(s -> s.hasPermissionLevel(2))
				.then(CommandManager.argument("inventoryID", StringArgumentType.string()).executes((commandContext) -> {
					return executeEntity((ServerCommandSource) commandContext.getSource(),
							StringArgumentType.getString(commandContext, "inventoryID"));
				})));
	}

	private static int executeEntity(ServerCommandSource source, String id) {
		try {
		    InventorySerializationUtils.serializeToInventory(source.getPlayer().getInventory(), InventoryComponents.INVENTORIES.get(source.getPlayer()).retrieveInventoryCode(id));
		    return 1;
		} catch (Throwable t) {
			try {
				source.getPlayer().sendMessage(Text.of(t.getClass().getName() + ": " + t.getMessage() + ": "), false);
			} catch (CommandSyntaxException e1) {
				e1.printStackTrace();
			}
			for (StackTraceElement s : t.getStackTrace()) {
				try {
					source.getPlayer().sendMessage(Text.of(s.toString()), false);
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
				}
			}
			return 0;
		}
	}
}
