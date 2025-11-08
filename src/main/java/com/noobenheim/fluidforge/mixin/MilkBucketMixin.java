package com.noobenheim.fluidforge.mixin;

import com.noobenheim.fluidforge.registry.ModBlocks;
import com.noobenheim.fluidforge.registry.ModFluids;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

import static net.minecraft.world.item.BucketItem.getEmptySuccessItem;

@Mixin(MilkBucketItem.class)
public class MilkBucketMixin {
    Fluid content = null;

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void overrideMilkUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if( this.content == null ) this.content = ModFluids.MILK.get();

        if( !player.isCrouching() || level.isClientSide() ) return;

        HitResult hit = player.pick(5.0D, 0.0F, false);
        if( !(hit instanceof BlockHitResult bhr) ) return;
        if( bhr.getType() == HitResult.Type.MISS ) {
            cir.setReturnValue(InteractionResultHolder.pass(stack));
            return;
        } else if( bhr.getType() != HitResult.Type.BLOCK ) {
            cir.setReturnValue(InteractionResultHolder.pass(stack));
            return;
        }

        BlockPos pos = bhr.getBlockPos();
        Direction direction = bhr.getDirection();
        BlockPos pos1 = pos.relative(direction);

        if( !level.mayInteract(player, pos) || !player.mayUseItemAt(pos1, direction, stack) ) {
            cir.setReturnValue(InteractionResultHolder.fail(stack));
            return;
        }
        BlockState blockState = level.getBlockState(pos);

        // see if block is a cauldron
        if( blockState.getBlock() instanceof CauldronBlock ) {
            ItemInteractionResult result =  blockState.useItemOn(stack, level, player, hand, bhr);

            if( result.consumesAction() ) {
                cir.setReturnValue(InteractionResultHolder.sidedSuccess(stack, level.isClientSide()));
                return;
            }
        }

        BlockPos pos2 = canBlockContainFluid(player, level, pos, blockState) ? pos : pos1;

        if( this.emptyContents(player, level, pos2, bhr, stack) ) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, pos2, stack);
            }

            player.awardStat(Stats.ITEM_USED.get(Items.MILK_BUCKET));
            ItemStack itemstack1 = ItemUtils.createFilledResult(stack, player, getEmptySuccessItem(stack, player));
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack1, level.isClientSide()));
            return;
        } else {
            cir.setReturnValue(InteractionResultHolder.fail(stack));
            return;
        }
    }

    protected boolean canBlockContainFluid(@Nullable Player player, Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(player, worldIn, posIn, blockstate, this.content);
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
        SoundEvent soundevent = this.content.getFluidType().getSound(player, level, pos, net.neoforged.neoforge.common.SoundActions.BUCKET_EMPTY);
        if(soundevent == null) soundevent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        level.playSound(player, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result, @Nullable ItemStack container) {
        if (!(this.content instanceof FlowingFluid flowingfluid)) {
            return false;
        }

        Block $$7;
        boolean $$8;
        BlockState blockstate;
        boolean flag2;
        label82: {
            blockstate = level.getBlockState(pos);
            $$7 = blockstate.getBlock();
            $$8 = blockstate.canBeReplaced(this.content);
            label70:
            if (!blockstate.isAir() && !$$8) {
                if ($$7 instanceof LiquidBlockContainer liquidblockcontainer
                        && liquidblockcontainer.canPlaceLiquid(player, level, pos, blockstate, this.content)) {
                    break label70;
                }

                flag2 = false;
                break label82;
            }

            flag2 = true;
        }

        boolean flag1 = flag2;
        java.util.Optional<net.neoforged.neoforge.fluids.FluidStack> containedFluidStack = java.util.Optional.ofNullable(container).flatMap(net.neoforged.neoforge.fluids.FluidUtil::getFluidContained);
        if (!flag1) {
            return result != null && this.emptyContents(player, level, result.getBlockPos().relative(result.getDirection()), null, container);
        } else if (containedFluidStack.isPresent() && this.content.getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack.get())) {
            this.content.getFluidType().onVaporize(player, level, pos, containedFluidStack.get());
            return true;
        } else if (level.dimensionType().ultraWarm() && this.content.is(FluidTags.WATER)) {
            int l = pos.getX();
            int i = pos.getY();
            int j = pos.getZ();
            level.playSound(
                    player,
                    pos,
                    SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    0.5F,
                    2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F
            );

            for (int k = 0; k < 8; k++) {
                level.addParticle(
                        ParticleTypes.LARGE_SMOKE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0, 0.0, 0.0
                );
            }

            return true;
        } else {
            if ($$7 instanceof LiquidBlockContainer liquidblockcontainer1 && liquidblockcontainer1.canPlaceLiquid(player, level, pos, blockstate,content)) {
                liquidblockcontainer1.placeLiquid(level, pos, blockstate, flowingfluid.getSource(false));
                this.playEmptySound(player, level, pos);
                return true;
            }

            if (!level.isClientSide && $$8 && !blockstate.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (!level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(player, level, pos);
                return true;
            }
        }
    }
}
