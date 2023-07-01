package com.xxmustafacooTR.kernelmanager.fragments.kernel;

import com.xxmustafacooTR.kernelmanager.R;
import com.xxmustafacooTR.kernelmanager.fragments.ApplyOnBootFragment;
import com.xxmustafacooTR.kernelmanager.fragments.DescriptionFragment;
import com.xxmustafacooTR.kernelmanager.fragments.recyclerview.RecyclerViewFragment;
import com.xxmustafacooTR.kernelmanager.utils.kernel.power.Regulator;
import com.xxmustafacooTR.kernelmanager.utils.kernel.power.marginVoltage;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.CardView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.RecyclerViewItem;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.SeekBarView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.SwitchView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.XYGraphView;

import java.util.ArrayList;
import java.util.List;

public class PowerFragment extends RecyclerViewFragment {
    private marginVoltage mVoltagemargin;
    private Regulator mRegulator;

    private XYGraphView mCurLittleVolt;
    private XYGraphView mCurBigVolt;
    private XYGraphView mCurGpuVolt;
    private XYGraphView mCurMifVolt;

    private List<SwitchView> mEnableViews = new ArrayList<>();

    @Override
    protected void init() {
        super.init();

        mVoltagemargin = marginVoltage.getInstance();
        mRegulator = Regulator.getInstance();

        addViewPagerFragment(ApplyOnBootFragment.newInstance(this));
        if (mVoltagemargin.supported()) {
            addViewPagerFragment(DescriptionFragment.newInstance(getString(R.string.marginVoltage),getString(R.string.marginVoltage_Summary)));
        }
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        mEnableViews.clear();

        if(mRegulator.supported()) {
            currentVoltageInit(items);
        }

        if (mVoltagemargin.supported()) {
            marginVoltageInit(items);
        }
    }

    private void currentVoltageInit(List<RecyclerViewItem> items) {
        final CardView currentVoltage = new CardView(getActivity());
        currentVoltage.setTitle(getString(R.string.voltages));

        if(mRegulator.hasBIGbuck()) {
            mCurBigVolt = new XYGraphView();
            mCurBigVolt.setTitle(getString(R.string.cpucl1_voltage));
            currentVoltage.addItem(mCurBigVolt);
        }

        if(mRegulator.hasLITTLEbuck()) {
            mCurLittleVolt = new XYGraphView();
            mCurLittleVolt.setTitle(getString(R.string.cpucl0_voltage));
            currentVoltage.addItem(mCurLittleVolt);
        }

        if(mRegulator.hasGPUbuck()) {
            mCurGpuVolt = new XYGraphView();
            mCurGpuVolt.setTitle(getString(R.string.gpu_volt));
            currentVoltage.addItem(mCurGpuVolt);
        }

        if(mRegulator.hasMIFbuck()) {
            mCurMifVolt = new XYGraphView();
            mCurMifVolt.setTitle(getString(R.string.mif_voltage));
            currentVoltage.addItem(mCurMifVolt);
        }

        items.add(currentVoltage);
    }

