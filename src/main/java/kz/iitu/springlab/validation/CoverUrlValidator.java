package kz.iitu.springlab.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class CoverUrlValidator
        implements ConstraintValidator<ValidCoverUrl, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            URI uri = new URI(value);

            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();

            if (scheme == null
                    || host == null
                    || host.isBlank()
                    || path == null) {
                return false;
            }

            if (!scheme.equalsIgnoreCase("http")
                    && !scheme.equalsIgnoreCase("https")) {
                return false;
            }

            String lowerPath = path.toLowerCase(Locale.ROOT);

            return lowerPath.endsWith(".jpg")
                    || lowerPath.endsWith(".jpeg")
                    || lowerPath.endsWith(".png");

        } catch (URISyntaxException ex) {
            return false;
        }
    }
}