/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarf_10;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sarf.AugmentationFormula;
import sarf.SarfDictionary;
import sarf.noun.GenericNounSuffixContainer;
import sarf.util.ArabCharUtil;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;

/**
 *
 * @author ALshmowkh
 */
public class MainClass {

    List unaugmentedTrilateralRoots;
    Conjugations conj;

    boolean checkTashkil(String root) {
        String FATHA = "َ";
        String DAMMA = "ُ";
        String KASRA = "ِ";
        String SKOON = "ْ";
        String SHADDA = "ّ";
        return root.contains(FATHA)
                || root.contains(DAMMA)
                || root.contains(KASRA)
                || root.contains(SKOON)
                || root.contains(SHADDA);
    }

    public MainClass() {
        //-------------------------------------------
        unaugmentedTrilateralRoots = new ArrayList(6);
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
    }

    public void enableUnaugmentedButton(int index, UnaugmentedTrilateralRoot root) {

        unaugmentedTrilateralRoots.set(index, root);

        String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
        String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();

//        conjugations = createEmptyList();
//        conjugations.set(7, presentRootText);
//        conjResult = sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier.getInstance().build(root, ControlPaneContainer.getInstance().getKov(), conjugations, SystemConstants.PRESENT_TENSE, true);
//        presentRootText = conjResult.getFinalResult().get(7).toString();
        pl(pastRootText + "\t" + presentRootText);
//        JOptionPane.showMessageDialog(null, pastRootText + "\n pick ... Unaugment\n" + presentRootText);
//        btn.setRootText(pastRootText + " " + presentRootText);

    }

    void enableAugmentedButton(int index, AugmentedTrilateralRoot root) {

        int formulaNo = index + 1;
        String pastRootText = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(root, 7, formulaNo).toString();
        String presentRootText = sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(root, 7, formulaNo).toString();

        pl(pastRootText + "\t" + presentRootText);
    }

    void unAugmentedRoot(String EnteredRoot) {

        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
//        الفعل الرباعي
//        AugmentedQuadriliteralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(rootIn);
//        UnaugmentedQuadriliteralRoot unaugmentedRoot = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(rootIn);

        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
//            pl(root.getTransitive());
            enableUnaugmentedButton(Integer.parseInt(root.getConjugation()) - 1, root);
//         cls.enableUnaugmentedButton(0,unaugmentedList);     

//            pl(root.getConjugation());
//            pl((char) root.getC3());
        }

    }

    void AugmentedRoot(String EnteredRoot) {

        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);

        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
//            pl(formula.getTransitive());
            enableAugmentedButton(formula.getFormulaNo() - 1, augmentedRoot);

        }
    }

    void getTransitivity(String rootText) {

        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootText);
        List conjugations = new ArrayList();

        Map<String, String> map = new HashMap();
        Iterator iterU = unaugmentedList.iterator();

        while (iterU.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iterU.next();
            int index = Integer.parseInt(root.getConjugation()) - 1;
            String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
            String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            map.put(pastRootText, root.getTransitive());
            conjugations.add(pastRootText);
            conjugations.add(presentRootText);

        }
        Iterator iterA = augmentedRoot.getAugmentationList().iterator();
        while (iterA.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iterA.next();
            int index = formula.getFormulaNo() - 1;
            String pastRootText = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(augmentedRoot, 7, index).toString();
            String presentRootText = sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(augmentedRoot, 7, index).toString();
            map.put(pastRootText, formula.getTransitive() + "");
            conjugations.add(pastRootText);
            conjugations.add(presentRootText);

        }
    }

    void getUnAugOrgGerunds(String rootText) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);

        Iterator iter = unaugmentedList.iterator();

        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            Collection gsymbols = root.getGerundsSymbols();
            Iterator it = gsymbols.iterator();
            while (it.hasNext()) {
                pl(root.getGerund(it.next().toString()).getValue());
//            pl("");
            }
//            pl("----gerund----" + root.getGerund("H1").getValue());
        }
    }
//اسم الفاعل

    void getActiveParticipate(String rootText) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);

        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
        for (int i = 0; i < 18; i++) {
            String suffix = GenericNounSuffixContainer.getInstance().get(i);
            String g = GenericNounSuffixContainer.getInstance().getPrefix() + root.getC1() + ArabCharUtil.FATHA + "ا" + root.getC2() + ArabCharUtil.KASRA + root.getC3() + suffix;
            pl(i + " : " + g);
        }
    }
