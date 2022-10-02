package XMLExport;

import util.Constants;
import Sarafy.Root;
import Sarafy.RootInfo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sarf.AugmentationFormula;
import sarf.SarfDictionary;
import sarf.SystemConstants;
import sarf.Validator;
import sarf.gerund.modifier.trilateral.unaugmented.meem.TitlateralUnaugmentedMeemModifier;
import sarf.gerund.modifier.trilateral.unaugmented.nomen.TitlateralUnaugmentedNomenModifier;
import sarf.gerund.modifier.trilateral.unaugmented.quality.TitlateralUnaugmentedQualityModifier;
import sarf.gerund.modifier.trilateral.unaugmented.standard.UnaugmentedTrilateralStandardGerundModifier;
import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerund;
//import sarf.gerund.trilateral.augmented.TrilateralAugmentedGerundConjugator;
import sarf.gerund.trilateral.augmented.nomen.TrilateralAugmentedNomenGerundConjugator;
import sarf.gerund.trilateral.unaugmented.IUnaugmentedTrilateralGerundConjugator;
import sarf.gerund.trilateral.unaugmented.QualityGerundConjugator;
import sarf.gerund.trilateral.unaugmented.StandardTrilateralUnaugmentedSuffixContainer;
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
//import sarf.noun.trilateral.unaugmented.assimilate.AssimilateAdjectiveConjugator;
import sarf.noun.trilateral.unaugmented.elative.ElativeNounConjugator;
//import sarf.noun.trilateral.unaugmented.exaggeration.NonStandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.exaggeration.StandardExaggerationConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.NonStandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.instrumental.StandardInstrumentalConjugator;
import sarf.noun.trilateral.unaugmented.modifier.IUnaugmentedTrilateralNounModifier;
import sarf.noun.trilateral.unaugmented.modifier.activeparticiple.ActiveParticipleModifier;
import sarf.noun.trilateral.unaugmented.modifier.assimilate.AssimilateModifier;
import sarf.noun.trilateral.unaugmented.modifier.elative.ElativeModifier;
import sarf.noun.trilateral.unaugmented.modifier.exaggeration.ExaggerationModifier;
import sarf.noun.trilateral.unaugmented.modifier.instrumental.InstrumentalModifier;
import sarf.noun.trilateral.unaugmented.modifier.passiveparticiple.PassiveParticipleModifier;
import sarf.noun.trilateral.unaugmented.modifier.timeandplace.TimeAndPlaceModifier;
//import sarf.noun.trilateral.unaugmented.timeandplace.TimeAndPlaceConjugator;
import sarf.ui.SelectionInfo;
import sarf.ui.controlpane.ActiveVerbSelectionUI;
import sarf.ui.controlpane.GerundSelectionUI;
import sarf.verb.trilateral.augmented.AugmentedTrilateralRoot;
import sarf.verb.trilateral.augmented.active.past.AugmentedActivePastConjugator;
import sarf.verb.trilateral.augmented.active.present.AugmentedActivePresentConjugator;
import sarf.verb.trilateral.augmented.imperative.AugmentedImperativeConjugator;
import sarf.verb.trilateral.augmented.modifier.AugmentedTrilateralModifier;
import sarf.verb.trilateral.augmented.passive.past.AugmentedPassivePastConjugator;
import sarf.verb.trilateral.augmented.passive.present.AugmentedPassivePresentConjugator;
import sarf.verb.trilateral.unaugmented.ConjugationResult;
//import sarf.verb.trilateral.augmented.ConjugationResult;
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
    String path = "./ALshmowkh_db/test/test80.xml";
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

    void writeToXML(Element rootNode) throws SAXException, ParserConfigurationException, IOException, TransformerException {

        doc.appendChild(rootNode);
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

    Element bindVerbConjsUnAug(String syntax, RootInfo rootInfo) {
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

    Element bindVerbConjsAug(String syntax, List verbList, AugmentedTrilateralRoot sarfroot, int formula, int kov, String tense, boolean active) {
        Element element = doc.createElement(syntax);
        List finalresult;
        sarf.verb.trilateral.augmented.ConjugationResult conjResult = AugmentedTrilateralModifier.getInstance().build(sarfroot, kov, formula, verbList, tense, active, null);
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

    Element bindVerbConjsAug2(String syntax, List verbList) {
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

    Element bindDerviation(String syntax, UnaugmentedTrilateralRoot sarfroot, int kov) {

        Element element = doc.createElement(syntax);
        Element formulaNode;
        Element indefinite, definite, annexed;
        String formu = "";
        List nouns;
        List formulas = new ArrayList();
        ConjugationResult conjResult;
        IUnaugmentedTrilateralNounConjugator conjugator = null;
        IUnaugmentedTrilateralNounModifier modifier = null;

        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(sarfroot);

        if (syntax.trim().equals(Constants.SUBJUCT)) {
            formulas.add("فَاعِل");
            conjugator = UnaugmentedTrilateralActiveParticipleConjugator.getInstance();
            modifier = ActiveParticipleModifier.getInstance();
        } else if (syntax.trim().equals(Constants.OBJECT)) {
            formulas.add("مَفْعُول");
            conjugator = UnaugmentedTrilateralPassiveParticipleConjugator.getInstance();
            modifier = PassiveParticipleModifier.getInstance();
        } else if (syntax.trim().equals(Constants.INSTRUMENTAL)) {
            conjugator = StandardInstrumentalConjugator.getInstance();
            modifier = InstrumentalModifier.getInstance();
            formulas = conjugator.getAppliedFormulaList(sarfroot);
            if (nounsObject.getNonStandardInstrumentals().isEmpty()) {
                formulas.addAll(NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(sarfroot));
            }
        } else if (syntax.trim().equals(Constants.EXAGGERATION)) {
            conjugator = StandardExaggerationConjugator.getInstance();
            modifier = ExaggerationModifier.getInstance();
            formulas = conjugator.getAppliedFormulaList(sarfroot);

            if (nounsObject.getNonStandardExaggerations().isEmpty()) {
                formulas.addAll(NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(sarfroot));
            }
        } else if (syntax.trim().equals(Constants.ELATIVE)) {
            formulas.add("فَاعِل");
            conjugator = UnaugmentedTrilateralActiveParticipleConjugator.getInstance();
            modifier = ActiveParticipleModifier.getInstance();

        } else if (syntax.trim().equals(Constants.ASSIMILATE)) {
            formulas.add("فَاعِل");
            conjugator = UnaugmentedTrilateralActiveParticipleConjugator.getInstance();
            modifier = ActiveParticipleModifier.getInstance();

        } else if (syntax.trim().equals(Constants.TIMEPLACE)) {
            formulas.add("فَاعِل");
            conjugator = UnaugmentedTrilateralActiveParticipleConjugator.getInstance();
            modifier = ActiveParticipleModifier.getInstance();

        } else {
            System.out.println("error");
        }
        if (true) {

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
        } else if (syntax.trim().equals("machine")) {

        } else if (syntax.trim().equals("exaggeration")) {

        } else {
            formulaNode = doc.createElement("formula");
            if (syntax.trim().equals("machine")) {
                formulas = NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(sarfroot);
            } else if (syntax.trim().equals("exaggeration")) {
                formulas = NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(sarfroot);
            }
            if (!nounsObject.getStandardInstrumentals().isEmpty() || !nounsObject.getElatives().isEmpty() || !nounsObject.getStandardExaggerations().isEmpty() || !nounsObject.getAssimilates().isEmpty() || !nounsObject.getTimeAndPlaces().isEmpty()) {
                formulas.addAll(conjugator.getAppliedFormulaList(sarfroot));
                //_______________

                //_______________
                return element;
            }
        }
        return element;
    }

    Element bindFormula(String formula, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator, IUnaugmentedTrilateralNounModifier modifier, int kov) {
        Element formulaNode = doc.createElement("formula");
        formulaNode.setAttribute("value", formula);

        Element indefinite, definite, annexed;
        List nouns;
        indefinite = doc.createElement("indefinite");
        try {
            nouns = conjugator.createNounList(sarfroot, formula);
            nouns = modifier.build(sarfroot, kov, nouns, formula).getFinalResult();
            for (int i = 0; i < 18; i++) {
                indefinite.appendChild(bindTerm(i, nouns.get(i).toString()));
            }
        } catch (Exception e) {

        }
        formulaNode.appendChild(indefinite);

        definite = doc.createElement("definite");
        GenericNounSuffixContainer.getInstance().selectDefiniteMode();
        nouns = conjugator.createNounList(sarfroot, formula);
        nouns = modifier.build(sarfroot, kov, nouns, formula).getFinalResult();
        for (int i = 0; i < 18; i++) {
            definite.appendChild(bindTerm(i, nouns.get(i).toString()));
        }
        formulaNode.appendChild(definite);

        annexed = doc.createElement("annexed");
        GenericNounSuffixContainer.getInstance().selectAnnexedMode();
        nouns = conjugator.createNounList(sarfroot, formula);
        nouns = modifier.build(sarfroot, kov, nouns, formula).getFinalResult();
        for (int i = 0; i < 18; i++) {
            annexed.appendChild(bindTerm(i, nouns.get(i).toString()));
        }
        formulaNode.appendChild(annexed);
//        }
        return formulaNode;
    }


//    Element bindDerviation2(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator) {

    Element bindDerviation3(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator, IUnaugmentedTrilateralNounModifier modifier, int kov) {

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
//        } else if (syntax.trim().equals("elative") || syntax.trim().equals("machine") || syntax.trim().equals("exaggeration") || syntax.trim().equals("assimilate") || syntax.trim().equals("timePlace")) {

        } else {
            formulaNode = doc.createElement("formula");
            TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(sarfroot);
            List formulas = null;
            if (syntax.trim().equals("machine")) {
                formulas = NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(sarfroot);
            } else if (syntax.trim().equals("exaggeration")) {
                formulas = NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(sarfroot);
            }
            if (!nounsObject.getStandardInstrumentals().isEmpty() || !nounsObject.getElatives().isEmpty() || !nounsObject.getStandardExaggerations().isEmpty() || !nounsObject.getAssimilates().isEmpty() || !nounsObject.getTimeAndPlaces().isEmpty()) {
                formulas.addAll(conjugator.getAppliedFormulaList(sarfroot));
                //_______________

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
        }
//        if (element.hasChildNodes()) {
        return element;
////        } else {
//            return null;
//        }
    }

    Element bindDerviation2(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralNounConjugator conjugator, IUnaugmentedTrilateralNounModifier modifier, int kov) {

        Element element = doc.createElement(syntax);
        String formula;
        List formulas = new ArrayList();

        if (syntax.trim().equals(Constants.SUBJUCT)) {
            formulas.add("فَاعِل");
        } else if (syntax.trim().equals(Constants.OBJECT)) {
            formulas.add("مَفْعُول");
        } else {
            formulas = conjugator.getAppliedFormulaList(sarfroot);
        }
        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(sarfroot);
        if (syntax.equals(Constants.TIMEPLACE)) {
            if (nounsObject.getTimeAndPlaces() != null && !nounsObject.getTimeAndPlaces().isEmpty()) {
                try {
                    Iterator itr = formulas.iterator();
                    while (itr.hasNext()) {
                        formula = itr.next().toString();
//                        pl(formula);
                        element.appendChild(this.bindFormula(formula, sarfroot, conjugator, modifier, kov));
                    }
                } catch (Exception e) {

                }
            }
            return element;
        }
        if (syntax.equals(Constants.ASSIMILATE)) {
            if (nounsObject.getAssimilates() != null && !nounsObject.getAssimilates().isEmpty()) {
                try {
                    Iterator itr = formulas.iterator();
                    while (itr.hasNext()) {
                        formula = itr.next().toString();
                        element.appendChild(this.bindFormula(formula, sarfroot, conjugator, modifier, kov));
                    }
                } catch (Exception e) {

                }
            }
            return element;
        }

        try {
            Iterator itr = formulas.iterator();
            while (itr.hasNext()) {
                formula = itr.next().toString();
                element.appendChild(this.bindFormula(formula, sarfroot, conjugator, modifier, kov));
            }
        } catch (Exception e) {

        }
//        if (syntax.trim().equals(Constants.INSTRUMENTAL)) {
////            if (!nounsObject.getNonStandardInstrumentals().isEmpty()) {
//            formulas = NonStandardInstrumentalConjugator.getInstance().getAppliedFormulaList(sarfroot);
//            if (!formulas.isEmpty()) {
//                itr = formulas.iterator();
//                while (itr.hasNext()) {
//                    formula = itr.next().toString();
//                    element.appendChild(this.bindFormula(formula, sarfroot, NonStandardInstrumentalConjugator.getInstance(), modifier, kov));
//                }
//            }
////            }
//        }
        if (syntax.trim().equals(Constants.EXAGGERATION)) {
            if (nounsObject.getNonStandardExaggerations() != null) {
                if (!nounsObject.getNonStandardExaggerations().isEmpty()) {
                    formulas = NonStandardExaggerationConjugator.getInstance().getAppliedFormulaList(sarfroot);
                    pl(formulas);
                    if (!formulas.isEmpty()) {
                        Iterator itr2 = formulas.iterator();
                        while (itr2.hasNext()) {
                            formula = itr2.next().toString();
                            element.appendChild(this.bindFormula(formula, sarfroot, NonStandardExaggerationConjugator.getInstance(), modifier, kov));
                        }
                    }
                }
            }
        }

        return element;
    }

    Element bindDerviationAug(String syntax, AugmentedTrilateralRoot sarfroot, int formulaNo, int kov) {
        Element element = doc.createElement(syntax);
        Element indefinite = doc.createElement("indefinite");
        Element definite = doc.createElement("definite");
        Element annexed = doc.createElement("annexed");

//        TrilateralAugmentedGerundConjugator.getInstance().setListener(new GerundSelectionUI());
//        TrilateralAugmentedGerundConjugator.getInstance().setAugmentedTrilateralModifierListener(new GerundSelectionUI());
        List nounsIndef = null;
        List nounsDef = null;
        List nounsAnn = null;
        String s = syntax.toLowerCase().trim();
        if (s.equals(Constants.SUBJUCT)) {
            nounsIndef = AugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsIndef = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsDef = AugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsDef = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            nounsAnn = AugmentedTrilateralActiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsAnn = sarf.noun.trilateral.augmented.modifier.activeparticiple.ActiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();

        } else if (s.equals(Constants.OBJECT)) {
            nounsIndef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsIndef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsDef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsDef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();

            nounsAnn = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createNounList(sarfroot, formulaNo);
            nounsAnn = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();

        } else if (s.equals(Constants.TIMEPLACE)) {
            nounsIndef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createTimeAndPlaceNounList(sarfroot, formulaNo);
            nounsIndef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsDef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createTimeAndPlaceNounList(sarfroot, formulaNo);
            nounsDef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            nounsAnn = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createTimeAndPlaceNounList(sarfroot, formulaNo);
            nounsAnn = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();

        } else if (s.equals(Constants.ORIGINAL)) {
            try {
                nounsIndef = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
                nounsIndef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

            }
        } else if (s.equals(Constants.NOMEN)) {
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsIndef = TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
            nounsIndef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();

        } else if (s.equals(Constants.MEEM)) {
            nounsIndef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(sarfroot, formulaNo);
            nounsIndef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsDef = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(sarfroot, formulaNo);
            nounsDef = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();
            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            nounsAnn = AugmentedTrilateralPassiveParticipleConjugator.getInstance().createMeemGerundNounList(sarfroot, formulaNo);
            nounsAnn = sarf.noun.trilateral.augmented.modifier.passiveparticiple.PassiveParticipleModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();

        }

        if (nounsIndef != null && nounsDef != null && nounsAnn != null) {
            for (int i = 0; i < 18; i++) {
                String noun = nounsIndef.get(i).toString();
                String nounDef = nounsDef.get(i).toString();
                String nounAnn = nounsAnn.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    indefinite.appendChild(bindTerm(i, noun));
                    definite.appendChild(bindTerm(i, nounDef));
                    annexed.appendChild(bindTerm(i, nounAnn));

                }
            }
            element.appendChild(indefinite);
            element.appendChild(definite);
            element.appendChild(annexed);

        }
        return element;
    }

    Element bindOrgAugGerund(String syntax, AugmentedTrilateralRoot sarfroot, int formulaNo, int kov) throws ClassNotFoundException {
        Element element = doc.createElement(syntax);
        Element indefinite = doc.createElement("indefinite");
        Element definite = doc.createElement("definite");
        Element annexed = doc.createElement("annexed");
        List nounsIndef = null;
        List nounsDef = null;
        List nounsAnn = null;
        String s = syntax.toLowerCase().trim();
        if (s.equals(Constants.ORIGINAL)) {
            try {
                nounsIndef = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
                nounsIndef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();
                GenericNounSuffixContainer.getInstance().selectDefiniteMode();
                nounsDef = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
                nounsDef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();
                GenericNounSuffixContainer.getInstance().selectAnnexedMode();
                nounsAnn = TrilateralAugmentedGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
                nounsAnn = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();
            } catch (Exception e) {

            }
        } else if (s.equals(Constants.NOMEN)) {
            nounsIndef = XMLExport.TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
            nounsIndef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsIndef, null).getFinalResult();

            GenericNounSuffixContainer.getInstance().selectDefiniteMode();
            nounsDef = XMLExport.TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
            nounsDef = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsDef, null).getFinalResult();

            GenericNounSuffixContainer.getInstance().selectAnnexedMode();
            nounsAnn = XMLExport.TrilateralAugmentedNomenGerundConjugator.getInstance().createGerundList(sarfroot, formulaNo);
            nounsAnn = sarf.gerund.modifier.trilateral.augmented.standard.TitlateralAugmentedStandardModifier.getInstance().build(sarfroot, kov, formulaNo, nounsAnn, null).getFinalResult();
        }

        if (nounsIndef != null && nounsDef != null && nounsAnn != null) {
            for (int i = 0; i < 18; i++) {
                String noun = nounsIndef.get(i).toString();
                String nounDef = nounsDef.get(i).toString();
                String nounAnn = nounsAnn.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    indefinite.appendChild(bindTerm(i, noun));
                    definite.appendChild(bindTerm(i, nounDef));
                    annexed.appendChild(bindTerm(i, nounAnn));
                }
            }
            element.appendChild(indefinite);
            element.appendChild(definite);
            element.appendChild(annexed);

        }
        return element;
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

    Element bindGerund(String syntax, UnaugmentedTrilateralRoot sarfroot, IUnaugmentedTrilateralGerundConjugator conjugator, IUnaugmentedTrilateralNounModifier modifier, int kov) {
        Element element = doc.createElement(syntax);
        Element formula;
        Element indefinite, definite, annexed;
        String formu;
        List formulas;
        INounSuffixContainer nounSuffix;
        if (syntax.trim().equals("quality")) {
            formulas = new ArrayList();
            formulas.add("فِعْلَة");
        } else {
            formulas = conjugator.getAppliedFormulaList(sarfroot);

        }
        if (syntax.trim().equals("original")) {
            nounSuffix = StandardTrilateralUnaugmentedSuffixContainer.getInstance();
        } else {
            nounSuffix = GenericNounSuffixContainer.getInstance();
        }
        Iterator itr = formulas.iterator();

        while (itr.hasNext()) {
            formu = itr.next().toString();
            formula = doc.createElement("formula");
            formula.setAttribute("value", formu);

            indefinite = doc.createElement("indefinite");
            List nouns = conjugator.createGerundList(sarfroot, formu);
            nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
            for (int i = 0; i < 18; i++) {
                String noun = nouns.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    indefinite.appendChild(this.bindTerm(i, noun));
                }
            }
            formula.appendChild(indefinite);

            definite = doc.createElement("definite");
            nounSuffix.selectDefiniteMode();
            nouns = conjugator.createGerundList(sarfroot, formu);
            nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
            for (int i = 0; i < 18; i++) {
                String noun = nouns.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    definite.appendChild(this.bindTerm(i, noun));
                }
            }
            formula.appendChild(definite);

            annexed = doc.createElement("annexed");
            nounSuffix.selectAnnexedMode();
            nouns = conjugator.createGerundList(sarfroot, formu);
            nouns = modifier.build(sarfroot, kov, nouns, formu).getFinalResult();
            for (int i = 0; i < 18; i++) {
                String noun = nouns.get(i).toString();
                if (!noun.trim().isEmpty()) {
                    annexed.appendChild(this.bindTerm(i, noun));
                }
            }
            formula.appendChild(annexed);

            element.appendChild(formula);
        }
        return element;
    }

    Element bindTriUnAug(String rootText) {
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
            List pastActive = UnaugmentedTrilateralModifier.getInstance().build(sarfRoot, kov, ActivePastConjugator.getInstance().createVerbList(sarfRoot), SystemConstants.PAST_TENSE, true).getFinalResult();
            List presentActive = UnaugmentedTrilateralModifier.getInstance().build(sarfRoot, kov, ActivePresentConjugator.getInstance().createNominativeVerbList(sarfRoot), SystemConstants.PRESENT_TENSE, true).getFinalResult();

            String pastRootText = pastActive.get(7).toString();
            String presentRootText = presentActive.get(7).toString();

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
            activeVerb.appendChild(this.bindVerbConjsUnAug("past", new RootInfo(sarfRoot, ActivePastConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, true, SystemConstants.PAST_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("nominativePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createNominativeVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("accusativePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createAccusativeVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("jussitivePresent", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createJussiveVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("emphasize", new RootInfo(sarfRoot, ActivePresentConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, true, SystemConstants.PRESENT_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("imperative", new RootInfo(sarfRoot, UnaugmentedImperativeConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, true, SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE)));
            activeVerb.appendChild(this.bindVerbConjsUnAug("emphEmperative", new RootInfo(sarfRoot, UnaugmentedImperativeConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, true, SystemConstants.EMPHASIZED_IMPERATIVE_TENSE)));
            verbConjugation.appendChild(activeVerb);

            Element passiveVerb = doc.createElement("passiveVerb");
            //"past", "nominative", "accusative", "jussive", "emphasize"
            passiveVerb.appendChild(this.bindVerbConjsUnAug("past", new RootInfo(sarfRoot, PassivePastConjugator.getInstance().createVerbList(sarfRoot), true, false, kov, false, SystemConstants.PAST_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjsUnAug("nominativePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createNominativeVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjsUnAug("accusativePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createAccusativeVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjsUnAug("jussitivePresent", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createJussiveVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            passiveVerb.appendChild(this.bindVerbConjsUnAug("emphasize", new RootInfo(sarfRoot, PassivePresentConjugator.getInstance().createEmphasizedVerbList(sarfRoot), true, false, kov, false, SystemConstants.PRESENT_TENSE)));
            verbConjugation.appendChild(passiveVerb);
            conjugation.appendChild(verbConjugation);
            //derviations
            Element nounDerviation = doc.createElement("nounDerviation");
            nounDerviation.appendChild(this.bindDerviation2(Constants.SUBJUCT, sarfRoot, UnaugmentedTrilateralActiveParticipleConjugator.getInstance(), ActiveParticipleModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.OBJECT, sarfRoot, UnaugmentedTrilateralPassiveParticipleConjugator.getInstance(), PassiveParticipleModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.EXAGGERATION, sarfRoot, StandardExaggerationConjugator.getInstance(), ExaggerationModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.INSTRUMENTAL, sarfRoot, StandardInstrumentalConjugator.getInstance(), InstrumentalModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.TIMEPLACE, sarfRoot, TimeAndPlaceConjugator.getInstance(), TimeAndPlaceModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.ELATIVE, sarfRoot, ElativeNounConjugator.getInstance(), ElativeModifier.getInstance(), kov));
            nounDerviation.appendChild(this.bindDerviation2(Constants.ASSIMILATE, sarfRoot, AssimilateAdjectiveConjugator.getInstance(), AssimilateModifier.getInstance(), kov));
            conjugation.appendChild(nounDerviation);
            //gerunds
            Element gerund = doc.createElement("gerund");
            gerund.appendChild(this.bindGerund("original", sarfRoot, TrilateralUnaugmentedGerundConjugator.getInstance(), UnaugmentedTrilateralStandardGerundModifier.getInstance(), kov));
            gerund.appendChild(this.bindGerund("meem", sarfRoot, MeemGerundConjugator.getInstance(), TitlateralUnaugmentedMeemModifier.getInstance(), kov));
            gerund.appendChild(this.bindGerund("nomen", sarfRoot, TrilateralUnaugmentedNomenGerundConjugator.getInstance(), TitlateralUnaugmentedNomenModifier.getInstance(), kov));
            gerund.appendChild(this.bindGerund("quality", sarfRoot, QualityGerundConjugator.getInstance(), TitlateralUnaugmentedQualityModifier.getInstance(), kov));
//
            conjugation.appendChild(gerund);
            //--------------------
            unaugmented.appendChild(conjugation);
        }
        return unaugmented;
    }

    Element bindTriAug(String rootText) throws ClassNotFoundException {
        Element augmented = doc.createElement("augmented");
        Element conjugation = null;
        AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootText);
        int kov = KovRulesManager.getInstance().getTrilateralKovRule(rootText.charAt(0), rootText.charAt(1), rootText.charAt(2)).getKov();
        if (augmentedRoot == null) {
            return augmented;
        }
        Iterator itr1 = augmentedRoot.getAugmentationList().iterator();

        while (itr1.hasNext()) {
            AugmentationFormula formula = (AugmentationFormula) itr1.next();
            int formulaNo = formula.getFormulaNo();
            conjugation = doc.createElement("conjuagtion");
            conjugation.setAttribute("id", formulaNo + "");

            List pastActive = AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedActivePastConjugator.getInstance().createVerbList(augmentedRoot, formulaNo), SystemConstants.PAST_TENSE, true, null).getFinalResult();
            List presentActive = AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedActivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, true, null).getFinalResult();

            String pastRootText = pastActive.get(7).toString();
            String presentRootText = presentActive.get(7).toString();

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

            activeVerb.appendChild(this.bindVerbConjsAug2("nominativePresent", presentActive));
            activeVerb.appendChild(this.bindVerbConjsAug2("accusativePresent", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedActivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, true, null).getFinalResult()));
            activeVerb.appendChild(this.bindVerbConjsAug2("jussitivePresent", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedActivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, true, null).getFinalResult()));
            activeVerb.appendChild(this.bindVerbConjsAug2("emphasize", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedActivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, true, null).getFinalResult()));
            activeVerb.appendChild(this.bindVerbConjsAug2("imperative", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedImperativeConjugator.getInstance().getNotEmphsizedConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.NOT_EMPHASIZED_IMPERATIVE_TENSE, true, null).getFinalResult()));
            activeVerb.appendChild(this.bindVerbConjsAug2("emphEmperative", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedImperativeConjugator.getInstance().getEmphsizedConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.EMPHASIZED_IMPERATIVE_TENSE, true, null).getFinalResult()));

            verbConjugation.appendChild(activeVerb);

            Element passiveVerb = doc.createElement("passiveVerb");
////            //"past", "nominative", "accusative", "jussive", "emphasize"
            passiveVerb.appendChild(this.bindVerbConjsAug2("past", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedPassivePastConjugator.getInstance().createVerbList(augmentedRoot, formulaNo), SystemConstants.PAST_TENSE, false, null).getFinalResult()));
            passiveVerb.appendChild(this.bindVerbConjsAug2("nominativePresent", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedPassivePresentConjugator.getInstance().getNominativeConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, false, null).getFinalResult()));
            passiveVerb.appendChild(this.bindVerbConjsAug2("accusativePresent", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedPassivePresentConjugator.getInstance().getAccusativeConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, false, null).getFinalResult()));
            passiveVerb.appendChild(this.bindVerbConjsAug2("jussitivePresent", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedPassivePresentConjugator.getInstance().getJussiveConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, false, null).getFinalResult()));
            passiveVerb.appendChild(this.bindVerbConjsAug2("emphasize", AugmentedTrilateralModifier.getInstance().build(augmentedRoot, kov, formulaNo, AugmentedPassivePresentConjugator.getInstance().getEmphasizedConjugator().createVerbList(augmentedRoot, formulaNo), SystemConstants.PRESENT_TENSE, false, null).getFinalResult()));
            verbConjugation.appendChild(passiveVerb);
