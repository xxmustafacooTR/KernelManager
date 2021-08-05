package com.thunder.thundertweaks.fragments.kernel;

import com.thunder.thundertweaks.R;
import com.thunder.thundertweaks.fragments.ApplyOnBootFragment;
import com.thunder.thundertweaks.fragments.DescriptionFragment;
import com.thunder.thundertweaks.fragments.recyclerview.RecyclerViewFragment;
import com.thunder.thundertweaks.utils.Log;
import com.thunder.thundertweaks.utils.Utils;
import com.thunder.thundertweaks.utils.kernel.cpu.CPUFreq;
import com.thunder.thundertweaks.utils.kernel.game.GameControl;
import com.thunder.thundertweaks.utils.kernel.gpu.GPUFreqExynos;
import com.thunder.thundertweaks.views.recyclerview.CardView;
import com.thunder.thundertweaks.views.recyclerview.DescriptionView;
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

    @Override
    protected void init() {
        super.init();

        mGameControl = GameControl.getInstance();
        mGPUFreqExynos = GPUFreqExynos.getInstance();
        mCPUFreq = CPUFreq.getInstance(getActivity());

        addViewPagerFragment(ApplyOnBootFragment.newInstance(this));
        if (GameControl.supported()) {
            addViewPagerFragment(DescriptionFragment.newInstance(getString(R.string.gameControl),getString(R.string.gameControl_Summary)));
        }
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        if (GameControl.supported()) {
            gameControlInit(items);
        }
    }

    private void gameControlInit(List<RecyclerViewItem> items) {
        CardView gameCotrolCard = new CardView(getActivity());
        if(mGameControl.hasVersion())
            gameCotrolCard.setTitle(getString(R.string.gameControl) + " v" + mGameControl.getVersion());
        else
            gameCotrolCard.setTitle(getString(R.string.gameControl));

        if(mGameControl.hasAlwaysOn()){
            SwitchView alwaysOn = new SwitchView();
            alwaysOn.setTitle(getString(R.string.gameControl_always_on));
            alwaysOn.setSummary(getString(R.string.gameControl_always_on_desc));
            alwaysOn.setChecked(mGameControl.isEnabledAlwaysOn());
            alwaysOn.addOnSwitchListener((switchView, isChecked) ->
                    mGameControl.enableAlwaysOn(isChecked, getActivity())
            );
            gameCotrolCard.addItem(alwaysOn);
        }

        if(mCPUFreq.getFreqs() != null) {

            if (mGameControl.hasBIGMax()) {
                SelectView BIGMax = new SelectView();
                BIGMax.setSummary(getString(R.string.gameControl_BIG_max));
                BIGMax.setItems(mCPUFreq.getAdjustedFreq(getActivity()));
                BIGMax.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setBIGMax(mCPUFreq.getFreqs().get(position), getActivity()));

                gameCotrolCard.addItem(BIGMax);

                BIGMax.setItem((mGameControl.getBIGMax() / 1000) + getString(R.string.mhz));
            }

            if (mGameControl.hasBIGMin()) {
                SelectView BIGMin = new SelectView();
                BIGMin.setSummary(getString(R.string.gameControl_BIG_min));
                BIGMin.setItems(mCPUFreq.getAdjustedFreq(getActivity()));
                BIGMin.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setBIGMin(mCPUFreq.getFreqs().get(position), getActivity()));

                gameCotrolCard.addItem(BIGMin);

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

                        gameCotrolCard.addItem(MIDDLEMax);

                        MIDDLEMax.setItem((mGameControl.getMIDDLEMax() / 1000) + getString(R.string.mhz));
                    }

                    if (mGameControl.hasMIDDLEMin()) {
                        SelectView MIDDLEMin = new SelectView();
                        MIDDLEMin.setSummary(getString(R.string.gameControl_MIDDLE_min));
                        MIDDLEMin.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getMidCpu(), getActivity()));
                        MIDDLEMin.setOnItemSelected((selectView, position, item)
                                -> mGameControl.setMIDDLEMin(mCPUFreq.getFreqs(mCPUFreq.getMidCpu()).get(position), getActivity()));

                        gameCotrolCard.addItem(MIDDLEMin);

                        MIDDLEMin.setItem((mGameControl.getMIDDLEMin() / 1000) + getString(R.string.mhz));
                    }
                }

                if (mGameControl.hasLITTLEMax()) {
                    SelectView LITTLEMax = new SelectView();
                    LITTLEMax.setSummary(getString(R.string.gameControl_LITTLE_max));
                    LITTLEMax.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getLITTLECpu(), getActivity()));
                    LITTLEMax.setOnItemSelected((selectView, position, item)
                            -> mGameControl.setLITTLEMax(mCPUFreq.getFreqs(mCPUFreq.getLITTLECpu()).get(position), getActivity()));

                    gameCotrolCard.addItem(LITTLEMax);

                    LITTLEMax.setItem((mGameControl.getLITTLEMax() / 1000) + getString(R.string.mhz));
                }

                if (mGameControl.hasLITTLEMin()) {
                    SelectView LITTLEMin = new SelectView();
                    LITTLEMin.setSummary(getString(R.string.gameControl_LITTLE_min));
                    LITTLEMin.setItems(mCPUFreq.getAdjustedFreq(mCPUFreq.getLITTLECpu(), getActivity()));
                    LITTLEMin.setOnItemSelected((selectView, position, item)
                            -> mGameControl.setLITTLEMin(mCPUFreq.getFreqs(mCPUFreq.getLITTLECpu()).get(position), getActivity()));

                    gameCotrolCard.addItem(LITTLEMin);

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

                gameCotrolCard.addItem(GPUMax);

                GPUMax.setItem((mGameControl.getGPUMax() / 1000) + getString(R.string.mhz));
            }

            if(mGameControl.hasGPUMin()) {
                SelectView GPUMin = new SelectView();
                GPUMin.setSummary(getString(R.string.gameControl_GPU_min));
                GPUMin.setItems(mGPUFreqExynos.getAdjustedFreqs(getActivity()));
                GPUMin.setOnItemSelected((selectView, position, item)
                        -> mGameControl.setGPUMin(mGPUFreqExynos.getAvailableFreqs().get(position), getActivity()));

                gameCotrolCard.addItem(GPUMin);

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

            gameCotrolCard.addItem(minMIFfreq);
        }

        //TODO Use UI for this feature with smart game detecting
        if(mGameControl.hasGamePackages()) {
            GenericSelectView gamePackages = new GenericSelectView();
            gamePackages.setSummary(getString(R.string.gameControl_packages));
            gamePackages.setValue(String.valueOf(mGameControl.getGamePackages()));
            gamePackages.setValueRaw(gamePackages.getValue());
            gamePackages.setOnGenericValueListener((genericSelectView, value)
                    -> mGameControl.setGamePackages(value.replaceAll("\\s+","\n"), getActivity()));

            gameCotrolCard.addItem(gamePackages);
        }

        items.add(gameCotrolCard);
    }
}
