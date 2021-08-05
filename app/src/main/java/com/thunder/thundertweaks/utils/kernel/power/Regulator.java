package com.thunder.thundertweaks.utils.kernel.power;

import android.content.Context;
import android.util.Log;

import com.thunder.thundertweaks.fragments.ApplyOnBootFragment;
import com.thunder.thundertweaks.utils.Utils;
import com.thunder.thundertweaks.utils.root.Control;
import com.thunder.thundertweaks.utils.root.RootFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.thunder.thundertweaks.utils.Utils.strToInt;

public class Regulator {
    private static Regulator sIOInstance;

    public static Regulator getInstance() {
        if (sIOInstance == null) {
            sIOInstance = new Regulator();
        }
        return sIOInstance;
    }

    private static HashMap<String, String> REGULATORS_LIST;
    private static final String REGULATOR_NAME_DIR = "/name";
    private static final String REGULATOR_MAX_DIR = "/max_microvolts";
    private static final String REGULATOR_MIN_DIR = "/min_microvolts";
    private static final String REGULATOR_CUR_DIR = "/microvolts";

    private static final String S9_SPEEDY = "/sys/devices/platform/141c0000.speedy/i2c-15/15-0000";
    private static final String S9PLUS_SPEEDY = "/sys/devices/platform/141c0000.speedy/i2c-17/17-0000";
    private static final String N9_SPEEDY = "/sys/devices/platform/141c0000.speedy/i2c-18/18-0000";
    private static final String S9_REGULATOR = "/s2mps18-regulator/regulator";
    private static final String S9_REGULATOR_NAME = "s2mps18";
    private static final String S9_LITTLE_NAME = "vdd_cpucl0";
    private static final String S9_BIG_NAME = "vdd_cpucl1";
    private static final String S9_GPU_NAME = "vdd_g3d";
    private static final String S9_MIF_NAME = "vdd_mif";

    private final List<String> mSpeedies = new ArrayList<>();
    private final List<String> mRegulators = new ArrayList<>();

    {
        mSpeedies.add(S9_SPEEDY);
        mSpeedies.add(S9PLUS_SPEEDY);
        mSpeedies.add(N9_SPEEDY);

        mRegulators.add(S9_REGULATOR);
    }

    private String SPEEDY;
    private String REGULATOR;
    private String REGULATOR_NAME;

    private String LITTLE;
    private String BIG;
    private String GPU;
    private String MIF;

    private Regulator() {
        for (String file : mSpeedies) {
            if (Utils.existFile(file)) {
                SPEEDY = file;
                REGULATOR_NAME = file + REGULATOR_NAME_DIR;
                for (String reg : mRegulators) {
                    if (Utils.existFile(SPEEDY+reg)) {
                        REGULATOR = SPEEDY+reg;
                        break;
                    }
                }
                break;
            }
        }

        if(hasDriverVersion()){
            if(getDriverVersion().contains(S9_REGULATOR_NAME)){
                REGULATORS_LIST = indexRegulator(REGULATOR);

                LITTLE = searchBuck(S9_LITTLE_NAME);
                BIG = searchBuck(S9_BIG_NAME);
                GPU = searchBuck(S9_GPU_NAME);
                MIF = searchBuck(S9_MIF_NAME);
            }
        }
    }

    private static HashMap<String, String> indexRegulator(String dir){
        HashMap<String, String> allList = new HashMap<String, String>();
        List<String> fileList = new RootFile(dir).list();

        if(dir != null && fileList != null) {
            for (String file : fileList) {
                file = dir + "/" + file;
                if (Utils.existFile(file + "/name")) {

                    allList.put(Utils.readFile(file + "/name"), file);

                }
            }

            return allList;
        }

        return null;
    }

    private static String searchBuck(String name){
        if(REGULATORS_LIST != null && name != null) {
            for (String i : REGULATORS_LIST.keySet()) {
                if(i.equals(name))
                    return REGULATORS_LIST.get(i);
            }
        }

        return null;
    }

    public boolean hasDriverVersion() {
        return REGULATOR_NAME != null;
    }

    public String getDriverVersion() {
        return Utils.readFile(REGULATOR_NAME);
    }

    public boolean hasLITTLEbuck() {
        return LITTLE != null;
    }

    public Integer getLITTLEMax() {
        return strToInt(Utils.readFile(LITTLE + REGULATOR_MAX_DIR));
    }

    public Integer getLITTLEMin() {
        return strToInt(Utils.readFile(LITTLE + REGULATOR_MIN_DIR));
    }

    public Integer getLITTLECur() {
        return strToInt(Utils.readFile(LITTLE + REGULATOR_CUR_DIR));
    }

    public boolean hasBIGbuck() {
        return BIG != null;
    }

    public Integer getBIGMax() {
        return strToInt(Utils.readFile(BIG + REGULATOR_MAX_DIR));
    }

    public Integer getBIGMin() {
        return strToInt(Utils.readFile(BIG + REGULATOR_MIN_DIR));
    }

    public Integer getBIGCur() {
        return strToInt(Utils.readFile(BIG + REGULATOR_CUR_DIR));
    }

    public boolean hasGPUbuck() {
        return GPU != null;
    }

    public Integer getGPUMax() {
        return strToInt(Utils.readFile(GPU + REGULATOR_MAX_DIR));
    }

    public Integer getGPUMin() {
        return strToInt(Utils.readFile(GPU + REGULATOR_MIN_DIR));
    }

    public Integer getGPUCur() {
        return strToInt(Utils.readFile(GPU + REGULATOR_CUR_DIR));
    }

    public boolean hasMIFbuck() {
        return MIF != null;
    }

    public Integer getMIFMax() {
        return strToInt(Utils.readFile(MIF + REGULATOR_MAX_DIR));
    }

    public Integer getMIFMin() {
        return strToInt(Utils.readFile(MIF + REGULATOR_MIN_DIR));
    }

    public Integer getMIFCur() {
        return strToInt(Utils.readFile(MIF + REGULATOR_CUR_DIR));
    }

    public boolean hasRegulator() {
        return REGULATOR != null;
    }

    public boolean supported() {
        return hasRegulator();
    }

    private void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.POWER, id, context);
    }
}
