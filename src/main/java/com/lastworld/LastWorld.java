package com.lastworld;

import com.lastworld.registry.ModEntities;
import com.lastworld.registry.ModItems;
import com.lastworld.story.HorrorEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LastWorld.MODID)
public class LastWorld {
    public static final String MODID = "lastworld";

    public LastWorld() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITY_TYPES.register(bus);
        ModItems.ITEMS.register(bus);
        MinecraftForge.EVENT_BUS.register(new HorrorEvents());
    }
}
