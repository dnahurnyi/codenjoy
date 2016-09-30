package com.epam.dojo.icancode.model.items;

/*-
 * #%L
 * iCanCode - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 EPAM
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Tickable;
import com.epam.dojo.icancode.model.Elements;
import com.epam.dojo.icancode.model.interfaces.IItem;

/**
 * Created by oleksandr.baglai on 20.06.2016.
 */
public class Laser extends FieldItem implements Tickable {

    private final Direction direction;

    public Laser(Elements element) {
        super(element);
        this.direction = getDirection(element);
    }

    public Laser(Direction direction) {
        super(getElement(direction));
        this.direction = direction;
    }

    private static Elements getElement(Direction direction) {
        switch (direction) {
            case LEFT: return Elements.LASER_LEFT;
            case RIGHT: return Elements.LASER_RIGHT;
            case DOWN: return Elements.LASER_DOWN;
            case UP: return Elements.LASER_UP;
        }
        throw new IllegalStateException("Unexpected direction: " + direction);
    }

    private static Direction getDirection(Elements element) {
        switch (element) {
            case LASER_LEFT: return Direction.LEFT;
            case LASER_RIGHT: return Direction.RIGHT;
            case LASER_DOWN: return Direction.DOWN;
            case LASER_UP: return Direction.UP;
        }
        throw new IllegalStateException("Unexpected element: " + element);
    }

    @Override
    public void action(IItem item) {
        if (item instanceof Hero) {
            Hero hero = (Hero) item;
            if (!hero.isFlying()) {
                removeFromCell();
                hero.dieOnLaser();
            }
        }
    }

    @Override
    public void tick() {
        int newX = direction.changeX(getCell().getX());
        int newY = direction.changeY(getCell().getY());

        if (!field.isBarrier(newX, newY)) {
            field.move(this, newX, newY);
        } else {
            removeFromCell();
        }
    }
}
