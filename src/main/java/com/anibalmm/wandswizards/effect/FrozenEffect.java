package com.anibalmm.wandswizards.effect;


import com.anibalmm.wandswizards.data.ModAttachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrozenEffect extends MobEffect {

    public FrozenEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

   @Override
   public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplification) {
        // For the shaking effect
       entity.setTicksFrozen(entity.getTicksRequiredToFreeze());
       entity.setData(ModAttachments.FROZEN_VISUAL, true);
       return super.applyEffectTick(serverLevel, entity, amplification);
   }

   @Override
   public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
       return true;}
    
    
   }

   


