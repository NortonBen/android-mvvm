package com.norton.mvvmbase.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.norton.mvvmbase.R;
import com.norton.mvvmbase.binding.FragmentDataBindingComponent;
import com.norton.mvvmbase.databinding.LoginFragmentBinding;
import com.norton.mvvmbase.dependency.Injectable;
import com.norton.mvvmbase.repository.remote.Resource;
import com.norton.mvvmbase.repository.remote.Status;
import com.norton.mvvmbase.repository.remote.data.LoginAPI.Login;
import com.norton.mvvmbase.utils.AutoClearedValue;

import android.databinding.DataBindingComponent;

import javax.inject.Inject;

public class LoginFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<LoginFragmentBinding> binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoginFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        loginViewModel.getResult().observe(this, loginResource -> {
            if (loginResource.code == 401 || loginResource.code == 400) {
                binding.get().loginError.setText("Email hoặc mật khẩu của bạn không đúng!");
                binding.get().setResource(Resource.success(null, 200));
            } else {
                binding.get().setResource(loginResource);
                binding.get().loginError.setText("");
            }
            if (loginResource.status == Status.LOADING) {
                binding.get().loginPassword.setEnabled(false);
                binding.get().loginUserName.setEnabled(false);
            } else {
                binding.get().loginPassword.setEnabled(true);
                binding.get().loginUserName.setEnabled(true);
            }
        });
        binding.get().setLogin(new Login("Admin", "Abcd1234", true));
        binding.get().loginBtn.setOnClickListener(v -> {
            Login login = new Login(
                    binding.get().loginUserName.getText().toString(),
                    binding.get().loginPassword.getText().toString(),
                    true
            );
            if (!validate(login)) {
                return;
            }
            loginViewModel.login(login);
        });
    }

    private boolean validate(Login login) {
        if (login.UserName.isEmpty()) {
            binding.get().loginUserName.setError("Bạn chưa nhập Email");
            return  false;
        }
        if (login.Password.isEmpty()) {
            binding.get().loginPassword.setError("Bạn chưa nhập Mật Khẩu");
            return false;
        }
        return true;
    }
}
