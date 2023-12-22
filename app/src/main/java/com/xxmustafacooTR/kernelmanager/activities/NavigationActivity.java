/*
 * Copyright (C) 2015-2016 Willi Ye <williye97@gmail.com>
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xxmustafacooTR.kernelmanager.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.xxmustafacooTR.kernelmanager.R;
import com.xxmustafacooTR.kernelmanager.fragments.BaseFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BatteryFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BoefflaWakelockFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BusCamFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BusDispFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BusIntFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.BusMifFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.CPUVoltageCl1Fragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.CPUFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.CPUHotplugFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.CPUVoltageCl0Fragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.CPUVoltageCl2Fragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.EntropyFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.GPUFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.DvfsFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.GameFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.HmpFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.IOFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.KSMFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.LEDFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.LMKFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.PowerFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.WakelockFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.MiscFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.ScreenFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.SoundFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.SpectrumFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.ThermalFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.VMFragment;
import com.xxmustafacooTR.kernelmanager.fragments.kernel.WakeFragment;
import com.xxmustafacooTR.kernelmanager.fragments.other.AboutFragment;
import com.xxmustafacooTR.kernelmanager.fragments.other.SettingsFragment;
import com.xxmustafacooTR.kernelmanager.fragments.statistics.DeviceFragment;
import com.xxmustafacooTR.kernelmanager.fragments.statistics.InputsFragment;
import com.xxmustafacooTR.kernelmanager.fragments.statistics.MemoryFragment;
import com.xxmustafacooTR.kernelmanager.fragments.statistics.OverallFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.BackupFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.BuildpropFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.DataSharingFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.InitdFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.OnBootFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.ProfileFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.RecoveryFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.customcontrols.CustomControlsFragment;
import com.xxmustafacooTR.kernelmanager.fragments.tools.downloads.DownloadsFragment;
import com.xxmustafacooTR.kernelmanager.services.monitor.Monitor;
import com.xxmustafacooTR.kernelmanager.utils.AppSettings;
import com.xxmustafacooTR.kernelmanager.utils.Device;
import com.xxmustafacooTR.kernelmanager.utils.Utils;
import com.xxmustafacooTR.kernelmanager.utils.kernel.battery.Battery;
import com.xxmustafacooTR.kernelmanager.utils.kernel.bus.VoltageCam;
import com.xxmustafacooTR.kernelmanager.utils.kernel.bus.VoltageDisp;
import com.xxmustafacooTR.kernelmanager.utils.kernel.bus.VoltageInt;
import com.xxmustafacooTR.kernelmanager.utils.kernel.bus.VoltageMif;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpuhotplug.Hotplug;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpuvoltage.VoltageCl0;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpuvoltage.VoltageCl1;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpuvoltage.VoltageCl2;
import com.xxmustafacooTR.kernelmanager.utils.kernel.entropy.Entropy;
import com.xxmustafacooTR.kernelmanager.utils.kernel.game.GameControl;
import com.xxmustafacooTR.kernelmanager.utils.kernel.gpu.GPU;
import com.xxmustafacooTR.kernelmanager.utils.kernel.hmp.Hmp;
import com.xxmustafacooTR.kernelmanager.utils.kernel.dvfs.Dvfs;
import com.xxmustafacooTR.kernelmanager.utils.kernel.io.IO;
import com.xxmustafacooTR.kernelmanager.utils.kernel.ksm.KSM;
import com.xxmustafacooTR.kernelmanager.utils.kernel.led.LED;
import com.xxmustafacooTR.kernelmanager.utils.kernel.lmk.LMK;
import com.xxmustafacooTR.kernelmanager.utils.kernel.power.marginVoltage;
import com.xxmustafacooTR.kernelmanager.utils.kernel.screen.Screen;
import com.xxmustafacooTR.kernelmanager.utils.kernel.sound.Sound;
import com.xxmustafacooTR.kernelmanager.utils.kernel.spectrum.Spectrum;
import com.xxmustafacooTR.kernelmanager.utils.kernel.thermal.Thermal;
import com.xxmustafacooTR.kernelmanager.utils.kernel.wake.Wake;
import com.xxmustafacooTR.kernelmanager.utils.kernel.boefflawakelock.BoefflaWakelock;
import com.xxmustafacooTR.kernelmanager.utils.kernel.wakelock.Wakelock;
import com.xxmustafacooTR.kernelmanager.utils.root.RootUtils;
import com.xxmustafacooTR.kernelmanager.utils.tools.Backup;
import com.xxmustafacooTR.kernelmanager.utils.tools.SupportedDownloads;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class NavigationActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String PACKAGE = NavigationActivity.class.getCanonicalName();
    public static final String INTENT_SECTION = PACKAGE + ".INTENT.SECTION";

    private ArrayList<NavigationFragment> mFragments = new ArrayList<>();
    private Map<Integer, Class<? extends Fragment>> mActualFragments = new LinkedHashMap<>();

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private long mLastTimeBackbuttonPressed;

    private int mSelection;

    @Override
    protected boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            new FragmentLoader(this).execute();
        } else {
            mFragments = savedInstanceState.getParcelableArrayList("fragments");
            init(savedInstanceState);
        }

         if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
                        mDrawer.closeDrawer(GravityCompat.START);
                    } else {
                        Fragment currentFragment = getFragment(mSelection);
                        if (!(currentFragment instanceof BaseFragment)
                                || !((BaseFragment) currentFragment).onBackPressed()) {
                            long currentTime = SystemClock.elapsedRealtime();
                            if (currentTime - mLastTimeBackbuttonPressed > 2000) {
                                mLastTimeBackbuttonPressed = currentTime;
                                Utils.toast(R.string.press_back_again_exit, NavigationActivity.this);
                            } else {
                                finishAffinity();
                            }
                        }
                    }


                }
            };
            getOnBackPressedDispatcher().addCallback(this, callback);
        }

        collectPerm();
    }

    private static class FragmentLoader extends AsyncTask<Void, Void, Void> {

        private WeakReference<NavigationActivity> mRefActivity;

        private FragmentLoader(NavigationActivity activity) {
            mRefActivity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            NavigationActivity activity = mRefActivity.get();
            if (activity == null) return null;
            activity.initFragments();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NavigationActivity activity = mRefActivity.get();
            if (activity == null) return;
            activity.init(null);
        }
    }

    /*
     * Permission Requests
     */
    private void collectPerm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.POST_NOTIFICATIONS }, 100);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO }, 101);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_NOTIFICATION_POLICY }, 102);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE }, 103);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel("test",
                "test", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setSound(null, null);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private void initFragments() {
        mFragments.clear();
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.statistics));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.overall, OverallFragment.class, R.drawable.ic_chart));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.device, DeviceFragment.class, R.drawable.ic_device));
        if (Device.MemInfo.getInstance().getItems().size() > 0) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.memory, MemoryFragment.class, R.drawable.ic_save));
        }
        if (Device.Input.getInstance().supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.inputs, InputsFragment.class, R.drawable.ic_keyboard));
        }
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.kernel));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpu, CPUFragment.class, R.drawable.ic_cpu));
        if (Hotplug.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpu_hotplug, CPUHotplugFragment.class, R.drawable.ic_switch));
        }
        if (Hmp.getInstance().supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.hmp, HmpFragment.class, R.drawable.ic_cpu));
        }
        if (Thermal.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.thermal, ThermalFragment.class, R.drawable.ic_temperature));
        }
        if (GPU.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.gpu, GPUFragment.class, R.drawable.ic_gpu));
        }
        if (GameControl.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.game_nav, GameFragment.class, R.drawable.ic_spectrum_game));
        }
        if (Dvfs.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.dvfs_nav, DvfsFragment.class, R.drawable.ic_dvfs));
        }
        if (marginVoltage.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.power_nav, PowerFragment.class, R.drawable.ic_bolt));
        }
        if (Screen.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.screen, ScreenFragment.class, R.drawable.ic_display));
        }
        if (Wake.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.gestures, WakeFragment.class, R.drawable.ic_touch));
        }
        if (Sound.getInstance().supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.sound, SoundFragment.class, R.drawable.ic_music));
        }
        if (Spectrum.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.spectrum, SpectrumFragment.class, R.drawable.ic_spectrum_logo));
        }
        if (Battery.getInstance(this).supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.battery, BatteryFragment.class, R.drawable.ic_battery));
        }
        if (LED.getInstance().supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.led, LEDFragment.class, R.drawable.ic_led));
        }
        if (IO.getInstance().supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.io_scheduler, IOFragment.class, R.drawable.ic_sdcard));
        }
        if (KSM.getInstance().supported()) {
            if (KSM.getInstance().isUKSM()) {
                mFragments.add(new NavigationActivity.NavigationFragment(R.string.uksm_name, KSMFragment.class, R.drawable.ic_merge));
            } else {
                mFragments.add(new NavigationActivity.NavigationFragment(R.string.ksm_name, KSMFragment.class, R.drawable.ic_merge));
            }
        }
        if (LMK.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.lmk, LMKFragment.class, R.drawable.ic_stackoverflow));
        }
        if (Wakelock.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.wakelock_nav, WakelockFragment.class, R.drawable.ic_unlock));
        }
        if (BoefflaWakelock.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.boeffla_wakelock, BoefflaWakelockFragment.class, R.drawable.ic_unlock));
        }
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.virtual_memory, VMFragment.class, R.drawable.ic_server));
        if (Entropy.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.entropy, EntropyFragment.class, R.drawable.ic_numbers));
        }
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.misc, MiscFragment.class, R.drawable.ic_clear));
        //mFragments.add(new NavigationActivity.NavigationFragment(R.string.voltage_control));
        if (VoltageCl2.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpucl2_voltage, CPUVoltageCl2Fragment.class, R.drawable.ic_bolt));
            if (VoltageCl1.supported()) {
                mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpucl1_voltage, CPUVoltageCl1Fragment.class, R.drawable.ic_bolt));
            }
        } else {
            if (VoltageCl1.supported()) {
                mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpucl2_voltage, CPUVoltageCl1Fragment.class, R.drawable.ic_bolt));
            }
        }
        if (VoltageCl0.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.cpucl0_voltage, CPUVoltageCl0Fragment.class, R.drawable.ic_bolt));
        }
        if (VoltageMif.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.busMif_voltage, BusMifFragment.class, R.drawable.ic_bolt));
        }
        if (VoltageInt.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.busInt_voltage, BusIntFragment.class, R.drawable.ic_bolt));
        }
        if (VoltageDisp.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.busDisp_voltage, BusDispFragment.class, R.drawable.ic_bolt));
        }
        if (VoltageCam.supported()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.busCam_voltage, BusCamFragment.class, R.drawable.ic_bolt));
        }
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.tools));
        //mFragments.add(new NavigationActivity.NavigationFragment(R.string.data_sharing, DataSharingFragment.class, R.drawable.ic_database));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.custom_controls, CustomControlsFragment.class, R.drawable.ic_console));

        SupportedDownloads supportedDownloads = new SupportedDownloads(this);
        if (supportedDownloads.getLink() != null) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.downloads, DownloadsFragment.class, R.drawable.ic_download));
        }
        if (Backup.hasBackup()) {
            mFragments.add(new NavigationActivity.NavigationFragment(R.string.backup, BackupFragment.class, R.drawable.ic_restore));
        }
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.build_prop_editor, BuildpropFragment.class, R.drawable.ic_edit));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.profile, ProfileFragment.class, R.drawable.ic_layers));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.recovery, RecoveryFragment.class, R.drawable.ic_security));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.initd, InitdFragment.class, R.drawable.ic_shell));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.on_boot, OnBootFragment.class, R.drawable.ic_start));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.other));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.settings, SettingsFragment.class, R.drawable.ic_settings));
        mFragments.add(new NavigationActivity.NavigationFragment(R.string.about, AboutFragment.class, R.drawable.ic_about));
        //mFragments.add(new NavigationActivity.NavigationFragment(R.string.contributors, ContributorsFragment.class, R.drawable.ic_people));
        //mFragments.add(new NavigationActivity.NavigationFragment(R.string.help, HelpFragment.class, R.drawable.ic_help));
    }

    private void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = getToolBar();
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, 0, 0);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.clearFocus();
            }
        });

        if (savedInstanceState != null) {
            mSelection = savedInstanceState.getInt(INTENT_SECTION);
        }

        appendFragments(false);
        String section = getIntent().getStringExtra(INTENT_SECTION);
        if (section != null) {
            for (Map.Entry<Integer, Class<? extends Fragment>> entry : mActualFragments.entrySet()) {
                Class<? extends Fragment> fragmentClass = entry.getValue();
                if (fragmentClass != null && fragmentClass.getCanonicalName().equals(section)) {
                    mSelection = entry.getKey();
                    break;
                }
            }
            getIntent().removeExtra(INTENT_SECTION);
        }

        if (mSelection == 0 || mActualFragments.get(mSelection) == null) {
            mSelection = firstTab();
        }
        onItemSelected(mSelection, false);

        if (AppSettings.isDataSharing(this)) {
            startService(new Intent(this, Monitor.class));
        }
    }

    private int firstTab() {
        for (Map.Entry<Integer, Class<? extends Fragment>> entry : mActualFragments.entrySet()) {
            if (entry.getValue() != null) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public void appendFragments() {
        appendFragments(true);
    }

    private void appendFragments(boolean setShortcuts) {
        mActualFragments.clear();
        Menu menu = mNavigationView.getMenu();
        menu.clear();

        SubMenu lastSubMenu = null;
        for (NavigationFragment navigationFragment : mFragments) {
            Class<? extends Fragment> fragmentClass = navigationFragment.mFragmentClass;
            int id = navigationFragment.mId;

            Drawable drawable = ContextCompat.getDrawable(this,
                        AppSettings.isSectionIcons(this)
                        && navigationFragment.mDrawable != 0 ? navigationFragment.mDrawable :
                        R.drawable.ic_blank);

            if (fragmentClass == null) {
                lastSubMenu = menu.addSubMenu(id);
                mActualFragments.put(id, null);
            } else if (AppSettings.isFragmentEnabled(fragmentClass, this)) {
                MenuItem menuItem = lastSubMenu == null ? menu.add(0, id, 0, id) :
                        lastSubMenu.add(0, id, 0, id);
                menuItem.setIcon(drawable);
                menuItem.setCheckable(true);
                if (mSelection != 0) {
                    mNavigationView.setCheckedItem(mSelection);
                }
                mActualFragments.put(id, fragmentClass);
            }
        }
        if (setShortcuts) {
            setShortcuts();
        }
    }

    private NavigationFragment findNavigationFragmentByClass(Class<? extends Fragment> fragmentClass) {
        if (fragmentClass == null) return null;
        for (NavigationFragment navigationFragment : mFragments) {
            if (fragmentClass == navigationFragment.mFragmentClass) {
                return navigationFragment;
            }
        }
        return null;
    }

    private void setShortcuts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return;

        PriorityQueue<Class<? extends Fragment>> queue = new PriorityQueue<>(
                (o1, o2) -> {
                    int opened1 = AppSettings.getFragmentOpened(o1, this);
                    int opened2 = AppSettings.getFragmentOpened(o2, this);
                    return opened2 - opened1;
                });

        for (Map.Entry<Integer, Class<? extends Fragment>> entry : mActualFragments.entrySet()) {
            Class<? extends Fragment> fragmentClass = entry.getValue();
            if (fragmentClass == null || fragmentClass == SettingsFragment.class) continue;

            queue.offer(fragmentClass);
        }

        List<ShortcutInfo> shortcutInfos = new ArrayList<>();
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.removeAllDynamicShortcuts();
        for (int i = 0; i < 4; i++) {
            NavigationFragment fragment = findNavigationFragmentByClass(queue.poll());
            if (fragment == null || fragment.mFragmentClass == null) continue;
            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra(INTENT_SECTION, fragment.mFragmentClass.getCanonicalName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            ShortcutInfo shortcut = new ShortcutInfo.Builder(this,
                    fragment.mFragmentClass.getSimpleName())
                    .setShortLabel(getString(fragment.mId))
                    .setLongLabel(Utils.strFormat(getString(R.string.open), getString(fragment.mId)))
                    .setIcon(Icon.createWithResource(this, fragment.mDrawable == 0 ?
                            R.drawable.ic_blank : fragment.mDrawable))
                    .setIntent(intent)
                    .build();
            shortcutInfos.add(shortcut);
        }
        shortcutManager.setDynamicShortcuts(shortcutInfos);
    }

    public ArrayList<NavigationFragment> getFragments() {
        return mFragments;
    }

    public Map<Integer, Class<? extends Fragment>> getActualFragments() {
        return mActualFragments;
    }

    @Override
    public void finish() {
        super.finish();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int id : mActualFragments.keySet()) {
            Fragment fragment = fragmentManager.findFragmentByTag(id + "_key");
            if (fragment != null) {
                fragmentTransaction.remove(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
        RootUtils.closeSU();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("fragments", mFragments);
        outState.putInt(INTENT_SECTION, mSelection);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        onItemSelected(item.getItemId(), true);
        return true;
    }

    private void onItemSelected(final int res, boolean saveOpened) {
        mDrawer.closeDrawer(GravityCompat.START);
        getSupportActionBar().setTitle(getString(res));
        mNavigationView.setCheckedItem(res);
        mSelection = res;
        final Fragment fragment = getFragment(res);

        if (saveOpened) {
            AppSettings.saveFragmentOpened(fragment.getClass(),
                    AppSettings.getFragmentOpened(fragment.getClass(), this) + 1,
                    this);
        }
        setShortcuts();

        mDrawer.postDelayed(()
                        -> {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.content_frame, fragment, res + "_key").commitAllowingStateLoss();
                },
                250);
    }

    private Fragment getFragment(int res) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(res + "_key");
        if (fragment == null && mActualFragments.containsKey(res)) {
            fragment = Fragment.instantiate(this,
                    Objects.requireNonNull(Objects.requireNonNull(mActualFragments.get(res)).getCanonicalName()));
        }
        return fragment;
    }

    public static class NavigationFragment implements Parcelable {

        public int mId;
        public Class<? extends Fragment> mFragmentClass;
        private int mDrawable;

        NavigationFragment(int id) {
            this(id, null, 0);
        }

        NavigationFragment(int id, Class<? extends Fragment> fragment, int drawable) {
            mId = id;
            mFragmentClass = fragment;
            mDrawable = drawable;
        }

        NavigationFragment(Parcel parcel) {
            mId = parcel.readInt();
            mFragmentClass = (Class<? extends Fragment>) parcel.readSerializable();
            mDrawable = parcel.readInt();
        }

        @Override
        public String toString() {
            return String.valueOf(mId);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mId);
            dest.writeSerializable(mFragmentClass);
            dest.writeInt(mDrawable);
        }

        public static final Creator CREATOR = new Creator<NavigationFragment>() {
            @Override
            public NavigationFragment createFromParcel(Parcel source) {
                return new NavigationFragment(source);
            }

            @Override
            public NavigationFragment[] newArray(int size) {
                return new NavigationFragment[0];
            }
        };
    }

}
