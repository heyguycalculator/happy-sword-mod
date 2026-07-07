package com.example.happysword.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class AttackClientMixin {
	// This is a placeholder mixin - the actual attack handling happens server-side
	// The attacker sees no effects, only the victim does
}
