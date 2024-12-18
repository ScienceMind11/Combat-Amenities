package net.hollowed.backslot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.hollowed.backslot.networking.*;
import net.hollowed.backslot.util.TransformResourceReloadListener;
import net.minecraft.resource.ResourceType;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Backslot implements ModInitializer {
	public static final String MOD_ID = "combatamenities";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Json stuff
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new TransformResourceReloadListener());

		PayloadTypeRegistry.playC2S().register(BackslotPacketPayload.ID, BackslotPacketPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BackSlotCreativeClientPacketPayload.BACKSLOT_CREATIVE_CLIENT_PACKET_ID, BackSlotCreativeClientPacketPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(BackSlotClientPacketPayload.BACKSLOT_CLIENT_PACKET_ID, BackSlotClientPacketPayload.CODEC);

        BackSlotServerPacket.registerServerPacket();
		BackSlotCreativeClientPacket.registerClientPacket();

		LOGGER.info("It is time for backing and slotting");
	}

	public static final GameRules.Key<GameRules.BooleanRule> KEEP_BACK_SLOT_ITEM =
			GameRuleRegistry.register("keepBackSlotItem", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(false));
}