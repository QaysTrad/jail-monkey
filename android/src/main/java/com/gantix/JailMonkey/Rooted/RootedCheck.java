package com.gantix.JailMonkey.Rooted;

import android.content.Context;
import com.scottyab.rootbeer.RootBeer;
import java.util.HashMap;
import java.util.Map;
import com.guardsquare.dexguard.runtime.detection.*;

public class RootedCheck {
    private static boolean checkWithJailMonkeyMethod() {
        CheckApiVersion check;

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            check = new GreaterThan23();
        } else {
            check = new LessThan23();
        }
        return check.checkRooted();
    }

    private final boolean jailMonkeyResult;
    private final RootBeerResults rootBeerResults;

    public RootedCheck(Context context) {
        jailMonkeyResult = checkWithJailMonkeyMethod();
        rootBeerResults = new RootBeerResults(context);
    }

    public boolean isJailBroken() {
        return jailMonkeyResult || rootBeerResults.isJailBroken();
    }

    public Map<String, Object> getResultByDetectionMethod() {
        final Map<String, Object> map = new HashMap<>();

        map.put("jailMonkey", jailMonkeyResult);
        map.put("rootBeer", rootBeerResults.toNativeMap());

        return map;
    }

    private static class RootBeerResults {
        private final boolean detectRootManagementApps;
        private final boolean detectPotentiallyDangerousApps;
        private final boolean checkForSuBinary;
        private final boolean checkForDangerousProps;
        private final boolean checkForRWPaths;
        private final boolean detectTestKeys;
        private final boolean checkSuExists;
        private final boolean checkForRootNative;
        private final boolean checkForMagiskBinary;
        private final boolean checkIsRooted;
        private final boolean checkEmulator;

        RootBeerResults(Context context) {
            final RootBeer rootBeer = new RootBeer(context);
            int checkEmulatorDevice = EmulatorDetector.isRunningInEmulator(context);
            rootBeer.setLogging(false);

            detectRootManagementApps = rootBeer.detectRootManagementApps();
            detectPotentiallyDangerousApps = rootBeer.detectPotentiallyDangerousApps();
            checkForSuBinary = rootBeer.checkForSuBinary();
            checkForDangerousProps = rootBeer.checkForDangerousProps();
            checkForRWPaths = rootBeer.checkForRWPaths();
            detectTestKeys = rootBeer.detectTestKeys();
            checkSuExists = rootBeer.checkSuExists();
            checkForRootNative = rootBeer.checkForRootNative();
            checkForMagiskBinary = rootBeer.checkForMagiskBinary();
            checkIsRooted = rootBeer.isRooted();
            checkEmulator = checkEmulatorDevice != 0;
        }

        public boolean isJailBroken() {
            return detectRootManagementApps || detectPotentiallyDangerousApps || checkForSuBinary
                    || checkForDangerousProps || checkForRWPaths
                    || detectTestKeys || checkSuExists || checkForRootNative || checkForMagiskBinary
                    || checkIsRooted || checkEmulator;
        }

        public Map<String, Object> toNativeMap() {
            final Map<String, Object> map = new HashMap<>();

            map.put("detectRootManagementApps", detectRootManagementApps);
            map.put("detectPotentiallyDangerousApps", detectPotentiallyDangerousApps);
            map.put("checkForSuBinary", checkForSuBinary);
            map.put("checkForDangerousProps", checkForDangerousProps);
            map.put("checkForRWPaths", checkForRWPaths);
            map.put("detectTestKeys", detectTestKeys);
            map.put("checkSuExists", checkSuExists);
            map.put("checkForRootNative", checkForRootNative);
            map.put("checkForMagiskBinary", checkForMagiskBinary);
            map.put("checkIsRooted", checkIsRooted);
            map.put("checkEmulator", checkEmulator);

            return map;
        }
    }
}
