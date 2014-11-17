package com.beanu.arad.crop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.beanu.arad.crop.base.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crop {
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    private Uri mImageCaptureUri;
    private Activity activity;
    private Fragment fragment;
    private ImageView mImageView;
    private int outputX, outputY;
    private int aspectX, aspectY;
    private boolean isCroped;//是否已经给图片赋值
    private boolean enableCrop;//是否需要切图

    public Crop(Context context, int outputX, int outputY, int aspectX, int aspectY) {
        if (context instanceof Activity) {
            activity = (Activity) context;
            this.outputX = outputX;
            this.outputY = outputY;
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            enableCrop = true;
        }
    }

    public Crop(Context context, int outputX, int outputY, int aspectX, int aspectY, boolean enableCrop) {
        if (context instanceof Activity) {
            activity = (Activity) context;
            this.outputX = outputX;
            this.outputY = outputY;
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            this.enableCrop = enableCrop;
        }
    }

    public boolean isCroped() {
        return isCroped;
    }

    public void setImageView(ImageView view) {
        mImageView = view;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void showDialog() {
        final String[] items = new String[]{"打开相机", "从图库选择"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("选择图片");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // pick from camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);
                        if (fragment != null)
                            fragment.startActivityForResult(intent, PICK_FROM_CAMERA);
                        else
                            activity.startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { // pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    if (fragment != null)
                        fragment.startActivityForResult(Intent.createChooser(intent, "Complete action using"),
                                PICK_FROM_FILE);
                    else

                        activity.startActivityForResult(Intent.createChooser(intent, "Complete action using"),
                                PICK_FROM_FILE);
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public int onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return 0;

        if (enableCrop) {
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    doCrop();
                    break;

                case PICK_FROM_FILE:
                    mImageCaptureUri = data.getData();
                    doCrop();
                    break;

                case CROP_FROM_CAMERA:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        mImageView.setImageBitmap(photo);
                        isCroped = true;
                    }
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists())
                        f.delete();
                    break;
            }
        } else {
            //不需要剪切
            switch (requestCode) {
                case PICK_FROM_FILE:
                    mImageCaptureUri = data.getData();
                    String path = getRealPathFromURI(mImageCaptureUri); //from Gallery
                    if (path == null)
                        path = mImageCaptureUri.getPath(); //from File Manager

                    if (path != null) {
                        File f = new File(path);
                        setImage(f);
                    }
                    break;
                case PICK_FROM_CAMERA:
                    File f = new File(mImageCaptureUri.getPath());
                    setImage(f);
                    break;
            }
        }
        return 1;
    }

    private void setImage(File file) {

        int degree = readPictureDegree(file.getAbsolutePath());

        try {
            Bitmap cbitmap = convertBitmap(file);
            Bitmap newbitmap = rotaingImageView(degree, cbitmap);
            mImageView.setImageBitmap(newbitmap);
            isCroped = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);

        if (cursor == null) return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    private Bitmap convertBitmap(File file) throws IOException {
        Bitmap bitmap = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();
        final int REQUIRED_SIZE = 100;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 3;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = scale;
        fis = new FileInputStream(file.getAbsolutePath());
        bitmap = BitmapFactory.decodeStream(fis, null, op);
        fis.close();
        // 保存压缩图片 替换临时图片
        FileOutputStream out = new FileOutputStream(file);
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
            out.flush();
            out.close();
        }
        return bitmap;
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Intent _intent = new Intent(activity, CropImage.class);
            _intent.putExtra("image-path", mImageCaptureUri.getPath());
            _intent.putExtra("scale", true);
            _intent.putExtra("crop", true);
            _intent.putExtra("outputX", outputX);
            _intent.putExtra("outputY", outputY);
            _intent.putExtra("aspectX", aspectX);
            _intent.putExtra("aspectY", aspectY);
            _intent.putExtra("return-data", true);
            if (fragment != null)
                fragment.startActivityForResult(_intent, CROP_FROM_CAMERA);
            else
                activity.startActivityForResult(_intent, CROP_FROM_CAMERA);
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("crop", true);
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                if (fragment != null)
                    fragment.startActivityForResult(i, CROP_FROM_CAMERA);
                else
                    activity.startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = activity.getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = activity.getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(activity.getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (fragment != null)
                            fragment.startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                        else
                            activity.startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            activity.getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }
}
