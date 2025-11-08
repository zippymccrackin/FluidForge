package com.noobenheim.fluidforge.registry;

import com.noobenheim.fluidforge.block.MilkCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.noobenheim.fluidforge.FluidForge.MODID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<LiquidBlock> MILK_FLUID = BLOCKS.register("milk_fluid",
            ()-> new LiquidBlock(ModFluids.MILK.get(),
                            BlockBehaviour.Properties.of()
                                    .noCollission()
                                    .replaceable()
                                    .liquid()
                                    .strength(100.0F)
                                    .noLootTable()
                    ));

    public static final DeferredBlock<Block> MILK_CAULDRON = BLOCKS.register("milk_cauldron", MilkCauldronBlock::new);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
