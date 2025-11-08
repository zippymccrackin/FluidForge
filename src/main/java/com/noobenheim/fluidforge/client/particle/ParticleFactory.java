package com.noobenheim.fluidforge.client.particle;

import com.noobenheim.fluidforge.registry.ModParticles;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class ParticleFactory {
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSprite(ModParticles.MILK_DRIP_PARTICLE.get(), MilkParticle::createHangParticle);
    }
}
