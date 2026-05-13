package com.qloak.qlibs.entity;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Lightweight component system interface — attach data to entities
 * without requiring a full ECS or heavy framework.
 */
public interface EntityComponent<T extends Entity> {
    void onAttach(@NotNull T entity);
    void tick(@NotNull T entity);
    void onRemove(@NotNull T entity);
}
