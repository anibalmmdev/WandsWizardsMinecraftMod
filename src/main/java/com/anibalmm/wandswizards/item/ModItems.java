package com.anibalmm.wandswizards.item;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.item.custom.IceWizardWand;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItems {
    
    // Create a Deferred Register to hold Items which will all be registered under the "wandswizards" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WandsWizards.MODID);
    public static final DeferredItem<Item> ICE_WIZARD_WAND = ITEMS.registerItem("ice_wizard_wand",properties -> new IceWizardWand(properties.durability(1000).repairable(Items.ICE).useCooldown(3.5f)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
