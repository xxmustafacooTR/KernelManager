package com.xxmustafacooTR.kernelmanager.utils.root;

import com.xxmustafacooTR.kernelmanager.utils.Device;
import com.xxmustafacooTR.kernelmanager.utils.Utils;

public class DeviceCheck {

    //TODO
    enum CHECK {
        ALL,
        FILE_EXISTS,
        PROCESSOR_MODEL,
        PROCESSOR_CORE,
        VENDOR,
        PHONE_MODEL,
        ARCH,
        HARDWARE,
        BOOTLOADER,
        BOARD,
    }

    public static boolean deviceSupported() {
        return (check(CHECK.PHONE_MODEL, "SM-N960F") || check(CHECK.PHONE_MODEL, "SM-G960F") ||
                check(CHECK.PHONE_MODEL, "SM-G965F") || check(CHECK.PHONE_MODEL, "SM-N960N") ||
                check(CHECK.PHONE_MODEL, "SM-G960N") || check(CHECK.PHONE_MODEL, "SM-G965N") ||
                check(CHECK.FILE_EXISTS, "/sys/devices/platform/17500000.mali/clock") || check(CHECK.FILE_EXISTS, "/data/.kernelmanager/bypass"));
    }

    private static boolean check(CHECK mode, String value){
        boolean ret;

        switch(mode) {
            case ALL:
                ret = true;
                break;
            case FILE_EXISTS:
                ret = Utils.existFile(value);
                break;
            case PROCESSOR_MODEL:
                //TODO
                ret = false;
                break;
            case PROCESSOR_CORE:
                //TODO
                ret = false;
                break;
            case VENDOR:
                ret = Device.getVendor().equals(value);
                break;
            case PHONE_MODEL:
                ret = Device.getModel().equals(value);
                break;
            case ARCH:
                ret = Device.getArchitecture().equals(value);
                break;
            case HARDWARE:
                ret = Device.getHardware().equals(value);
                break;
            case BOOTLOADER:
                ret = Device.getBootloader().equals(value);
                break;
            case BOARD:
                ret = Device.getBoard().equals(value);
                break;
            default:
                ret = false;
        }

        return ret;
    }
}