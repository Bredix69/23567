package com.lastworld.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MemoryNoteItem extends Item {
    public MemoryNoteItem(Properties properties) { super(properties); }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.literal("§8[Запись] §7Ты уверен, что здесь раньше никого не было?"));
            serverPlayer.getCooldowns().addCooldown(this, 30);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
