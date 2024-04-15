package geometry_objects.angle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.EquivalenceClasses.*;
import utilities.LinkedEquivalenceClass.LinkedEquivalenceClass;

/**
 * Given the figure below:
 * 
 *    A-------B----C-----------D
 *     \
 *      \
 *       \
 *        E
 *         \
 *          \
 *           F
 * 
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{

	public AngleEquivalenceClasses(AngleStructureComparator comp) {
		super(comp);
	}

}