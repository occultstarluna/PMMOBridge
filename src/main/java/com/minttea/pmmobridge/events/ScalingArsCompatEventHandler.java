package com.minttea.pmmobridge.events;

import com.hollingsworth.arsnouveau.api.event.SpellModifierEvent;
import com.minttea.pmmobridge.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.silentchaos512.scalinghealth.utils.SHPlayers;

public class ScalingArsCompatEventHandler{

    @SubscribeEvent
    public static void scalingSpellDamage(SpellModifierEvent event){

        if (event.caster instanceof PlayerEntity) {
            int magicLevel = SHPlayers.getPlayerData((PlayerEntity) event.caster).getPowerCrystals();
            double magicProficency = magicLevel * Config.SCALING_SPELL_DMG.get();
            event.builder.addDamageModifier(magicProficency);
        }

    }

}
