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
package com.xxmustafacooTR.kernelmanager.fragments.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.Menu;

import com.xxmustafacooTR.kernelmanager.R;
import com.xxmustafacooTR.kernelmanager.activities.EditorActivity;
import com.xxmustafacooTR.kernelmanager.fragments.SwitcherFragment;
import com.xxmustafacooTR.kernelmanager.fragments.recyclerview.RecyclerViewFragment;
import com.xxmustafacooTR.kernelmanager.utils.AppSettings;
import com.xxmustafacooTR.kernelmanager.utils.Utils;
import com.xxmustafacooTR.kernelmanager.utils.ViewUtils;
import com.xxmustafacooTR.kernelmanager.utils.root.RootUtils;
import com.xxmustafacooTR.kernelmanager.utils.tools.Initd;
import com.xxmustafacooTR.kernelmanager.views.dialog.Dialog;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.CardView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.DescriptionView;
import com.xxmustafacooTR.kernelmanager.views.recyclerview.RecyclerViewItem;

import java.util.List;

/**
 * Created by willi on 16.07.16.
 */
public class InitdFragment extends RecyclerViewFragment {

    private static String sysblock;

    static {
        try {
            sysblock = RootUtils.isSAR() ? "/" : "/system";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Dialog mExecuteDialog;
    private Dialog mResultDialog;
    private Dialog mDeleteDialog;
    private boolean mShowCreateNameDialog;

    private String mCreateName;

    private String mEditInitd;

    @Override
    protected Drawable getTopFabDrawable() {
        Drawable drawable = DrawableCompat.wrap(
                ContextCompat.getDrawable(getActivity(), R.drawable.ic_add));
        DrawableCompat.setTint(drawable, Color.WHITE);
        return drawable;
    }

    @Override
    protected boolean showTopFab() {
        return true;
    }

    @Override
    protected void init() {
        super.init();

        addViewPagerFragment(SwitcherFragment.newInstance(
                getString(R.string.emulate_initd),
                getString(R.string.emulate_initd_summary),
                AppSettings.isInitdOnBoot(getActivity()),
                (compoundButton, b) -> AppSettings.saveInitdOnBoot(b, getActivity())));

        if (mExecuteDialog != null) {
            mExecuteDialog.show();
        }
        if (mResultDialog != null) {
            mResultDialog.show();
        }
        if (mDeleteDialog != null) {
            mDeleteDialog.show();
        }
        if (mShowCreateNameDialog) {
            showCreateDialog();
        }
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        load(items);
    }

    private void reload() {
        getHandler().postDelayed(() -> {
            clearItems();
            reload(new ReloadHandler<>());
        }, 250);
    }

    @Override
    protected void load(List<RecyclerViewItem> items) {
        super.load(items);

        for (final String initd : Initd.list()) {
            CardView cardView = new CardView(getActivity());
            cardView.setOnMenuListener((cardView1, popupMenu) -> {
                Menu menu = popupMenu.getMenu();
                menu.add(Menu.NONE, 0, Menu.NONE, getString(R.string.edit));
                menu.add(Menu.NONE, 1, Menu.NONE, getString(R.string.delete));

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case 0:
                            mEditInitd = initd;
                            Intent intent = new Intent(getActivity(), EditorActivity.class);
                            intent.putExtra(EditorActivity.TITLE_INTENT, initd);
                            intent.putExtra(EditorActivity.TEXT_INTENT, Initd.read(initd));
                            startActivityForResult(intent, 0);
                            break;
                        case 1:
                            mDeleteDialog = ViewUtils.dialogBuilder(getString(R.string.sure_question),
                                    (dialogInterface, i) -> {
                                    },
                                    (dialogInterface, i) -> {
                                        Initd.delete(initd);
                                        reload();
                                    },
                                    dialogInterface -> mDeleteDialog = null, getActivity());
                            mDeleteDialog.show();
                            break;
                    }
                    return false;
                });
            });

            DescriptionView descriptionView = new DescriptionView();
            descriptionView.setSummary(initd);
            descriptionView.setOnItemClickListener(item -> {
                mExecuteDialog = ViewUtils.dialogBuilder(getString(R.string.exceute_question, initd),
                        (dialogInterface, i) -> {
                        },
                        (dialogInterface, i) -> execute(initd),
                        dialogInterface -> mExecuteDialog = null,
                        getActivity());
                mExecuteDialog.show();
            });

            cardView.addItem(descriptionView);
            items.add(cardView);
        }
    }

    private void execute(final String initd) {
        showDialog(new ExecuteTask(getActivity(), initd));
    }

    private static class ExecuteTask extends DialogLoadHandler<InitdFragment> {
        private String mInitd;
        private String mResult;

        private ExecuteTask(Context context, String initd) {
            super(null, context.getString(R.string.executing));
            mInitd = initd;
        }

        @Override
        public Void doInBackground(InitdFragment fragment) {
            mResult = Initd.execute(mInitd);
            return null;
        }

        @Override
        public void onPostExecute(InitdFragment fragment, Void aVoid) {
            super.onPostExecute(fragment, aVoid);

            if (mResult != null && !mResult.isEmpty()) {
                fragment.mResultDialog = ViewUtils.dialogBuilder(mResult,
                        null,
                        null,
                        dialogInterface -> fragment.mResultDialog = null,
                        fragment.getActivity()).setTitle(
                        fragment.getString(R.string.result));
                fragment.mResultDialog.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;
        if (requestCode == 0) {
            Initd.write(mEditInitd, data.getCharSequenceExtra(EditorActivity.TEXT_INTENT).toString());
            reload();
        } else if (requestCode == 1) {
            Initd.write(mCreateName, data.getCharSequenceExtra(EditorActivity.TEXT_INTENT).toString());
            mCreateName = null;
            reload();
        }
    }

    @Override
    protected void onTopFabClick() {
        super.onTopFabClick();

        showCreateDialog();
    }

    private void showCreateDialog() {
        mShowCreateNameDialog = true;
        ViewUtils.dialogEditText(null,
                (dialogInterface, i) -> {
                },
                text -> {
                    if (text.isEmpty()) {
                        Utils.toast(R.string.name_empty, getActivity());
                        return;
                    }

                    if (Initd.list().indexOf(text) > -1) {
                        Utils.toast(getString(R.string.already_exists, text), getActivity());
                        return;
                    }

                    mCreateName = text;
                    Intent intent = new Intent(getActivity(), EditorActivity.class);
                    intent.putExtra(EditorActivity.TITLE_INTENT, mCreateName);
                    intent.putExtra(EditorActivity.TEXT_INTENT, "#!/system/bin/sh\n\n");
                    startActivityForResult(intent, 1);
                }, getActivity()).setTitle(getString(R.string.name))
                .setOnDismissListener(
                        dialogInterface -> mShowCreateNameDialog = false).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RootUtils.mount(false, sysblock);
    }
}
