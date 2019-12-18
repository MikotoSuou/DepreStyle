package com.example.bj_pogi.deprestylefinalfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ControllerClass extends AppCompatActivity {


    //GLOBAL METHODS
    public boolean isNullOrEmpty(String str){
        if(str == null || str.trim().isEmpty() || str.trim().equals("")){
            return true;
        } else {
            return false;
        }
    }

    public String removeFirebaseExceptionMsg(String fbMessage){
        return fbMessage.replace(fbMessage.split(":")[0] + ":", "").trim();

    }


    //START OF ALERTDIALOG
    public void showAlertDialog(Context context, String title, int resId, String msg, Boolean bool, String btnName){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setIcon(resId)
                .setMessage(msg)
                .setCancelable(bool)
                .setPositiveButton(btnName, null)
                .create();
        dialog.show();
    }

    public void showAlertLogoutDialog(Context context){

    }

    public void showAlertDialogWhenNoInternet(Context context){
        AlertDialog mDialog = new AlertDialog.Builder(context)
                .setTitle("Connection error")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setMessage("Cannot connect to server. Please check your internet connection and try again")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .create();
        mDialog.show();
    }


    //START OF INTENT
    public void goToNextAndDestroyPrev(Context context, Class mClass){
        Intent intent = new Intent(context, mClass);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    //START OF TOAST
    public void makeToastMsg(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    //START OF SNACKBAR
    public void showSnackBarShortly(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }


    public void showSnackBarWithDismiss(View view, String message,String actName) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actName, null)
                .show();
    }

    public void showSnackBarWithDestroyActivity(View view, String message,String actName) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actName, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .show();
    }


}
