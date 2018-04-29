package com.learning.library.repository.common;

import org.junit.Ignore;

@Ignore
public interface DBCommand<T> {
    T execute();
}
