package com.lastworld.client.render;

import com.lastworld.LastWorld;
import com.lastworld.client.ClientModEvents;
import com.lastworld.client.model.ObserverModel;
import com.lastworld.entity.ObserverEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ObserverRenderer extends MobRenderer<ObserverEntity, ObserverModel<ObserverEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(LastWorld.MODID, "textures/entity/observer.png");

    public ObserverRenderer(EntityRendererProvider.Context context) {
        super(context, new ObserverModel<>(context.bakeLayer(ClientModEvents.OBSERVER_LAYER)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(ObserverEntity entity) {
        return TEXTURE;
    }
}
