/*
 * Hologram-Lib - Asynchronous, high-performance Minecraft Hologram
 * library for 1.8-1.18 servers.
 * Copyright (C) unldenis <https://github.com/unldenis>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.unldenis.hologram.placeholder;

import com.github.unldenis.hologram.util.Validate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders {

  private final Map<String, Function<Player, String>> placeholders = new ConcurrentHashMap<>();

  public void add(@NotNull String key, @NotNull Function<Player, String> result) {
    Validate.notNull(key, "Key cannot be null");
    Validate.notNull(result, "Function cannot be null");
    placeholders.put(key, result);
  }

  @NotNull
  public String parse(@NotNull String line, @NotNull Player player) {
    String c = line;
    for (Map.Entry<String, Function<Player, String>> entry : placeholders.entrySet()) {
      c = c.replaceAll(entry.getKey(), entry.getValue().apply(player));
    }
    return c;
  }

  @NotNull
  public Component parse(@NotNull Component line, @NotNull Player player) {
    Component c = line;
    for (Map.Entry<String, Function<Player, String>> entry : placeholders.entrySet()) {
      c = c.replaceText(TextReplacementConfig.builder()
              .match(entry.getKey())
              .replacement(entry.getValue().apply(player))
              .build());
    }
    return c;
  }
}


