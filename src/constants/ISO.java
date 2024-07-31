package constants;

import java.util.Locale;
import java.util.Set;

public class ISO {
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

    public static Set<String> getIsoCountries(){
        return ISO_COUNTRIES;
    }
}
