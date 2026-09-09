package com.anibalmm.wandswizards.entity.client;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.event.FrozenEffectRenderEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
 
@EventBusSubscriber(modid = WandsWizards.MODID)
public class FrozenIceRenderer{
 
   
    private static final float PADDING = 0.35f;
 
    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?, ?> event) {
        renderFrozenIce(event.getRenderState(), event.getPoseStack(), event.getSubmitNodeCollector());
    }
 
    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post<?> event) {
        renderFrozenIce(event.getRenderState(), event.getPoseStack(), event.getSubmitNodeCollector());
    }
 
  
    private static void renderFrozenIce(EntityRenderState renderState, PoseStack poseStack,
                                         SubmitNodeCollector collector) {
        MovingBlockRenderState blockState = renderState.getRenderData(FrozenEffectRenderEvent.FREEZE_RENDER_KEY);
        if (blockState == null) {
            return;
        }
        float width = renderState.boundingBoxWidth + PADDING;
        float height = renderState.boundingBoxHeight + PADDING;
 
        poseStack.pushPose();
        poseStack.scale(width, height, width);
        poseStack.translate(-0.5, 0, -0.5);
 
        collector.submitMovingBlock(poseStack, blockState, 0);
 
        poseStack.popPose();
    }
}