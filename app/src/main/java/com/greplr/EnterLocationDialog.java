package com.greplr;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by raghav on 20/06/15.
 */
public class EnterLocationDialog extends DialogFragment {

    private Button buttonDone;


    private EditText editText;
    String location;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialogbox_take_location, null);

        buttonDone = (Button)dialoglayout.findViewById(R.id.button_done);

        editText = (EditText)dialoglayout.findViewById(R.id.dialog_edittext);


        buttonDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                location = editText.getText().toString();
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialoglayout)
                .setTitle("Enter Location")
                .setCancelable(false)
                .setInverseBackgroundForced(true);

        AlertDialog dialog = builder.create();

        return dialog;
    }

}
