package com.anibalmm.wandswizards.datagen;

import com.anibalmm.wandswizards.WandsWizards;
import com.anibalmm.wandswizards.item.ModItems;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, WandsWizards.MODID);
    }

      @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateIceWizardWand(ModItems.ICE_WIZARD_WAND.get(),itemModels);

    }

    public void generateIceWizardWand(Item item, ItemModelGenerators itemModels) {
        ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        itemModels.itemModelOutput.accept(item, itemModels.createFlatModelDispatch(flatModel,ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_in_hand"))));
    }
}
