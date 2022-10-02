package sarf_10;

import XMLExport.TimeAndPlaceConjugator;
//import XMLExport.TrilateralAugmentedGerundConjugator;
//import XMLExport.TrilateralAugmentedGerundConjugator;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sarf.AugmentationFormula;
import sarf.SarfDictionary;
import sarf.SeparatedPronounsContainer;
import sarf.gerund.quadrilateral.augmented.QuadriliteralAugmentedGerundConjugator;
import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund;
//import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugator;
import sarf.gerund.trilateral.unaugmented.GerundDescription;
import sarf.gerund.trilateral.unaugmented.QualityGerundConjugator;
import sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedGerundConjugator;
//import sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedGerundConjugator;
import sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator;
import sarf.gerund.trilateral.unaugmented.meem.MeemGerundConjugator;
import sarf.kov.KovRulesManager;
import sarf.kov.TrilateralKovRule;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.INounSuffixContainer;
import sarf.noun.TrilateralUnaugmentedNouns;
import sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator;
import sarf.noun.trilateral.augmented.AugmentedTrilateralNoun;
import sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator;
import sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator;
import sarf.noun.trilateral.unaugmented.assimilate.AssimilateAdjectiveConjugator;
import sarf.noun.trilateral.unaugmented.elative.ElativeNounConjugator;
import sarf.noun.trilateral.unaugmented.exaggeration.NonStandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.NonStandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier;
import sarf.noun.trilateral.unaugmented.timeandplace.NonStandardTimeAndPlaceNounFormula;
//import sarf.noun.trilateral.unaugmented.timeandplace.TimeAndPlaceConjugator;
import sarf.ui.NounConjugationUI;
import sarf.ui.controlpane.GerundSelectionUI;
import sarf.verb.quadriliteral.augmented.AugmentedQuadriliteralRoot;
import sarf.verb.quadriliteral.unaugmented.UnaugmentedQuadriliteralRoot;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifierListener;
import sarf.verb.trilateral.unaugmented.ConjugationResult;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;

public class Conjugations {

    List unaugmentedTrilateralRoots;

