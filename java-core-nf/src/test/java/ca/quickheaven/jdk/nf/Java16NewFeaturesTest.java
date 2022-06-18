package ca.quickheaven.jdk.nf;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemClassLoader;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link <a href="https://www.baeldung.com/java-16-new-features">New Features in Java 16</a>}
 */
public class Java16NewFeaturesTest {


    interface HelloWorld {
        default String hello() {
            return "world";
        }
    }

    @Test
    public void givenAnInterfaceWithDefaulMethod_whenCreatingProxyInstance_thenCanInvokeDefaultMethod() throws Exception {
        Object proxy = Proxy.newProxyInstance(getSystemClassLoader(), new Class<?>[]{HelloWorld.class},
                (prox, method, args) -> {
                    if (method.isDefault()) {
                        return InvocationHandler.invokeDefault(prox, method, args);
                    }
                    return method.invoke(prox, args);
                }
        );
        Method method = proxy.getClass().getMethod("hello");
        assertThat(method.invoke(proxy)).isEqualTo("world");
    }

    @Test
    public void givenASpecificTime_whenFormattingUsingTheBSymbol_thenExpectVerbosePeriodOfDay() {
        LocalTime date = LocalTime.parse("15:25:08.690791");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");

        formatter = DateTimeFormatter.ofPattern("h BBBB");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");

        formatter = DateTimeFormatter.ofPattern("h BBBBB");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");
    }

    @Test
    public void givenAStream_whenCreatingANewListFromStream_thenCollectorsOrInbuiltFunctionAreEquivalent() {
        List<String> integersAsString = Arrays.asList("1", "2", "3");
        List<Integer> ints = integersAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> intsEquivalent = integersAsString.stream().map(Integer::parseInt).toList();
        assertThat(ints).isEqualTo(intsEquivalent);
    }

}
