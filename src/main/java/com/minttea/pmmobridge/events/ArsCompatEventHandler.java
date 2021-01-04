package com.minttea.pmmobridge.events;

import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.minttea.pmmobridge.config.Config;
import harmonised.pmmo.skills.Skill;
import harmonised.pmmo.util.XP;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

//@Mod.EventBusSubscriber(modid = ArsNouveau.MODID)
public class ArsCompatEventHandler {

    public static final Logger LOGGER = LogManager.getLogger();


    @SubscribeEvent
    public static void awardSpellCastXp(SpellCastEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        UUID uuid = entity.getUniqueID();
        ServerPlayerEntity player = XP.getPlayerByUUID(uuid);
        if(player != null) {
            List<AbstractSpellPart> spell = event.spell;
            Double xpAward = 1.0;
            LOGGER.debug("Spell cast!");
            for (AbstractSpellPart spellpart : spell
            ) {
                LOGGER.debug("Adding xp for " + spellpart.name);
                switch (spellpart.getTier()) {
                    case THREE:
                        xpAward = Config.SPELL_TIER_3.get();
                        XP.awardXp(player, "magic", null, xpAward, false, false, false);
                        break;
                    case TWO:
                        xpAward = Config.SPELL_TIER_2.get();
                        XP.awardXp(player, "magic", null, xpAward, false, false, false);
                        break;
                    case ONE:
                        xpAward = Config.SPELL_TIER_1.get();
                        XP.awardXp(player, "magic", null, xpAward, false, false, false);
                        break;
                    default:

                }


            }
        }

    }
    @SubscribeEvent
    public static void maxManaByLevel(MaxManaCalcEvent event)
    {
        int magicLevel = Skill.getLevel("magic", event.getEntity().getUniqueID());
        int maxMana = event.getMax();
        double manaBonus = 1+ magicLevel * Config.MAX_BONUS.get();
        LOGGER.debug("Changing mana from " + maxMana + " by " + manaBonus);
        event.setMax((int)(maxMana * manaBonus));
    }
    @SubscribeEvent
    public static void manaRegenByLevel(ManaRegenCalcEvent event)
    {
        int magicLevel = Skill.getLevel("magic", event.getEntity().getUniqueID());
        int regen = (int) event.getRegen();
        double manaBonus = 1+ magicLevel * Config.REGEN_BONUS.get();
        event.setRegen((int)(regen * manaBonus));
    }

}