    // active verb conjugations
    void getActiveAugVerbConj(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();

            List past = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerbList(augmentedRoot, formulaNo);
            pl(past);
        }
    }

    public Conjugations() {
        unaugmentedTrilateralRoots = new ArrayList(6);
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
        unaugmentedTrilateralRoots.add("");
    }

    void getUnAugTriRoots(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);

        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
            String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            pl(pastRootText + "\t" + presentRootText);
        }
    }

    void getAugTriRoots(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        while (iter.hasNext()) {

            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            pl(formula.getFormulaNo());
//            trilateralControlPane.enableAugmentedButton(formula.getFormulaNo() - 1, augmentedRoot);
            String pastRootText = sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator.getInstance().createVerb(augmentedRoot, 7, formulaNo).toString();
            String presentRootText = sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(augmentedRoot, 7, formulaNo).toString();
            pl(pastRootText + "\t" + presentRootText);

        }
    }

    void getUnAugQuadRoots(String EnteredRoot) {
//        AugmentedQuadriliteralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedQuadrilateralRoot(root);
//        UnaugmentedQuadriliteralRoot unaugmentedRoot = SarfDictionary.getInstance().getUnaugmentedQuadrilateralRoot(root);
    }

    void getAugQuadRoots(String EnteredRoot) {

    }

    void getUnAugOrgGerunds(String EnteredRoot) {
//        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        List gerunds;// = sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugator.getInstance().createGerundList((AugmentedTrilateralRoot) selectionInfo.getRoot(), selectionInfo.getAugmentationFormulaNo());
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        AugmentationFormula formula = (AugmentationFormula) iter.next();
        int formulaNo = formula.getFormulaNo();
        pl(formulaNo);
        gerunds = sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugator.getInstance().createGerundList(augmentedRoot, formulaNo - 1);
        Iterator it = gerunds.iterator();
        while (it.hasNext()) {
            pl(it.next());

        }
//        while (iter.hasNext()) {
//
//          
//           
//        }
    }

    void getExaggerations(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

            List standardExaggerations = sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator.getInstance().getAppliedFormulaList(root);
            Iterator itr = standardExaggerations.iterator();
            while (itr.hasNext()) {
                pl(itr.next());
            }
        }
    }

    void unAugmentedRoot(String EnteredRoot) {

        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);

        Iterator iter = unaugmentedList.iterator();
        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            int index = Integer.parseInt(root.getConjugation()) - 1;
            unaugmentedTrilateralRoots.set(index, root);
            String pastRootText = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerb(7, root).toString();
            String presentRootText = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerb(7, root).toString();
            pl(pastRootText + "\t" + presentRootText);
        }
    }

    List<UnaugmentedTrilateralRoot> getUnAugRoots(String EnteredRoot) {
        List<UnaugmentedTrilateralRoot> UTroots = new ArrayList();
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);

        Iterator itr = unaugmentedList.iterator();
        while (itr.hasNext()) {
            UTroots.add((UnaugmentedTrilateralRoot) itr.next());
        }
        return UTroots;
    }

    public List<String> getPronouns() {
        List pronounsList = SeparatedPronounsContainer.getInstance().getPronouns();
        Iterator itr = pronounsList.iterator();
        List<String> pronouns = new ArrayList();
        while (itr.hasNext()) {
            String pronoun = (String) itr.next();
//            pl(pronoun);
            pronouns.add(pronoun);
        }
        return pronouns;
    }

    List<String> getActiveVerbConj(UnaugmentedTrilateralRoot root) {
//        Map<String, String> conjugations = new HashMap(13);
        List<String> conjugations = new ArrayList();
        List past, imperative = null;
        past = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerbList(root);
        imperative = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance().createVerbList(root);
        Iterator itr = imperative.iterator();
        while (itr.hasNext()) {
//            pl(itr.next());
            Object o = itr.next();
            if (o != null) {
                conjugations.add(o.toString());
            } else {
                conjugations.add("");
            }

        }
        return conjugations;
    }

    Map<String, ArrayList<String>> getActiveVerbConjs(UnaugmentedTrilateralRoot root) {

        ArrayList<String> conjugations;// = new ArrayList();
        Map<String, ArrayList<String>> activeConjs = new HashMap();
        List past, nominative, accusative, jussive, emphasize, imperative, imperEmph = null;
        past = sarf.verb.trilateral.unaugmented.active.ActivePastConjugator.getInstance().createVerbList(root);
        nominative = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createNominativeVerbList(root);
        accusative = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createAccusativeVerbList(root);
        jussive = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createJussiveVerbList(root);
        emphasize = sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator.getInstance().createEmphasizedVerbList(root);
        imperative = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance().createVerbList(root);
        imperEmph = sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator.getInstance().createEmphasizedVerbList(root);
//        past=sarf.verb.trilateral.augmented.AugmentedTrilateralRoot
        List[] lists = {past, nominative, accusative, jussive, emphasize, imperative, imperEmph};

        String[] conjs = {"past", "nominative", "accusative", "jussive", "emphasize", "imperative", "imperEmph"};

        for (int i = 0; i < 7; i++) {
            conjugations = new ArrayList();
            Iterator itr = lists[i].iterator();
            while (itr.hasNext()) {
//            pl(itr.next());
                Object o = itr.next();
                if (o != null) {
                    conjugations.add(o.toString());
                } else {
                    conjugations.add("");
                }
            }
            activeConjs.put(conjs[i], conjugations);
        }
        return activeConjs;
    }

    Map<String, ArrayList<String>> getPassiveVerbConjs(UnaugmentedTrilateralRoot root) {

        ArrayList<String> conjugations;// = new ArrayList();
        Map<String, ArrayList<String>> passivConjs = new HashMap();
        List past, nominative, accusative, jussive, emphasize;
        past = sarf.verb.trilateral.unaugmented.passive.PassivePastConjugator.getInstance().createVerbList(root);

        nominative = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance().createNominativeVerbList(root);
        accusative = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance().createAccusativeVerbList(root);
        jussive = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance().createJussiveVerbList(root);
        emphasize = sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator.getInstance().createEmphasizedVerbList(root);

        List[] lists = {past, nominative, accusative, jussive, emphasize};

        String[] conjs = {"past", "nominative", "accusative", "jussive", "emphasize"};

        for (int i = 0; i < 5; i++) {
            conjugations = new ArrayList();
            Iterator itr = lists[i].iterator();
            while (itr.hasNext()) {
//            pl(itr.next());
                Object o = itr.next();
                if (o != null) {
                    conjugations.add(o.toString());
                } else {
                    conjugations.add("");
                }
            }
            passivConjs.put(conjs[i], conjugations);
        }
        return passivConjs;
    }
