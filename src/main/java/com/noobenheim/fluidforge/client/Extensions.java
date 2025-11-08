package com.noobenheim.fluidforge.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noobenheim.fluidforge.FluidForge;
import com.noobenheim.fluidforge.registry.ModFluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class Extensions {
    public static final IClientFluidTypeExtensions MILK_FLUID = new IClientFluidTypeExtensions() {
        @Override
        public @NotNull ResourceLocation getStillTexture() {
            return ResourceLocation.fromNamespaceAndPath(FluidForge.MODID, "fluid/milk_still");
        }

        @Override
        public @NotNull ResourceLocation getFlowingTexture() {
            return ResourceLocation.fromNamespaceAndPath(FluidForge.MODID, "fluid/milk_flow");
        }

        @Override
        public @NotNull ResourceLocation getOverlayTexture() {
            return ResourceLocation.fromNamespaceAndPath(FluidForge.MODID, "fluid/milk_overlay");
        }

        @Override
        public @NotNull ResourceLocation getRenderOverlayTexture(Minecraft mc) {
            return ResourceLocation.fromNamespaceAndPath(FluidForge.MODID, "textures/misc/milk_fluid.png");
        }

        @Override
        public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return new Vector3f(1.0F, 1.0F, 1.0F);
        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(0.3F);
        }
    };

    public static void register(RegisterClientExtensionsEvent event) {
        event.registerFluidType(MILK_FLUID, ModFluidTypes.MILK_FLUID_TYPE);
    }
}
