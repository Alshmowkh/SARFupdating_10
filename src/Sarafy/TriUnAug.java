package Sarafy;

import java.util.Iterator;
import java.util.List;
import sarf.SarfDictionary;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.active.ActivePastConjugator;
import sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator;

public class TriUnAug {
    Verbs verbs;
    
    void getUnAugTriRoots(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);

        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            System.out.println(root.getConjugation());
            String pastRootText = ActivePastConjugator.getInstance().createVerb(7, root).toString();
            String presentRootText = ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            System.out.println(pastRootText + "\t" + presentRootText);
        }
        
    }
}
