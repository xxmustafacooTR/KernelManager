package com.thunder.thundertweaks.utils.kernel.power;

import android.content.Context;

import com.thunder.thundertweaks.fragments.ApplyOnBootFragment;
import com.thunder.thundertweaks.utils.Log;
import com.thunder.thundertweaks.utils.Utils;
import com.thunder.thundertweaks.utils.root.Control;
import com.thunder.thundertweaks.utils.root.RootFile;

import java.util.ArrayList;
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

    private static final String REGULATOR_MAX_DIR = "/max_microvolts";
    private static final String REGULATOR_MIN_DIR = "/min_microvolts";
    private static final String REGULATOR_CUR_DIR = "/microvolts";

    private static final String S9_SPEEDY = "/sys/devices/platform/141c0000.speedy/i2c-17/17-0000";
    private static final String S9_REGULATOR_NAME_LOCATION = S9_SPEEDY + "/name";
    private static final String S9_REGULATOR = S9_SPEEDY + "/s2mps18-regulator/regulator";
    private static final String S9_REGULATOR_NAME = "s2mps18";
    private static final String S9_LITTLE_NAME = "vdd_cpucl0";
    private static final String S9_BIG_NAME = "vdd_cpucl1";
    private static final String S9_GPU_NAME = "vdd_g3d";
    private static final String S9_MIF_NAME = "vdd_mif";

    private final List<String> mRegulators = new ArrayList<>();
    private final List<String> mRegulatorNames = new ArrayList<>();

    {
        mRegulators.add(S9_REGULATOR);

        mRegulatorNames.add(S9_REGULATOR_NAME_LOCATION);
    }

    private String REGULATOR;
    private String REGULATOR_NAME;

    private String LITTLE;
    private String BIG;
    private String GPU;
    private String MIF;

    private Regulator() {
        for (String file : mRegulators) {
            if (Utils.existFile(file)) {
                REGULATOR = file;
                break;
            }
        }

        for (String file : mRegulatorNames) {
            if (Utils.existFile(file)) {
                REGULATOR_NAME = file;
                break;
            }
        }

        if(hasDriverVersion()){
            List<String> REGULATORS_LIST;

            if(getDriverVersion().contains(S9_REGULATOR_NAME)){
                REGULATORS_LIST = new RootFile(S9_REGULATOR).list();

                LITTLE = searchBuck(S9_REGULATOR, REGULATORS_LIST, S9_LITTLE_NAME);
                BIG = searchBuck(S9_REGULATOR, REGULATORS_LIST, S9_BIG_NAME);
                GPU = searchBuck(S9_REGULATOR, REGULATORS_LIST, S9_GPU_NAME);
                MIF = searchBuck(S9_REGULATOR, REGULATORS_LIST, S9_MIF_NAME);
            }
        }
    }

    //TODO index all files to an object then search in it
    private static String searchBuck(String dir, List<String> fileList, String name){
        if(dir != null && fileList != null && name != null) {
            for (String file : fileList) {
                file = dir + "/" + file;
                if (Utils.existFile(file + "/name")) {
                    if(Utils.readFile(file + "/name").equals(name)){
                        return file;
                    }
                }
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
