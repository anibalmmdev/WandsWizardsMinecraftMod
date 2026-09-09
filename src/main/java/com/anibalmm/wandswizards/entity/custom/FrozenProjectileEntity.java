package com.anibalmm.wandswizards.entity.custom;

import com.anibalmm.wandswizards.effect.ModEffects;
import com.anibalmm.wandswizards.entity.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class FrozenProjectileEntity extends AbstractHurtingProjectile {
    public int amplification = 1;

    public FrozenProjectileEntity(EntityType<? extends FrozenProjectileEntity> type, Level level) {
        super(type, level);
    }

    public FrozenProjectileEntity(LivingEntity shooter, Level level, Vec3 direction) {
        super(ModEntities.FROZENPROJECTILE.get(), shooter, direction, level);
    }

    public FrozenProjectileEntity(Level level, double x, double y, double z) {
        super(ModEntities.FROZENPROJECTILE.get(), x, y, z, level);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        this.discard();

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Level entitylevel = this.level();
        if (entitylevel instanceof ServerLevel serverLevel) {
            Entity hitEntity = hitResult.getEntity();

            boolean wasHurt;
            if (hitEntity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.FROZEN_EFFECT, 120, amplification), this.getEffectSource());
                DamageSource damageSource = this.damageSources().thrown(this, this.getOwner());
                wasHurt = hitEntity.hurtServer(serverLevel, damageSource, 2.0F);
                entitylevel.playSound((Entity)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_HURT_FREEZE, SoundSource.NEUTRAL, 1.5F, 0.4F / (livingEntity.getRandom().nextFloat() * 0.4F + 0.8F));
                this.discard();
            }
        }

    }

    @Override
    public void tick() {
        super.tick();
        Vec3 newPosition = this.position().add(this.getDeltaMovement());
        if (this.level().isClientSide()) {
            Vec3 particleOrigin = newPosition.subtract(this.getDeltaMovement());
            this.spawnParticles(particleOrigin, this.getDeltaMovement());
        }
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return ParticleTypes.FIREWORK;
    }

    private void spawnParticles(Vec3 origin, Vec3 movement) {

    }
}