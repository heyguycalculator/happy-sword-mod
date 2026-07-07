package com.example.happysword;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HappySwordMod implements ModInitializer {
	public static final String MOD_ID = "happysword";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Happy Sword Mod initialized!");
		HappySwordItems.register();
		HappySwordTabs.register();
		FlashEffectManager.initialize();
		CowChaosManager.initialize();
	}
}
