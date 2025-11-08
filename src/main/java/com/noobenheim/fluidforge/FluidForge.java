package com.noobenheim.fluidforge;

import com.noobenheim.fluidforge.client.Extensions;
import com.noobenheim.fluidforge.client.particle.ParticleFactory;
import com.noobenheim.fluidforge.events.CauldronFluidRegister;
import com.noobenheim.fluidforge.registry.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(FluidForge.MODID)
public class FluidForge {
    public static final String MODID = "fluidforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FluidForge(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(Extensions::register);
        modEventBus.addListener(CauldronFluidRegister::registerMilkCauldron);
        modEventBus.addListener(ParticleFactory::registerParticleFactories);
    }
}
