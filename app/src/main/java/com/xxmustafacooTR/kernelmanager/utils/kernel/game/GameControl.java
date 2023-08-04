package com.xxmustafacooTR.kernelmanager.utils.kernel.game;

import android.content.Context;

import com.xxmustafacooTR.kernelmanager.fragments.ApplyOnBootFragment;
import com.xxmustafacooTR.kernelmanager.utils.Utils;
import com.xxmustafacooTR.kernelmanager.utils.root.Control;

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
    private static final String THERMAL_BYPASS = GAMING_CONTROL + "/thermal_bypass";
    private static final String BATTERY_IDLE = GAMING_CONTROL + "/battery_idle";
    private static final String INT_MIN = GAMING_CONTROL + "/min_int_freq";
    private static final String MIF_MIN = GAMING_CONTROL + "/min_mif_freq";
    private static final String LITTLE_MIN = GAMING_CONTROL + "/min_little_freq";
    private static final String LITTLE_MAX = GAMING_CONTROL + "/max_little_freq";
    private static final String MIDDLE_MIN = GAMING_CONTROL + "/min_middle_freq";
    private static final String MIDDLE_MAX = GAMING_CONTROL + "/max_middle_freq";
    private static final String BIG_MIN = GAMING_CONTROL + "/min_big_freq";
    private static final String BIG_MAX = GAMING_CONTROL + "/max_big_freq";
    private static final String GPU_MIN = GAMING_CONTROL + "/min_gpu_freq";
    private static final String GPU_MAX = GAMING_CONTROL + "/max_gpu_freq";
    private static final String LITTLE_FREQ = GAMING_CONTROL + "/custom_little_freq";
    private static final String LITTLE_VOLT = GAMING_CONTROL + "/custom_little_voltage";
    private static final String BIG_FREQ = GAMING_CONTROL + "/custom_big_freq";
    private static final String BIG_VOLT = GAMING_CONTROL + "/custom_big_voltage";
    private static final String GPU_FREQ = GAMING_CONTROL + "/custom_gpu_freq";
    private static final String GPU_VOLT = GAMING_CONTROL + "/custom_gpu_voltage";

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

    public static boolean hasThermalBypass(){
        return Utils.existFile(THERMAL_BYPASS);
    }

    public static Boolean isEnabledThermalBypass(){
        return Utils.readFile(THERMAL_BYPASS).equals("1");
    }

    public static void enableThermalBypass(Boolean enable, Context context){
        run(Control.write(enable ? "1" : "0", THERMAL_BYPASS), THERMAL_BYPASS, context);
    }

    public static boolean hasThermalInit() {
        return hasThermalBypass();
    }

    public static boolean hasBatteryIdle(){
        return Utils.existFile(BATTERY_IDLE);
    }

    public static Boolean isEnabledBatteryIdle(){
        return Utils.readFile(BATTERY_IDLE).equals("1");
    }

    public static void enableBatteryIdle(Boolean enable, Context context){
        run(Control.write(enable ? "1" : "0", BATTERY_IDLE), BATTERY_IDLE, context);
    }

    public static boolean hasMIFMin(){
        return Utils.existFile(MIF_MIN);
    }

    public void setMIFMin(int value, Context context) {
        run(Control.write(String.valueOf(value), MIF_MIN),
                MIF_MIN, context);
    }

    public static int getINTMin() {
        return Utils.strToInt(Utils.readFile(INT_MIN));
    }

    public static boolean hasINTMin(){
        return Utils.existFile(INT_MIN);
    }

    public void setINTMin(int value, Context context) {
        run(Control.write(String.valueOf(value), INT_MIN),
                INT_MIN, context);
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

    public static boolean hasLittleFreq(){
        return Utils.existFile(LITTLE_FREQ);
    }

    public void setLittleFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), LITTLE_FREQ),
                LITTLE_FREQ, context);
    }

    public static int getLittleFreq() {
        return Utils.strToInt(Utils.readFile(LITTLE_FREQ));
    }

    public static boolean hasLittleVolt(){
        return Utils.existFile(LITTLE_VOLT);
    }

    public void setLittleVolt(int value, Context context) {
        run(Control.write(String.valueOf(value), LITTLE_VOLT),
                LITTLE_VOLT, context);
    }

    public static int getLittleVolt() {
        return Utils.strToInt(Utils.readFile(LITTLE_VOLT));
    }

    public static boolean hasBigFreq(){
        return Utils.existFile(BIG_FREQ);
    }

    public void setBigFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), BIG_FREQ),
                BIG_FREQ, context);
    }

    public static int getBigFreq() {
        return Utils.strToInt(Utils.readFile(BIG_FREQ));
    }

    public static boolean hasBigVolt(){
        return Utils.existFile(BIG_VOLT);
    }

    public void setBigVolt(int value, Context context) {
        run(Control.write(String.valueOf(value), BIG_VOLT),
                BIG_VOLT, context);
    }

    public static int getBigVolt() {
        return Utils.strToInt(Utils.readFile(BIG_VOLT));
    }

    public static boolean hasGpuFreq(){
        return Utils.existFile(GPU_FREQ);
    }

    public void setGpuFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), GPU_FREQ),
                GPU_FREQ, context);
    }

    public static int getGpuFreq() {
        return Utils.strToInt(Utils.readFile(GPU_FREQ));
    }

    public static boolean hasGpuVolt(){
        return Utils.existFile(GPU_VOLT);
    }

    public void setGpuVolt(int value, Context context) {
        run(Control.write(String.valueOf(value), GPU_VOLT),
                GPU_VOLT, context);
    }

    public static int getGpuVolt() {
        return Utils.strToInt(Utils.readFile(GPU_VOLT));
    }

    public static boolean hasGamePackages(){
        return Utils.existFile(GAME_PACKAGES);
    }

    public void setGamePackages(String value, Context context) {
        run(Control.write(value.trim(), GAME_PACKAGES), GAME_PACKAGES, context);
    }

    public static String getGamePackages() {
        return Utils.readFile(GAME_PACKAGES);
    }

    public static Boolean checkGamePackage(String gamePackage) {
        return Utils.readFile(GAME_PACKAGES).contains(gamePackage);
    }

    public void addGamePackage(String value, Context context) {
        String readed = Utils.readFile(GAME_PACKAGES);

        if (!readed.contains(value))
            readed += "\n" + value;

        setGamePackages(readed, context);
    }

    public void removeGamePackage(String value, Context context) {
        String packages[] = Utils.readFile(GAME_PACKAGES).split("\n");
        String readed = "";

        for (String onepackage : packages) {
            if(!onepackage.equals(value))
                readed += "\n" + onepackage;
        }

        setGamePackages(readed, context);
    }

    public void editGamePackage(Boolean add, String value, Context context) {
        if (add)
            addGamePackage(value, context);
        else
            removeGamePackage(value, context);
    }

    public static boolean supported() {
        return hasGameControl();
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.GAME, id, context);
    }
}
