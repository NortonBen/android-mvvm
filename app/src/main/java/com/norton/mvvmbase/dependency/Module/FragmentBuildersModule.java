package com.norton.mvvmbase.dependency.Module;

/**
 * Created by norton on 08/04/2018.
 */
import com.norton.mvvmbase.ui.category.CategoryFragment;
import com.norton.mvvmbase.ui.home.HomeFragment;
import com.norton.mvvmbase.ui.login.LoginFragment;
import com.norton.mvvmbase.ui.product.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract CategoryFragment contributeCategoryFragment();

    @ContributesAndroidInjector
    abstract ProductFragment contributeProductFragment();
}
