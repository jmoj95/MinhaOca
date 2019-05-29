package com.example.minhaoca.service;

import com.example.minhaoca.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseService {

    private DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewUser(String uid, String username, String phone) {
        User user = new User(username, phone);
        DatabaseReference databaseReference = this.getDatabaseReference();
        databaseReference.child("users").child(uid).setValue(user);
    }

    public DatabaseReference getCurrentUser() {
        FirebaseAuthService firebaseAuthService = new FirebaseAuthService();
        String uid = firebaseAuthService.getCurrentUser().getUid();
        DatabaseReference databaseReference = this.getDatabaseReference();
        return databaseReference.child("users").child(uid);
    }

}
