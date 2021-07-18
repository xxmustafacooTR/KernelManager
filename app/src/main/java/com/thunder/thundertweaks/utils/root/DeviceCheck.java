package com.thunder.thundertweaks.utils.root;

import com.thunder.thundertweaks.utils.Device;
import com.thunder.thundertweaks.utils.Log;
import com.thunder.thundertweaks.utils.Utils;

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
        boolean ret = false;

        try {
            while (!ret) {
                ret = check(CHECK.PHONE_MODEL, "SM-N960F");
                ret = check(CHECK.PHONE_MODEL, "SM-G960F");
                ret = check(CHECK.PHONE_MODEL, "SM-G965F");
                ret = check(CHECK.PHONE_MODEL, "SM-N960N");
                ret = check(CHECK.PHONE_MODEL, "SM-G960N");
                ret = check(CHECK.PHONE_MODEL, "SM-G965N");
                ret = check(CHECK.FILE_EXISTS, "/sys/devices/platform/17500000.mali/clock");
                break;
            }
        } catch(Exception ignored) {
        }

        return ret;
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