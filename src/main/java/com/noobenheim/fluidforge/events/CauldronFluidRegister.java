package com.noobenheim.fluidforge.events;

import com.noobenheim.fluidforge.registry.ModBlocks;
import com.noobenheim.fluidforge.registry.ModFluids;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;

public class CauldronFluidRegister {
    public static void registerMilkCauldron(RegisterCauldronFluidContentEvent event) {
        event.register(ModBlocks.MILK_CAULDRON.get(), ModFluids.MILK.get(), 3000, LayeredCauldronBlock.LEVEL);
    }
}
