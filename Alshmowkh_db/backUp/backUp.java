package XMLExport;

import Sarafy.Root;
import Sarafy.RootInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sarf.AugmentationFormula;
import sarf.SarfDictionary;
import sarf.SystemConstants;
import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugator;
import sarf.gerund.trilateral.augmented.nomen.TrilateralAugmentedNomenGerundConjugator;
import sarf.gerund.trilateral.unaugmented.IUnaugmentedTrilateralGerundConjugator;
import sarf.gerund.trilateral.unaugmented.QualityGerundConjugator;
import sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedGerundConjugator;
import sarf.gerund.trilateral.unaugmented.TrilateralUnaugmentedNomenGerundConjugator;
import sarf.gerund.trilateral.unaugmented.meem.MeemGerundConjugator;
import sarf.kov.KovRulesManager;
import sarf.noun.GenericNounSuffixContainer;
import sarf.noun.INounSuffixContainer;
import sarf.noun.IUnaugmentedTrilateralNounConjugator;
import sarf.noun.TrilateralUnaugmentedNouns;
import sarf.noun.trilateral.augmented.AugmentedTrilateralActiveParticipleConjugator;
import sarf.noun.trilateral.augmented.AugmentedTrilateralPassiveParticipleConjugator;
import sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralActiveParticipleConjugator;
import sarf.noun.trilateral.unaugmented.UnaugmentedTrilateralPassiveParticipleConjugator;
import sarf.noun.trilateral.unaugmented.assimilate.AssimilateAdjectiveConjugator;
import sarf.noun.trilateral.unaugmented.elative.ElativeNounConjugator;
import sarf.noun.trilateral.unaugmented.exaggeration.NonStandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.NonStandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModifier;
import sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier;
import sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier;
import sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier;
import sarf.noun.trilateral.unaugmented.timeandplace.TimeAndPlaceConjugator;
import sarf.ui.SelectionInfo;
import sarf.ui.controlpane.GerundSelectionUI;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator;
import sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator;
import sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator;
import sarf.verb.trilateral.augmented.passive.past.AugmentedPassivePastConjugator;
import sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator;
import sarf.verb.trilateral.unaugmented.ConjugationResult;
import sarf.verb.trilateral.unaugmented.UnaugmentedImperativeConjugator;
import sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot;
import sarf.verb.trilateral.unaugmented.active.ActivePastConjugator;
import sarf.verb.trilateral.unaugmented.active.ActivePresentConjugator;
import sarf.verb.trilateral.unaugmented.modifier.UnaugmentedTrilateralModifier;
import sarf.verb.trilateral.unaugmented.passive.PassivePastConjugator;
import sarf.verb.trilateral.unaugmented.passive.PassivePresentConjugator;
import sarf_10.Conjugations;

public class XMLExporter {

    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;
    String path = "./ALshmowkh_db/test/test20.xml";
//    Root sarfRoot;
    int countRoot;
    Element past;
    Element present;
    Element term;
//    Element conjugation;
//    UnaugmentedTrilateralRoot sarfRoot;
    String[] pronouns;
    List list;

    public XMLExporter() throws ParserConfigurationException {
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.newDocument();
        countRoot = 0;
        pronouns = new String[]{"أنا", "نحن", "أنتَ", "أنتِ", "أنتما", "أنتم", "أنتنُّ", "هو", "هي", "هما", "هما2", "هم", "هنُّ"};
    }

    void writeToXML(String rootIn) throws SAXException, ParserConfigurationException, IOException, TransformerException {

        doc.appendChild(createBody(rootIn));
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.VERSION, "1.0");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        doc.setXmlStandalone(true);
        DOMSource dom = new DOMSource(doc);
        File out = new File(path);
        StreamResult sr = new StreamResult(out);
        t.transform(dom, sr);

