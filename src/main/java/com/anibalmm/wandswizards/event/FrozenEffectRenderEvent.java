package com.anibalmm.wandswizards.event;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.data.ModAttachments;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.minecraft.util.context.ContextKey;
 
@EventBusSubscriber(modid = WandsWizards.MODID)
public class FrozenEffectRenderEvent {
 
     // Create the frozen block context
    public static final ContextKey<MovingBlockRenderState> FREEZE_RENDER_KEY =
            new ContextKey<>(Identifier.fromNamespaceAndPath("wandswizards", "frozen_block"));
 
    // Check the entity modifiers for rendering the ice effect
    @SubscribeEvent
    public static void onRegisterRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
    //Any mob
        event.registerEntityModifier(
                new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {},
                (entity, state) -> state.setRenderData(FREEZE_RENDER_KEY, buildFrozenBlockState(entity))
        );
 
        // Players
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState state) {
                if (avatar instanceof LivingEntity living) {
                    state.setRenderData(FREEZE_RENDER_KEY, buildFrozenBlockState(living));
                }
            }
        });
    }
 
    // We create the "moving block" render
    private static MovingBlockRenderState buildFrozenBlockState(LivingEntity entity) {

        if (!entity.getData(ModAttachments.FROZEN_VISUAL)) {
            return null;
        }
 
        MovingBlockRenderState blockState = new MovingBlockRenderState();
        BlockPos pos = entity.blockPosition();
        blockState.blockState = Blocks.ICE.defaultBlockState();
        blockState.blockPos = pos;
        blockState.randomSeedPos = pos;
 
        Level level = entity.level();
        if (level instanceof ClientLevel clientLevel) {
            blockState.biome = clientLevel.getBiome(pos);
            blockState.cardinalLighting = clientLevel.cardinalLighting();
            blockState.lightEngine = clientLevel.getLightEngine();
        }
 
        return blockState;
    }
 
   

}
 