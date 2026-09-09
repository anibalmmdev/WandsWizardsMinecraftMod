package com.anibalmm.wandswizards.entity.client;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.entity.custom.FrozenProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class FrozenProjectileRenderer extends EntityRenderer<FrozenProjectileEntity, FrozenProjectileRendererState> {

    private FrozenProjectileModel model;

    public FrozenProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FrozenProjectileModel(context.bakeLayer(ModModelLayerLocations.FROZEN_PROJECTILE));
    }

    @Override
    public @NonNull FrozenProjectileRendererState createRenderState() {
        return new FrozenProjectileRendererState();
    }

    @Override
    public void extractRenderState(FrozenProjectileEntity entity, FrozenProjectileRendererState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        state.pitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        state.spinRotation = (entity.tickCount + partialTicks) * 15.0F;
    }
    public Identifier getTextureLocation() {
        return Identifier.fromNamespaceAndPath(WandsWizards.MODID, "textures/entity/frozenprojectile/frozenprojectile.png");
    }

    @Override
    public void submit(FrozenProjectileRendererState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.spinRotation));
        submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.entityCutout(getTextureLocation()),
                LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1,null, state.outlineColor, null);
        poseStack.popPose();
    }
}
