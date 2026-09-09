package com.anibalmm.wandswizards.datagen;

import com.anibalmm.wandswizards.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner{

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        public String getName() {
            return "WandsWizards Recipes";
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider,recipeOutput);
        }
    }

    @Override
    protected void buildRecipes() {
        iceWizardWand(ModItems.ICE_WIZARD_WAND.get());
    }

    protected void iceWizardWand(Item result) {
        shaped(RecipeCategory.COMBAT, result)
                .define('I', Blocks.ICE)
                .define('R', Items.REDSTONE)
                .define('G',Items.GLOWSTONE_DUST)
                .define('U',Items.GUNPOWDER)
                .pattern(" UR")
                .pattern(" IG")
                .pattern("I  ")
                .unlockedBy(getHasName(Blocks.ICE), has(Blocks.ICE))
                .unlockedBy(getHasName(Items.GUNPOWDER),has(Items.GUNPOWDER))
                .unlockedBy(getHasName(Items.REDSTONE),has(Items.REDSTONE))
                .unlockedBy(getHasName(Items.GLOWSTONE_DUST),has(Items.GLOWSTONE_DUST))
                .group(getItemName(result))
                .save(output,"wandswizards:ice_wand_wizard_recipe");
    }
}
