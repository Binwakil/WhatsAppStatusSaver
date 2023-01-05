package com.wakili.whatsappstatusaver.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wakili.whatsappstatusaver.Activity.MainActivity;
import com.wakili.whatsappstatusaver.Activity.ShowItem;
import com.wakili.whatsappstatusaver.Adapter.ViewAdapter;
import com.wakili.whatsappstatusaver.BuildConfig;
import com.wakili.whatsappstatusaver.Interface.OnClick;
import com.wakili.whatsappstatusaver.R;
import com.wakili.whatsappstatusaver.Util.Constant;
import com.wakili.whatsappstatusaver.Util.Method;
import com.google.android.material.textview.MaterialTextView;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ImageFragment extends Fragment {

    private Method method;
    private OnClick onClick;
    private String root;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ViewAdapter viewAdapter;
    private MaterialTextView textView_noData;
    private LayoutAnimationController animation;
    public Uri statussting;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

       // Toast.makeText(getActivity(), statussting, Toast.LENGTH_SHORT).show();
        onClick = new OnClick() {
            @Override
            public void position(int position, String type) {
                startActivity(new Intent(getActivity(), ShowItem.class)
                        .putExtra("position", position)
                        .putExtra("type", type));
            }
        };
        method = new Method(getActivity(), onClick);

        progressBar = view.findViewById(R.id.progressbar_fragment);
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
        textView_noData = view.findViewById(R.id.textView_noData_fragment);
        textView_noData.setVisibility(View.GONE);

        int resId = R.anim.layout_animation_from_bottom;
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swiperefreshid);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  new DataImage().execute();
                  android11call();
                pullToRefresh.setRefreshing(false);
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        new DataImage().execute();
        android11call();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    public class DataImage extends AsyncTask<String, String, String> {

        boolean isVlaue = false;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            //Toast.makeText(getActivity(), "Four Here", Toast.LENGTH_SHORT).show();

            try {
                switch (method.url_type()) {
                    case "w": {
                        root = Environment.getExternalStorageDirectory() + BuildConfig.url;
                        File file = new File(root);
                        File file1 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "WhatsApp/Media/.Statuses");
                        File file2 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
                        if (file.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file);
                        } else if (file1.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file1);
                        }
                        else if (file2.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file2);
                        }
                        else
                        {
                            Constant.imageArray = getListFiles(file);
                            Constant.imageArray.addAll(getListFiles(file1));
                            Constant.imageArray.addAll(getListFiles(file2));
                        }

                        break;
                    }
                    case "wb": {
                        root = Environment.getExternalStorageDirectory() + BuildConfig.url_second;
                        File file = new File(root);
                        File file1 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "WhatsApp/Media/.Statuses");
                        File file2 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

                        if (file.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file);
                        } else if (file1.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file1);
                        }
                        else if (file2.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file2);
                        }
                        else
                        {
                            Constant.imageArray = getListFiles(file);
                            Constant.imageArray.addAll(getListFiles(file1));
                            Constant.imageArray.addAll(getListFiles(file2));
                        }
                        break;
                    }
                    case "wball": {
                        root = Environment.getExternalStorageDirectory() + BuildConfig.url;
                        File file = new File(root);
                        Constant.imageArray.clear();
                        Constant.imageArray = getListFiles(file);
                        root = Environment.getExternalStorageDirectory() + BuildConfig.url_second;
                        File file_second = new File(root);
                        Constant.imageArray.addAll(getListFiles(file_second));
                        File file1 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "WhatsApp/Media/.Statuses");
                        File file2 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
                        if (file.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file);
                        } else if (file1.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file1);
                        }
                        else if (file2.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file2);
                        }
                        else {
                            Constant.imageArray = getListFiles(file);
                            Constant.imageArray.addAll(getListFiles(file1));
                            Constant.imageArray.addAll(getListFiles(file2));
                        }
                        break;
                    }
                    default:
                    {
                        root = Environment.getExternalStorageDirectory() + BuildConfig.url;
                        File file = new File(root);
                        File file1 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "WhatsApp/Media/.Statuses");
                        File file2 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

                        if (file.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file);
                        } else if (file1.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file1);
                        }
                        else if (file2.exists()) {
                            Constant.imageArray.clear();
                            Constant.imageArray = getListFiles(file2);
                        }
                        else
                        {
                            Constant.imageArray = getListFiles(file);
                            Constant.imageArray.addAll(getListFiles(file1));
                            Constant.imageArray.addAll(getListFiles(file2));
                        }

                    }
                }
            } catch (Exception e) {
                isVlaue = true;
                Log.d("error", e.toString());
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {

            if (!isVlaue) {
                if (Constant.imageArray.size() == 0) {
                    textView_noData.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    textView_noData.setVisibility(View.GONE);
                    viewAdapter = new ViewAdapter(getActivity(), Constant.imageArray, "image", method);
                    recyclerView.setAdapter(viewAdapter);
                    recyclerView.setLayoutAnimation(animation);
                }
            } else {
                textView_noData.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);

            super.onPostExecute(s);
        }
    }
    private void android11call() {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            try {
                //Toast.makeText(getActivity(), "Android 11", Toast.LENGTH_SHORT).show();
                String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
                File targetDirector = new File(targetPath);
                if (targetDirector.exists()) {
                    Constant.imageArray.clear();
                    Constant.imageArray = getListFiles(targetDirector);
                }

                //Toast.makeText(getActivity(), "File 1  "+targetDirector.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                textView_noData.setVisibility(View.GONE);
                viewAdapter = new ViewAdapter(getActivity(), Constant.videoArray, "video", method);
                recyclerView.setAdapter(viewAdapter);
                recyclerView.setLayoutAnimation(animation);

                progressBar.setVisibility(View.GONE);
            }
            catch (Exception ex)
            {

            }



        }
    }
    private List<File> getListFiles(File parentDir) {
        List<File> inFiles = new ArrayList<>();
        try {
            File[] statusFiles;
            statusFiles = parentDir.listFiles();
            //files.clear();
            Arrays.sort(statusFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);

            if (statusFiles != null) {
                for (File file : statusFiles) {
                    Log.e("check", file.getName());
                    if (file.getName().endsWith(".jpg") ||
                            file.getName().endsWith(".gif") ||
                            file.getName().endsWith(".png")) {
                        if (!inFiles.contains(file))
                            inFiles.add(file);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }
        return inFiles;
    }

}
