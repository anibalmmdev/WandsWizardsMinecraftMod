package com.anibalmm.wandswizards.event;

import java.util.HashMap;
import java.util.Map;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.data.ModAttachments;
import com.anibalmm.wandswizards.effect.ModEffects;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import javax.annotation.processing.SupportedSourceVersion;


@EventBusSubscriber(modid = WandsWizards.MODID)
public class FrozenEffectEvent {


    //Save the pose of the mob to freeze

    private record FrozenPose(Vec3 position, float xRot, float yRot, float yBodyRot, float yHeadRot,
                              double yPosePosition) {
    }

    private static final Map<Integer, FrozenPose> FROZEN_POSES = new HashMap<>();

    //if the mob is frozen we capture their whole pose on the pre tick
    @SubscribeEvent
    public static void onEntityPreTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Mob mob) || !(mob.level() instanceof ServerLevel)) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity entity && entity.hasEffect(ModEffects.FROZEN_EFFECT)) {
            // Stop movement by zeroing out X and Z velocity
            Vec3 delta = mob.getDeltaMovement();
            mob.setDeltaMovement(new Vec3(0.0, delta.y, 0.0));
            mob.goalSelector.getAvailableGoals().forEach(goal -> goal.stop());
            mob.targetSelector.getAvailableGoals().forEach(goal -> goal.stop());
            if (mob.level() instanceof ServerLevel level) {
                Brain brain = mob.getBrain();
                brain.stopAll(level, mob);
            }
            mob.setTarget(null);
            mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
            mob.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            mob.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
            mob.getNavigation().stop();
            mob.setAggressive(false);

            FROZEN_POSES.put(entity.getId(), new FrozenPose(
                    entity.position(),
                    entity.getXRot(),
                    entity.getYRot(),
                    entity.yBodyRot,
                    entity.yHeadRot,
                    entity.position().y
            ));
        }

    }

    @SubscribeEvent
    public static void onEntityRideEvent(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    //when the tick occurs we set the pose captured at the start when frozen
    @SubscribeEvent
    public static void onEntityPostTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Mob mob) || !(mob.level() instanceof ServerLevel)) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        FrozenPose pose = FROZEN_POSES.remove(entity.getId());
        /*if (pose == null) {
            return; // Entity not frozen
        }*/
        if(entity.hasEffect(ModEffects.FROZEN_EFFECT)){
        if (entity.onGround() || entity.isNoGravity()) {
            entity.resetFallDistance();
            if (entity.isNoGravity()) {
                entity.setNoGravity(false);
            }
            entity.setJumping(false);
            entity.setIsInPowderSnow(true);
        }
        entity.setSpeed(0.0F);
            float closeAngle = Mth.wrapDegrees90(entity.getYRot());
            float closeAnglex = Mth.wrapDegrees90(entity.getXRot());
            mob.setYRot(entity.getYRot() - closeAngle);
            mob.setXRot(entity.getXRot() - closeAnglex);
            mob.setYHeadRot(entity.getYRot());
            // Stop movement by zeroing out X and Z velocity
            mob.setDeltaMovement(new Vec3(0.0, -0.32, 0.0));
            mob.goalSelector.getAvailableGoals().forEach(goal -> goal.stop());
            mob.targetSelector.getAvailableGoals().forEach(goal -> goal.stop());
            if (mob.level() instanceof ServerLevel level) {
                Brain brain = mob.getBrain();
                brain.stopAll(level, mob);
            }
            mob.setTarget(null);
            mob.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
            mob.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            mob.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
            mob.getNavigation().stop();
            mob.setAggressive(false);

        entity.setPosRaw(pose.position.x,entity.position().y,pose.position.z);
        entity.setTicksFrozen(entity.getTicksRequiredToFreeze());}
    }


    //When the event is added we check its frozen and if the entity is a mob we remove their AI
    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().value() == ModEffects.FROZEN_EFFECT.value()
                && event.getEntity() instanceof Mob mob) {
            mob.setData(ModAttachments.FROZEN_VISUAL, true);
        }
    }

    // Give the AI back to the mob
    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        if (event.getEffectInstance() != null
                && event.getEffectInstance().getEffect().value() == ModEffects.FROZEN_EFFECT.value()) {
            clearFrozenState(event.getEntity());
        }

    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEffectInstance() != null
                && event.getEffectInstance().getEffect().value() == ModEffects.FROZEN_EFFECT.value()) {
            clearFrozenState(event.getEntity());
        }
    }

    private static void clearFrozenState(LivingEntity entity) {
        entity.setIsInPowderSnow(false);
        entity.setData(ModAttachments.FROZEN_VISUAL, false);
        if (entity instanceof Mob mob) {
            mob.setNoAi(false);
        }
    }

    //Interrupt jumping attempts
    @SubscribeEvent
    public static void onJump(LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.FROZEN_EFFECT)) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
        }
    }

    //Interrupt attacks
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    //Interrupt any action usage
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    //Interrupt item usage
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    //Interrupt block breaking
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    // Cancel vanilla damage freeze effect, we only want the visual shaking
    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(event.getEntity() instanceof Mob attackedMob) || !(attackedMob.level() instanceof ServerLevel level)) {
            return;
        }

        if (entity.hasEffect(ModEffects.FROZEN_EFFECT) && (event.getSource().is(DamageTypes.FREEZE) || event.getSource().is(DamageTypes.FALL))) {
            event.setCanceled(true);
        }

    }


    @SubscribeEvent
    public static void onMobAttack(LivingChangeTargetEvent event) {
        if (event.getEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityTeleport(EntityTeleportEvent.TeleportCommand event) {

        if (event.getEntity().asLivingEntity().hasEffect(ModEffects.FROZEN_EFFECT)) {
            event.setCanceled(true);
        }
    }


}