        if (out.exists()) {
            System.out.println("The db has been done created in directory : " + path);
        } else {
            System.out.println("There is error at creating db.");
        }
    }

    Element bindTerm(int p, String word) {
        term = doc.createElement("term");
        String casse, number, sex;
        if (p < 6) {
            casse = "nominative";
        } else if (p < 12) {
            casse = "accussative";
        } else {
            casse = "jussitive";
        }
        if (p == 0 || p == 1 || p == 6 || p == 7 || p == 12 || p == 13) {
            number = "single";
        } else if (p == 2 || p == 3 || p == 8 || p == 9 || p == 14 || p == 15) {
            number = "dual";
        } else {
            number = "plural";
        }
        if (p % 2 == 0) {
            sex = "male";
        } else {
            sex = "female";
        }

        term.setAttribute("case", casse);
        term.setAttribute("number", number);
        term.setAttribute("sex", sex);
        term.setAttribute("value", word);
        term.setAttribute("dediacritic", new Util().deDiacritic(word));
        return term;
    }

    Element bindVerbConjs2(String syntax, RootInfo rootInfo) {
        Element element = doc.createElement(syntax);
        List finalresult;
        ConjugationResult conjResult = UnaugmentedTrilateralModifier.getInstance().build((UnaugmentedTrilateralRoot) rootInfo.getRoot(), rootInfo.getKov(), rootInfo.getList(), rootInfo.getConjugation(), rootInfo.isActive());
        finalresult = conjResult.getFinalResult();

        if (finalresult != null) {
            for (int i = 0; i < 13; i++) {
                Object verb = finalresult.get(i);
                if (verb != null) {
                    term = doc.createElement("term");
                    term.setAttribute("value", verb.toString());
                    term.setAttribute("pronoun", pronouns[i]);
                    term.setAttribute("dediacritic", new Util().deDiacritic(verb.toString()));

                    element.appendChild(term);
                }
            }
        }
        return element;
    }

    Element bindVerbConjs(String syntax, List verbList) {
        Element element = doc.createElement(syntax);

        if (verbList != null) {
            for (int i = 0; i < 13; i++) {
                Object verb = verbList.get(i);
                if (verb != null) {
                    term = doc.createElement("term");
                    term.setAttribute("value", verb.toString());
                    term.setAttribute("pronoun", pronouns[i]);
                    term.setAttribute("dediacritic", new Util().deDiacritic(verb.toString()));

                    element.appendChild(term);
                }
            }
        }
        return element;
    }

    Element bindDerviation(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator) {
        Element element = doc.createElement(syntax);
        Element formula;
        Element indefinite;
        String formu;
        if (syntax.trim().equals("subject") || syntax.trim().equals("object")) {
            if (syntax.trim().equals("subject")) {
                formu = "فَاعِل";
            } else if (syntax.trim().equals("object")) {
                formu = "مَفْعُول";
            } else {
                return null;
            }

            List nouns = conjugator.createNounList(sarfroot, formu);
            formula = doc.createElement("formula");
            indefinite = doc.createElement("indefinite");

            for (int i = 0; i < 18; i++) {
                indefinite.appendChild(bindTerm(i, nouns.get(i).toString()));
            }
            formula.setAttribute("value", formu);
            formula.appendChild(indefinite);
            element.appendChild(formula);
            return element;
        } else if (syntax.trim().equals("elative") || syntax.trim().equals("assimilate") || syntax.trim().equals("timePlace")) {
            formula = doc.createElement("formula");
            indefinite = doc.createElement("indefinite");
            TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(sarfroot);
            if (!nounsObject.getElatives().isEmpty() || !nounsObject.getAssimilates().isEmpty() || !nounsObject.getTimeAndPlaces().isEmpty()) {
                List standFormulas = ElativeNounConjugator.getInstance().getAppliedFormulaList(sarfroot);
                Iterator itr = standFormulas.iterator();
                while (itr.hasNext()) {
                    formu = itr.next().toString();
                    List nouns = ElativeNounConjugator.getInstance().createNounList(sarfroot, formu);
                    for (int i = 0; i < 18; i++) {
                        indefinite.appendChild(bindTerm(i, nouns.get(i).toString()));
                    }
                    formula.setAttribute("value", formu);
                    formula.appendChild(indefinite);
                    element.appendChild(formula);
                }

                return element;
            }
        } else {

            List formulas = conjugator.getAppliedFormulaList(sarfroot);
            Iterator itr = formulas.iterator();
            while (itr.hasNext()) {
                formu = itr.next().toString();
                formula = doc.createElement("formula");
                formula.setAttribute("value", formu);
                indefinite = doc.createElement("indefinite");

                List nouns = conjugator.createNounList(sarfroot, formu);
                for (int i = 0; i < 18; i++) {
                    Object noun = nouns.get(i);
                    if (noun != null) {
                        indefinite.appendChild(this.bindTerm(i, noun.toString()));
                    }
                }
                formula.appendChild(indefinite);
                element.appendChild(formula);
            }
        }
//        if (element.hasChildNodes()) {
        return element;
////        } else {
//            return null;
//        }
    }

