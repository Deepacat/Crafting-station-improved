package tfar.craftingstation.slot;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A SlotItemHandler that properly marks its owning BlockEntity as dirty when modified.
 * This fixes the issue where SlotItemHandler.setChanged() only marks a dummy container,
 * causing the actual BlockEntity's state to not be saved.
 */
public class BigSlot extends SlotItemHandler {
  private final int index;
  @Nullable
  private final BlockEntity blockEntity;

  public BigSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    this(itemHandler, index, xPosition, yPosition, null);
  }

  public BigSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, @Nullable BlockEntity blockEntity) {
    super(itemHandler, index, xPosition, yPosition);
    this.index = index;
    this.blockEntity = blockEntity;
  }

  private void markBlockEntityChanged() {
    if (blockEntity != null) {
      Level level = blockEntity.getLevel();
      if (level != null) {
        blockEntity.setChanged();
        // Explicitly mark chunk as unsaved - BlockEntity.setChanged() alone is not sufficient
        if (level instanceof ServerLevel serverLevel) {
          serverLevel.getChunkAt(blockEntity.getBlockPos()).setUnsaved(true);
        }
      }
    }
  }

  @Override
  public void setChanged() {
    super.setChanged();
    markBlockEntityChanged();
  }

  @Override
  @Nonnull
  public ItemStack remove(int amount) {
    ItemStack result = super.remove(amount);
    // SlotItemHandler.remove() calls extractItem() but doesn't call setChanged()
    if (!result.isEmpty()) {
      markBlockEntityChanged();
    }
    return result;
  }

  @Override
  public void set(@Nonnull ItemStack stack) {
    super.set(stack);
    // super.set() calls setChanged(), but we ensure BlockEntity is marked too
    markBlockEntityChanged();
  }

  @Override
  public boolean isSameInventory(Slot other) {
    return other instanceof BigSlot && ((BigSlot) other).getItemHandler() == this.getItemHandler();
  }
}