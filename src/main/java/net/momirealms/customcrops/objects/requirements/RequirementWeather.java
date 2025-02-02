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

package net.momirealms.customcrops.objects.requirements;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RequirementWeather extends Requirement implements RequirementInterface {

    public RequirementWeather(@NotNull String[] values, boolean mode, @Nullable String msg) {
        super(values, mode, msg);
    }

    @Override
    public boolean isConditionMet(PlantingCondition plantingCondition) {
        World world = plantingCondition.getLocation().getWorld();
        String weather;
        if (world.isThundering()) weather = "thunder";
        else if (world.isClearWeather()) weather = "clear";
        else weather = "rain";
        if (mode) {
            for (String value : values) {
                if (!value.equalsIgnoreCase(weather)) {
                    notMetMessage(plantingCondition.getPlayer());
                    return false;
                }
            }
            return true;
        }
        else {
            for (String value : values) {
                if (value.equalsIgnoreCase(weather)) {
                    return true;
                }
            }
            notMetMessage(plantingCondition.getPlayer());
            return false;
        }
    }
}