//gerunds

    void getAugOrgGerunds2(String EnteredRoot) {

        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        int kov = KovRulesManager.getInstance().getTrilateralKovRule(EnteredRoot.charAt(0), EnteredRoot.charAt(1), EnteredRoot.charAt(2)).getKov();
        if (augmentedRoot != null) {
            List nounsIndef = null;

            Iterator itr1 = augmentedRoot.getAugmentationList().iterator();
            while (itr1.hasNext()) {
                try {
                    AugmentationFormula formula = (AugmentationFormula) itr1.next();
                    int formulaNo = formula.getFormulaNo();
                    nounsIndef = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(augmentedRoot, formulaNo);

                } catch (Exception e) {

                }

//                nounsIndef = XMLExport.TrilateralAugmentedGerundConjugator.getInstance().createGerundList(augmentedRoot, formulaNo);
//                nounsIndef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(augmentedRoot, kov, formulaNo, nounsIndef, null).getFinalResult();
                pl(nounsIndef);

            }
        }
    }

    void getMeemGerunds(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        List formulas = MeemGerundConjugator.getInstance().getAppliedFormulaList(root);
        pl(formulas);

        Iterator itr = formulas.iterator();

        while (itr.hasNext()) {
            List orgGerus = MeemGerundConjugator.getInstance().createGerundList(root, itr.next().toString());
            pl(orgGerus);
            Iterator it = orgGerus.iterator();
            while (it.hasNext()) {
                Object o = it.next();
                if (o != null || o != "" || !o.toString().trim().isEmpty()) {
                    pl(o);
                }
            }
        }

    }

    void getNomenGerunds(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        List formulas = TrilateralUnaugmentedNomenGerundConjugator.getInstance().getAppliedFormulaList(root);
        pl(formulas);

        Iterator itr = formulas.iterator();

        while (itr.hasNext()) {
            List orgGerus = TrilateralUnaugmentedNomenGerundConjugator.getInstance().createGerundList(root, itr.next().toString());
//            pl(orgGerus);
            Iterator it = orgGerus.iterator();
            while (it.hasNext()) {
                String el = (String) it.next();
                if (!el.trim().isEmpty()) {
                    pl(el);
                }
            }
        }

    }

    void getQualGerunds(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        List orgGerus = QualityGerundConjugator.getInstance().createGerundList(root, "فعلة"); //"فِعْلَة"
        pl(orgGerus);
        Iterator it = orgGerus.iterator();
        while (it.hasNext()) {
            String el = (String) it.next();
            if (!el.trim().isEmpty()) {
                pl(el);
            }
        }
    }

    void getAugOrgGerunds(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        TrilateralAugmentedGerundConjugator.getInstance().setAugmentedTrilateralModifierListener(new GerundSelectionUI());

        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            String gerundPatternClassName2 = "sarf.gerund.trilateral.augmented.pattern.GerundPattern3";
            for (int i = 0; i < 18; i++) {
                Object[] parameters = {augmentedRoot, i + ""};
                try {
                    TrilateralAugmentedGerund gerund = (TrilateralAugmentedGerund) Class.forName(gerundPatternClassName2).getConstructors()[1].newInstance(parameters);
                    pl(gerund);
                } catch (ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

                }
//          
            }
        }
    }

    void getAugNomenGerunds(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        List nomen = null;
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            nomen = sarf.gerund.trilateral.augmented.nomen.TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(augmentedRoot, formulaNo);
            pl(nomen);
        }
    }

    void getAugMeemGerunds(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator iter = augmentedRoot.getAugmentationList().iterator();
        List meem = null;
        while (iter.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) iter.next();
            int formulaNo = formula.getFormulaNo();
            meem = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(augmentedRoot, formulaNo);
            pl(meem);
        }
    }
