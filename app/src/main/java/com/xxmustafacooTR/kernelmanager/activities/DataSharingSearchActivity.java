/*
 * Copyright (C) 2017 Willi Ye <williye97@gmail.com>
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

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.xxmustafacooTR.kernelmanager.R;
import com.xxmustafacooTR.kernelmanager.fragments.recyclerview.RecyclerViewFragment;
import com.xxmustafacooTR.kernelmanager.utils.Utils;
import com.xxmustafacooTR.kernelmanager.utils.server.DeviceInfo;
import com.xxmustafacooTR.kernelmanager.utils.server.ServerSearchDevice;
import com.xxmustafacooTR.kernelmanager.views.dialog.Dialog;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.ButtonView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.RecyclerViewItem;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.datasharing.DataSharingDeviceView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.datasharing.DataSharingPageView;

import java.util.List;

/**
 * Created by willi on 16.09.17.
 */

public class DataSharingSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragments);

        initToolBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                getFragment(), "create_fragment").commit();
    }

    private Fragment getFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("create_fragment");
        if (fragment == null) {
            fragment = new DataSharingSearchFragment();
        }
        return fragment;
    }

    public static class DataSharingSearchFragment extends RecyclerViewFragment {

        private ServerSearchDevice mServerSearchDevice;
        private List<String> mBoards;
        private String mSelection;

        @Override
        protected boolean showViewPager() {
            return false;
        }

        @Override
        protected void postInit() {
            super.postInit();

            ((BaseActivity) getActivity()).getSupportActionBar()
                    .setTitle(mSelection == null ? getString(R.string.show_all) : mSelection);
            if (mServerSearchDevice == null) {
                showProgress();
                mServerSearchDevice = new ServerSearchDevice("https://www.grarak.com", getActivity());
                loadDevices(1, null);
            }
        }

        private void loadDevices(final int page, final String filter) {
            showProgress();
            clearItems();

            ButtonView filterBtn = new ButtonView();
            filterBtn.setText(getString(R.string.filter));
            filterBtn.setFullSpan(true);
            filterBtn.setOnClickListener(view -> {
                if (mBoards != null) {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle(getString(R.string.board))
                            .setItems(mBoards.toArray(new String[mBoards.size()]),
                                    (dialogInterface, i) -> {
                                        loadDevices(1, i == 0 ? null : mBoards.get(i));
                                        ((BaseActivity) getActivity()).getSupportActionBar()
                                                .setTitle(mSelection = mBoards.get(i));
                                    }).show();
                }
            });
            addItem(filterBtn);

            final DataSharingPageView pageView = new DataSharingPageView();
            pageView.setPage(page);
            pageView.setDataSharingPageListener(new DataSharingPageView.DataSharingPageListener() {
                @Override
                public void onPrevious() {
                    loadDevices(page - 1, filter);
                }

                @Override
                public void onNext() {
                    loadDevices(page + 1, filter);
                }
            });
            addItem(pageView);

            if (page > 1) {
                mServerSearchDevice.getDevices(new ServerSearchDevice.DeviceSearchListener() {
                    @Override
                    public void onDevicesResult(List<DeviceInfo> devices, int page) {
                        pageView.showPrevious(true);
                    }

                    @Override
                    public void onDevicesFailure() {
                        pageView.showPrevious(false);
                    }
                }, page - 1, filter);
            }
            mServerSearchDevice.getDevices(new ServerSearchDevice.DeviceSearchListener() {
                @Override
                public void onDevicesResult(List<DeviceInfo> devices, int page) {
                    for (int i = 0; i < devices.size(); i++) {
                        addItem(new DataSharingDeviceView(devices.get(i), (page - 1) * 10 + 1 + i));
                    }
                    hideProgress();
                }

                @Override
                public void onDevicesFailure() {
                    hideProgress();
                    failure(getString(R.string.failed_devices));
                }
            }, page, filter);
            mServerSearchDevice.getDevices(new ServerSearchDevice.DeviceSearchListener() {
                @Override
                public void onDevicesResult(List<DeviceInfo> devices, int page) {
                    pageView.showNext(true);
                }

                @Override
                public void onDevicesFailure() {
                    pageView.showNext(false);
                }
            }, page + 1, filter);
            mServerSearchDevice.getBoards(new ServerSearchDevice.BoardSearchListener() {
                @Override
                public void onBoardResult(List<String> boards) {
                    if (isAdded()) {
                        boards.add(0, getString(R.string.show_all));
                        mBoards = boards;
                    }
                }

                @Override
                public void onBoardFailure() {
                    if (isAdded()) {
                        failure(getString(R.string.failed_board));
                    }
                }
            });
        }

        @Override
        protected void addItems(List<RecyclerViewItem> items) {
        }

        private void failure(String message) {
            Utils.toast(message, getActivity());
            hideProgress();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mServerSearchDevice != null) {
                mServerSearchDevice.cancel();
                mServerSearchDevice = null;
            }
        }
    }

}
