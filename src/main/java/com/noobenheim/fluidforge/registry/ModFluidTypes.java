package com.noobenheim.fluidforge.registry;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.noobenheim.fluidforge.FluidForge.MODID;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, MODID);

    public static final DeferredHolder<FluidType, FluidType> MILK_FLUID_TYPE = FLUID_TYPES.register("milk",
            ()-> new FluidType(FluidType.Properties.create()
                    .descriptionId("block.milk_fluid")
                    .temperature(32)
                    .lightLevel(0)
                    .density(1024)
                    .viscosity(1024)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .addDripstoneDripping(1.0F, ModParticles.MILK_DRIP_PARTICLE.get(), ModBlocks.MILK_CAULDRON.get(), SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON)
                    .supportsBoating(true)
            ));

    public static void register(IEventBus bus) {
        FLUID_TYPES.register(bus);
    }
}