//get derivations;

    void getSubjectExagg(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

//        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
        List NonStandardFormulas = sarf.noun.trilateral.unaugmented.exaggeration.NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(root);
        Iterator itr = NonStandardFormulas.iterator();
//        while (itr.hasNext()) {
//            List conjugatedNouns = NonStandardExaggerationConjugator.getInstance().createNounList(root, itr.next().toString());
//            pl(conjugatedNouns);
//        }
        List standFormulas = StandardExaggerationConjugator.getInstance().getAppliedFormulaList(root);
//        standFormulas.addAll(NonStandardFormulas);
        itr = standFormulas.iterator();
        while (itr.hasNext()) {
            List conjugatedNouns = StandardExaggerationConjugator.getInstance().createNounList(root, itr.next().toString());
            pl(conjugatedNouns);
        }

    }

    void getMachineName(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
        List NonStandardFormulas = NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(root);
        Iterator itr = NonStandardFormulas.iterator();
        while (itr.hasNext()) {
            List conjugatedNouns = NonStandardInstrumentalConjugator.getInstance().createNounList(root, itr.next().toString());
//            pl(conjugatedNouns);
        }
        List standFormulas = StandardInstrumentalConjugator.getInstance().getAppliedFormulaList(root);
//        standFormulas.addAll(NonStandardFormulas);
        itr = standFormulas.iterator();
        while (itr.hasNext()) {
            List conjugatedNouns = StandardInstrumentalConjugator.getInstance().createNounList(root, itr.next().toString());
            pl(conjugatedNouns);
        }

    }

    void getTimePlace(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        TimeAndPlaceConjugator timeplace = TimeAndPlaceConjugator.getInstance();

        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
            if (!nounsObject.getTimeAndPlaces().isEmpty()) {
//            List f=
                List standFormulas = timeplace.getAppliedFormulaList(root);
                pl(standFormulas);
                Iterator itr = standFormulas.iterator();
                while (itr.hasNext()) {
                    String formu = standFormulas.get(0).toString();
                    pl(formu);
                    List conjugatedNouns = timeplace.createNounList(root, formu);
                    pl(conjugatedNouns);
//                pl(timeplace.createNoun(root, 0, standFormulas.get(0).toString()));
                }
            }
        }
//pl(nounsObject.getTimeAndPlaces());
    }

    void getTimePlace3(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        TimeAndPlaceConjugator timeplace = TimeAndPlaceConjugator.getInstance();

        while (iter.hasNext()) {
            UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
            TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
            if (!nounsObject.getTimeAndPlaces().isEmpty()) {
////            List f=
                List standFormulas = timeplace.getAppliedFormulaList(root);
//            pl(root.getConjugation()+"\t:"+ standFormulas);
                Iterator itr = standFormulas.iterator();
                while (itr.hasNext()) {
                    String formu = itr.next().toString();
                    pl(formu);
                    List conjugatedNouns = timeplace.createNounList(root, formu);
                    pl(conjugatedNouns);
////                pl(timeplace.createNoun(root, 0, standFormulas.get(0).toString()));
                }
            }
        }
//pl(nounsObject.getTimeAndPlaces());
    }

    void getTimePlace2(String EnteredRoot) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();
        TimeAndPlaceConjugator timeplace = TimeAndPlaceConjugator.getInstance();
        List standFormulas = timeplace.getAppliedFormulaList(root);
        String f = standFormulas.get(0).toString();
        List nouns = timeplace.createNounList(root, "مَفْع");
        pl(f);
