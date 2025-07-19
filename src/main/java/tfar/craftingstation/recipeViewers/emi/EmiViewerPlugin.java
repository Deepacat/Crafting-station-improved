package tfar.craftingstation.recipeViewers.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import tfar.craftingstation.init.ModMenuTypes;

@EmiEntrypoint
public class EmiViewerPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addRecipeHandler(ModMenuTypes.crafting_station, new EmiRecipeHandler());
    }
}
