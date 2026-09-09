package com.anibalmm.wandswizards.entity.client;

import com.anibalmm.wandswizards.WandsWizards;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayerLocations {
    public static final ModelLayerLocation FROZEN_PROJECTILE =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(WandsWizards.MODID, "frozen_projectile"), "main");
}
