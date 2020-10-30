package ar.com.fennoma.paymentezsdk.services;

import android.os.AsyncTask;

import ar.com.fennoma.paymentezsdk.exceptions.PmzException;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;

public class BaseTask<T> extends AsyncTask<Void, Void, T> {

    public interface IServiceCaller<T> {
        T callService() throws PmzException;
    }

    private final API.ServiceCallback<T> callback;
    private final IServiceCaller<T> serviceCaller;

    private boolean running = false;
    private PmzException exception;
    public Boolean processedError;

    public BaseTask(API.ServiceCallback<T> callback, IServiceCaller<T> serviceCaller) {
        this.callback = callback;
        this.serviceCaller = serviceCaller;
        processedError = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        running = true;
    }

    @Override
    protected T doInBackground(Void... voids) {
        try {
            return serviceCaller.callService();
        }  catch (PmzException e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(T response) {
        running = false;
        super.onPostExecute(response);
        if(response == null && exception == null) {
            callback.onFailure();
        } else if(exception != null) {
            PmzErrorMessage error = PmzErrorMessage.getError(exception.getErrorCode());
            if(error.isInvalidSession()) {
                callback.sessionExpired();
            } else {
                callback.onError(error);
            }
        } else {
            callback.onSuccess(response);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        this.running = false;
    }

    public boolean isRunning(){
        return running;
    }

}
