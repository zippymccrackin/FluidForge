package com.noobenheim.fluidforge.registry;

import com.noobenheim.fluidforge.fluid.MilkFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.noobenheim.fluidforge.FluidForge.MODID;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MODID);

    public static final DeferredHolder<Fluid, FlowingFluid> MILK = FLUIDS.register("milk", () -> new MilkFluid.Source(ModFluids.MILK_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MILK_FLOWING = FLUIDS.register("flowing_milk", () -> new MilkFluid.Flowing(ModFluids.MILK_PROPERTIES));

    public static final MilkFluid.Properties MILK_PROPERTIES = new MilkFluid.Properties(
            ModFluidTypes.MILK_FLUID_TYPE,
            ModFluids.MILK,
            ModFluids.MILK_FLOWING)
            .bucket(()->Items.MILK_BUCKET)
            .block(ModBlocks.MILK_FLUID);

    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }
}