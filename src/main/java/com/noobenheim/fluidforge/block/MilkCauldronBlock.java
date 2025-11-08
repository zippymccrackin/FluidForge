package com.noobenheim.fluidforge.block;

import com.noobenheim.fluidforge.registry.ModBlocks;
import com.noobenheim.fluidforge.registry.ModFluids;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;

public class MilkCauldronBlock extends LayeredCauldronBlock {
    public MilkCauldronBlock() {
        super(Biome.Precipitation.NONE, CauldronInteraction.newInteractionMap("milk"), BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));

        this.interactions.map().put(Items.BUCKET,
                ((state, level, pos, player, hand, stack) ->
                        CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), blockstate -> blockstate.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BUCKET_FILL)));

        CauldronInteraction.EMPTY.map().put(Items.MILK_BUCKET,
                ((state, level, pos, player, hand, stack) ->
                        CauldronInteraction.emptyBucket(level, pos, player, hand, stack, ModBlocks.MILK_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY)));
    }

    @Override
    protected boolean canReceiveStalactiteDrip(Fluid fluid) {
        return fluid == ModFluids.MILK.get();
    }
}
