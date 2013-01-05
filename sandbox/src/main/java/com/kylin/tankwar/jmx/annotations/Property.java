package com.kylin.tankwar.jmx.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Represents a Protocol property assigned from corresponding field in TankWar properties file.
 * 
 * Property annotation can decorate either a field or a method of a Property class. If a method is decorated with Property annotation it is assumed 
 * that such a method is a setter with only one parameter type that a specified converter can convert from a String to an actual type.
 * 
 * @author kylin
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
public @interface Property {

	String name() default "";

    String description() default "";

    String deprecatedMessage() default "";
    
    String dependsUpon() default "";

    String[] systemProperty() default {};

    /**
     * Global.NON_LOOPBACK_ADDRESS means pick any valid non-loopback IPv4 address
     */
    String defaultValueIPv4() default "" ;

    /**
     * Global.NON_LOOPBACK_ADDRESS means pick any valid non-loopback IPv6 address
     */
    String defaultValueIPv6() default "" ;

    /** Expose this property also as a managed attribute */
    boolean exposeAsManagedAttribute() default true;

    /* Should this managed attribute be writeable ? If set to true, automatically sets exposeAsManagedAttribute to true */
    boolean writable() default true;
}
