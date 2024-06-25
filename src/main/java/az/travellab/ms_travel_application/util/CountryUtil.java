package az.travellab.ms_travel_application.util;

public enum CountryUtil {
    COUNTRY_UTIL;

    public boolean findCountry(String message, String country) {
        message = message.toLowerCase();
        country = country.toLowerCase();

        if (message.contains(country))
            return true;

        var words = message.split("\\s+");
        for (String word : words) {
            if (calculateLevenshteinDistance(word, country) < 2) {
                return true;
            }
        }
        return false;
    }

    private int calculateLevenshteinDistance(String word1, String word2) {
        var length = word1.length();
        var length2 = word2.length();

        var dp = new int[length + 1][length2 + 1];

        for (var i = 0; i <= length; i++) {
            dp[i][0] = i;
        }
        for (var j = 0; j <= length2; j++) {
            dp[0][j] = j;
        }

        for (var i = 1; i <= length; i++) {
            var c1 = word1.charAt(i - 1);
            for (var j = 1; j <= length2; j++) {
                var c2 = word2.charAt(j - 1);
                var cost = c1 == c2 ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[length][length2];
    }
}