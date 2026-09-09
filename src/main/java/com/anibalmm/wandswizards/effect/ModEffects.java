package com.anibalmm.wandswizards.effect;

import com.anibalmm.wandswizards.WandsWizards;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = 
        DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, WandsWizards.MODID);

    public static final DeferredHolder<MobEffect, FrozenEffect> FROZEN_EFFECT = MOB_EFFECTS.register("frozen", () -> new FrozenEffect(MobEffectCategory.HARMFUL, 0x9AD9EA));
    
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }

}
