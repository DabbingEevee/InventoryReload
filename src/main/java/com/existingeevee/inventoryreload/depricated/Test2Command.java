package com.existingeevee.inventoryreload.depricated;

import com.existingeevee.inventoryreload.utils.InventorySerializationUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

@Deprecated
public class Test2Command {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher
		.register(
				CommandManager
						.literal("test2command").requires(
								s -> s.hasPermissionLevel(2)).then(CommandManager
										.argument("json",
												StringArgumentType.string())
						.executes((commandContext) -> {
															return executeEntity(
																	(ServerCommandSource) commandContext
																			.getSource(),
																			StringArgumentType
																			.getString(
																					commandContext,
																					"json"));
														})));
	}

	private static int executeEntity(ServerCommandSource source, String string) {
		try {
			if (source.getEntity() instanceof PlayerEntity) {
				InventorySerializationUtils.serializeToInventory(((PlayerEntity) source.getEntity()).getInventory(), string);
				//source.getPlayer().sendMessage(Text.of(
				//		InventorySerializationUtils.deserializeInventory(
				//				((PlayerEntity) source.getEntity())
				//				.getInventory()
				//				
				//				)), false);
			}
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