//        pl(timeplace.createNoun(root, 0, standFormulas.get(0).toString()));

    }

    void getElative(String EnteredRoot) {//اسم التفضيل
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
        if (!nounsObject.getElatives().isEmpty()) {
            List standFormulas = ElativeNounConjugator.getInstance().getAppliedFormulaList(root);
            Iterator itr = standFormulas.iterator();
            while (itr.hasNext()) {
                List conjugatedNouns = ElativeNounConjugator.getInstance().createNounList(root, itr.next().toString());
                pl(conjugatedNouns);
            }
        }
//pl(nounsObject.getTimeAndPlaces());
    }

    void getAssimilate(String EnteredRoot) {//الصفة المشبهة
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);
        if (!nounsObject.getAssimilates().isEmpty()) {
            List standFormulas = AssimilateAdjectiveConjugator.getInstance().getAppliedFormulaList(root);
            Iterator itr = standFormulas.iterator();
            while (itr.hasNext()) {
                List conjugatedNouns = AssimilateAdjectiveConjugator.getInstance().createNounList(root, itr.next().toString());
                pl(conjugatedNouns);
            }
        }
//pl(nounsObject.getTimeAndPlaces());
    }
//get derivations for augmentations

    void getTriUnAugAssimi(String rootText) {

    }

    void getAllNounsOfAugActiveSubject(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);

        Map map = AugmentedTrilateralActiveParticipleConjugator.getInstance().createAllNounList(augmentedRoot);
        Collection set = map.values();
        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            pl(itr.next());
        }
    }

    void getAllNounsOfAugPassiveObject(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator itr = augmentedRoot.getAugmentationList().iterator();

        while (itr.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) itr.next();
            int formulaNo = formula.getFormulaNo();
            List list = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createNounList(augmentedRoot, formulaNo);
            pl(list);
        }
    }

    void getAllNounsOfAugDerivationsTimePlace(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator itr = augmentedRoot.getAugmentationList().iterator();

        while (itr.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) itr.next();
            int formulaNo = formula.getFormulaNo();
            List list = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createTimeAndPlaceNounList(augmentedRoot, formulaNo);
            pl(list);
        }
    }

    void getAllNounsOfAugDerivationsMeem(String EnteredRoot) {
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(EnteredRoot);
        Iterator itr = augmentedRoot.getAugmentationList().iterator();

        while (itr.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) itr.next();
            int formulaNo = formula.getFormulaNo();
            List list = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(augmentedRoot, formulaNo);
            pl(list);
        }
    }

    void indefininteTest(String EnteredRoot, int kov) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        GenericNounSuffixContainer.getInstance().selectInDefiniteMode();// x=new GenericNounSuffixContainer();

        List list = UnaugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(root, "فَاعِل");
        ConjugationResult conjResult = ActiveParticipleModifier.getInstance().build(root, kov, list, "فَاعِل");

        pl(conjResult.getFinalResult());
    }

    void defininteTest(String EnteredRoot, int kov) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        GenericNounSuffixContainer.getInstance().selectDefiniteMode();// x=new GenericNounSuffixContainer();

        List list = UnaugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(root, "فَاعِل");
        ConjugationResult conjResult = ActiveParticipleModifier.getInstance().build(root, kov, list, "فَاعِل");

        pl(conjResult.getFinalResult());
    }

    void annexedTest(String EnteredRoot, int kov) {
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(EnteredRoot);
        Iterator iter = unaugmentedList.iterator();
        UnaugmentedTrilateralRoot root = (UnaugmentedTrilateralRoot) iter.next();

        GenericNounSuffixContainer.getInstance().selectAnnexedMode();// x=new GenericNounSuffixContainer();

        List list = UnaugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(root, "فَاعِل");
        ConjugationResult conjResult = ActiveParticipleModifier.getInstance().build(root, kov, list, "فَاعِل");

        pl(conjResult.getFinalResult());
    }

    void kovRule(String rootText) {
        char c1 = rootText.charAt(0);
        char c2 = rootText.charAt(1);
        char c3 = rootText.charAt(2);

        TrilateralKovRule rule = KovRulesManager.getInstance().getTrilateralKovRule(c1, c2, c3);
        String kovText = rule.getDesc();
        int kov = rule.getKov();
        pl(kovText + "\t" + kov);
    }

    void pl(Object o) {
        System.out.println(o);
    }

}
