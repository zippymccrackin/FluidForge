package com.noobenheim.fluidforge.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.noobenheim.fluidforge.FluidForge.MODID;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MILK_DRIP_PARTICLE = PARTICLES.register("dripping_milk", ()-> new SimpleParticleType(false));

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}
