package com.minttea.pmmobridge.setup;

import com.minttea.pmmobridge.PMMOBridgeMod;
import com.minttea.pmmobridge.events.ArsCompatEventHandler;
import com.minttea.pmmobridge.events.BloodCompatEventHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PMMOBridgeMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PMMOBridgeMod.MOD_ID);
    public static final Logger LOGGER = LogManager.getLogger();
    public static void register()
    {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        if(ModList.get().isLoaded("ars_nouveau")){
            LOGGER.debug("Ars Nouveau detected!  Patching in! 7765");
            MinecraftForge.EVENT_BUS.register(ArsCompatEventHandler.class);
        }
        if(ModList.get().isLoaded("bloodmagic")){
            LOGGER.debug("Blood Magic detected!  Patching in! 7765");
            MinecraftForge.EVENT_BUS.register(BloodCompatEventHandler.class);
        }
    }
}