    private void marginVoltageInit(List<RecyclerViewItem> items) {
        final CardView marginVoltage = new CardView(getActivity());
        marginVoltage.setTitle(getString(R.string.marginVoltage));

        if (mVoltagemargin.hasBIGPercentMargin()) {
            SeekBarView BigPercentMargin = new SeekBarView();
            BigPercentMargin.setTitle(getString(R.string.marginVoltage_BIG_percent));
            BigPercentMargin.setMax(100);
            BigPercentMargin.setMin(-100);
            BigPercentMargin.setProgress(mVoltagemargin.getBIGPercentMargin()+100);
            BigPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setBIGPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(BigPercentMargin);
        }

        if (mVoltagemargin.hasMIDPercentMargin()) {
            SeekBarView MIDPercentMargin = new SeekBarView();
            MIDPercentMargin.setTitle(getString(R.string.marginVoltage_MID_percent));
            MIDPercentMargin.setMax(100);
            MIDPercentMargin.setMin(-100);
            MIDPercentMargin.setProgress(mVoltagemargin.getMIDPercentMargin()+100);
            MIDPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setMIDPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(MIDPercentMargin);
        }

        if (mVoltagemargin.hasLITPercentMargin()) {
            SeekBarView LITPercentMargin = new SeekBarView();
            LITPercentMargin.setTitle(getString(R.string.marginVoltage_LIT_percent));
            LITPercentMargin.setMax(100);
            LITPercentMargin.setMin(-100);
            LITPercentMargin.setProgress(mVoltagemargin.getLITPercentMargin()+100);
            LITPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setLITPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(LITPercentMargin);
        }

        if (mVoltagemargin.hasG3DPercentMargin()) {
            SeekBarView G3DPercentMargin = new SeekBarView();
            G3DPercentMargin.setTitle(getString(R.string.marginVoltage_G3D_percent));
            G3DPercentMargin.setMax(100);
            G3DPercentMargin.setMin(-100);
            G3DPercentMargin.setProgress(mVoltagemargin.getG3DPercentMargin()+100);
            G3DPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setG3DPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(G3DPercentMargin);
        }

        if (mVoltagemargin.hasMIFPercentMargin()) {
            SeekBarView MIFPercentMargin = new SeekBarView();
            MIFPercentMargin.setTitle(getString(R.string.marginVoltage_MIF_percent));
            MIFPercentMargin.setMax(100);
            MIFPercentMargin.setMin(-100);
            MIFPercentMargin.setProgress(mVoltagemargin.getMIFPercentMargin()+100);
            MIFPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setMIFPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(MIFPercentMargin);
        }

        if (mVoltagemargin.hasMFCPercentMargin()) {
            SeekBarView MFCPercentMargin = new SeekBarView();
            MFCPercentMargin.setTitle(getString(R.string.marginVoltage_MFC_percent));
            MFCPercentMargin.setMax(100);
            MFCPercentMargin.setMin(-100);
            MFCPercentMargin.setProgress(mVoltagemargin.getMFCPercentMargin()+100);
            MFCPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setMFCPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(MFCPercentMargin);
        }

        if (mVoltagemargin.hasNPUPercentMargin()) {
            SeekBarView NPUPercentMargin = new SeekBarView();
            NPUPercentMargin.setTitle(getString(R.string.marginVoltage_NPU_percent));
            NPUPercentMargin.setMax(100);
            NPUPercentMargin.setMin(-100);
            NPUPercentMargin.setProgress(mVoltagemargin.getNPUPercentMargin()+100);
            NPUPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setNPUPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(NPUPercentMargin);
        }

        if (mVoltagemargin.hasAUDPercentMargin()) {
            SeekBarView AUDPercentMargin = new SeekBarView();
            AUDPercentMargin.setTitle(getString(R.string.marginVoltage_AUD_percent));
            AUDPercentMargin.setMax(100);
            AUDPercentMargin.setMin(-100);
            AUDPercentMargin.setProgress(mVoltagemargin.getAUDPercentMargin()+100);
            AUDPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setAUDPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(AUDPercentMargin);
        }

        if (mVoltagemargin.hasCAMPercentMargin()) {
            SeekBarView CAMPercentMargin = new SeekBarView();
            CAMPercentMargin.setTitle(getString(R.string.marginVoltage_CAM_percent));
            CAMPercentMargin.setMax(100);
            CAMPercentMargin.setMin(-100);
            CAMPercentMargin.setProgress(mVoltagemargin.getCAMPercentMargin()+100);
            CAMPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setCAMPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(CAMPercentMargin);
        }

        if (mVoltagemargin.hasCPPercentMargin()) {
            SeekBarView CPPercentMargin = new SeekBarView();
            CPPercentMargin.setTitle(getString(R.string.marginVoltage_CP_percent));
            CPPercentMargin.setMax(100);
            CPPercentMargin.setMin(-100);
            CPPercentMargin.setProgress(mVoltagemargin.getCPPercentMargin()+100);
            CPPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setCPPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(CPPercentMargin);
        }

        if (mVoltagemargin.hasDISPPercentMargin()) {
            SeekBarView DISPPercentMargin = new SeekBarView();
            DISPPercentMargin.setTitle(getString(R.string.marginVoltage_DISP_percent));
            DISPPercentMargin.setMax(100);
            DISPPercentMargin.setMin(-100);
            DISPPercentMargin.setProgress(mVoltagemargin.getDISPPercentMargin()+100);
            DISPPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setDISPPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(DISPPercentMargin);
        }

        if (mVoltagemargin.hasFSYS0PercentMargin()) {
            SeekBarView FSYS0PercentMargin = new SeekBarView();
            FSYS0PercentMargin.setTitle(getString(R.string.marginVoltage_FSYS0_percent));
            FSYS0PercentMargin.setMax(100);
            FSYS0PercentMargin.setMin(-100);
            FSYS0PercentMargin.setProgress(mVoltagemargin.getFSYS0PercentMargin()+100);
            FSYS0PercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setFSYS0PercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(FSYS0PercentMargin);
        }

        if (mVoltagemargin.hasINTPercentMargin()) {
            SeekBarView INTPercentMargin = new SeekBarView();
            INTPercentMargin.setTitle(getString(R.string.marginVoltage_INT_percent));
            INTPercentMargin.setMax(100);
            INTPercentMargin.setMin(-100);
            INTPercentMargin.setProgress(mVoltagemargin.getINTPercentMargin()+100);
            INTPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setINTPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(INTPercentMargin);
        }

        if (mVoltagemargin.hasINTCAMPercentMargin()) {
            SeekBarView INTCAMPercentMargin = new SeekBarView();
            INTCAMPercentMargin.setTitle(getString(R.string.marginVoltage_INTCAM_percent));
            INTCAMPercentMargin.setMax(100);
            INTCAMPercentMargin.setMin(-100);
            INTCAMPercentMargin.setProgress(mVoltagemargin.getINTCAMPercentMargin()+100);
            INTCAMPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setINTCAMPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(INTCAMPercentMargin);
        }

        if (mVoltagemargin.hasIVAPercentMargin()) {
            SeekBarView IVAPercentMargin = new SeekBarView();
            IVAPercentMargin.setTitle(getString(R.string.marginVoltage_IVA_percent));
            IVAPercentMargin.setMax(100);
            IVAPercentMargin.setMin(-100);
            IVAPercentMargin.setProgress(mVoltagemargin.getIVAPercentMargin()+100);
            IVAPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setIVAPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(IVAPercentMargin);
        }

        if (mVoltagemargin.hasSCOREPercentMargin()) {
            SeekBarView SCOREPercentMargin = new SeekBarView();
            SCOREPercentMargin.setTitle(getString(R.string.marginVoltage_SCORE_percent));
            SCOREPercentMargin.setMax(100);
            SCOREPercentMargin.setMin(-100);
            SCOREPercentMargin.setProgress(mVoltagemargin.getSCOREPercentMargin()+100);
            SCOREPercentMargin.setOnSeekBarListener(new SeekBarView.OnSeekBarListener() {
                @Override
                public void onStop(SeekBarView seekBarView, int position, String value) {
                    mVoltagemargin.setSCOREPercentMargin((position-100), getActivity());
                }

                @Override
                public void onMove(SeekBarView seekBarView, int position, String value) {
                }
            });

            marginVoltage.addItem(SCOREPercentMargin);
        }

        items.add(marginVoltage);
    }

