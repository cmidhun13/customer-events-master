package com.syzegee.customer.events.enums;

import com.syzegee.customer.events.entity.CustomerStatus;

@FunctionalInterface
public interface ICustomerStatus<T> {
    T updateStatus(T x);
}
