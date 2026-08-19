package util;

import manager.RequestManager;
import java.util.Random;

/**
 * Generates random usernames using famous people from around the world.
 */
public class RandomDataGenerator {
    private static final Random random = new Random();

    // Famous people from around the world (first + last names combined)
    private static final String[] FAMOUS_NAMES = {
            "AlbertEinstein", "IsaacNewton", "MarieCurie", "LeonardoDaVinci",
            "WilliamShakespeare", "CharlesDarwin", "NikolaTesla", "ThomasEdison",
            "GalileoGalilei", "AdaLovelace", "AlanTuring", "BenjaminFranklin",
            "AlexanderGrahamBell", "WrightBrothers", "ChristopherColumbus",
            "VascoDaGama", "FerdinandMagellan", "MarcoPolo", "AmeliaEarhart",
            "NeilArmstrong", "BuzzAldrin", "YuriGagarin", "SallyRide",
            "MichaelJackson", "ElvisPresley", "FreddieMercury", "Madonna",
            "Beyonce", "TaylorSwift", "EdSheeran", "Adele", "Shakira",
            "Messi", "Ronaldo", "Neymar", "KobeBryant", "MichaelJordan",
            "UsainBolt", "MichaelPhelps", "SimoneBiles", "TigerWoods",
            "BarackObama", "DonaldTrump", "JoeBiden", "NelsonMandela",
            "WinstonChurchill", "MartinLutherKing", "MahatmaGandhi",
            "DalaiLama", "MotherTeresa", "OprahWinfrey", "ElonMusk",
            "JeffBezos", "BillGates", "MarkZuckerberg", "SteveJobs",
            "TimCook", "SundarPichai", "SatyaNadella", "MukeshAmbani",
            "RatanTata", "ShahRukhKhan", "AishwaryaRai", "PriyankaChopra",
            "ViratKohli", "SachinTendulkar", "MSDhoni", "RohitSharma",
            "CristianoRonaldo", "ZinedineZidane", "Pelé", "DiegoMaradona"
    };

    public static String generateRandomUsername() {
        return FAMOUS_NAMES[random.nextInt(FAMOUS_NAMES.length)] +
                (random.nextInt(100) + 1);
    }

    public static String generateRandomIp() {
        return IpUtil.generateRandomIp();
    }

    public static String generateRandomTimestamp() {
        return TimeUtil.getRandomPastTimestamp();
    }

    public static void generateRandomRequests(int count, RequestManager requestManager) {
        for (int i = 0; i < count; i++) {
            String username = generateRandomUsername();
            String ip = generateRandomIp();
            String time = generateRandomTimestamp();
            requestManager.addRequest(username, ip, time);
        }
    }
}