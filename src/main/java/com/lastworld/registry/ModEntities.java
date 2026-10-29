package com.lastworld.registry;

import com.lastworld.LastWorld;
import com.lastworld.entity.ObserverEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LastWorld.MODID);

    public static final RegistryObject<EntityType<ObserverEntity>> OBSERVER =
            ENTITY_TYPES.register("observer", () -> EntityType.Builder
                    .of(ObserverEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.4F)
                    .clientTrackingRange(32)
                    .build("observer"));

    private ModEntities() {}
}
