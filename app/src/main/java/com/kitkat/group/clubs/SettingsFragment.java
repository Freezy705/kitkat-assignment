package com.kitkat.group.clubs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Glenn on 17/02/2019.
 */

public class SettingsFragment extends Fragment {

    private StorageReference storageRef;
    private static final String TAG = "SettingsFragment";
    private static final int GALLERY_INTENT = 2;

    public SettingsFragment() {
        // Empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started SettingsFragment");

        View view = inflater.inflate(R.layout.fragment_user_settings, container, false);

        storageRef = FirebaseStorage.getInstance().getReference();

        view.findViewById(R.id.change_profile_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/png");

                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri image = data.getData();
            StorageReference filePath = storageRef.child("member-avatars").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            filePath.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "Profile picture changed.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}