package com.anibalmm.wandswizards.item.custom;

import com.anibalmm.wandswizards.entity.custom.FrozenProjectileEntity;



import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceWizardWand extends Item {

    public IceWizardWand(Properties properties) {
        super(properties);
        
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
        Projectile.spawnProjectileFromRotation((source, l, itemStack) -> new FrozenProjectileEntity(level, player.position().x(), player.getEyePosition().y(), player.position().z()), serverLevel, stack, player, -0.5F, 0.5F, 1.0F);
         //damage item
         player.getItemInHand(hand).hurtAndBreak(1, player, hand);
         //play sound
         level.playSound((Entity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
         //spawn particles
         
      }
      
      return InteractionResult.SUCCESS;
   }
    

}