////
            conjugation.appendChild(verbConjugation);
//
//            //derviations
            Element nounDerviation = doc.createElement("nounDerviation");
            nounDerviation.appendChild(this.bindDerviationAug("subject", augmentedRoot, formulaNo, kov));
            nounDerviation.appendChild(this.bindDerviationAug("object", augmentedRoot, formulaNo, kov));
            nounDerviation.appendChild(this.bindDerviationAug("timePlace", augmentedRoot, formulaNo, kov));
            conjugation.appendChild(nounDerviation);
//
//            //gerunds
            Element gerund = doc.createElement("gerund");
            gerund.appendChild(this.bindOrgAugGerund(Constants.ORIGINAL, augmentedRoot, formulaNo, kov));
            gerund.appendChild(this.bindOrgAugGerund(Constants.NOMEN, augmentedRoot, formulaNo, kov));
            gerund.appendChild(this.bindDerviationAug("meem", augmentedRoot, formulaNo, kov));
//
            conjugation.appendChild(gerund);
            //--------------------
            augmented.appendChild(conjugation);
        }
//    }
        return augmented;
    }

    Element createBody(String rootText) throws ParserConfigurationException, ClassNotFoundException {
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
        root.appendChild(bindTriAug(rootText));
        //------------------

//----------------------------------  
        roots.appendChild(root);
        return roots;
    }

    Element createRoots(String rootText) throws ParserConfigurationException, ClassNotFoundException {
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
        root.appendChild(bindTriAug(rootText));
        //------------------

//----------------------------------  
        roots.appendChild(root);
        return roots;
    }

    Element createRoot(String rootText) throws ParserConfigurationException, ClassNotFoundException {
//        Element roots = doc.createElement("roots");
        countRoot++;
//        pl(countRoot);
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
        root.appendChild(bindTriAug(rootText));
        //------------------

//----------------------------------  
//        roots.appendChild(root);
        return root;
    }

    List<String> importRoots() throws SAXException, IOException {
        List<String> rawroots = new ArrayList();
        Document docimport = db.parse(new File("./ALshmowkh_db/TrilateralRoots.xml"));
        NodeList rootsNodes = docimport.getElementsByTagName("root");
        for (int i = 0; i < rootsNodes.getLength(); i++) {
            String rot = rootsNodes.item(i).getAttributes().getNamedItem("value").getNodeValue();
            rawroots.add(rot);
        }
        return rawroots;
    }

    public Boolean trisProcess(List<String> rawroots) throws TransformerException, ClassNotFoundException, ParserConfigurationException {

        Element roots = doc.createElement("roots");

        for (int i = 1; i < rawroots.size() - 1; i = i + 1) {
            String rootIn = rawroots.get(i);
//        String rootIn = "ثقف";
            pl(countRoot + ": " + rootIn);
            List alefAlternatives = Validator.getInstance().getTrilateralAlefAlternatives(rootIn);
            if (alefAlternatives.isEmpty()) {
                //لا يوجد احتمالات للألف
                AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootIn);
                List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootIn);

                if (augmentedRoot == null && unaugmentedList.isEmpty()) {
                    System.out.println("لم يرد هذا الجذر في قاعدة المعطيات      ");
                } else {
                    roots.appendChild(createRoot(rootIn));
                }
            } else {
                //تجريب بدائل الألف
                List rootTextList = new LinkedList();
                Iterator iter = alefAlternatives.iterator();
                while (iter.hasNext()) {
                    String alterativeRoot = (String) iter.next();
                    AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(alterativeRoot);
                    List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(alterativeRoot);
                    if (augmentedRoot != null || !unaugmentedList.isEmpty()) {
                        rootTextList.add(alterativeRoot);
                    }
                }
                //فحص اذا نجح احتمال أو أكثر
                if (rootTextList.isEmpty()) {
                    //لم ينجح أي بديل
                    System.out.println("لم يرد هذا الجذر في قاعدة المعطيات      ");
                } else {
                    iter = rootTextList.iterator();
                    while (iter.hasNext()) {
                        roots.appendChild(createRoot(iter.next().toString()));
                    }
                }
            }
        }
        doc.appendChild(roots);
