package com.arleckk.edcobranza.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.global.Utilities;
import com.arleckk.edcobranza.task.UploadPhotoTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by arleckk on 7/15/16.
 */
public class TakePhotoFragment extends Fragment {

    private View view;
    private ImageView mImageView;
    private FloatingActionButton fab;
    private ProgressDialog mProgress;
    File destination;
    ByteArrayOutputStream bytes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_camera, container, false);
        }

        mImageView = (ImageView) view.findViewById(R.id.photo);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        GestorActivity.onPhotoTaken = new GestorActivity.OnPhotoTaken() {
            @Override
            public void onPhotoTaken(Object object) {
                final Bitmap bitmap = (Bitmap) object;
                mImageView.setImageBitmap(bitmap);
                destination = new File(Environment.getExternalStorageDirectory(),"PRUEBA_FONACOT"+".jpg");
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("camera_debug","destination path: "+destination.getAbsolutePath());
                        new UploadPhotoTask(getContext(),mProgress).execute(bitmap);
//                        new UploadFileTask(getContext(),mProgress).execute(destination);
                    }
                });

            }
        };

//        GestorActivity.onPhotoTaken = new GestorActivity.OnPhotoTaken() {
//            @Override
//            public void onPhotoTaken(Object object) {
//                Bitmap bitmap = (Bitmap) object;
//                mImageView.setImageBitmap(bitmap);
//                bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bytes);
//                byte[] bitmapdata = bytes.toByteArray();
//                destination = new File(Environment.getExternalStorageDirectory(),"FONACOT"+".jpg");
//                try {
//                    FileOutputStream fos = new FileOutputStream(destination);
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                fab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.v("camera_debug","destination path: "+destination.getAbsolutePath());
//                        new UploadFileTask(getContext(),mProgress).execute(destination);
//                    }
//                });
//            }
//        };

        return view;
    }

}
