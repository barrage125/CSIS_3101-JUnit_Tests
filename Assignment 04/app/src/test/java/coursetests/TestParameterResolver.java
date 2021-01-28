// File:    TestParameterResolver.java
// Project: CSIS3101 JUnit Assignment 04
// Author:  Alexander Lotz
// History: Version 1.0 January 26, 2021

package coursetests;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TestParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return true;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().getType() == String.class)
        {
            return parameterContext.getParameter().toString();
        }
        return null;
    }
}
