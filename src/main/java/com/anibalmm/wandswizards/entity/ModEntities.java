package com.anibalmm.wandswizards.entity;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.entity.custom.FrozenProjectileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.createEntities(WandsWizards.MODID);

    public static final ResourceKey<EntityType<?>> FROZENPROJECTILE_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(WandsWizards.MODID, "frozen_projectile"));


    public static final Supplier<EntityType<FrozenProjectileEntity>> FROZENPROJECTILE = ENTITY_TYPES.register("frozen_projectile",
            () -> EntityType.Builder.<FrozenProjectileEntity>of(FrozenProjectileEntity::new, MobCategory.MISC).noLootTable()
                    .sized(0.6f, 0.6f).build(FROZENPROJECTILE_KEY));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
