package org.maxgamer.quickshop.integration.plotsquared;

import com.plotsquared.core.configuration.Caption;
import com.plotsquared.core.configuration.Captions;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.types.BooleanFlag;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.integration.IntegrateStage;
import org.maxgamer.quickshop.integration.IntegratedPlugin;
import org.maxgamer.quickshop.integration.IntegrationStage;
import org.maxgamer.quickshop.util.Util;

import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
@IntegrationStage(loadStage = IntegrateStage.onEnableAfter)
public class PlotSquaredIntegrationV5 implements IntegratedPlugin {
    private final QuickShop plugin;
    private final boolean whiteList;
    private QuickshopCreateFlag createFlag;
    private QuickshopTradeFlag tradeFlag;

    public PlotSquaredIntegrationV5(QuickShop plugin) {
        this.plugin = plugin;
        this.whiteList = plugin.getConfig().getBoolean("integration.plotsquared.whitelist-mode");
    }

    @Override
    public @NotNull String getName() {
        return "PlotSquared";
    }

    @Override
    public boolean canCreateShopHere(@NotNull Player player, @NotNull Location location) {
        com.plotsquared.core.location.Location pLocation =
                new com.plotsquared.core.location.Location(
                        location.getWorld().getName(),
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ());
        Plot plot = pLocation.getPlot();
        if (plot == null) {
            return !whiteList;
        }
        return plot.getFlag(createFlag);
    }

    @Override
    public boolean canTradeShopHere(@NotNull Player player, @NotNull Location location) {
        com.plotsquared.core.location.Location pLocation =
                new com.plotsquared.core.location.Location(
                        location.getWorld().getName(),
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ());
        Plot plot = pLocation.getPlot();
        if (plot == null) {
            return !whiteList;
        }
        return plot.getFlag(tradeFlag);
    }

    @Override
    public void load() {
        this.createFlag = new QuickshopCreateFlag();
        this.tradeFlag = new QuickshopTradeFlag();
        GlobalFlagContainer.getInstance().addAll(Arrays.asList(createFlag, tradeFlag));
        plugin.getLogger().info(ChatColor.GREEN + getName() + " flags register successfully.");
        Util.debugLog("Success register " + getName() + " flags.");
    }

    @Override
    public void unload() {
    }

    static class QuickshopCreateFlag extends BooleanFlag<QuickshopCreateFlag> {

        protected QuickshopCreateFlag(boolean value, Caption description) {
            super(value, description);
        }

        public QuickshopCreateFlag() {
            super(true, Captions.FLAG_CATEGORY_BOOLEAN);
        }

        @Override
        protected QuickshopCreateFlag flagOf(@NotNull Boolean aBoolean) {
            return new QuickshopCreateFlag(aBoolean, Captions.FLAG_CATEGORY_BOOLEAN);
        }
    }

    static class QuickshopTradeFlag extends BooleanFlag<QuickshopTradeFlag> {

        protected QuickshopTradeFlag(boolean value, Caption description) {
            super(value, description);
        }

        public QuickshopTradeFlag() {
            super(true, Captions.FLAG_CATEGORY_BOOLEAN);
        }

        @Override
        protected QuickshopTradeFlag flagOf(@NotNull Boolean aBoolean) {
            return new QuickshopTradeFlag(aBoolean, Captions.FLAG_CATEGORY_BOOLEAN);
        }
    }

}
