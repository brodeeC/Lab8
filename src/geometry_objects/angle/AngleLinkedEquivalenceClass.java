package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.LinkedEquivalenceClass.LinkedEquivalenceClass;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
                             STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 */
public class AngleLinkedEquivalenceClass extends LinkedEquivalenceClass<Angle>
{
    /**
     * Constructor
     * @param _comparator
     */
    public AngleLinkedEquivalenceClass(AngleStructureComparator _comparator) {
        super(_comparator);
    }

    /**
     * true if the angle belongs in this equivalence class
     */
    @Override
    public boolean belongs(Angle angle){

        //If the comparator returns the Max Int value then the two angles are structurally incomparable.
        if(angle != null && super._comparator.compare(_canonical, angle) != AngleStructureComparator.STRUCTURALLY_INCOMPARABLE) return true;
		
		return false;
    }
    
}