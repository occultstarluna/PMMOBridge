package com.minttea.pmmobridge.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Mod.EventBusSubscriber
public class Config {

    public static final Logger LOGGER = LogManager.getLogger();


    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Double> MANA_XP;
    public static ForgeConfigSpec.ConfigValue<Double> MAX_BONUS;
    public static ForgeConfigSpec.ConfigValue<Double> REGEN_BONUS;


    public static ForgeConfigSpec.ConfigValue<Double> LP_SACRIFICE;
    public static ForgeConfigSpec.ConfigValue<Double> LP_DRAIN;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

            SERVER_BUILDER.comment("Ars Nouveau").push("ARS");
            MANA_XP = SERVER_BUILDER.comment("XP gained per Mana spent").define("ars_mana", 0.1d);
            MAX_BONUS = SERVER_BUILDER.comment("% Bonus to Max Mana per level").define("ars_max_bonus", .01d);
            REGEN_BONUS = SERVER_BUILDER.comment("% Bonus to Mana Regen per level").define("ars_regen_bonus", .00666d);
            SERVER_BUILDER.pop();


            SERVER_BUILDER.comment("Blood Magic").push("BLOOD");
            LP_SACRIFICE = SERVER_BUILDER.comment("Modifier for converting from LP to XP when adding LP (Recommended at .01)").define("blood_sacrifice", .01d);
            LP_DRAIN = SERVER_BUILDER.comment("Modifier for converting from LP to XP when spending LP (Recommended at .01)").define("blood_drain", .01d);
            SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();

    }


}