//    Element bindDerviation2(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator) {
    Element bindDerviation2(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator, final IUnaugmentedTrilateralNounModifier modifier, int kov) {

        Element element = doc.createElement(syntax);
        Element formulaNode;
        Element indefinite, definite, annexed;
        String formu;
        List nouns;
        ConjugationResult conjResult;
        if (syntax.trim().equals("subject") || syntax.trim().equals("object")) {
            if (syntax.trim().equals("subject")) {
                formu = "فَاعِل";
            } else if (syntax.trim().equals("object")) {
                formu = "مَفْعُول";
            } else {
                return null;
            }

            nouns = conjugator.createNounList(sarfroot, formu);
            conjResult = modifier.build(sarfroot, kov, nouns, formu);
            nouns = conjResult.getFinalResult();
            formulaNode = doc.createElement("formula");
            formulaNode.setAttribute("value", formu);

            indefinite = doc.createElement("indefinite");
            for (int i = 0; i < 18; i++) {
                indefinite.appendChild(bindTerm(i, nouns.get(i).toString()));
            }
            formulaNode.appendChild(indefinite);
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nouns = conjugator.createNounList(sarfroot, formu);
            conjResult = modifier.build(sarfroot, kov, nouns, formu);
            nouns = conjResult.getFinalResult();
            definite = doc.createElement("definite");
            for (int i = 0; i < 18; i++) {
                definite.appendChild(bindTerm(i, nouns.get(i).toString()));
            }
            formulaNode.appendChild(definite);

            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            nouns = conjugator.createNounList(sarfroot, formu);
            conjResult = modifier.build(sarfroot, kov, nouns, formu);
            nouns = conjResult.getFinalResult();
            annexed = doc.createElement("annexed");
            for (int i = 0; i < 18; i++) {
                annexed.appendChild(bindTerm(i, nouns.get(i).toString()));
            }
            formulaNode.appendChild(annexed);

            element.appendChild(formulaNode);
            return element;
        } else if (syntax.trim().equals("elative") || syntax.trim().equals("machine") || syntax.trim().equals("exaggeration") || syntax.trim().equals("assimilate") || syntax.trim().equals("timePlace")) {
            formulaNode = doc.createElement("formula");
            TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(sarfroot);
            if (!nounsObject.getStandardInstrumentals().isEmpty() || !nounsObject.getElatives().isEmpty() || !nounsObject.getStandardExaggerations().isEmpty() || !nounsObject.getAssimilates().isEmpty() || !nounsObject.getTimeAndPlaces().isEmpty()) {
                List formulas = conjugator.getAppliedFormulaList(sarfroot);
                //_______________
                if (syntax.trim().equals("machine")) {
                    formulas.addAll(NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(sarfroot));
                } else if (syntax.trim().equals("exaggeration")) {
                    formulas.addAll(NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(sarfroot));
                }
                //_______________
                Iterator itr = formulas.iterator();
                while (itr.hasNext()) {
                    formu = itr.next().toString();
                    formulaNode.setAttribute("value", formu);
                    indefinite = doc.createElement("indefinite");
                    nouns = conjugator.createNounList(sarfroot, formu);
                    nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
                    for (int i = 0; i < 18; i++) {
                        indefinite.appendChild(bindTerm(i, nouns.get(i).toString()));
                    }
                    formulaNode.appendChild(indefinite);

                    definite = doc.createElement("definite");
                    GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                    nouns = conjugator.createNounList(sarfroot, formu);
                    nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
                    for (int i = 0; i < 18; i++) {
                        definite.appendChild(bindTerm(i, nouns.get(i).toString()));
                    }
                    formulaNode.appendChild(definite);

                    annexed = doc.createElement("annexed");
                    GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                    nouns = conjugator.createNounList(sarfroot, formu);
                    nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
                    for (int i = 0; i < 18; i++) {
                        annexed.appendChild(bindTerm(i, nouns.get(i).toString()));
                    }
                    formulaNode.appendChild(annexed);

                    element.appendChild(formulaNode);
                }

                return element;
            }
        } else {

            List formulas = conjugator.getAppliedFormulaList(sarfroot);
            Iterator itr = formulas.iterator();
            while (itr.hasNext()) {
                formu = itr.next().toString();
                formulaNode = doc.createElement("formula");
                formulaNode.setAttribute("value", formu);
                indefinite = doc.createElement("indefinite");

                nouns = conjugator.createNounList(sarfroot, formu);
                for (int i = 0; i < 18; i++) {
                    Object noun = nouns.get(i);
                    if (noun != null) {
                        indefinite.appendChild(this.bindTerm(i, noun.toString()));
                    }
                }
                formulaNode.appendChild(indefinite);
                element.appendChild(formulaNode);
            }
        }
//        if (element.hasChildNodes()) {
        return element;
////        } else {
//            return null;
//        }
    }

    Element bindDerviationAug(String syntax, AugmentedTrilateralRoot sarfroot, int formulaNo
    ) {
        Element element = doc.createElement(syntax);
        Element indefinite = doc.createElement("indefinite");
        TrilateralAugmentedGerundConjugator.getInstance().setListener(new GerundSelectionUI());
        TrilateralAugmentedGerundConjugator.getInstance().setAugmentedTrilateralModifierListener(new GerundSelectionUI());

        switch (syntax.trim()) {
            case "subject": {
                List nouns = AugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;
            }
            case "object": {
                List nouns = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;

            }
            case "timePlace": {
                List nouns = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createTimeAndPlaceNounList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;
            }
            case "original": {
                List nouns = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;
            }
            case "nomen": {
//                gerunds = sarf.gerund.trilateral.augmented.nomen.TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList((AugmentedTrilateralRoot) selectionInfo.getRoot(), selectionInfo.getAugmentationFormulaNo());
                List nouns = TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;
            }
            case "meem": {
                List nouns = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(sarfroot, formulaNo);

                for (int i = 0; i < 18; i++) {
                    String noun = nouns.get(i).toString();
                    if (!noun.trim().isEmpty()) {
                        indefinite.appendChild(bindTerm(i, noun));
                    }
                }
                element.appendChild(indefinite);
                return element;
            }
            default:
                return element;
        }
    }

    Element bindGerund(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralGerundConjugator conjugator
    ) {
        Element element = doc.createElement(syntax);
        Element formula;
        Element indefinite;
        String formu;
        List formulas;
        if (syntax.trim().equals("quality")) {
            formulas = new ArrayList();
            formulas.add("فِعْلَة");
        } else {
            formulas = conjugator.getAppliedFormulaList(sarfroot);
        }
        Iterator itr = formulas.iterator();

        while (itr.hasNext()) {
            formu = itr.next().toString();
            formula = doc.createElement("formula");
            formula.setAttribute("value", formu);
            indefinite = doc.createElement("indefinite");

            List nouns = conjugator.createGerundList(sarfroot, formu);
            for (int i = 0; i < 18; i++) {
                String noun = nouns.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    indefinite.appendChild(this.bindTerm(i, noun));
                }
            }
            formula.appendChild(indefinite);
            element.appendChild(formula);
        }

