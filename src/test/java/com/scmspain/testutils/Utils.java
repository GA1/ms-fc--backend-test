package com.scmspain.testutils;

import javax.persistence.*;
import java.util.*;

public class Utils {

    public static TypedQuery<Long> createDummyTypedQuery(List<Long> ids) {
        return new TypedQuery<Long>() {
            @Override
            public List<Long> getResultList() {
                return ids;
            }

            @Override
            public Long getSingleResult() {
                return null;
            }

            @Override
            public TypedQuery<Long> setMaxResults(int maxResult) {
                return null;
            }

            @Override
            public TypedQuery<Long> setFirstResult(int startPosition) {
                return null;
            }

            @Override
            public TypedQuery<Long> setHint(String hintName, Object value) {
                return null;
            }

            @Override
            public <T> TypedQuery<Long> setParameter(Parameter<T> param, T value) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(String name, Object value) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(String name, Calendar value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(String name, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(int position, Object value) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(int position, Calendar value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setParameter(int position, Date value, TemporalType temporalType) {
                return null;
            }

            @Override
            public TypedQuery<Long> setFlushMode(FlushModeType flushMode) {
                return null;
            }

            @Override
            public TypedQuery<Long> setLockMode(LockModeType lockMode) {
                return null;
            }

            @Override
            public int executeUpdate() {
                return 0;
            }

            @Override
            public int getMaxResults() {
                return 0;
            }

            @Override
            public int getFirstResult() {
                return 0;
            }

            @Override
            public Map<String, Object> getHints() {
                return null;
            }

            @Override
            public Set<Parameter<?>> getParameters() {
                return null;
            }

            @Override
            public Parameter<?> getParameter(String name) {
                return null;
            }

            @Override
            public <T> Parameter<T> getParameter(String name, Class<T> type) {
                return null;
            }

            @Override
            public Parameter<?> getParameter(int position) {
                return null;
            }

            @Override
            public <T> Parameter<T> getParameter(int position, Class<T> type) {
                return null;
            }

            @Override
            public boolean isBound(Parameter<?> param) {
                return false;
            }

            @Override
            public <T> T getParameterValue(Parameter<T> param) {
                return null;
            }

            @Override
            public Object getParameterValue(String name) {
                return null;
            }

            @Override
            public Object getParameterValue(int position) {
                return null;
            }

            @Override
            public FlushModeType getFlushMode() {
                return null;
            }

            @Override
            public LockModeType getLockMode() {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> cls) {
                return null;
            }
        };
    }

}
