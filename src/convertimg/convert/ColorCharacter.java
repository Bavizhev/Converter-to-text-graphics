package convertimg.convert;

import convertimg.convertInter.TextColorSchema;

public class ColorCharacter implements TextColorSchema {
    final int INTENSITY_VALUES = 256;
    char[] symbols = {'@', '$', '%', '#', '*', '+', '-', '.'};


    @Override
    public char convert(int color) {
        return symbols[(int) ((float) color / (float) (INTENSITY_VALUES / symbols.length))];
    }
}
