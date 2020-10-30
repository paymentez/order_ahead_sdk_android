package ar.com.fennoma.paymentezsdk.services;

import java.util.List;

import ar.com.fennoma.paymentezsdk.exceptions.PmzException;
import ar.com.fennoma.paymentezsdk.models.PmzCapacity;
import ar.com.fennoma.paymentezsdk.models.PmzCategory;
import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;
import ar.com.fennoma.paymentezsdk.models.PmzItem;
import ar.com.fennoma.paymentezsdk.models.PmzMenu;
import ar.com.fennoma.paymentezsdk.models.PmzOrder;
import ar.com.fennoma.paymentezsdk.models.PmzSession;
import ar.com.fennoma.paymentezsdk.models.PmzStore;

public class API {

    public interface ServiceCallback<T> {
        void onSuccess(T response);
        void onError(PmzErrorMessage error);
        void onFailure();
        void sessionExpired();
    }

    public static void getStores(final ServiceCallback<List<PmzStore>> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<List<PmzStore>>() {
            @Override
            public List<PmzStore> callService() throws PmzException {
                return Services.getStores();
            }
        }).execute();
    }

    public static void getSession(final PmzSession session, final ServiceCallback<String> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<String>() {
            @Override
            public String callService() throws PmzException {
                return Services.getToken(session);
            }
        }).execute();
    }

    public static void getMenu(final Long storeId, ServiceCallback<PmzMenu> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzMenu>() {
            @Override
            public PmzMenu callService() throws PmzException {
                return Services.getMenu(storeId);
            }
        }).execute();
    }

    public static void startOrder(final PmzOrder starter, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.startOrder(starter);
            }
        }).execute();
    }

    public static void getCapacities(final ServiceCallback<List<PmzCapacity>> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<List<PmzCapacity>>() {
            @Override
            public List<PmzCapacity> callService() throws PmzException {
                return Services.getCapacities();
            }
        }).execute();
    }

    public static void getOrder(final Long orderId, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.getOrder(orderId);
            }
        }).execute();
    }

    public static void addItemWithConfigurations(final PmzItem orderId, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.addItemWithConfig(orderId);
            }
        }).execute();
    }
}