//         doc.appendChild((Node) rootNode.cloneNode(true));
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
        return true;
    }

    Boolean triProcess(String rootIn) throws TransformerException, ClassNotFoundException, ParserConfigurationException {
        Element roots = doc.createElement("roots");

        List alefAlternatives = Validator.getInstance().getTrilateralAlefAlternatives(rootIn);
        if (alefAlternatives.isEmpty()) {
            //لا يوجد احتمالات للألف
            AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(rootIn);
            List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootIn);

            if (augmentedRoot == null && unaugmentedList.isEmpty()) {
                System.out.println("لم يرد هذا الجذر في قاعدة المعطيات      ");
            } else {
                roots.appendChild(createRoot(rootIn));
            }
        } else {
            //تجريب بدائل الألف
            List rootTextList = new LinkedList();
            Iterator iter = alefAlternatives.iterator();
            while (iter.hasNext()) {
                String alterativeRoot = (String) iter.next();
                AugmentedTrilateralRoot augmentedRoot = SarfDictionary.getInstance().getAugmentedTrilateralRoot(alterativeRoot);
                List unaugmentedList = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(alterativeRoot);
                if (augmentedRoot != null || !unaugmentedList.isEmpty()) {
                    rootTextList.add(alterativeRoot);
                }
            }
            //فحص اذا نجح احتمال أو أكثر
            if (rootTextList.isEmpty()) {
                //لم ينجح أي بديل
                System.out.println("لم يرد هذا الجذر في قاعدة المعطيات      ");
            } else {
                iter = rootTextList.iterator();
                while (iter.hasNext()) {
                    roots.appendChild(createRoot(iter.next().toString()));
                }
            }
        }

        doc.appendChild(roots);
//         doc.appendChild((Node) rootNode.cloneNode(true));
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
        return true;
    }

    void initi() throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException {
        XMLExporter exporter = new XMLExporter();
        List<String> rawroots = exporter.importRoots();
//        trisProcess(rawroots);
        triProcess("هدف");
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException, ClassNotFoundException {
        new XMLExporter().initi();
    }

    static void pl(Object o) {
        System.out.println(o);
    }
}
