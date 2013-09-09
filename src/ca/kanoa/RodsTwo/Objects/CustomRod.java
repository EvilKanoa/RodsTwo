package ca.kanoa.RodsTwo.Objects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface CustomRod {
	double minimumVersion() default 1.0;
}
