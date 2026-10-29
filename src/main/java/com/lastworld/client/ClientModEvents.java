package com.lastworld.client;

import com.lastworld.LastWorld;
import com.lastworld.client.model.ObserverModel;
import com.lastworld.client.render.ObserverRenderer;
import com.lastworld.registry.ModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LastWorld.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    public static final ModelLayerLocation OBSERVER_LAYER =
            new ModelLayerLocation(new ResourceLocation(LastWorld.MODID, "observer"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OBSERVER_LAYER, ObserverModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.OBSERVER.get(), ObserverRenderer::new);
    }
}
