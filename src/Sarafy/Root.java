package Sarafy;

import XMLExport.XMLExporter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sarf.AugmentationFormula;
import sarf.SarfDictionary;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedImperativeConjugator;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.active.ActivePastConjugator;
import sarf.verb.trilateral.unaugmented.active.ActivePastVerb;
import sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator;

public class Root {

    String rootText;
    List resultList;
    Map<Integer, List<String>> resultMap;

    public Root(String rootIn) {
        rootText = rootIn;

    }

    public String getVerbType() {
        if (rootText.trim().length() == 3) {
            return "trilateral";
        } else if (rootText.trim().length() == 4) {
            return "quadilateral";
        } else {
            return "unknown";
        }
    }

    public Map<Integer, List<String>> getTriUnAug() {
        resultMap = new HashMap();
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            String pastRootText = ActivePastConjugator.getInstance().createVerb(7, root).toString();
            String presentRootText = ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            resultList = new ArrayList();
            resultList.add(pastRootText);
            resultList.add(presentRootText);
            int conjNo = Integer.parseInt(root.getConjugation());
            resultMap.put(conjNo, resultList);// pl(pastRootText + "\t" + presentRootText);
        }
        return resultMap;
    }

    public Map<Integer, String> getPresentTriUnAug() {
        Map<Integer, String> result = new HashMap();
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            String presentRootText = ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            int conjNo = Integer.parseInt(root.getConjugation());
            result.put(conjNo, presentRootText);// pl(pastRootText + "\t" + presentRootText);
        }
        return result;
    }

    List<String> getPresentTriUnAug2() {
        resultList = new ArrayList();
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            resultList.add(presentRootText);
        }
        return resultList;
    }

    List<String> getPastTriAug() {
        resultList = new ArrayList();
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootText);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            String pastRootText = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(augmentedRoot, 7, formulaNo).toString();
            resultList.add(pastRootText);

        }
        return resultList;
    }

    List<String> getPresentTriAug() {
        resultList = new ArrayList();
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootText);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            String presentRootText = sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(augmentedRoot, 7, formulaNo).toString();
            resultList.add(presentRootText);

        }
        return resultList;
    }

    Map<Integer, List<String>> getPastActive() {
        Map<Integer, List<String>> verbs = new HashMap();
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            int conjNo = Integer.parseInt(root.getConjugation());
            List past = ActivePastConjugator.getInstance().createVerbList(root);
            verbs.put(conjNo, past);
        }
        return verbs;
    }

    List<String> foo() {
        resultList = new ArrayList();

        return resultList;
    }

    public void foo2() {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            String pastRootText = ActivePastConjugator.getInstance().createVerb(10, root).toString();
            String presentRootText = ActivePresentConjugator.getInstance().createNominativeVerb(10, root).toString();
            List imper = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance().createVerbList(root);//.createEmphasizedVerbList(root);//UnaugmentedImperativeConjugator.getInstance().createVerbList(root);
//            List subject = sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator.getInstance().getAppliedFormulaList(root).UnaugmentedImperativeConjugator.getInstance().createVerbList(root);//.createEmphasizedVerbList(root);//UnaugmentedImperativeConjugator.getInstance().createVerbList(root);

            pl(imper + "\n" + pastRootText + "\t" + presentRootText);
        }
    }

    public static void main(String[] args) {
        Root root = new Root("لكم");
//        for (Map.Entry<Integer, List<String>> entry : root.getPastActive().entrySet()) {
//            pl(entry.getKey() + ": " + entry.getValue());
//        }
        root.foo2();

    }

    static void pl(Object o) {
        System.out.println(o);
    }
}
