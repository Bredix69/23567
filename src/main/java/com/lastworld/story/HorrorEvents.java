package com.lastworld.story;

import com.lastworld.entity.ObserverEntity;
import com.lastworld.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HorrorEvents {
    private final Map<UUID, Integer> playerTicks = new HashMap<>();
    private final Map<UUID, Integer> presence = new HashMap<>();
    private final Map<UUID, Boolean> introSeen = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        Player player = event.player;
        if (!(player.level() instanceof ServerLevel level) || player.isSpectator() || player.isCreative()) return;

        UUID id = player.getUUID();
        int ticks = playerTicks.merge(id, 1, Integer::sum);

        if (!introSeen.getOrDefault(id, false) && ticks > 100) {
            player.displayClientMessage(Component.literal("§8... §7Ветер стих. Где-то в темноте слышны шаги."), true);
            introSeen.put(id, true);
        }

        if (ticks % 1200 == 0) {
            int p = presence.merge(id, 1, Integer::sum);
            if (p == 2) {
                player.sendSystemMessage(Component.literal("§8[Последний мир] §7Тебе кажется, что за деревьями кто-то стоит."));
            } else if (p == 5) {
                player.sendSystemMessage(Component.literal("§8[Последний мир] §7Не оборачивайся слишком быстро."));
            }
        }

        // Every ~8-16 minutes, a rare observer encounter may occur at night in the overworld.
        if (level.dimension() == Level.OVERWORLD && level.isNight() && ticks > 600 && ticks % 200 == 0
                && level.random.nextInt(24) == 0 && level.getEntitiesOfClass(ObserverEntity.class,
                player.getBoundingBox().inflate(48)).isEmpty()) {
            BlockPos origin = player.blockPosition();
            double angle = level.random.nextDouble() * Math.PI * 2.0;
            int distance = 18 + level.random.nextInt(10);
            BlockPos spawn = origin.offset((int)(Math.cos(angle) * distance), 0, (int)(Math.sin(angle) * distance));
            spawn = level.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spawn);
            if (level.isLoaded(spawn) && level.isEmptyBlock(spawn) && level.getMaxLocalRawBrightness(spawn) > 0) {
                ObserverEntity observer = ModEntities.OBSERVER.get().create(level);
                if (observer != null) {
                    observer.moveTo(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                    level.addFreshEntity(observer);
                    player.sendSystemMessage(Component.literal("§8[Последний мир] §7Тебе показалось, что кто-то смотрит на тебя."));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID id = event.getEntity().getUUID();
        playerTicks.remove(id);
        presence.remove(id);
        introSeen.remove(id);
    }
}
