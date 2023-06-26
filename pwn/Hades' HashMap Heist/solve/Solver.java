import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Solver {

    public static String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(StandardCharsets.US_ASCII)));
    }

    public static void main(String[] args) {
        // The artifact whose hashCode we want to mimic
        Artifact referenceArtifact = new Artifact("Apollo's Lyre", "Enchants with divine melodies");

        // Artifact containing the name needed, but no power description (because empty string hashes to 0)
        Artifact artifact = new Artifact("Artemis' Silver Bow", "");

        // The intended hash for the power description string
        int hash = referenceArtifact.hashCode() - artifact.hashCode();

        // Generate a string with the given hashCode
        String s = getStringForHash(hash);

        // Verify hashCodes match
        if (s.hashCode() != hash) {
            throw new IllegalStateException("hashCodes don't match 1: " + s.hashCode() + " ; " + hash);
        }

        // Generate new artifact with right hashCode
        Artifact newArtifact = new Artifact("Artemis' Silver Bow", s);

        // Verify hashCodes match
        if (referenceArtifact.hashCode() != newArtifact.hashCode()) {
            throw new IllegalStateException("hashCodes don't match 2: " + referenceArtifact.hashCode() + " ; " + newArtifact.hashCode());
        }

        // Print result
        System.out.println("String: `" + s + "` (hex " + toHex(s) + "); hash: " + hash + " | " + s.hashCode());
    }

    /**
     * Create an ASCII string with a given {@link String#hashCode()}.
     *
     * @param hash the hashcode.
     * @return the string created.
     */
    public static String getStringForHash(int hash) {
        // Convert to unsigned base 31 string
        long hashL = Integer.toUnsignedLong(hash);
        String b31 = Long.toString(hashL, 31);

        char[] outChars = new char[7];
        char[] inChars = b31.toCharArray();

        // Convert each base 31 digit back to an integer, then to a char and store in array
        for (int i = 0; i < 7; i++) {
            char inChar = i < inChars.length ? inChars[i] : '0';
            outChars[i] = (char) Integer.parseInt(Character.toString(inChar), 31);
        }

        // Create string from char array
        return new String(outChars);
    }

}
