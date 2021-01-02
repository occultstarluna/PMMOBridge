package com.minttea.pmmobridge.events;


import harmonised.pmmo.config.JType;
import harmonised.pmmo.skills.Skill;
import harmonised.pmmo.util.XP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wayoftime.bloodmagic.api.compat.IDemonWillWeapon;
import wayoftime.bloodmagic.event.SacrificeKnifeUsedEvent;
import wayoftime.bloodmagic.event.SoulNetworkEvent;
import wayoftime.bloodmagic.potion.BloodMagicPotions;

import java.util.Map;


public class BloodCompatEventHandler {

    public static final Logger LOGGER = LogManager.getLogger();



    @SubscribeEvent
    public static void awardWillXp(LivingDropsEvent event)
    {
        LivingEntity attacked = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity attacker = source.getTrueSource();
        if (attacked.isPotionActive(BloodMagicPotions.SOUL_SNARE) && (attacked instanceof MobEntity || attacked.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL)
                && (attacker != null && attacker instanceof PlayerEntity))
        {
            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack held = player.getHeldItemMainhand();
            if(held.getItem() instanceof IDemonWillWeapon && !player.getEntityWorld().isRemote)
            {
                IDemonWillWeapon weapon = (IDemonWillWeapon) held.getItem();
                attacked.getEntity().getEntityString();
                Map<String, Double> xpAwardMap = XP.getXp(attacked.getEntity().getEntityString(), JType.XP_VALUE_KILL);
                Double xpAward = xpAwardMap.get("combat");
                Skill.MAGIC.addXp(attacker.getUniqueID(), xpAward, null, true, false);
            }

            //XP.awardXpTrigger(attacker.getUniqueID(), "bloodmagic.will.kill", null, true,false);
        }

    }
    @SubscribeEvent
    public static void awardSacrificeXp(SacrificeKnifeUsedEvent event)
    {
        int lpadded = event.lpAdded;
        int xpaward = lpadded/10;
        Skill.MAGIC.addXp(event.player.getUniqueID(), xpaward, null,  true, false);
    }
    @SubscribeEvent
    public static void awardSoulDrainXp(SoulNetworkEvent.Syphon event)
    {
        LOGGER.debug("EVENT DETECTED");
        PlayerEntity player = event.getNetwork().getPlayer();
        int lpDrained = event.getTicket().getAmount();
        int xpaward = lpDrained/10;
        LOGGER.debug("awarding "+xpaward+"xp!");
        Skill.MAGIC.addXp(player.getUniqueID(), xpaward, null, true, false);
    }

}

