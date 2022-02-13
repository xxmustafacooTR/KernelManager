package com.thunder.thundertweaks.fragments.kernel;

import androidx.appcompat.app.AlertDialog;

import com.thunder.thundertweaks.R;
import com.thunder.thundertweaks.fragments.ApplyOnBootFragment;
import com.thunder.thundertweaks.fragments.DescriptionFragment;
import com.thunder.thundertweaks.fragments.recyclerview.RecyclerViewFragment;
import com.thunder.thundertweaks.utils.AppSettings;
import com.thunder.thundertweaks.utils.Log;
import com.thunder.thundertweaks.utils.PackageInfo;
import com.thunder.thundertweaks.utils.Utils;
import com.thunder.thundertweaks.utils.kernel.cpu.CPUFreq;
import com.thunder.thundertweaks.utils.kernel.cpu.Misc;
import com.thunder.thundertweaks.utils.kernel.game.GameControl;
import com.thunder.thundertweaks.utils.kernel.gpu.GPUFreqExynos;
import com.thunder.thundertweaks.views.recyclerview.ButtonView2;
import com.thunder.thundertweaks.views.recyclerview.CardView;
import com.thunder.thundertweaks.views.recyclerview.GenericSelectView;
import com.thunder.thundertweaks.views.recyclerview.RecyclerViewItem;
import com.thunder.thundertweaks.views.recyclerview.SeekBarView;
import com.thunder.thundertweaks.views.recyclerview.SelectView;
import com.thunder.thundertweaks.views.recyclerview.SwitchView;

import java.util.ArrayList;
import java.util.Arrays;
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
