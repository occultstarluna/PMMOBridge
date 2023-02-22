package com.minttea.pmmobridge.events;


import harmonised.pmmo.config.JType;
import harmonised.pmmo.util.XP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

import static com.minttea.pmmobridge.config.Config.LP_DRAIN;
import static com.minttea.pmmobridge.config.Config.LP_SACRIFICE;


public class BloodCompatEventHandler {

    public static final Logger LOGGER = LogManager.getLogger();



    //@SubscribeEvent
    public static void awardWillXp(LivingDropsEvent event)
    {
        LivingEntity attacked = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        if (attacked.hasEffect(BloodMagicPotions.SOUL_SNARE) && (attacked instanceof MobEntity || attacked.getEntity().level.getDifficulty() == Difficulty.PEACEFUL)
                && (attacker != null && attacker instanceof PlayerEntity))
        {
            PlayerEntity player = (PlayerEntity) attacker;
            ItemStack held = player.getMainHandItem();
            if(held.getItem() instanceof IDemonWillWeapon && !player.getCommandSenderWorld().isClientSide)
            {
                IDemonWillWeapon weapon = (IDemonWillWeapon) held.getItem();
                attacked.getEntity().getEncodeId();
                Map<String, Double> xpAwardMap = XP.getXp(attacked.getEntity(), JType.XP_VALUE_KILL);
                Double xpAward = xpAwardMap.get("combat");
               // Skill.MAGIC.addXp(attacker.getUniqueID(), xpAward, null, true, false);
            }

            //XP.awardXpTrigger(attacker.getUniqueID(), "bloodmagic.will.kill", null, true,false);
        }

    }
    @SubscribeEvent
    public static void awardSacrificeXp(SacrificeKnifeUsedEvent event)
    {
        if(!event.player.level.isClientSide()) {
            LOGGER.debug("sacrificed " + event.lpAdded + " lp 7654");
            int lpadded = event.lpAdded;
            LOGGER.debug("UUID: " + event.player.getStringUUID());
            ServerPlayerEntity player = XP.getPlayerByUUID(event.player.getUUID());

            Double xpaward = lpadded * LP_SACRIFICE.get();
            if (player != null) {
                XP.awardXp(player, "magic", null, xpaward, false, false, false);
            }
            //Skill.MAGIC.addXp(event.player.getUniqueID(), xpaward, null,  true, false);
        }

    }
    @SubscribeEvent
    public static void awardSoulDrainXp(SoulNetworkEvent.Syphon event)
    {
        if(!event.getNetwork().getPlayer().level.isClientSide()) {
            ServerPlayerEntity player = XP.getPlayerByUUID(event.getNetwork().getPlayer().getUUID());

            int lpDrained = event.getTicket().getAmount();
            double xpaward = lpDrained * LP_DRAIN.get();
            LOGGER.debug("awarding " + xpaward + "xp!");
            if (player != null) {
                XP.awardXp(player, "magic", null, xpaward, false, false, false);
            }
        }
    }

}