    @Override
    protected void refresh() {
        super.refresh();

        if (mCurBigVolt != null) {
            int volt = mRegulator.getBIGCur();
            float maxVolt = mRegulator.getBIGMax();
            mCurBigVolt.setText(volt / 1000 + getString(R.string.mv));
            float per = (float) volt / maxVolt * 100f;
            mCurBigVolt.addPercentage(Math.round(per > 100 ? 100 : per < 0 ? 0 : per));
        }

        if (mCurLittleVolt != null) {
            int volt = mRegulator.getLITTLECur();
            float maxVolt = mRegulator.getLITTLEMax();
            mCurLittleVolt.setText(volt / 1000 + getString(R.string.mv));
            float per = (float) volt / maxVolt * 100f;
            mCurLittleVolt.addPercentage(Math.round(per > 100 ? 100 : per < 0 ? 0 : per));
        }

        if (mCurGpuVolt != null) {
            int volt = mRegulator.getGPUCur();
            float maxVolt = mRegulator.getGPUMax();
            mCurGpuVolt.setText(volt / 1000 + getString(R.string.mv));
            float per = (float) volt / maxVolt * 100f;
            mCurGpuVolt.addPercentage(Math.round(per > 100 ? 100 : per < 0 ? 0 : per));
        }

        if (mCurMifVolt != null) {
            int volt = mRegulator.getMIFCur();
            float maxVolt = mRegulator.getMIFMax();
            mCurMifVolt.setText(volt / 1000 + getString(R.string.mv));
            float per = (float) volt / maxVolt * 100f;
            mCurMifVolt.addPercentage(Math.round(per > 100 ? 100 : per < 0 ? 0 : per));
        }
    }
}
