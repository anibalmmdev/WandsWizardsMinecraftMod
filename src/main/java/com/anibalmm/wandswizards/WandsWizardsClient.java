package com.anibalmm.wandswizards;

import com.anibalmm.wandswizards.entity.ModEntities;
import com.anibalmm.wandswizards.entity.client.FrozenProjectileModel;
import com.anibalmm.wandswizards.entity.client.FrozenProjectileRenderer;
import com.anibalmm.wandswizards.entity.client.ModModelLayerLocations;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = WandsWizards.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = WandsWizards.MODID, value = Dist.CLIENT)
public class WandsWizardsClient {
    public WandsWizardsClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        EntityRenderers.register(ModEntities.FROZENPROJECTILE.get(), FrozenProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

        event.registerLayerDefinition(ModModelLayerLocations.FROZEN_PROJECTILE, FrozenProjectileModel::createBodyLayer);
    }
}
