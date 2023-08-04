package com.xxmustafacooTR.kernelmanager.utils.kernel.cpuhotplug;

import android.content.Context;

import com.xxmustafacooTR.kernelmanager.fragments.ApplyOnBootFragment;
import com.xxmustafacooTR.kernelmanager.utils.Log;
import com.xxmustafacooTR.kernelmanager.utils.Utils;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpu.CPUFreq;
import com.xxmustafacooTR.kernelmanager.utils.root.Control;
import com.xxmustafacooTR.kernelmanager.utils.root.RootUtils;

import java.util.List;

/**
 * Created by Morogoku on 25/04/2017.
 */

public class SamsungPlug {

    private static final String HOTPLUG_SAMSUNG = "/sys/power/cpuhotplug";
	private static final String HOTPLUG_SAMSUNG_ENABLE = "/sys/power/cpuhotplug/enabled";
    private static final String HOTPLUG_SAMSUNG_GOVERNOR = "/sys/power/cpuhotplug/governor";
    private static final String HOTPLUG_SAMSUNG_GOVERNOR_ENABLE = "/sys/power/cpuhotplug/governor/enabled";
    private static final String HOTPLUG_SAMSUNG_USER_MODE = "/sys/power/cpuhotplug/governor/user_mode";
    private static final String HOTPLUG_SAMSUNG_MAX_ONLINE_CPU = "/sys/power/cpuhotplug/max_online_cpu";
    private static final String HOTPLUG_SAMSUNG_MIN_ONLINE_CPU = "/sys/power/cpuhotplug/min_online_cpu";
    private static final String HOTPLUG_SAMSUNG_SINGLE_CHANGE_MS = "/sys/power/cpuhotplug/governor/single_change_ms";
    private static final String HOTPLUG_SAMSUNG_DUAL_CHANGE_MS = "/sys/power/cpuhotplug/governor/dual_change_ms";
    private static final String HOTPLUG_SAMSUNG_TRIPLE_CHANGE_MS = "/sys/power/cpuhotplug/governor/triple_change_ms";
    private static final String HOTPLUG_SAMSUNG_QUAD_CHANGE_MS = "/sys/power/cpuhotplug/governor/quad_change_ms";
    private static final String HOTPLUG_SAMSUNG_CL_BUSY_RATIO = "/sys/power/cpuhotplug/governor/cl_busy_ratio";
    private static final String HOTPLUG_SAMSUNG_LDSUM_ENABLE = "/sys/power/cpuhotplug/governor/ldsum_enabled";
    private static final String HOTPLUG_SAMSUNG_LDSUM_HEAVY_THREAD = "/sys/power/cpuhotplug/governor/ldsum_heavy_thr";
    private static final String HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE = "/sys/power/cpuhotplug/governor/skip_lit_enabled";
    private static final String HOTPLUG_SAMSUNG_BIG_HEAVY_THREAD = "/sys/power/cpuhotplug/governor/big_heavy_thr";
    private static final String HOTPLUG_SAMSUNG_BIG_IDLE_THREAD = "/sys/power/cpuhotplug/governor/big_idle_thr";
    private static final String HOTPLUG_SAMSUNG_LITTLE_HEAVY_THREAD = "/sys/power/cpuhotplug/governor/lit_heavy_thr";
    private static final String HOTPLUG_SAMSUNG_LITTLE_IDLE_THREAD = "/sys/power/cpuhotplug/governor/lit_idle_thr";
    private static final String HOTPLUG_SAMSUNG_SINGLE_FREQ = "/sys/power/cpuhotplug/governor/single_freq";
    private static final String HOTPLUG_SAMSUNG_DUAL_FREQ = "/sys/power/cpuhotplug/governor/dual_freq";
    private static final String HOTPLUG_SAMSUNG_TRIPLE_FREQ = "/sys/power/cpuhotplug/governor/triple_freq";
    private static final String HOTPLUG_SAMSUNG_QUAD_FREQ = "/sys/power/cpuhotplug/governor/quad_freq";
	private static final String HOTPLUG_SAMSUNG_LIT_MULT_RATIO = "/sys/power/cpuhotplug/governor/lit_mult_ratio";
	private static final String HOTPLUG_SAMSUNG_TO_DUAL_RATIO = "/sys/power/cpuhotplug/governor/to_dual_ratio";
	private static final String HOTPLUG_SAMSUNG_TO_QUAD_RATIO = "/sys/power/cpuhotplug/governor/to_quad_ratio";
	private static final String HOTPLUG_SAMSUNG_BIG_MODE_DUAL = "/sys/power/cpuhotplug/governor/big_mode_dual";
	private static final String HOTPLUG_SAMSUNG_BIG_MODE_NORMAL = "/sys/power/cpuhotplug/governor/big_mode_normal";
    private static final String CPU_MAX_FREQ = "/sys/devices/system/cpu/cpu4/cpufreq/scaling_max_freq";

