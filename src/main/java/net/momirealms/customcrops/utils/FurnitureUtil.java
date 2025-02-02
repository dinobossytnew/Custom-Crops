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

package net.momirealms.customcrops.utils;

import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;

import java.util.Random;

public class FurnitureUtil {

    private static final Rotation[] rotations4 = {Rotation.NONE, Rotation.FLIPPED, Rotation.CLOCKWISE, Rotation.COUNTER_CLOCKWISE};

    public static ItemFrame getItemFrame(Location location) {
        for(Entity entity : location.getWorld().getNearbyEntities(location,0,0,0)){
            if (entity instanceof ItemFrame itemFrame) {
                return itemFrame;
            }
        }
        return null;
    }

    public static boolean hasFurniture(Location location) {
        return getItemFrame(location) != null;
    }

    public static Rotation getRandomRotation() {
        return rotations4[new Random().nextInt(rotations4.length-1)];
    }
}
