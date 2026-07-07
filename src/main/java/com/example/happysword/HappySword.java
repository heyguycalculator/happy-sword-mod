package com.example.happysword;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.animal.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class HappySword extends SwordItem {
	public HappySword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.getWorld().isClient) {
			return super.postHit(stack, target, attacker);
		}

		if (target instanceof ServerPlayerEntity player) {
			HappyEffects.applyHappyEffects(player);
		} else if (target instanceof CowEntity cow) {
			HappyEffects.applyCowChaos(cow);
		}

		return super.postHit(stack, target, attacker);
	}
}
