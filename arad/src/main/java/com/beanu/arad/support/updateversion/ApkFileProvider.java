package com.beanu.arad.support.updateversion;

import android.support.v4.content.FileProvider;

/**
 * 适配7.0以上安装apk
 * Created by jrl on 2018/1/20.
 * <p>
 * <provider
 *      android:name="com.beanu.arad.support.updateversion.ApkFileProvider"
 *      android:authorities="${applicationId}.install"
 *      android:exported="false"
 *      android:grantUriPermissions="true">
 *      <meta-data
 *          android:name="android.support.FILE_PROVIDER_PATHS"
 *          android:resource="@xml/apk_path" />
 * </provider>
 */

public class ApkFileProvider extends FileProvider {
}
