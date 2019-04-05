package com.example.a325.fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a325.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {


    @Bind(R.id.tv_cons)
    TextView mtvCons;
    @Bind(R.id.tv_clear)
    TextView mtvClear;
    @Bind(R.id.progressbar)
    ProgressBar mprogressbar;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        ButterKnife.bind(this, view);

        getDiveceSize();

        return view;
    }

    /**
     *
     *
     *
     */
    private void getDiveceSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }

        String occupyText = formatSize(blockSize * (totalBlocks - availableBlocks));
        String availableText = formatSize(blockSize * availableBlocks);

        String text = "已占用" + occupyText + "，剩余" + availableText + "可用";
        mtvCons.setText(text);

        mprogressbar.setMax((int) totalBlocks);
        mprogressbar.setProgress((int) (totalBlocks-availableBlocks));

    }

    private String formatSize(long size) {
        return Formatter.formatFileSize(getActivity(), size);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
