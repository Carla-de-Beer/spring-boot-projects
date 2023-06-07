package dev.cadebe.persons_api.util;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ColorMap {

    private static Map<Integer, String> mappedValues;

    static {
        ColorMap.mappedValues = new HashMap<>();
        ColorMap.mappedValues.put(0, "Blue");
        ColorMap.mappedValues.put(1, "Green");
        ColorMap.mappedValues.put(2, "Purple");
        ColorMap.mappedValues.put(3, "Red");
        ColorMap.mappedValues.put(4, "Lemon yellow");
        ColorMap.mappedValues.put(5, "Turquoise");
        ColorMap.mappedValues.put(6, "White");
    }

    public static int getOrdinalFromString(String inputColor) {
        String colorName = inputColor.substring(0, 1).toUpperCase() + inputColor.substring(1).toLowerCase();
        for (Map.Entry<Integer, String> integerStringEntry : ColorMap.mappedValues.entrySet()) {
            String colorCode = ((String) ((Map.Entry) integerStringEntry).getValue());
            if (colorCode.equals((colorName))) {
                return (int) ((Map.Entry) integerStringEntry).getKey();
            }
        }
        return -1;
    }

    public static String getStringFromOrdinal(int i) {
        return ColorMap.mappedValues.get(i);
    }
}
