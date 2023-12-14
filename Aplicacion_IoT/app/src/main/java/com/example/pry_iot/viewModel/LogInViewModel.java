package com.example.pry_iot.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pry_iot.repository.UserRepository;
import com.example.pry_iot.response.UserResponse;

public class LogInViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<UserResponse> userResponseLiveData = new MutableLiveData<>();

    public LogInViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application.getApplicationContext());
    }

    public void loginUser(String email, String password) {
        userRepository.getUser(email, password, userResponseLiveData);
    }

    public LiveData<UserResponse> getUserResponseLiveData() {
        return userResponseLiveData;
    }
}




