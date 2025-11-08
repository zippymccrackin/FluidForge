package com.noobenheim.fluidforge.fluid;

import com.noobenheim.fluidforge.registry.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MilkFluid extends BaseFlowingFluid {
    protected MilkFluid(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getAmount(FluidState state) {
        return 8;
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return ModParticles.MILK_DRIP_PARTICLE.get();
    }

    public static class Flowing extends MilkFluid {
        public Flowing(Properties properties) {
            super(properties);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }
    }

    public static class Source extends MilkFluid {
        public Source(Properties properties) {
            super(properties);
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
