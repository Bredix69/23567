package com.lastworld.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ObserverEntity extends Monster {
    private int vanishTimer = 0;
    private int stareTicks = 0;

    public ObserverEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.xpReward = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.FOLLOW_RANGE, 28.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.55D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        Player target = this.level().getNearestPlayer(this, 24.0D);
        if (target != null && this.tickCount % 20 == 0) {
            Vec3 delta = target.position().subtract(this.position());
            this.setYRot((float)(Math.atan2(delta.z, delta.x) * (180F / Math.PI)) - 90F);
            this.yRotO = this.getYRot();
            stareTicks++;
        }

        if (vanishTimer > 0) {
            vanishTimer--;
            if (vanishTimer == 0 && this.level() instanceof ServerLevel) {
                this.discard();
            }
        } else if (target != null && this.tickCount > 100 && this.random.nextInt(900) == 0) {
            // Rarely leave after being noticed, keeping the encounter eerie rather than combat-focused.
            vanishTimer = 1;
        }
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        // The Observer can be fought, but it is deliberately difficult to remove.
        return super.hurt(source, amount);
    }
}
