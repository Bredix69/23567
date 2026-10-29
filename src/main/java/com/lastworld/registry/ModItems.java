package com.lastworld.registry;

import com.lastworld.LastWorld;
import com.lastworld.item.MemoryNoteItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LastWorld.MODID);

    public static final RegistryObject<Item> MEMORY_NOTE =
            ITEMS.register("memory_note", () -> new MemoryNoteItem(new Item.Properties().stacksTo(1)));

    private ModItems() {}
}
