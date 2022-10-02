/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XMLExport;

import java.util.ArrayList;

public class Util {

    public ArrayList<Character> diacriticList() {
        ArrayList<Character> marks = new ArrayList();
        marks.add('َ');//فتحة
        marks.add('ِ');//كسرة
        marks.add('ُ');//ضمة
        marks.add('ْ');//سكون
        marks.add('ّ');//شدة
        marks.add('ٌ');//تنوين مضموم
        marks.add('ً');//تنوين مفتوح
        marks.add('ٍ');//تنوين مكسور

        return marks;
    }

    public String deDiacritic(String diac) {
        char[] chars = diac.toCharArray();
        String res = "";
        for (Character ch : chars) {
            if (!diacriticList().contains(ch)) {
                res = res + ch;
            }
        }
        return res;
    }
}
