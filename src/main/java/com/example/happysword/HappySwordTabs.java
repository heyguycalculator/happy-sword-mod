package com.example.happysword;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HappySwordTabs {
	public static final ItemGroup DO_NOT_TOUCH_TAB = FabricItemGroup.builder()
		.displayName(Text.literal("DO NOT TOUCH"))
		.icon(() -> new ItemStack(net.minecraft.item.Items.BARRIER))
		.entries((displayContext, entries) -> {
			entries.add(HappySwordItems.HAPPY_SWORD);
		})
		.build();

	public static void register() {
		Registry.register(Registries.ITEM_GROUP, new Identifier("happysword", "do_not_touch"), DO_NOT_TOUCH_TAB);
	}
}
