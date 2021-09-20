package com.existingeevee.inventoryreload.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InventorySerializationUtils {

	public static String deserializeInventory(Inventory inv) {
		
		Map<Integer,String> contents = new HashMap<Integer,String>();
				
		for (int i = 0; i < inv.size(); i++) {
			//System.out.println(i + " - " + Registry.ITEM.getId(inv.getStack(i).getItem()).toString() + (inv.getStack(i).getNbt() == null ? "{}" : inv.getStack(i).getNbt().asString()));
			if (!Registry.ITEM.getId(inv.getStack(i).getItem()).toString().equalsIgnoreCase("minecraft:air"))
				contents.put(i, Registry.ITEM.getId(inv.getStack(i).getItem()).toString() + (inv.getStack(i).getNbt() == null ? "{}" : inv.getStack(i).getNbt().asString()) + inv.getStack(i).getCount());
		}
		
        Gson gsonObj = new Gson();
        String jsonStr = gsonObj.toJson(contents);
        
        return Base64.getEncoder().encodeToString(jsonStr.getBytes());		
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void serializeToInventory(Inventory inv, String inventoryJsonB64) throws CommandSyntaxException {
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(new String(Base64.getDecoder().decode(inventoryJsonB64)), Map.class);
		inv.clear();
		for (Entry<String, String> itemStack : map.entrySet()) {
			String identifier = "";
			String countStr = "";
			
			int count = 0;
			
			for (String c : itemStack.getValue().split("")) {
				if (!c.startsWith("{")) {
					identifier += c;
				} else {
					break;
				}
			}
			
			for (String c : StringUtils.reverseString(itemStack.getValue()).split("")) {
				if (!c.startsWith("}")) {
					countStr += c;
				} else {
					break;
				}
			}
			
			count = Integer.parseInt(StringUtils.reverseString(countStr));

			ItemStack stack = new ItemStack(Registry.ITEM.get(new Identifier(identifier)));
			stack.setNbt(StringNbtReader.parse(StringUtils.reverseString(StringUtils.reverseString(itemStack.getValue().replaceFirst(Pattern.quote(identifier), "")).replaceFirst(Pattern.quote(countStr), ""))));
			stack.setCount(count);
			
			inv.setStack(Integer.parseInt(itemStack.getKey()), stack);
			
		}
	}
	
}
