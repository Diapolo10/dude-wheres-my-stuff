/*
 * Copyright (c) 2018, Daniel Teo <https://github.com/takuyakanbr>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package dev.thource.runelite.dudewheresmystuff;

import com.google.common.collect.ImmutableMap;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;

import java.awt.*;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

class OverviewTabPanel extends TabContentPanel {
    private final DudeWheresMyStuffConfig config;

    private final Map<Tab, OverviewItemPanel> overviews;
    private final CoinsManager coinsManager;
    private final CarryableManager carryableManager;

    OverviewTabPanel(ItemManager itemManager, DudeWheresMyStuffConfig config, DudeWheresMyStuffPanel pluginPanel, CoinsManager coinsManager, CarryableManager carryableManager) {
        this.config = config;
        this.coinsManager = coinsManager;
        this.carryableManager = carryableManager;

        setLayout(new GridLayout(0, 1, 0, 8));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        overviews = Stream.of(Tab.TABS)
                .filter(v -> v != Tab.OVERVIEW)
                .collect(ImmutableMap.toImmutableMap(
                        Function.identity(),
                        t ->
                        {
                            OverviewItemPanel p = new OverviewItemPanel(itemManager, pluginPanel, t, t.getName());
                            add(p);
                            return p;
                        }
                ));
    }

    @Override
    public void update() {
        overviews.get(Tab.COINS).updateStatus(String.format("%,d gp", coinsManager.getTotalValue()), Color.LIGHT_GRAY);
        overviews.get(Tab.CARRYABLE_STORAGE).updateStatus(String.format("%,d gp", carryableManager.getTotalValue()), Color.LIGHT_GRAY);
    }
}