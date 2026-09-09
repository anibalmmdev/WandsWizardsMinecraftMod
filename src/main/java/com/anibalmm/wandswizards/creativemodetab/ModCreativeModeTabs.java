package com.anibalmm.wandswizards.creativemodetab;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.item.ModItems;

import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs extends ModelProvider {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "wandswizards" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WandsWizards.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("wands_and_wizards_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ICE_WIZARD_WAND.get()))
                    .title(Component.translatable("creativetab.wandswizards.wands"))
                    .withTabsBefore(CreativeModeTabs.INGREDIENTS)
                    .displayItems((itemDisplayParameters, output) -> {
          
                        output.accept(ModItems.ICE_WIZARD_WAND);


                    }).build());

    public ModCreativeModeTabs(PackOutput output, String modId) {
        super(output, WandsWizards.MODID);
        
    }

      public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }



}
