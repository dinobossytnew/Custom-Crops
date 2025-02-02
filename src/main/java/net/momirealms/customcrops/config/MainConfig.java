/*
 *  Copyright (C) <2022> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customcrops.config;

import net.momirealms.customcrops.helper.Log;
import net.momirealms.customcrops.integrations.AntiGrief;
import net.momirealms.customcrops.integrations.SkillXP;
import net.momirealms.customcrops.integrations.protection.*;
import net.momirealms.customcrops.objects.QualityRatio;
import net.momirealms.customcrops.utils.AdventureUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainConfig {

    public static World[] worlds;
    public static List<World> worldList;
    public static boolean whiteOrBlack;
    public static String customPlugin;
    public static boolean OraxenHook;
    public static boolean realisticSeasonHook;
    public static boolean cropMode;
    public static List<AntiGrief> antiGriefs;
    public static SkillXP skillXP;
    public static double dryGrowChance;
    public static boolean limitation;
    public static int wireAmount;
    public static int frameAmount;
    public static QualityRatio qualityRatio;
    public static boolean canRightClickHarvest;
    public static boolean emptyHand;
    public static int waterBucketToSprinkler;
    public static int waterToWaterCan;
    public static int wateringCanToSprinkler;
    public static int timeToGrow;
    public static int timeToWork;
    public static int timeToDry;
    public static String lang;
    public static boolean preventInWrongSeason;
    public static boolean notifyInWrongSeason;
    public static boolean enableBoneMeal;
    public static double boneMealChance;
    public static Particle boneMealSuccess;
    public static boolean enableCrow;
    public static double crowChance;
    public static boolean enableActionBar;
    public static String actionBarLeft;
    public static String actionBarFull;
    public static String actionBarEmpty;
    public static String actionBarRight;
    public static boolean enableSprinklerInfo;
    public static double sprinklerInfoY;
    public static int sprinklerInfoDuration;
    public static String sprinklerLeft;
    public static String sprinklerFull;
    public static String sprinklerEmpty;
    public static String sprinklerRight;
    public static boolean enableFertilizerInfo;
    public static double fertilizerInfoY;
    public static int fertilizerInfoDuration;
    public static String fertilizerInfo;
    public static boolean enableParticles;
    public static boolean enableAnimations;
    public static boolean autoGrow;
    public static boolean enableCompensation;
    public static boolean syncSeason;
    public static World syncWorld;
    public static boolean autoBackUp;

    public static void load() {
        ConfigUtil.update("config.yml");
        YamlConfiguration config = ConfigUtil.getConfig("config.yml");

        lang = config.getString("lang","english");

        whiteOrBlack = config.getString("worlds.mode","whitelist").equals("whitelist");
        List<String> worldsName = config.getStringList("worlds.list");
        worlds = new World[worldsName.size()];
        for (int i = 0; i < worldsName.size(); i++) {
            if (Bukkit.getWorld(worldsName.get(i)) != null) {
                worlds[i] = Bukkit.getWorld(worldsName.get(i));
            }
        }
        worldList = List.of(worlds);
        cropMode = config.getString("mechanics.crops-mode", "tripwire").equals("tripwire");
        limitation = config.getBoolean("optimization.limitation.enable", true);
        wireAmount = config.getInt("optimization.limitation.tripwire-amount", 64);
        frameAmount = config.getInt("optimization.limitation.itemframe-amount", 64);

        autoGrow = config.getBoolean("mechanics.auto-grow.enable", true);
        enableCompensation = config.getBoolean("mechanics.auto-grow.time-compensation", true);
        timeToGrow = config.getInt("mechanics.auto-grow.crops-grow-time", 20000);
        timeToWork = config.getInt("mechanics.auto-grow.sprinkler-work-time", 300);
        timeToDry = config.getInt("mechanics.auto-grow.pot-dry-time", 200);
        dryGrowChance = config.getDouble("mechanics.dry-pot-grow-chance", 0.5);

        waterBucketToSprinkler = config.getInt("mechanics.fill.water-bucket-to-sprinkler", 3);
        waterToWaterCan = config.getInt("mechanics.fill.waterblock-to-watering-can", 1);
        wateringCanToSprinkler = config.getInt("mechanics.fill.watering-can-to-sprinkler", 1);

        canRightClickHarvest = config.getBoolean("mechanics.right-click-harvest.enable", true);
        emptyHand = config.getBoolean("mechanics.right-click-harvest.require-empty-hand", true);
        preventInWrongSeason = config.getBoolean("mechanics.prevent-plant-if-wrong-season", true);
        notifyInWrongSeason = config.getBoolean("mechanics.should-notify-if-wrong-season", true);

        enableBoneMeal = config.getBoolean("mechanics.bone-meal", true);
        boneMealChance = config.getDouble("mechanics.chance", 0.5);

        syncSeason = config.getBoolean("mechanics.season.sync-seasons.enable", false);
        syncWorld = Bukkit.getWorld(config.getString("mechanics.season.sync-seasons.world", "world"));

        autoBackUp = config.getBoolean("optimization.auto-back-up", true);

        enableParticles = !config.getBoolean("optimization.disable-water-particles", false);
        enableAnimations = !config.getBoolean("optimization.disable-sprinkler-animation", false);

        try {
            boneMealSuccess = Particle.valueOf(config.getString("mechanics.success-particle", "VILLAGER_HAPPY"));
        }
        catch (IllegalArgumentException e) {
            AdventureUtil.consoleMessage("<red>[CustomCrops] Illegal Particle Argument for Bone Meal</red>");
        }

        enableCrow = config.getBoolean("mechanics.crow.enable", false);
        crowChance = config.getDouble("mechanics.crow.chance", 0.001);

        String[] split = StringUtils.split(config.getString("mechanics.default-quality-ratio", "17/2/1"), "/");
        double[] weight = new double[3];
        assert split != null;
        weight[0] = Double.parseDouble(split[0]);
        weight[1] = Double.parseDouble(split[1]);
        weight[2] = Double.parseDouble(split[2]);
        double weightTotal = weight[0] + weight[1] + weight[2];
        qualityRatio = new QualityRatio(weight[0]/(weightTotal), 1 - weight[1]/(weightTotal));

        enableActionBar = config.getBoolean("actionbar.enable", true);
        actionBarLeft = config.getString("actionbar.left", "<font:customcrops:default>뀂");
        actionBarFull = config.getString("actionbar.full", "뀁뀃");
        actionBarEmpty = config.getString("actionbar.empty", "뀁뀄");
        actionBarRight = config.getString("actionbar.right", "뀁뀅</font>");

        enableSprinklerInfo = config.getBoolean("hologram.sprinkler-info.enable", true);
        sprinklerInfoY = config.getDouble("hologram.sprinkler-info.y-offset", -0.2);
        sprinklerInfoDuration = config.getInt("hologram.sprinkler-info.duration", 1);
        sprinklerLeft = config.getString("hologram.sprinkler-info.left", "<font:customcrops:default>뀂");
        sprinklerFull = config.getString("hologram.sprinkler-info.full", "뀁뀃");
        sprinklerEmpty = config.getString("hologram.sprinkler-info.empty", "뀁뀄");
        sprinklerRight = config.getString("hologram.sprinkler-info.right", "뀁뀅</font>");

        enableFertilizerInfo = config.getBoolean("hologram.fertilizer-info.enable", true);
        fertilizerInfoY = config.getDouble("hologram.fertilizer-info.y-offset", -0.2);
        fertilizerInfoDuration = config.getInt("hologram.fertilizer-info.duration", 1);
        fertilizerInfo = config.getString("hologram.fertilizer-info.text", "<font:customcrops:default>{fertilizer}</font> <white>{times}<gray>/<white>{max_times}");

        antiGriefs = new ArrayList<>();
        if (config.getBoolean("config.integration.Residence",false)){
            if (Bukkit.getPluginManager().getPlugin("Residence") == null) Log.warn("Failed to initialize Residence!");
            else {antiGriefs.add(new ResidenceHook());hookMessage("Residence");}
        }
        if (config.getBoolean("config.integration.Kingdoms",false)){
            if (Bukkit.getPluginManager().getPlugin("Kingdoms") == null) Log.warn("Failed to initialize Kingdoms!");
            else {antiGriefs.add(new KingdomsXHook());hookMessage("Kingdoms");}
        }
        if (config.getBoolean("config.integration.WorldGuard",false)){
            if (Bukkit.getPluginManager().getPlugin("WorldGuard") == null) Log.warn("Failed to initialize WorldGuard!");
            else {antiGriefs.add(new WorldGuardHook());hookMessage("WorldGuard");}
        }
        if (config.getBoolean("config.integration.GriefDefender",false)){
            if(Bukkit.getPluginManager().getPlugin("GriefDefender") == null) Log.warn("Failed to initialize GriefDefender!");
            else {antiGriefs.add(new GriefDefenderHook());hookMessage("GriefDefender");}
        }
        if (config.getBoolean("config.integration.PlotSquared",false)){
            if(Bukkit.getPluginManager().getPlugin("PlotSquared") == null) Log.warn("Failed to initialize PlotSquared!");
            else {antiGriefs.add(new PlotSquaredHook());hookMessage("PlotSquared");}
        }
        if (config.getBoolean("config.integration.Towny",false)){
            if (Bukkit.getPluginManager().getPlugin("Towny") == null) Log.warn("Failed to initialize Towny!");
            else {antiGriefs.add(new TownyHook());hookMessage("Towny");}
        }
        if (config.getBoolean("config.integration.Lands",false)){
            if (Bukkit.getPluginManager().getPlugin("Lands") == null) Log.warn("Failed to initialize Lands!");
            else {antiGriefs.add(new LandsHook());hookMessage("Lands");}
        }
        if (config.getBoolean("config.integration.GriefPrevention",false)){
            if (Bukkit.getPluginManager().getPlugin("GriefPrevention") == null) Log.warn("Failed to initialize GriefPrevention!");
            else {antiGriefs.add(new GriefPreventionHook());hookMessage("GriefPrevention");}
        }
        if (config.getBoolean("config.integration.CrashClaim",false)){
            if (Bukkit.getPluginManager().getPlugin("CrashClaim") == null) Log.warn("Failed to initialize CrashClaim!");
            else {antiGriefs.add(new CrashClaimHook());hookMessage("CrashClaim");}
        }
        if (config.getBoolean("config.integration.BentoBox",false)){
            if (Bukkit.getPluginManager().getPlugin("BentoBox") == null) Log.warn("Failed to initialize BentoBox!");
            else {antiGriefs.add(new BentoBoxHook());hookMessage("BentoBox");}
        }
    }

    public static World[] getWorldsArray() {
        if (MainConfig.whiteOrBlack) {
            return worlds;
        }
        else {
            List<World> worldList = new ArrayList<>(Bukkit.getWorlds());
            worldList.removeAll(MainConfig.worldList);
            return worldList.toArray(new World[0]);
        }
    }

    public static List<World> getWorldsList() {
        if (MainConfig.whiteOrBlack) {
            return worldList;
        }
        else {
            List<World> worldList = new ArrayList<>(Bukkit.getWorlds());
            worldList.removeAll(MainConfig.worldList);
            return worldList;
        }
    }

    private static void hookMessage(String plugin){
        AdventureUtil.consoleMessage("[CustomCrops] <gold>" + plugin + " <color:#FFEBCD>Hooked!");
    }
}
