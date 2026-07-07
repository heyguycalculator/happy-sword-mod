package com.example.happysword;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.animal.CowEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registries;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

import java.util.Random;

public class HappyEffects {
	private static final Random RANDOM = new Random();

	public static void applyHappyEffects(ServerPlayerEntity player) {
		// Apply potion effects - staggered for continuous chaos
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0, false, false));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0, false, false));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 2, false, false));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 2, false, false));

		// Play ALL sounds at once - beautiful chaos
		playAllSounds(player);

		// Start the epileptic flash effect
		startEpilepticFlash(player);

		// Screen shake
		player.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket(
			net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket.THUNDER, 1.0F));
	}

	public static void applyCowChaos(CowEntity cow) {
		ServerWorld world = (ServerWorld) cow.getWorld();
		
		// Make it FLY with random velocities every tick
		CowChaosManager.addChaosCow(cow);

		// Stretch it vertically by scaling
		cow.setScale(3.0f);

		// Vibrate position rapidly
		for (int i = 0; i < 5; i++) {
			double offsetX = (RANDOM.nextDouble() - 0.5) * 2;
			double offsetY = RANDOM.nextDouble() * 2;
			double offsetZ = (RANDOM.nextDouble() - 0.5) * 2;
			cow.addVelocity(offsetX, offsetY, offsetZ);
		}

		// Spam particles everywhere around it
		for (int i = 0; i < 30; i++) {
			double x = cow.getX() + (RANDOM.nextDouble() - 0.5) * 3;
			double y = cow.getY() + RANDOM.nextDouble() * 3;
			double z = cow.getZ() + (RANDOM.nextDouble() - 0.5) * 3;
			world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 1, 
				RANDOM.nextGaussian() * 0.3, RANDOM.nextGaussian() * 0.3, RANDOM.nextGaussian() * 0.3, 0.5);
		}

		// Play VERY LOUD sounds for the cow
		playAllSounds(cow);

		// Blindness so it can't see
		cow.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0, false, false));

		// Confusion/nausea
		cow.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1, false, false));

		// Make it super fast and jumpy
		cow.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 5, false, false));
	}

	private static void playAllSounds(Entity entity) {
		ServerWorld world = (ServerWorld) entity.getWorld();
		
		// Get all sounds and play them rapidly
		int soundCount = 0;
		for (SoundEvent soundEvent : Registries.SOUND_EVENT) {
			if (soundCount < 40) { // Limit to prevent lag
				float pitch = 0.5f + RANDOM.nextFloat() * 1.5f;
				float volume = 0.1f + RANDOM.nextFloat() * 0.4f;
				world.playSoundFromEntity(null, entity, soundEvent, 
					net.minecraft.sound.SoundCategory.AMBIENT, volume, pitch);
				soundCount++;
			}
		}
	}

	private static void startEpilepticFlash(ServerPlayerEntity player) {
		// This will be handled by a tick event to continuously flash
		FlashEffectManager.addFlashingPlayer(player);
	}
}
