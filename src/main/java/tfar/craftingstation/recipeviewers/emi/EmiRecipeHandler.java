package tfar.craftingstation.recipeviewers.emi;

import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiPlayerInventory;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import tfar.craftingstation.CraftingStationMenu;

import java.util.List;

public class EmiRecipeHandler implements StandardRecipeHandler<CraftingStationMenu> {

    @Override
    public List<Slot> getInputSources(CraftingStationMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < handler.slots.size(); ++i) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(CraftingStationMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public @Nullable Slot getOutputSlot(CraftingStationMenu handler) {
        return handler.slots.get(0);
    }

    @Override
    public EmiPlayerInventory getInventory(AbstractContainerScreen<CraftingStationMenu> screen) {
        return StandardRecipeHandler.super.getInventory(screen);
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext<CraftingStationMenu> context) {
        return StandardRecipeHandler.super.craft(recipe, context);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe emiRecipe) {
        return emiRecipe.getCategory() == VanillaEmiRecipeCategories.CRAFTING && emiRecipe.supportsRecipeTree();
    }
}
