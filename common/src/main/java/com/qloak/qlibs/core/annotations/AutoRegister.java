package com.qloak.qlibs.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field for automatic registration. Consumers should scan their own
 * entry classes and register annotated fields via {@code dev.architectury.registry}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoRegister {
    /**
     * Optional registry key override. If empty, inferred from field type.
     */
    String value() default "";
}
