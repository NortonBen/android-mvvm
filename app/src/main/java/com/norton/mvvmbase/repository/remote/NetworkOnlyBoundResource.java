package com.norton.mvvmbase.repository.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.norton.mvvmbase.AppExecutors;
import com.norton.mvvmbase.utils.Objects;

import timber.log.Timber;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 */
public abstract class NetworkOnlyBoundResource<ResultType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkOnlyBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue((Resource<ResultType>) Resource.loading(null));
        fetchFromNetwork();
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<ResultType>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() -> setValue(Resource.success(processResponse(response), response.code)));
                });
            } else {
                onFetchFailed();
                setValue(Resource.error(response.errorMessage, processResponse(response), response.code));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected ResultType processResponse(ApiResponse<ResultType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull ResultType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<ResultType>> createCall();
}