    public static void enableSamsungPlug(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", HOTPLUG_SAMSUNG_ENABLE), HOTPLUG_SAMSUNG_ENABLE, context);
    }

    public static boolean isSamsungPlugEnabled() {
        return Utils.readFile(HOTPLUG_SAMSUNG_ENABLE).equals("1");
    }

    public static boolean hasSamsungPlug() {
        return Utils.existFile(HOTPLUG_SAMSUNG_ENABLE);
    }

    public static void enableSamsungPlugGovernor(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", HOTPLUG_SAMSUNG_GOVERNOR_ENABLE), HOTPLUG_SAMSUNG_GOVERNOR_ENABLE, context);
    }

    public static boolean isSamsungPlugGovernorEnabled() {
        return Utils.readFile(HOTPLUG_SAMSUNG_GOVERNOR_ENABLE).equals("1");
    }

    public static boolean hasSamsungPlugGovernor() {
        return Utils.existFile(HOTPLUG_SAMSUNG_GOVERNOR_ENABLE);
    }

    public static String getClBusyRatio() {
        return Utils.readFile(HOTPLUG_SAMSUNG_CL_BUSY_RATIO);
    }

    public static void setClBusyRatio(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_CL_BUSY_RATIO), HOTPLUG_SAMSUNG_CL_BUSY_RATIO, context);
    }

    public static boolean hasClBusyRatio() {
        return Utils.existFile(HOTPLUG_SAMSUNG_CL_BUSY_RATIO);
    }

    public static String getUserMode() {
        return Utils.readFile(HOTPLUG_SAMSUNG_USER_MODE);
    }

    public static void setUserMode(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_USER_MODE), HOTPLUG_SAMSUNG_USER_MODE, context);
    }

    public static boolean hasUserMode() {
        return Utils.existFile(HOTPLUG_SAMSUNG_USER_MODE);
    }

    public static void enableLdsum(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", HOTPLUG_SAMSUNG_LDSUM_ENABLE), HOTPLUG_SAMSUNG_LDSUM_ENABLE, context);
    }

    public static boolean isLdsumEnabled() {
        return Utils.readFile(HOTPLUG_SAMSUNG_LDSUM_ENABLE).equals("1");
    }

    public static boolean hasLdsum() {
        return Utils.existFile(HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE);
    }

    public static String getLdsumHeavyThread(){
        return Utils.readFile(HOTPLUG_SAMSUNG_LDSUM_HEAVY_THREAD);
    }

    public static void setLdsumHeavyThread(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_LDSUM_HEAVY_THREAD), HOTPLUG_SAMSUNG_LDSUM_HEAVY_THREAD, context);
    }

    public static boolean hasLdsumHeavyThread() {
        return Utils.existFile(HOTPLUG_SAMSUNG_LDSUM_HEAVY_THREAD);
    }

    public static void enableSkipLit(boolean enable, Context context) {
        run(Control.write(enable ? "1" : "0", HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE), HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE, context);
    }

    public static boolean isSkipLitEnabled() {
        return Utils.readFile(HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE).equals("1");
    }

    public static boolean hasSkipLit() {
        return Utils.existFile(HOTPLUG_SAMSUNG_SKIP_LIT_ENABLE);
    }

    public static String getBigHeavyThread(){
        return Utils.readFile(HOTPLUG_SAMSUNG_BIG_HEAVY_THREAD);
    }

    public static void setBigHeavyThread(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_BIG_HEAVY_THREAD), HOTPLUG_SAMSUNG_BIG_HEAVY_THREAD, context);
    }

    public static boolean hasBigHeavyThread() {
        return Utils.existFile(HOTPLUG_SAMSUNG_BIG_HEAVY_THREAD);
    }

    public static String getBigIdleThread(){
        return Utils.readFile(HOTPLUG_SAMSUNG_BIG_IDLE_THREAD);
    }

    public static void setBigIdleThread(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_BIG_IDLE_THREAD), HOTPLUG_SAMSUNG_BIG_IDLE_THREAD, context);
    }

    public static boolean hasBigIdleThread() {
        return Utils.existFile(HOTPLUG_SAMSUNG_BIG_IDLE_THREAD);
    }

    public static String getLittleIdleThread(){
        return Utils.readFile(HOTPLUG_SAMSUNG_LITTLE_IDLE_THREAD);
    }

    public static void setLittleIdleThread(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_LITTLE_IDLE_THREAD), HOTPLUG_SAMSUNG_LITTLE_IDLE_THREAD, context);
    }

    public static boolean hasLittleIdleThread() {
        return Utils.existFile(HOTPLUG_SAMSUNG_LITTLE_IDLE_THREAD);
    }

    public static String getLittleHeavyThread(){
        return Utils.readFile(HOTPLUG_SAMSUNG_LITTLE_HEAVY_THREAD);
    }

    public static void setLittleHeavyThread(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_LITTLE_HEAVY_THREAD), HOTPLUG_SAMSUNG_LITTLE_HEAVY_THREAD, context);
    }

    public static boolean hasLittleHeavyThread() {
        return Utils.existFile(HOTPLUG_SAMSUNG_LITTLE_HEAVY_THREAD);
    }

    public static String getMaxOnlineCpu() {
        String value = Utils.readFile(HOTPLUG_SAMSUNG_MAX_ONLINE_CPU);
        if (!value.isEmpty()) {
            return value.replace("max online cpu : ", "");
        }
        return null;
    }

    public static void setMaxOnlineCpu(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_MAX_ONLINE_CPU), HOTPLUG_SAMSUNG_MAX_ONLINE_CPU, context);
    }

    public static boolean hasMaxOnlineCpu() {
        return Utils.existFile(HOTPLUG_SAMSUNG_MAX_ONLINE_CPU);
    }

    public static String getMinOnlineCpu() {
        String value = Utils.readFile(HOTPLUG_SAMSUNG_MIN_ONLINE_CPU);
        if (!value.isEmpty()) {
            return value.replace("min online cpu : ", "");
        }
        return null;
    }

    public static void setMinOnlineCpu(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_MIN_ONLINE_CPU), HOTPLUG_SAMSUNG_MIN_ONLINE_CPU, context);
    }

    public static boolean hasMinOnlineCpu() {
        return Utils.existFile(HOTPLUG_SAMSUNG_MIN_ONLINE_CPU);
    }

    public static String getSingleChangeMs(){
        return Utils.readFile(HOTPLUG_SAMSUNG_SINGLE_CHANGE_MS);
    }

    public static void setSingleChangeMs(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_SINGLE_CHANGE_MS), HOTPLUG_SAMSUNG_SINGLE_CHANGE_MS, context);
    }

    public static boolean hasSingleChangeMs() {
        return Utils.existFile(HOTPLUG_SAMSUNG_SINGLE_CHANGE_MS);
    }

    public static String getDualChangeMs(){
        return Utils.readFile(HOTPLUG_SAMSUNG_DUAL_CHANGE_MS);
    }

    public static void setDualChangeMs(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_DUAL_CHANGE_MS), HOTPLUG_SAMSUNG_DUAL_CHANGE_MS, context);
    }

    public static boolean hasDualChangeMs() {
        return Utils.existFile(HOTPLUG_SAMSUNG_DUAL_CHANGE_MS);
    }

    public static String getTripleChangeMs(){
        return Utils.readFile(HOTPLUG_SAMSUNG_TRIPLE_CHANGE_MS);
    }

    public static void setTripleChangeMs(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_TRIPLE_CHANGE_MS), HOTPLUG_SAMSUNG_TRIPLE_CHANGE_MS, context);
    }

    public static boolean hasTripleChangeMs() {
        return Utils.existFile(HOTPLUG_SAMSUNG_TRIPLE_CHANGE_MS);
    }

    public static String getQuadChangeMs(){
        return Utils.readFile(HOTPLUG_SAMSUNG_QUAD_CHANGE_MS);
    }

    public static void setQuadChangeMs(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_QUAD_CHANGE_MS), HOTPLUG_SAMSUNG_QUAD_CHANGE_MS, context);
    }

    public static boolean hasQuadChangeMs() {
        return Utils.existFile(HOTPLUG_SAMSUNG_QUAD_CHANGE_MS);
    }
    public static void setSingleFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_SINGLE_FREQ),
                HOTPLUG_SAMSUNG_SINGLE_FREQ, context);
    }

    public static int getSingleFreq() {
        return Utils.strToInt(Utils.readFile(HOTPLUG_SAMSUNG_SINGLE_FREQ));
    }

    public static boolean hasSingleFreq() {
        return Utils.existFile(HOTPLUG_SAMSUNG_SINGLE_FREQ);
    }

    public static void setDualFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_DUAL_FREQ),
                HOTPLUG_SAMSUNG_DUAL_FREQ, context);
    }

    public static int getDualFreq() {
        return Utils.strToInt(Utils.readFile(HOTPLUG_SAMSUNG_DUAL_FREQ));
    }

    public static boolean hasDualFreq() {
        return Utils.existFile(HOTPLUG_SAMSUNG_DUAL_FREQ);
    }

    public static void setTripleFreq(int value, Context context) {
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_TRIPLE_FREQ),
                HOTPLUG_SAMSUNG_TRIPLE_FREQ, context);
    }

    public static int getTripleFreq() {
        return Utils.strToInt(Utils.readFile(HOTPLUG_SAMSUNG_TRIPLE_FREQ));
    }

    public static boolean hasTripleFreq() {
        return Utils.existFile(HOTPLUG_SAMSUNG_TRIPLE_FREQ);
    }

    public static void setQuadFreq(int value, Context context) {
        List<Integer> cpuFreqs= CPUFreq.getInstance(context).getFreqs();
        int maxFreq = cpuFreqs.get(cpuFreqs.size() - 1);
        String command = "chmod 0644 " + CPU_MAX_FREQ + " && echo ";
        if (getQuadFreq() > value)
            command += value + " > " + CPU_MAX_FREQ + " && echo ";
        command += value + " > " + HOTPLUG_SAMSUNG_QUAD_FREQ + " && echo " + maxFreq + " > " + CPU_MAX_FREQ;
        Log.e(command);
        run(command, HOTPLUG_SAMSUNG_QUAD_FREQ, context);;
    }

    public static int getQuadFreq() {
        return Utils.strToInt(Utils.readFile(HOTPLUG_SAMSUNG_QUAD_FREQ));
    }

    public static boolean hasQuadFreq() {
        return Utils.existFile(HOTPLUG_SAMSUNG_QUAD_FREQ);
    }

    public static String getLitMultRatio(){
        return Utils.readFile(HOTPLUG_SAMSUNG_LIT_MULT_RATIO);
    }

    public static void setLitMultRatio(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_LIT_MULT_RATIO), HOTPLUG_SAMSUNG_LIT_MULT_RATIO, context);
    }

    public static boolean hasLitMultRatio() {
        return Utils.existFile(HOTPLUG_SAMSUNG_LIT_MULT_RATIO);
    }

    public static String getToDualRatio(){
        return Utils.readFile(HOTPLUG_SAMSUNG_TO_DUAL_RATIO);
    }

    public static void setToDualRatio(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_TO_DUAL_RATIO), HOTPLUG_SAMSUNG_TO_DUAL_RATIO, context);
    }

    public static boolean hasToDualRatio() {
        return Utils.existFile(HOTPLUG_SAMSUNG_TO_DUAL_RATIO);
    }

    public static String getToQuadRatio(){
        return Utils.readFile(HOTPLUG_SAMSUNG_TO_QUAD_RATIO);
    }

    public static void setToQuadRatio(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_TO_QUAD_RATIO), HOTPLUG_SAMSUNG_TO_QUAD_RATIO, context);
    }

    public static boolean hasToQuadRatio() {
        return Utils.existFile(HOTPLUG_SAMSUNG_TO_QUAD_RATIO);
    }

    public static String getBigModeDual(){
        return Utils.readFile(HOTPLUG_SAMSUNG_BIG_MODE_DUAL);
    }

    public static void setBigModeDual(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_BIG_MODE_DUAL), HOTPLUG_SAMSUNG_BIG_MODE_DUAL, context);
    }

    public static boolean hasBigModeDual() {
        return Utils.existFile(HOTPLUG_SAMSUNG_BIG_MODE_DUAL);
    }

    public static String getBigModeNormal(){
		return Utils.readFile(HOTPLUG_SAMSUNG_BIG_MODE_NORMAL);
    }

    public static void setBigModeNormal(int value, Context context){
        run(Control.write(String.valueOf(value), HOTPLUG_SAMSUNG_BIG_MODE_NORMAL), HOTPLUG_SAMSUNG_BIG_MODE_NORMAL, context);
    }

    public static boolean hasBigModeNormal() {
        return Utils.existFile(HOTPLUG_SAMSUNG_BIG_MODE_NORMAL);
    }

    public static boolean supported() {
        return Utils.existFile(HOTPLUG_SAMSUNG) || Utils.existFile(HOTPLUG_SAMSUNG_GOVERNOR);
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.CPU_HOTPLUG, id, context);
    }
}
