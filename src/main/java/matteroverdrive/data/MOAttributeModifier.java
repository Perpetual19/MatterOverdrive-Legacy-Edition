/*
 * This file is part of Matter Overdrive
 * Copyright (C) 2018, Horizon Studio <contact@hrznstudio.com>, All rights reserved.
 *
 * Matter Overdrive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Matter Overdrive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Matter Overdrive.  If not, see <http://www.gnu.org/licenses>.
 */
package matteroverdrive.data;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

/**
 * Created by Simeon on 6/4/2015.
 */
public class MOAttributeModifier extends AttributeModifier {
    double value;

    public MOAttributeModifier(String name, double value, int operation) {
        super(name, value, operation);
        this.value = value;
    }

    public MOAttributeModifier(UUID uuid, String name, double value, int operation) {
        super(uuid, name, value, operation);
        this.value = value;
    }

    public double getAmount() {
        return this.value;
    }

    public void setAmount(double value) {
        this.value = value;
    }
}
