package com.yan.missyou.optional;

import com.yan.missyou.exception.http.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Argus
 * @className Optional
 * @description: Optional的使用 它并不是解决了空指针 而是强制开发者去调用时候进行一个空值的判断
 *                  https://www.cnblogs.com/zhangboyu/p/7580262.html
 * @date 2020/3/18 16:18
 * @Version V1.0
 */
public class OptionalTest {

    @Test(expected = NoSuchElementException.class)
    public void testOptional1() {
        Optional<String> empty = Optional.empty();
        empty.get();
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        Optional<String> opt = Optional.of(null);
    }

    @Test
    public void testOptional2() {
        Optional<String> opt = Optional.ofNullable(null);
        //public void ifPresent(Consumer<? super String> consumer)
        opt.ifPresent(t -> System.out.println("123"));
        //t 为传入ofNullable的 参数值 如果为空Consumer 是不会执行的 消费者 需要消费一个值
        opt.ifPresent(t -> System.out.println("t"));
        // 为空 打印默认值
        String s = opt.orElse("默认值");
        System.out.println(s);
    }

    @Test
    public void testOptional3() {
        Optional<String> opt = Optional.of("dda");
        // public void ifPresent(Consumer<? super String> consumer)
        opt.ifPresent(t -> System.out.println("123"));
        // t 为传入ofNullable的 参数值
        opt.ifPresent(t -> System.out.println(t));
        // 假如为不为空 就取到Optional.of("dda")包裹的值 orElse能拿到optional的值
        String s = opt.orElse("默认值");
        System.out.println(s);
    }

    @Test
    public void testOptional4() {
        Optional<String> opt = Optional.of("test");
        // 假如为空 就抛出一个运行期异常 Supplier 提供者 提供了一个值 通常由返回值
        String s = opt.orElseThrow(() -> {
            return new NotFoundException(999);
        });
        System.out.println(s);
    }

    @Test
    public void testOptional5() {
        Optional<String> opt = Optional.of("test");
        // 假如为空 就抛出一个运行期异常 Supplier 提供者 提供了一个值 通常由返回值
        String s = opt.orElseThrow(() -> {
            return new NotFoundException(999);
        });
        System.out.println(s);
    }

    /**
     * map 返回的也是optional对象 那么想取出来值可以用orElse
     */
    @Test
    public void testOptional6() {
        Optional<String> opt = Optional.of("test");
        String s = opt.map(t-> t+"newString").orElse("默认值");
        System.out.println(s);
    }

    @Test
    public void testOptional7() {
        Optional<String> opt = Optional.ofNullable(null);
        // .filter 是返回Boolean值 不过
        String s = opt.map(t-> t+"newString").orElse("默认值");
        System.out.println(s);
    }
}