package com.paymentez.plazez.sdk.services;

import java.util.List;

import com.paymentez.plazez.sdk.exceptions.PmzException;
import com.paymentez.plazez.sdk.models.PmzCapacity;
import com.paymentez.plazez.sdk.models.PmzErrorMessage;
import com.paymentez.plazez.sdk.models.PmzItem;
import com.paymentez.plazez.sdk.models.PmzMenu;
import com.paymentez.plazez.sdk.models.PmzOrder;
import com.paymentez.plazez.sdk.models.PmzPaymentData;
import com.paymentez.plazez.sdk.models.PmzSession;
import com.paymentez.plazez.sdk.models.PmzStore;

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

    public static void deleteItem(final PmzItem item, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.deleteItem(item);
            }
        }).execute();
    }

    public static void pay(final PmzPaymentData payment, final Long orderId, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.pay(payment, orderId);
            }
        }).execute();
    }

    public static void placeOrder(final PmzOrder order, final ServiceCallback<PmzOrder> callback) {
        new BaseTask<>(callback, new BaseTask.IServiceCaller<PmzOrder>() {
            @Override
            public PmzOrder callService() throws PmzException {
                return Services.placeOrder(order);
            }
        }).execute();
    }
}
