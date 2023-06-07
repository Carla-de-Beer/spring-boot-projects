package dev.cadebe.persons_api

import dev.cadebe.persons_api.util.ColorMap
import spock.lang.Specification
import spock.lang.Subject

class ColorMapTest extends Specification {

    @Subject
    def colorMap = new ColorMap()

    def "ColorMap getOrdinalFromString()"() {
        when: "calling getOrdinalFromString()"
        int result = ColorMap.getOrdinalFromString(colorName)

        then: "getOrdinalFromString() has been successfully called"
        result == expected

        where:
        colorName         | expected
        "Blue"            | 0
        "Green"           | 1
        "Purple"          | 2
        "Red"             | 3
        "Lemon yellow"    | 4
        "Turquoise"       | 5
        "White"           | 6
        "WHITE"           | 6
        "wHITe"           | 6
        "Some string ..." | -1
    }

    def "ColorMap getStringFromOrdinal()"() {
        when: "calling getStringFromOrdinal()"
        String result = ColorMap.getStringFromOrdinal(colorCode)

        then: "getStringFromOrdinal() has been successfully called"
        result == expected

        where:
        colorCode | expected
        0         | "Blue"
        1         | "Green"
        2         | "Purple"
        3         | "Red"
        4         | "Lemon yellow"
        5         | "Turquoise"
        6         | "White"
        -1        | null
        -1        | null
    }
}
