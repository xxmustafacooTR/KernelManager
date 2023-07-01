package com.xxmustafacooTR.kernelmanager.fragments.kernel;

import androidx.appcompat.app.AlertDialog;

import com.xxmustafacooTR.kernelmanager.R;
import com.xxmustafacooTR.kernelmanager.fragments.ApplyOnBootFragment;
import com.xxmustafacooTR.kernelmanager.fragments.DescriptionFragment;
import com.xxmustafacooTR.kernelmanager.fragments.recyclerview.RecyclerViewFragment;
import com.xxmustafacooTR.kernelmanager.utils.AppSettings;
import com.xxmustafacooTR.kernelmanager.utils.PackageInfo;
import com.xxmustafacooTR.kernelmanager.utils.kernel.cpu.CPUFreq;
import com.xxmustafacooTR.kernelmanager.utils.kernel.game.GameControl;
import com.xxmustafacooTR.kernelmanager.utils.kernel.gpu.GPUFreqExynos;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.ButtonView2;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.CardView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.GenericSelectView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.RecyclerViewItem;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.SeekBarView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.SelectView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.SwitchView;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends RecyclerViewFragment {
    private GameControl mGameControl;
    private GPUFreqExynos mGPUFreqExynos;
    private CPUFreq mCPUFreq;
    private PackageInfo mPackageInfo;

    private List<SwitchView> mPACKAGES = new ArrayList<>();
    private int mPackageInfoMode = 1;

    @Override
    protected void init() {
        super.init();

        mGameControl = GameControl.getInstance();
        mGPUFreqExynos = GPUFreqExynos.getInstance();
        mCPUFreq = CPUFreq.getInstance(getActivity());
        mPackageInfo = PackageInfo.getInstance(getActivity());

        addViewPagerFragment(ApplyOnBootFragment.newInstance(this));
        if (GameControl.supported()) {
            addViewPagerFragment(DescriptionFragment.newInstance(getString(R.string.gameControl),getString(R.string.gameControl_Summary)));
        }
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        if (GameControl.supported()) {
            gameControlInit(items);
            if(mGameControl.hasGamePackages()) {
                gamePackagesInit(items);
            }
        }
    }

    private void gameControlInit(List<RecyclerViewItem> items) {
        CardView gameControlCard = new CardView(getActivity());
        if(mGameControl.hasVersion())
            gameControlCard.setTitle(getString(R.string.gameControl) + " v" + mGameControl.getVersion());
        else
            gameControlCard.setTitle(getString(R.string.gameControl));

        if(mGameControl.hasAlwaysOn()){
            SwitchView alwaysOn = new SwitchView();
            alwaysOn.setTitle(getString(R.string.gameControl_always_on));
            alwaysOn.setSummary(getString(R.string.gameControl_always_on_desc));
            alwaysOn.setChecked(mGameControl.isEnabledAlwaysOn());
            alwaysOn.addOnSwitchListener((switchView, isChecked) ->
                    mGameControl.enableAlwaysOn(isChecked, getActivity())
            );
            gameControlCard.addItem(alwaysOn);
        }

        if(mGameControl.hasBatteryIdle()){
            SwitchView batteryidle = new SwitchView();
            batteryidle.setTitle(getString(R.string.gameControl_batteryidle));
            batteryidle.setSummary(getString(R.string.gameControl_batteryidle_desc));
            batteryidle.setChecked(mGameControl.isEnabledBatteryIdle());
            batteryidle.addOnSwitchListener((switchView, isChecked) ->
                    mGameControl.enableBatteryIdle(isChecked, getActivity())
            );
            gameControlCard.addItem(batteryidle);
        }

        if(mCPUFreq.getFreqs() != null) {

            if (mGameControl.hasBIGMax()) {
                SelectView BIGMax = new SelectView();
                BIGMax.setSummary(getString(R.string.gameControl_BIG_max));
                BIGMax.setItems(mCPUFreq.getAdjustedFreq(getActivity()));
                BIGMax.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setBIGMax(mCPUFreq.getFreqs().get(position), getActivity()));

                gameControlCard.addItem(BIGMax);

                BIGMax.setItem((mGameControl.getBIGMax() / 1000) + getString(R.string.mhz));
            }

            if (mGameControl.hasBIGMin()) {
                SelectView BIGMin = new SelectView();
                BIGMin.setSummary(getString(R.string.gameControl_BIG_min));
                BIGMin.setItems(mCPUFreq.getAdjustedFreq(getActivity()));
                BIGMin.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setBIGMin(mCPUFreq.getFreqs().get(position), getActivity()));

                gameControlCard.addItem(BIGMin);

                BIGMin.setItem((mGameControl.getBIGMin() / 1000) + getString(R.string.mhz));
            }

            if (mCPUFreq.isBigLITTLE()) {
                if (mCPUFreq.hasMidCpu()) {

                    if (mGameControl.hasMIDDLEMax()) {
                        SelectView MIDDLEMax = new SelectView();
                        MIDDLEMax.setSummary(getString(R.string.gameControl_MIDDLE_max));
                        MIDDLEMax.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getMidCpu(), getActivity()));
                        MIDDLEMax.setOnItemSelected((selectView, position, item)
                                -> mGameControl.setMIDDLEMax(mCPUFreq.getFreqs(mCPUFreq.getMidCpu()).get(position), getActivity()));

                        gameControlCard.addItem(MIDDLEMax);

                        MIDDLEMax.setItem((mGameControl.getMIDDLEMax() / 1000) + getString(R.string.mhz));
                    }

                    if (mGameControl.hasMIDDLEMin()) {
                        SelectView MIDDLEMin = new SelectView();
                        MIDDLEMin.setSummary(getString(R.string.gameControl_MIDDLE_min));
                        MIDDLEMin.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getMidCpu(), getActivity()));
                        MIDDLEMin.setOnItemSelected((selectView, position, item)
                                -> mGameControl.setMIDDLEMin(mCPUFreq.getFreqs(mCPUFreq.getMidCpu()).get(position), getActivity()));

                        gameControlCard.addItem(MIDDLEMin);

                        MIDDLEMin.setItem((mGameControl.getMIDDLEMin() / 1000) + getString(R.string.mhz));
                    }
                }

                if (mGameControl.hasLITTLEMax()) {
                    SelectView LITTLEMax = new SelectView();
                    LITTLEMax.setSummary(getString(R.string.gameControl_LITTLE_max));
                    LITTLEMax.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getLITTLECpu(), getActivity()));
                    LITTLEMax.setOnItemSelected((selectView, position, item)
                            -> mGameControl.setLITTLEMax(mCPUFreq.getFreqs(mCPUFreq.getLITTLECpu()).get(position), getActivity()));

                    gameControlCard.addItem(LITTLEMax);

                    LITTLEMax.setItem((mGameControl.getLITTLEMax() / 1000) + getString(R.string.mhz));
                }

                if (mGameControl.hasLITTLEMin()) {
                    SelectView LITTLEMin = new SelectView();
                    LITTLEMin.setSummary(getString(R.string.gameControl_LITTLE_min));
                    LITTLEMin.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getLITTLECpu(), getActivity()));
                    LITTLEMin.setOnItemSelected((selectView, position, item)
                            -> mGameControl.setLITTLEMin(mCPUFreq.getFreqs(mCPUFreq.getLITTLECpu()).get(position), getActivity()));

                    gameControlCard.addItem(LITTLEMin);

                    LITTLEMin.setItem((mGameControl.getLITTLEMin() / 1000) + getString(R.string.mhz));
                }
            }
        }

        if(mGPUFreqExynos.getAvailableFreqs() != null) {
            if(mGameControl.hasGPUMax()) {
                SelectView GPUMax = new SelectView();
                GPUMax.setSummary(getString(R.string.gameControl_GPU_max));
                GPUMax.setItems(mGPUFreqExynos.getAdjustedFreqs(getActivity()));
                GPUMax.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setGPUMax(mGPUFreqExynos.getAvailableFreqs().get(position), getActivity()));

                gameControlCard.addItem(GPUMax);

                GPUMax.setItem((mGameControl.getGPUMax() / 1000) + getString(R.string.mhz));
            }

            if(mGameControl.hasGPUMin()) {
                SelectView GPUMin = new SelectView();
                GPUMin.setSummary(getString(R.string.gameControl_GPU_min));
                GPUMin.setItems(mGPUFreqExynos.getAdjustedFreqs(getActivity()));
                GPUMin.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setGPUMin(mGPUFreqExynos.getAvailableFreqs().get(position), getActivity()));

                gameControlCard.addItem(GPUMin);

                GPUMin.setItem((mGameControl.getGPUMin() / 1000) + getString(R.string.mhz));
            }
        }

        if(mGameControl.hasMIFMin()) {
            GenericSelectView minMIFfreq = new GenericSelectView();
            minMIFfreq.setSummary(getString(R.string.gameControl_MIF_min));
            minMIFfreq.setValue(String.valueOf(mGameControl.getMIFMin()));
            minMIFfreq.setValueRaw(minMIFfreq.getValue());
            minMIFfreq.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setMIFMin(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(minMIFfreq);
        }

        if(mGameControl.hasINTMin()) {
            GenericSelectView minINTfreq = new GenericSelectView();
            minINTfreq.setSummary(getString(R.string.gameControl_INT_min));
            minINTfreq.setValue(String.valueOf(mGameControl.getINTMin()));
            minINTfreq.setValueRaw(minINTfreq.getValue());
            minINTfreq.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setINTMin(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(minINTfreq);
        }

        if (mGameControl.hasLittleFreq()) {
            GenericSelectView customLittlefreq = new GenericSelectView();
            customLittlefreq.setSummary(getString(R.string.gameControl_custom_little_freq));
            customLittlefreq.setValue(String.valueOf(mGameControl.getLittleFreq()));
            customLittlefreq.setValueRaw(customLittlefreq.getValue());
            customLittlefreq.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setLittleFreq(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customLittlefreq);
        }

        if (mGameControl.hasLittleVolt()) {
            GenericSelectView customLittleVolt = new GenericSelectView();
            customLittleVolt.setSummary(getString(R.string.gameControl_custom_little_volt));
            customLittleVolt.setValue(String.valueOf(mGameControl.getLittleVolt()));
            customLittleVolt.setValueRaw(customLittleVolt.getValue());
            customLittleVolt.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setLittleVolt(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customLittleVolt);
        }

        if (mGameControl.hasBigFreq()) {
            GenericSelectView customBigfreq = new GenericSelectView();
            customBigfreq.setSummary(getString(R.string.gameControl_custom_Big_freq));
            customBigfreq.setValue(String.valueOf(mGameControl.getBigFreq()));
            customBigfreq.setValueRaw(customBigfreq.getValue());
            customBigfreq.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setBigFreq(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customBigfreq);
        }

        if (mGameControl.hasBigVolt()) {
            GenericSelectView customBigVolt = new GenericSelectView();
            customBigVolt.setSummary(getString(R.string.gameControl_custom_Big_volt));
            customBigVolt.setValue(String.valueOf(mGameControl.getBigVolt()));
            customBigVolt.setValueRaw(customBigVolt.getValue());
            customBigVolt.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setBigVolt(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customBigVolt);
        }

        if (mGameControl.hasGpuFreq()) {
            GenericSelectView customGpufreq = new GenericSelectView();
            customGpufreq.setSummary(getString(R.string.gameControl_custom_Gpu_freq));
            customGpufreq.setValue(String.valueOf(mGameControl.getGpuFreq()));
            customGpufreq.setValueRaw(customGpufreq.getValue());
            customGpufreq.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setGpuFreq(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customGpufreq);
        }

        if (mGameControl.hasGpuVolt()) {
            GenericSelectView customGpuVolt = new GenericSelectView();
            customGpuVolt.setSummary(getString(R.string.gameControl_custom_Gpu_volt));
            customGpuVolt.setValue(String.valueOf(mGameControl.getGpuVolt()));
            customGpuVolt.setValueRaw(customGpuVolt.getValue());
            customGpuVolt.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setGpuVolt(Integer.parseInt(value), getActivity()));

            gameControlCard.addItem(customGpuVolt);
        }

        items.add(gameControlCard);
    }

    private void gamePackagesInit (List<RecyclerViewItem> items){
        final CardView CardPackages = new CardView(getActivity());
        CardPackages.setTitle(getString(R.string.gameControl_packages));

        CardGamePackagesInit(CardPackages);

        if (CardPackages.size() > 0) {
            items.add(CardPackages);
        }
    }

    private void CardGamePackagesInit(final CardView card) {
        card.clearItems();
        mPACKAGES.clear();

        mPackageInfoMode = AppSettings.getInt("game_control_show_packages_filter", 1, getActivity());

        SeekBarView mode = new SeekBarView();
        mode.setSummary(getString(R.string.gameControl_packages_mode));
        mode.setMax(11);
        mode.setMin(1);
        mode.setProgress(mPackageInfoMode - 1);
        mode.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
            @Override
            public void onStop(SeekBarView seekBarView, int position, String value) {
                mPackageInfoMode = position + 1;
                AppSettings.saveInt("game_control_show_packages_filter", mPackageInfoMode, getActivity());
                getHandler().postDelayed(() -> CardGamePackagesInit(card), 250);
            }

            @Override
            public void onMove(SeekBarView seekBarView, int position, String value) {
            }
        });

        card.addItem(mode);

        ButtonView2 reset = new ButtonView2();
        reset.setTitle(getString(R.string.reset));
        reset.setSummary(getString(R.string.reset_summary));
        reset.setButtonText(getString(R.string.reset));
        reset.setOnItemClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle(getString(R.string.wkl_alert_title));
            alert.setMessage(getString(R.string.gameControl_reset_message));
            alert.setNegativeButton(getString(R.string.cancel), (dialog, which) -> {});
            alert.setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                mPackageInfoMode = 1;
                mode.setProgress(mPackageInfoMode - 1);
                AppSettings.saveInt("game_control_show_packages_filter", mPackageInfoMode, getActivity());
                mGameControl.setGamePackages("", getActivity());
                getHandler().postDelayed(() -> CardGamePackagesInit(card), 250);
            });
            alert.show();
        });
        card.addItem(reset);

        for (int i=0; i<mPackageInfo.getAdjustedAppPackages(mPackageInfoMode).size(); i++) {
            String packageName = mPackageInfo.getAdjustedAppPackages(mPackageInfoMode).get(i);
            SwitchView gamePackages = new SwitchView();
            gamePackages.setSummary(packageName);
            gamePackages.setTitle(mPackageInfo.getAppNameFromPackage(packageName));
            gamePackages.setIcon(mPackageInfo.getIconFromPackage(packageName, getActivity()));
            gamePackages.setChecked(mGameControl.checkGamePackage(mPackageInfo.getAdjustedAppPackages(mPackageInfoMode).get(i)));
            gamePackages.addOnSwitchListener((switchView, isChecked) ->
                    mGameControl.editGamePackage(isChecked, String.valueOf(switchView.getSummary()), getActivity())
            );
            card.addItem(gamePackages);
            mPACKAGES.add(gamePackages);
        }
    }
}
