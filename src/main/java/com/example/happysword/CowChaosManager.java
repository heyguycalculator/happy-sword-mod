package com.example.happysword;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.animal.CowEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class CowChaosManager {
	private static final Set<CowEntity> CHAOS_COWS = Collections.newSetFromMap(new WeakHashMap<>());
	private static final Random RANDOM = new Random();

	static {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			Iterator<CowEntity> iterator = CHAOS_COWS.iterator();
			while (iterator.hasNext()) {
				CowEntity cow = iterator.next();
				if (cow.isRemoved() || cow.isDead()) {
					iterator.remove();
					continue;
				}

				applyCowChaosEffect(cow);
			}
		});
	}

	public static void addChaosCow(CowEntity cow) {
		CHAOS_COWS.add(cow);
	}

	private static void applyCowChaosEffect(CowEntity cow) {
		// Random violent movement
		double vx = (RANDOM.nextDouble() - 0.5) * 1.5;
		double vy = RANDOM.nextDouble() * 1.2; // Bias upward for flying effect
		double vz = (RANDOM.nextDouble() - 0.5) * 1.5;
		cow.setVelocity(vx, vy, vz);

		// Spin the cow
		cow.setYaw(cow.getYaw() + (RANDOM.nextFloat() * 60 - 30));
		cow.setPitch((RANDOM.nextFloat() * 60 - 30));

		// Particle explosion around cow
		ServerWorld world = (ServerWorld) cow.getWorld();
		for (int i = 0; i < 8; i++) {
			double x = cow.getX() + (RANDOM.nextDouble() - 0.5) * 2;
			double y = cow.getY() + RANDOM.nextDouble() * 2.5;
			double z = cow.getZ() + (RANDOM.nextDouble() - 0.5) * 2;

			world.spawnParticles(
				RANDOM.nextBoolean() ? ParticleTypes.FLASH : ParticleTypes.SOUL_FIRE_FLAME,
				x, y, z, 1, RANDOM.nextGaussian() * 0.4, RANDOM.nextGaussian() * 0.4, 
				RANDOM.nextGaussian() * 0.4, 0.5
			);
		}

		// Sometimes teleport it slightly (glitch effect)
		if (RANDOM.nextInt(5) == 0) {
			double newX = cow.getX() + (RANDOM.nextDouble() - 0.5) * 3;
			double newY = cow.getY() + RANDOM.nextDouble() * 2;
			double newZ = cow.getZ() + (RANDOM.nextDouble() - 0.5) * 3;
			cow.updatePosition(newX, newY, newZ);
		}

		// Rapid scaling for "stretching" effect
		cow.setScale(2.0f + (float) Math.sin(System.currentTimeMillis() * 0.01f) * 0.5f);
	}

	public static void initialize() {
		// Called during mod initialization
	}
}
