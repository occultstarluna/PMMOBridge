package com.minttea.pmmobridge;

import com.minttea.pmmobridge.config.Config;
import com.minttea.pmmobridge.setup.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pmmobridge")
public class PMMOBridgeMod
{
    /**
     * TODO: Add in Magic Skill bonuses for Blood Magic
     * TODO: Add in configuration system
     */
    public static final String MOD_ID = "pmmocompat";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public PMMOBridgeMod() {
        Registration.register();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SERVER_CONFIG);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("Patching into all the mods...");
        LOGGER.info("Nailed it, first try.");
    }



}