//اسم المفعول

    void getPassiveParticipate(String rootText) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);

        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
        for (int i = 0; i < 18; i++) {
            String suffix = GenericNounSuffixContainer.getInstance().get(i);
            String p = GenericNounSuffixContainer.getInstance().getPrefix() + "م" + ArabCharUtil.FATHA + root.getC1() + ArabCharUtil.SKOON + root.getC2() + ArabCharUtil.DAMMA + "و" + root.getC3() + suffix;

//            String g = GenericNounSuffixContainer.getInstance().getPrefix() + root.getC1() + ArabCharUtil.FATHA + "ا" + root.getC2() + ArabCharUtil.KASRA + root.getC3() + suffix;
            pl(p);
        }
    }

    void getDerviedNouns(String rootText) {

    }

    void makeRootObject(String rootIn) {

//        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootIn);
//
//        Iterator iter = unaugmentedList.iterator();
//        while (iter.hasNext()) {
        Object o = "sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot@1f17ae12";
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) o;
//                UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
        ////            String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
        ////            String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
        ////            pl(pastRootText + "\t" + presentRootText);
        //            pl(root);
        //        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainClass cls = new MainClass();
        cls.conj = new Conjugations();

        String rootIn = "لكم";
//        cls.makeRootObject(rootIn);
//----------------------------------------------------------
//        char c1 = rootIn.charAt(0);
//        char c2 = rootIn.charAt(1);
//        char c3 = rootIn.charAt(2);
//        TrilateralKovRule rule = KovRulesManager.getInstance().getTrilateralKovRule(c1, c2, c3);
//        int kov = KovRulesManager.getInstance().getTrilateralKovRule(rootIn.charAt(0), rootIn.charAt(1), rootIn.charAt(2)).getKov();
//        int kov = rule.getKov();
//        String desc = rule.getDesc();
//        pl(kov + ":" + desc);
        //-----------------------------------
        //-----------------------------------
        //        cls.conj.annexedTest(rootIn, kov);
        //        cls.conj.defininteTest(rootIn, kov);
        //        cls.conj.indefininteTest(rootIn, kov);
        //        cls.conj.kovRule(rootIn);
        //------------------------------------
        //        cls.conj.getAllNounsOfAugDerivationsMeem(rootIn);
        //        cls.conj.getAllNounsOfAugDerivationsTimePlace(rootIn);
        //        cls.conj.getAllNounsOfAugPassiveObject(rootIn);
        //        cls.conj.getAllNounsOfAugActiveSubject(rootIn);
        //------------
        //        cls.conj.getElative(rootIn);
        //        cls.conj.getAssimilate(rootIn);
        //        cls.conj.getTimePlace3(rootIn);
        //        cls.conj.getMachineName(rootIn);
        //        cls.conj.getSubjectExagg(rootIn);
        //        cls.getActiveParticipate(rootIn);
        //        cls.getPassiveParticipate(rootIn);
        //-----------------------
        //        cls.conj.getAugOrgGerunds(rootIn);
        //        cls.conj.getAugNomenGerunds(rootIn);
        //        cls.conj.getAugMeemGerunds(rootIn);
        //-----------------
                cls.conj.getAugOrgGerunds2(rootIn);
        //        cls.conj.getMeemGerunds(rootIn);
        //        cls.conj.getNomenGerunds(rootIn);
        //        cls.conj.getQualGerunds(rootIn);
        //       //---------------------
        //         cls.conj.getAugTriRoots(rootIn);
        //        cls.conj.getUnAugTriRoots(rootIn);
        //        cls.conj.getExaggerations(rootIn); 
        //------------------------------------------------------------------------------ 
        //الافعال المبنية للمعلوم والمبنية للمجهول
        //        List<UnaugmentedTrilateralRoot> UTroots = cls.conj.getUnAugRoots(rootIn);
        //        Map<String, ArrayList<String>> conjs = cls.conj.getActiveVerbConjs(UTroots.get(0));
        //        Map<String, ArrayList<String>> conjsPass = cls.conj.getPassiveVerbConjs(UTroots.get(0));
        //        for (Map.Entry<String, ArrayList<String>> entry : conjs.entrySet()) {
        //            pl(entry.getKey() + ": " + entry.getValue());
        //        }
        //        cls.conj.getActiveAugVerbConj(rootIn);
        //-------------------------------
        ////        List<String> pronouns = cls.conj.getPronouns();
        //--------------------------
        //        cls.getUnAugOrgGerunds(rootIn);
        //        cls.getDerviedNouns(rootIn);
    }

    static void pl(Object o) {
        System.out.println(o.toString());
    }

    static void p(Object o) {
        System.out.print(o.toString());
    }
}
