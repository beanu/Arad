package com.beanu.arad.support.updateversion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.beanu.arad.R;


public class UpdateDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.arad_newUpdateAvailable);
        builder.setMessage(getArguments().getString(UpdateChecker.APP_UPDATE_MESSAGE))
                .setPositiveButton(R.string.arad_dialogPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        goToDownload();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.arad_dialogNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    private void goToDownload() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DownloadService.class);
        intent.putExtra(UpdateChecker.APP_UPDATE_URL, getArguments().getString(UpdateChecker.APP_UPDATE_URL));
        getActivity().startService(intent);
    }
}