//        }
        return element;
    }

    Element bindTriUnAug(String rootText
    ) {
        int kov = KovRulesManager.getInstance().getTrilateralKovRule(rootText.charAt(0), rootText.charAt(1), rootText.charAt(2)).getKov();

        Element unaugmented = doc.createElement("unaugmented");
        Element conjugation = null;
        List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootText);
        Iterator itr1 = unaugmentedList.iterator();
        while (itr1.hasNext()) {
            UnaugmentedTrilateralRoot sarfRoot = (UnaugmentedTrilateralRoot) itr1.next();
//            RootInfo rootInfo = new RootInfo(sarfRoot, true, false, kov);
            conjugation = doc.createElement("conjuagtion");
            conjugation.setAttribute("id", sarfRoot.getConjugation());
            String pastRootText = ActivePastConjugator.getInstance().createVerb(7, sarfRoot).toString();
            String presentRootText = ActivePresentConjugator.getInstance().createNominativeVerb(7, sarfRoot).toString();

            past = doc.createElement("past");
            term = doc.createElement("term");
            term.setAttribute("value", pastRootText);
            past.appendChild(term);
            present = doc.createElement("present");
            term = doc.createElement("term");
            term.setAttribute("value", presentRootText);
            present.appendChild(term);
            conjugation.appendChild(past);
            conjugation.appendChild(present);
            //----------------------------------------------
            Element verbConjugation = doc.createElement("verbConjugation");
            Element activeVerb = doc.createElement("activeVerb");
            activeVerb.appendChild(this.bindVerbConjs2("past", new RootInfo(sarfRoot, ActivePastConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, true, SystemConstants.PAST_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("nominativePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createNominativeVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("accusativePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createAccusativeVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("jussitivePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createJussiveVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("emphasize", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("imperative", new RootInfo(sarfRoot, UnaugmentedImperativeConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, true, SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE)));
            activeVerb.appendChild(this.bindVerbConjs2("emphEmperative", new RootInfo(sarfRoot, UnaugmentedImperativeConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, true, SystemConstants.EMPHASIZED_IMPERATIVE_TENSE)));
            verbConjugation.appendChild(activeVerb);

            Element passiveVerb = doc.createElement("passiveVerb");
            //"past", "nominative", "accusative", "jussive", "emphasize"
            passiveVerb.appendChild(this.bindVerbConjs2("past", new RootInfo(sarfRoot, PassivePastConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, false, SystemConstants.PAST_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjs2("nominativePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createNominativeVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjs2("accusativePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createAccusativeVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjs2("jussitivePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createJussiveVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjs2("emphasize", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            verbConjugation.appendChild(passiveVerb);
            conjugation.appendChild(verbConjugation);
            //derviations
            Element nounDerviation = doc.createElement("nounDerviation");
            nounDerviation.appendChild(this.bindDerviation2("subject", sarfRoot, UnaugmentedTrilateralActiveParticipleConjugator.getInstance(), ActiveParticipleModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2("object", sarfRoot, UnaugmentedTrilateralPassiveParticipleConjugator.getInstance(), PassiveParticipleModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2("exaggeration", sarfRoot, StandardExaggerationConjugator.getInstance(), ExaggerationModifier.getInstance(), kov));

//            nounDerviation.appendChild(this.bindDerviation("", sarfRoot, StandardExaggerationConjugator.getInstance()));
////            nounDerviation.appendChild(this.bindDerviation("exaggeration2", sarfRoot, NonStandardExaggerationConjugator.getInstance()));
//            nounDerviation.appendChild(this.bindDerviation("machine", sarfRoot, StandardInstrumentalConjugator.getInstance()));
////            nounDerviation.appendChild(this.bindDerviation("machine2", sarfRoot, NonStandardInstrumentalConjugator.getInstance()));
////            nounDerviation.appendChild(this.bindDerviation("timiePlace", sarfRoot, TimeAndPlaceConjugator.getInstance()));
//            nounDerviation.appendChild(this.bindDerviation("elative", sarfRoot, ElativeNounConjugator.getInstance()));
//            nounDerviation.appendChild(this.bindDerviation("assimilate", sarfRoot, AssimilateAdjectiveConjugator.getInstance()));
            conjugation.appendChild(nounDerviation);
            //gerunds
//            Element gerund = doc.createElement("gerund");
//            gerund.appendChild(this.bindGerund("original", sarfRoot, TrilateralUnaugmentedGerundConjugator.getInstance()));
//            gerund.appendChild(this.bindGerund("meem", sarfRoot, MeemGerundConjugator.getInstance()));
//            gerund.appendChild(this.bindGerund("nomen", sarfRoot, TrilateralUnaugmentedNomenGerundConjugator.getInstance()));
//            gerund.appendChild(this.bindGerund("quality", sarfRoot, QualityGerundConjugator.getInstance()));
//
//            conjugation.appendChild(gerund);
            //--------------------
            unaugmented.appendChild(conjugation);
        }
        return unaugmented;
    }

    Element bindTriAug(String rootText
    ) {
        Element augmented = doc.createElement("augmented");
        Element conjugation = null;
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootText);
        Iterator itr1 = augmentedRoot.getAugmentationList().iterator();

        while (itr1.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) itr1.next();
            int formulaNo = formula.getFormulaNo();
            conjugation = doc.createElement("conjuagtion");
            conjugation.setAttribute("id", formulaNo + "");

            String pastRootText = AugmentedActivePastConjugator.getInstance().createVerb(augmentedRoot, 7, formulaNo).toString();
            String presentRootText = AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerb(augmentedRoot, 7, formulaNo).toString();

            past = doc.createElement("past");
            term = doc.createElement("term");
            term.setAttribute("value", pastRootText);
            past.appendChild(term);
            present = doc.createElement("present");
            term = doc.createElement("term");
            term.setAttribute("value", presentRootText);
            present.appendChild(term);
            conjugation.appendChild(past);
            conjugation.appendChild(present);
            //----------------------------------------------
            Element verbConjugation = doc.createElement("verbConjugation");
            Element activeVerb = doc.createElement("activeVerb");
            activeVerb.appendChild(this.bindVerbConjs("past", AugmentedActivePastConjugator.getInstance().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("nominativePresent", AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("accusativePresent", AugmentedActivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("jussitivePresent", AugmentedActivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("emphasize", AugmentedActivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("imperative", AugmentedImperativeConjugator.getInstance().getEmphsizedConjugator().createVerbList(augmentedRoot, formulaNo)));
            activeVerb.appendChild(this.bindVerbConjs("emphEmperative", AugmentedImperativeConjugator.getInstance().getEmphsizedConjugator().createVerbList(augmentedRoot, formulaNo)));
            verbConjugation.appendChild(activeVerb);
//
            Element passiveVerb = doc.createElement("passiveVerb");
//            //"past", "nominative", "accusative", "jussive", "emphasize"
            passiveVerb.appendChild(this.bindVerbConjs("past", AugmentedPassivePastConjugator.getInstance().createVerbList(augmentedRoot, formulaNo)));
            passiveVerb.appendChild(this.bindVerbConjs("nominativePresent", AugmentedPassivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(augmentedRoot, formulaNo)));
            passiveVerb.appendChild(this.bindVerbConjs("accusativePresent", AugmentedPassivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(augmentedRoot, formulaNo)));
            passiveVerb.appendChild(this.bindVerbConjs("jussitivePresent", AugmentedPassivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(augmentedRoot, formulaNo)));
            passiveVerb.appendChild(this.bindVerbConjs("emphasize", AugmentedPassivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(augmentedRoot, formulaNo)));
            verbConjugation.appendChild(passiveVerb);
//
            conjugation.appendChild(verbConjugation);
//
//            //derviations
            Element nounDerviation = doc.createElement("nounDerviation");
            nounDerviation.appendChild(this.bindDerviationAug("subject", augmentedRoot, formulaNo));
            nounDerviation.appendChild(this.bindDerviationAug("object", augmentedRoot, formulaNo));
            nounDerviation.appendChild(this.bindDerviationAug("timePlace", augmentedRoot, formulaNo));
            conjugation.appendChild(nounDerviation);
//
//            //gerunds
            Element gerund = doc.createElement("gerund");
//            gerund.appendChild(this.bindDerviationAug("original", augmentedRoot, formulaNo));
//            gerund.appendChild(this.bindDerviationAug("nomen", augmentedRoot, formulaNo));
            gerund.appendChild(this.bindDerviationAug("meem", augmentedRoot, formulaNo));

            conjugation.appendChild(gerund);
            //--------------------
            augmented.appendChild(conjugation);
        }
        return augmented;
    }

    Element createBody(String rootText) throws ParserConfigurationException {
        Element roots = doc.createElement("roots");
        countRoot++;
        Element root = doc.createElement("root");
        //set attributes
        root.setAttribute("id", countRoot + "");
        root.setAttribute("value", rootText);
        if (rootText.trim().length() == 3) {
            root.setAttribute("type", "trilateral");
        } else if (rootText.trim().length() == 4) {
            root.setAttribute("type", "quadilateral");
        } else {
            root.setAttribute("type", "unknown");
        }
        //binding
        //------------------------------
        root.appendChild(bindTriUnAug(rootText));
        //-------------------------------------------
//        root.appendChild(bindTriAug(rootText));
        //------------------

//----------------------------------  
        roots.appendChild(root);
        return roots;
    }

    List<String> importRoots() throws SAXException, IOException {
        List<String> rawroots = new ArrayList();
        doc = db.parse(new File("./ALshmowkh_db/testRoots.xml"));
        NodeList rootsNodes = doc.getElementsByTagName("root");
        for (int i = 0; i < rootsNodes.getLength(); i++) {
            String rot = rootsNodes.item(i).getAttributes().getNamedItem("value").getNodeValue();
            rawroots.add(rot);
        }
        return rawroots;
    }

    static void initi() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        XMLExporter exporter = new XMLExporter();
//        List<String> rawroots = exporter.importRoots();
//        for(String s:rawroots){
//            pl(s);
//        }
        String rootIn = "حبب";

        exporter.writeToXML(rootIn);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        initi();
    }

    static void pl(Object o) {
        System.out.println(o);
    }
}
