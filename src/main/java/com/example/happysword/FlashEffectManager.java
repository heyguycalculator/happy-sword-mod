package com.example.happysword;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public class FlashEffectManager {
	private static final Map<ServerPlayerEntity, Integer> FLASHING_PLAYERS = new WeakHashMap<>();
	private static final Random RANDOM = new Random();

	static {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			Iterator<Map.Entry<ServerPlayerEntity, Integer>> iterator = FLASHING_PLAYERS.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<ServerPlayerEntity, Integer> entry = iterator.next();
				ServerPlayerEntity player = entry.getKey();
				int ticks = entry.getValue() + 1;

				if (ticks > 100) { // 5 seconds at 20 ticks/second
					iterator.remove();
					continue;
				}

				// Rapid fire effects for epilepsy-like flashing
				if (ticks % 2 == 0) { // Every other tick for rapid flashing
					applyFlashEffect(player);
				}

				entry.setValue(ticks);
			}
		});
	}

	public static void addFlashingPlayer(ServerPlayerEntity player) {
		FLASHING_PLAYERS.put(player, 0);
	}

	private static void applyFlashEffect(ServerPlayerEntity player) {
		ServerWorld world = (ServerWorld) player.getWorld();

		// Alternate between bright and dark
		if (RANDOM.nextBoolean()) {
			// BRIGHT FLASH - nightvision
			player.networkHandler.sendPacket(
				new GameStateChangeS2CPacket(GameStateChangeS2CPacket.NOTCHING_ARROW_HOLD, 1.0F)
			);
		} else {
			// DARK FLASH - blindness spike
			player.networkHandler.sendPacket(
				new GameStateChangeS2CPacket(GameStateChangeS2CPacket.NOTCHING_ARROW_RELEASE, 0.0F)
			);
		}

		// Spam particles in front of player for visual chaos
		Vec3d look = player.getRotationVec(1.0f).multiply(3);
		for (int i = 0; i < 15; i++) {
			double x = player.getX() + look.x + (RANDOM.nextDouble() - 0.5) * 2;
			double y = player.getEyeY() + (RANDOM.nextDouble() - 0.5) * 2;
			double z = player.getZ() + look.z + (RANDOM.nextDouble() - 0.5) * 2;

			// Alternate particle colors for the flash effect
			if (RANDOM.nextBoolean()) {
				world.spawnParticles(ParticleTypes.FLASH, x, y, z, 2, 0, 0, 0, 0.1);
				world.spawnParticles(ParticleTypes.SOUL, x, y, z, 2, 
					RANDOM.nextGaussian() * 0.2, RANDOM.nextGaussian() * 0.2, RANDOM.nextGaussian() * 0.2, 0.3);
			} else {
				world.spawnParticles(ParticleTypes.SMOKE, x, y, z, 3, 
					RANDOM.nextGaussian() * 0.3, RANDOM.nextGaussian() * 0.3, RANDOM.nextGaussian() * 0.3, 0.1);
				world.spawnParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 1, 0, 0, 0, 0.15);
			}
		}

		// Random screen rotation via velocity
		if (RANDOM.nextInt(3) == 0) {
			player.addVelocity(
				(RANDOM.nextDouble() - 0.5) * 0.1,
				(RANDOM.nextDouble() - 0.5) * 0.1,
				(RANDOM.nextDouble() - 0.5) * 0.1
			);
		}
	}

	public static void initialize() {
		// Called during mod initialization to register the tick listener
	}
}
