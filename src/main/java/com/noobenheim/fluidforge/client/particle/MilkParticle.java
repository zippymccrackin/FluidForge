package com.noobenheim.fluidforge.client.particle;

import com.noobenheim.fluidforge.registry.ModFluids;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class MilkParticle extends DripParticle {
    protected MilkParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, ModFluids.MILK.get());

        this.setColor(1.0F, 1.0F, 1.0F);

        this.scale(0.75F);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static TextureSheetParticle createHangParticle(
            SimpleParticleType type,
            ClientLevel level,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        return new MilkParticle(level, x, y, z);
    }
}
