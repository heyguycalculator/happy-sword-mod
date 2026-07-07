package com.example.happysword;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HappySwordItems {
	public static final Item HAPPY_SWORD = registerItem("happy_sword",
		new HappySword(ToolMaterials.NETHERITE, 3, -2.4f, new Item.Settings()));

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier("happysword", name), item);
	}

	public static void register() {
		// Items are registered via the static initializers above
	}
}
