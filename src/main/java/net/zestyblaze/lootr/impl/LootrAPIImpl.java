package net.zestyblaze.lootr.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.zestyblaze.lootr.api.ILootrAPI;
import net.zestyblaze.lootr.api.LootFiller;
import net.zestyblaze.lootr.data.DataStorage;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public class LootrAPIImpl implements ILootrAPI {
    @Override
    public boolean clearPlayerLoot(UUID id) {
        return DataStorage.clearInventories(id);
    }

    @Override
    public @Nullable MenuProvider getModdedMenu(Level world, UUID id, BlockPos pos, ServerPlayer player, BaseContainerBlockEntity blockEntity, LootFiller filler, Supplier<ResourceLocation> tableSupplier, LongSupplier seedSupplier) {
        return DataStorage.getInventory(world, id, pos, player, blockEntity, filler, tableSupplier, seedSupplier);
    }

    @Override
    public @Nullable MenuProvider getModdedMenu(Level world, UUID id, BlockPos pos, ServerPlayer player, IntSupplier sizeSupplier, Supplier<Component> displaySupplier, LootFiller filler, Supplier<ResourceLocation> tableSupplier, LongSupplier seedSupplier) {
        return DataStorage.getInventory(world, id, pos, player, sizeSupplier, displaySupplier, filler, tableSupplier, seedSupplier);
    }
}
