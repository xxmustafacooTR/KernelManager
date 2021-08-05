package com.thunder.thundertweaks.utils.kernel.game;

import android.content.Context;

import com.thunder.thundertweaks.fragments.ApplyOnBootFragment;
import com.thunder.thundertweaks.utils.Utils;
import com.thunder.thundertweaks.utils.root.Control;

public class GameControl {

    private static GameControl sIOInstance;

    public static GameControl getInstance() {
        if (sIOInstance == null) {
            sIOInstance = new GameControl();
        }
        return sIOInstance;
    }

    private static final String GAMING_CONTROL = "/sys/kernel/gaming_control";
    private static final String VERSION = GAMING_CONTROL + "/version";
    private static final String GAME_PACKAGES = GAMING_CONTROL + "/game_packages";
    private static final String ALWAYS_ON = GAMING_CONTROL + "/always_on";
    private static final String MIF_MIN = GAMING_CONTROL + "/min_mif";
    private static final String LITTLE_MIN = GAMING_CONTROL + "/little_freq_min";
    private static final String LITTLE_MAX = GAMING_CONTROL + "/little_freq_max";
    private static final String MIDDLE_MIN = GAMING_CONTROL + "/middle_freq_min";
    private static final String MIDDLE_MAX = GAMING_CONTROL + "/middle_freq_max";
    private static final String BIG_MIN = GAMING_CONTROL + "/big_freq_min";
    private static final String BIG_MAX = GAMING_CONTROL + "/big_freq_max";
    private static final String GPU_MIN = GAMING_CONTROL + "/gpu_freq_min";
    private static final String GPU_MAX = GAMING_CONTROL + "/gpu_freq_max";

    public static boolean hasGameControl() {
        return Utils.existFile(GAMING_CONTROL);
    }

    public static boolean hasVersion(){
        return Utils.existFile(VERSION);
    }

    public static String getVersion() {
        return Utils.readFile(VERSION);
    }

    public static boolean hasAlwaysOn(){
        return Utils.existFile(ALWAYS_ON);
    }

    public static Boolean isEnabledAlwaysOn(){
        return Utils.readFile(ALWAYS_ON).equals("1");
    }

    public static void enableAlwaysOn(Boolean enable, Context context){
        run(Control.write(enable ? "1" : "0", ALWAYS_ON), ALWAYS_ON, context);
    }

    public static boolean hasMIFMin(){
        return Utils.existFile(MIF_MIN);
    }

    public void setMIFMin(int value, Context context) {
        run(Control.write(String.valueOf(value), MIF_MIN),
                MIF_MIN, context);
    }

    public static int getMIFMin() {
        return Utils.strToInt(Utils.readFile(MIF_MIN));
    }

    public static boolean hasLITTLEMin(){
        return Utils.existFile(LITTLE_MIN);
    }

    public void setLITTLEMin(int value, Context context) {
        run(Control.write(String.valueOf(value), LITTLE_MIN),
                LITTLE_MIN, context);
    }

    public static int getLITTLEMin() {
        return Utils.strToInt(Utils.readFile(LITTLE_MIN));
    }

    public static boolean hasLITTLEMax(){
        return Utils.existFile(LITTLE_MAX);
    }

    public void setLITTLEMax(int value, Context context) {
        run(Control.write(String.valueOf(value), LITTLE_MAX),
                LITTLE_MAX, context);
    }

    public static int getLITTLEMax() {
        return Utils.strToInt(Utils.readFile(LITTLE_MAX));
    }

    public static boolean hasMIDDLEMin(){
        return Utils.existFile(MIDDLE_MIN);
    }

    public void setMIDDLEMin(int value, Context context) {
        run(Control.write(String.valueOf(value), MIDDLE_MIN),
                MIDDLE_MIN, context);
    }

    public static int getMIDDLEMin() {
        return Utils.strToInt(Utils.readFile(MIDDLE_MIN));
    }

    public static boolean hasMIDDLEMax(){
        return Utils.existFile(MIDDLE_MAX);
    }

    public void setMIDDLEMax(int value, Context context) {
        run(Control.write(String.valueOf(value), MIDDLE_MAX),
                MIDDLE_MAX, context);
    }

    public static int getMIDDLEMax() {
        return Utils.strToInt(Utils.readFile(MIDDLE_MAX));
    }

    public static boolean hasBIGMin(){
        return Utils.existFile(BIG_MIN);
    }

    public void setBIGMin(int value, Context context) {
        run(Control.write(String.valueOf(value), BIG_MIN),
                BIG_MIN, context);
    }

    public static int getBIGMin() {
        return Utils.strToInt(Utils.readFile(BIG_MIN));
    }

    public static boolean hasBIGMax(){
        return Utils.existFile(BIG_MAX);
    }

    public void setBIGMax(int value, Context context) {
        run(Control.write(String.valueOf(value), BIG_MAX),
                BIG_MAX, context);
    }

    public static int getBIGMax() {
        return Utils.strToInt(Utils.readFile(BIG_MAX));
    }

    public static boolean hasGPUMin(){
        return Utils.existFile(GPU_MIN);
    }

    public void setGPUMin(int value, Context context) {
        run(Control.write(String.valueOf(value), GPU_MIN),
                GPU_MIN, context);
    }

    public static int getGPUMin() {
        return Utils.strToInt(Utils.readFile(GPU_MIN));
    }

    public static boolean hasGPUMax(){
        return Utils.existFile(GPU_MAX);
    }

    public void setGPUMax(int value, Context context) {
        run(Control.write(String.valueOf(value), GPU_MAX),
                GPU_MAX, context);
    }

    public static int getGPUMax() {
        return Utils.strToInt(Utils.readFile(GPU_MAX));
    }

    public static boolean hasGamePackages(){
        return Utils.existFile(GAME_PACKAGES);
    }

    public void setGamePackages(String value, Context context) {
        run(Control.write(value, GAME_PACKAGES), GAME_PACKAGES, context);
    }

    public static String getGamePackages() {
        return Utils.readFile(GAME_PACKAGES);
    }

    public static boolean supported() {
        return hasGameControl();
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.GAME, id, context);
    }
}
