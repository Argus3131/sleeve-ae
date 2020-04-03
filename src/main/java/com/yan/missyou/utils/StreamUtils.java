
package com.yan.missyou.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 集合流操作工具类，使用并行流，需要保证函数式是无状态、线程安全的
 *
 * @author 伍磊
 */
public class StreamUtils {

    private static final String EMPTY = "";

    /**
     * 将集合转换为并行流
     *
     * @param source
     * @return
     */
    private static <T> Stream<T> toStream(Collection<T> source) {
        return source.stream().parallel();
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    private static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 将一个List拆分成多个子list，保留输入集合的null对象
     *
     * @param <T>     对象类型
     * @param source  对象列表
     * @param maxSize 子列表大小
     * @return 分组后的子列表
     */
    public static <T> List<List<T>> split(final List<T> source,
                                          final int maxSize) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be positive");
        }

        final int size = source.size();
        if (size <= maxSize) {
            return Arrays.asList(source);
        }

        final int step = (size + maxSize - 1) / maxSize;

        List<List<T>> splitList = IntStream.range(0, step).parallel()
                .mapToObj(i -> {
                    int fromIdx = i * maxSize;
                    int toIdx = fromIdx + maxSize;
                    if (toIdx > size) {
                        toIdx = size;
                    }
                    return source.subList(fromIdx, toIdx);
                }).collect(Collectors.toList());

        return splitList;
    }

    /**
     * 将一个集合拆分成多个子list，保留输入集合的null对象
     *
     * @param <T>     对象类型
     * @param source  对象集合
     * @param maxSize 子列表大小
     * @return 分组后的子列表
     */
    public static <T> List<List<T>> split(final Collection<T> source,
                                          final int maxSize) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be positive");
        }

        final int size = source.size();
        if (size <= maxSize) {
            if (!(source instanceof List)) {
                return Arrays.asList((List<T>) new ArrayList<T>(source));
            }
            return Arrays.asList((List<T>) source);
        }

        final int step = (size + maxSize - 1) / maxSize;

        List<List<T>> splitList = IntStream
                .range(0, step).parallel().mapToObj(i -> source.stream()
                        .skip(i * maxSize).limit(maxSize).collect(Collectors.toList()))
                .collect(Collectors.toList());

        return splitList;
    }

    /**
     * 将一个集合拆分成多个子Set，保留输入集合的null对象
     *
     * @param <T>     对象类型
     * @param source  对象集合
     * @param maxSize 子列表大小
     * @return 分组后的子列表
     */
    public static <T> List<Set<T>> splitToSet(final Collection<T> source,
                                              final int maxSize) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be positive");
        }

        final int size = source.size();
        if (size <= maxSize) {
            if (!(source instanceof Set)) {
                return Arrays.asList((Set<T>) new HashSet<T>(source));
            }
            return Arrays.asList((Set<T>) source);
        }

        final int step = (size + maxSize - 1) / maxSize;

        List<Set<T>> splitList = IntStream
                .range(0, step).parallel().mapToObj(i -> source.stream()
                        .skip(i * maxSize).limit(maxSize).collect(Collectors.toSet()))
                .collect(Collectors.toList());

        return splitList;
    }

    /**
     * 将集合按特定的键转换成map，满足一对一关系，过滤输入集合的null对象，若key重复则后者覆盖前者
     *
     * @param <K>       Map的key类型
     * @param <V>       Map的value类型
     * @param source    对象集合
     * @param keyMapper 值->key映射
     * @return
     */
    public static <K, V> Map<K, V> toMap(Collection<V> source,
                                         Function<V, K> keyMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null).collect(Collectors
                .toMap(keyMapper, Function.identity(), (key1, key2) -> key2));
    }

    /**
     * 将集合按特定的键转换成map，满足一对一关系，过滤输入集合的null对象，若key重复则后者覆盖前者
     *
     * @param <E>       输入元素类型
     * @param <K>       Map的key类型
     * @param <V>       Map的value类型
     * @param source    对象集合
     * @param keyMapper E->key映射
     * @param valMapper E->value映射
     * @return
     */
    public static <E, K, V> Map<K, V> toMap(Collection<E> source,
                                            Function<E, K> keyMapper, Function<E, V> valMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null).collect(
                Collectors.toMap(keyMapper, valMapper, (key1, key2) -> key2));
    }

    /**
     * 将集合筛选并按特定的键转换成map，满足一对一关系，过滤输入集合的null对象，若key重复则后者覆盖前者
     *
     * @param <K>       Map的key类型
     * @param <V>       Map的value类型
     * @param source    对象集合
     * @param predicate 筛选条件
     * @param keyMapper 值->key映射
     * @return
     */
    public static <K, V> Map<K, V> filterToMap(Collection<V> source,
                                               Predicate<V> predicate, Function<V, K> keyMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .collect(Collectors.toMap(keyMapper, Function.identity(),
                        (key1, key2) -> key2));
    }

    /**
     * 将集合转换成相同对象类型的列表，保留输入集合的null对象
     *
     * @param <T>    对象类型
     * @param source 对象集合
     * @return
     */
    public static <T> List<T> toList(Collection<T> source) {
        if (source instanceof List) {
            return (List<T>) source;
        }
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return new ArrayList<>(source);
    }

    /**
     * 将集合转换成特定对象类型的列表，过滤输入集合的null对象
     *
     * @param <T>    转换的目标类型
     * @param <R>    原始类型
     * @param source 对象集合
     * @param mapper 类型转换映射
     * @return
     */
    public static <T, R> List<T> toList(Collection<R> source,
                                        Function<R, T> mapper) {
        return convert(source, mapper);
    }

    /**
     * 将集合转换成相同对象类型的集合，保留输入集合的null对象
     *
     * @param <T>    对象类型
     * @param source 对象集合
     * @return
     */
    public static <T> Set<T> toSet(Collection<T> source) {
        if (source instanceof Set) {
            return (Set<T>) source;
        }
        if (isEmpty(source)) {
            return new HashSet<>(0);
        }
        return new HashSet<>(source);
    }

    /**
     * 将集合转换成特定对象类型的集合，过滤输入集合的null对象
     *
     * @param <T>    转换的目标类型
     * @param <R>    原始类型
     * @param source 对象集合
     * @param mapper 类型转换映射
     * @return
     */
    public static <T, R> Set<T> toSet(Collection<R> source,
                                      Function<R, T> mapper) {
        if (isEmpty(source)) {
            return new HashSet<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .collect(Collectors.toSet());
    }

    /**
     * 去重，过滤集合中的重复对象，过滤输入集合的null对象
     *
     * @param <T>    对象类型
     * @param source 对象集合
     * @param cmp    比较器
     * @return
     */
    public static <T> List<T> distinct(Collection<T> source,
                                       Comparator<T> cmp) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(cmp)),
                        ArrayList::new));
    }

    /**
     * 将集合按特定的键分组, 满足一对多关系，过滤输入集合的null对象
     *
     * @param <K>       Map的key类型
     * @param <V>       Map的value列表的元素类型
     * @param source    对象集合
     * @param keyMapper 值->key映射
     * @return
     */
    public static <K, V> Map<K, List<V>> group(Collection<V> source,
                                               Function<V, K> keyMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null)
                .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * 将集合过滤并按特定的键分组, 满足一对多关系，过滤输入集合的null对象
     *
     * @param <K>       Map的key类型
     * @param <V>       Map的value列表的元素类型
     * @param source    对象集合
     * @param predicate 筛选条件
     * @param keyMapper 值->key映射
     * @return
     */
    public static <K, V> Map<K, List<V>> filterGroup(Collection<V> source,
                                                     Predicate<V> predicate, Function<V, K> keyMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * 将集合排序后按特定的键分组, 满足一对多关系，过滤输入集合的null对象
     *
     * @param <K>        Map的key类型
     * @param <V>        Map的value列表的元素类型
     * @param source     对象集合
     * @param comparator 比较器
     * @param keyMapper  值->key映射
     * @return
     */
    public static <K, V> Map<K, List<V>> sortGroup(Collection<V> source,
                                                   Comparator<V> comparator, Function<V, K> keyMapper) {
        if (isEmpty(source)) {
            return new HashMap<>(0);
        }
        return toStream(source).filter(a -> a != null).sorted(comparator)
                .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * 按特定规则筛选集合元素，返回List，过滤输入集合的null对象
     *
     * @param <T>       对象类型
     * @param source    对象集合
     * @param predicate 筛选条件
     * @return
     */
    public static <T> List<T> filter(Collection<T> source,
                                     Predicate<T> predicate) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 获取第一个对象
     *
     * @param <T>    对象类型
     * @param source 对象集合
     * @return
     */
    public static <T> T getFirst(Collection<T> source) {
        if (isEmpty(source)) {
            return null;
        }
        return toList(source).get(0);
    }

    /**
     * 获取第一个对象
     *
     * @param <T>    对象类型
     * @param source 对象集合
     * @return
     */
    public static <T> T getLast(Collection<T> source) {
        if (isEmpty(source)) {
            return null;
        }
        return toList(source).get(source.size() - 1);
    }

    /**
     * 按匹配条件返回第一个对象，过滤输入集合的null对象
     *
     * @param <T>       对象类型
     * @param source    对象集合
     * @param predicate 匹配条件
     * @return
     */
    public static <T> T findFirst(Collection<T> source,
                                  Predicate<T> predicate) {
        if (isEmpty(source)) {
            return null;
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .findFirst().orElse(null);
    }

    /**
     * 将集合按匹配条件筛选并排序返回第一个对象，过滤输入集合的null对象
     *
     * @param <T>        对象类型
     * @param source     对象集合
     * @param predicate  匹配条件
     * @param comparator 比较器
     * @return
     */
    public static <T> T sortFindFirst(Collection<T> source,
                                      Predicate<T> predicate, Comparator<T> comparator) {
        if (isEmpty(source)) {
            return null;
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .sorted(comparator).findFirst().orElse(null);
    }

    /**
     * 按匹配条件返回某一个对象，过滤输入集合的null对象
     *
     * @param <T>       对象类型
     * @param source    对象集合
     * @param predicate 匹配条件
     * @return
     */
    public static <T> T findAny(Collection<T> source, Predicate<T> predicate) {
        if (isEmpty(source)) {
            return null;
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .findAny().orElse(null);
    }

    /**
     * 按特定规则移除集合元素
     *
     * @param <T>       对象类型
     * @param source    对象集合
     * @param predicate 移除条件
     * @return
     */
    public static <T> void remove(Collection<T> source,
                                  Predicate<T> predicate) {
        if (isEmpty(source)) {
            return;
        }
        source.removeIf(predicate);
    }

    /**
     * 将集合转换成特定对象列表，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>    转换的目标类型
     * @param <R>    原始类型
     * @param source 对象集合
     * @param mapper 类型转换映射
     * @return
     */
    public static <T, R> List<T> convert(Collection<R> source,
                                         Function<R, T> mapper) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null).collect(Collectors.toList());
    }

    /**
     * 将集合转换成特定对象列表并排序，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>        转换的目标类型
     * @param <R>        原始类型
     * @param source     对象集合
     * @param comparator 比较器
     * @param mapper     类型转换映射
     * @return
     */
    public static <T, R> List<T> sortConvert(Collection<R> source,
                                             Comparator<R> comparator, Function<R, T> mapper) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).sorted(comparator)
                .map(mapper).filter(a -> a != null).collect(Collectors.toList());
    }

    /**
     * 将集合转换成特定对象列表并排序，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>        转换的目标类型
     * @param <R>        原始类型
     * @param source     对象集合
     * @param mapper     类型转换映射
     * @param comparator 比较器
     * @return
     */
    public static <T, R> List<T> convertSort(Collection<R> source,
                                             Function<R, T> mapper, Comparator<T> comparator) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null).sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * 将集合每个元素分别转换成特定对象列表，然后合并成1个列表，过滤输入集合的null对象和转换后的null对象
     *
     * @param <T>    转换的目标类型
     * @param <R>    原始类型
     * @param source 对象集合
     * @param mapper 类型转换映射
     * @return
     */
    public static <T, R> List<T> convertMerge(Collection<R> source,
                                              Function<R, ? extends Collection<T>> mapper) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .flatMap(x -> x.stream()).filter(a -> a != null)
                .collect(Collectors.toList());
    }

    /**
     * 将集合转换成特定对象列表，过滤输入集合的null对象和转换后的null对象，过滤重复对象
     *
     * @param <T>    转换的目标类型
     * @param <R>    原始类型
     * @param source 对象集合
     * @param mapper 类型转换映射
     * @return
     */
    public static <T, R> List<T> convertDistinct(Collection<R> source,
                                                 Function<R, T> mapper, Comparator<T> cmp) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(cmp)),
                        ArrayList::new));
    }

    /**
     * 对集合筛选并转换成特定对象列表，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>       转换的目标类型
     * @param <R>       原始类型
     * @param source    对象集合
     * @param predicate 筛选条件
     * @param mapper    类型转换映射
     * @return
     */
    public static <T, R> List<T> filterConvert(Collection<R> source,
                                               Predicate<R> predicate, Function<R, T> mapper) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).filter(predicate)
                .map(mapper).filter(a -> a != null).collect(Collectors.toList());
    }

    /**
     * 对集合转换成特定对象列表并筛选，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>       转换的目标类型
     * @param <R>       原始类型
     * @param source    对象集合
     * @param mapper    类型转换映射
     * @param predicate 筛选条件
     * @return
     */
    public static <T, R> List<T> convertFilter(Collection<R> source,
                                               Function<R, T> mapper, Predicate<T> predicate) {
        if (isEmpty(source)) {
            return new ArrayList<>(0);
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null).filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 将集合的元素归并，过滤输入集合的null对象
     *
     * @param <T>         对象类型
     * @param source      对象集合
     * @param accumulator 归并操作
     * @return
     */
    public static <T> T reduce(Collection<T> source,
                               BinaryOperator<T> accumulator) {
        if (isEmpty(source)) {
            return null;
        }
        return toStream(source).filter(a -> a != null).reduce(accumulator)
                .orElse(null);
    }

    /**
     * 将集合的元素转换后归并，过滤输入集合的null对象和转换后null对象
     *
     * @param <T>         归并的目标类型
     * @param <R>         原始类型
     * @param source      对象集合
     * @param mapper      类型转换映射
     * @param accumulator 归并操作
     * @return
     */
    public static <T, R> T convertReduce(Collection<R> source,
                                         Function<R, T> mapper, BinaryOperator<T> accumulator) {
        if (isEmpty(source)) {
            return null;
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null).reduce(accumulator).orElse(null);
    }

    /**
     * 将集合的元素以特定分隔符拼接成字符串，过滤输入集合的null对象和转换后null对象
     *
     * @param <R>       原始类型
     * @param source    对象集合
     * @param separator 分隔符
     * @param mapper    类型转换映射
     * @return
     */
    public static <R> String joinString(Collection<R> source, String separator,
                                        Function<R, String> mapper) {
        if (isEmpty(source)) {
            return EMPTY;
        }
        return toStream(source).filter(a -> a != null).map(mapper)
                .filter(a -> a != null)
                .reduce((a, b) -> String.format("%s%s%s", a, separator, b))
                .orElse(EMPTY);
    }

    /**
     * 将集合元素以特定分隔符拼接成的字符串分裂还原，过滤转换后null对象
     *
     * @param source    拼接字符串
     * @param separator 分隔符
     * @param mapper    类型转换映射
     * @return
     */
    public static <T> List<T> splitString(String source, String separator,
                                          Function<String, T> mapper) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>(0);
        }
        return Stream.of(source.split(separator)).parallel().map(mapper)
                .filter(a -> a != null).collect(Collectors.toList());
    }

    /**
     * 一对一匹配查询，过滤查询结果的null对象
     *
     * @param <P>             参数类型
     * @param <K>             参数-结果关联键类型
     * @param <R>             结果类型
     * @param params          输入参数集
     * @param maxSize         查询参数最大数量，用于分割查询参数集合
     * @param paramKeyMapper  参数key映射
     * @param resultKeyMapper 结果key映射
     * @param matcher         匹配成功的消费
     * @param resultMapper    结果集查询映射
     */
    public static <P, K, R> void matchQuery(Collection<P> params,
                                            final int maxSize, Function<P, K> paramKeyMapper,
                                            Function<R, K> resultKeyMapper, BiConsumer<P, R> matcher,
                                            Function<List<P>, List<R>> resultMapper) {
        //参数检验
        if (isEmpty(params)) {
            return;
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("queryStep must be positive");
        }

        //按查询步长分批查询
        final int size = params.size();
        final int step = (size + maxSize - 1) / maxSize;
        IntStream
                .range(0, step).parallel().mapToObj(i -> params.stream()
                .skip(i * maxSize).limit(maxSize).collect(Collectors.toList()))
                .forEach(subParams -> {
                    //根据参数查询结果
                    List<R> resultList = resultMapper.apply(subParams);
                    if (isEmpty(resultList)) {
                        return;
                    }

                    //构建结果映射
                    Map<K, R> resultMap = toStream(resultList)
                            .filter(a -> a != null)
                            .collect(Collectors.toMap(resultKeyMapper,
                                    Function.identity(), (key1, key2) -> key2));

                    //参数-结果匹配
                    toStream(subParams).forEach(param -> {
                        R result = resultMap.get(paramKeyMapper.apply(param));
                        if (result != null) {
                            matcher.accept(param, result);
                        }
                    });
                });
    }

    /**
     * 一对多匹配查询，过滤查询结果的null对象
     *
     * @param <P>             参数类型
     * @param <K>             参数-结果关联键类型
     * @param <R>             结果类型
     * @param params          输入参数集
     * @param maxSize         查询参数最大数量，用于分割查询参数集合
     * @param paramKeyMapper  参数key映射
     * @param resultKeyMapper 结果key映射
     * @param matcher         匹配成功的消费
     * @param resultMapper    结果集查询映射
     */
    public static <P, K, R> void matchesQuery(Collection<P> params,
                                              final int maxSize, Function<P, K> paramKeyMapper,
                                              Function<R, K> resultKeyMapper, BiConsumer<P, List<R>> matcher,
                                              Function<List<P>, List<R>> resultMapper) {
        //参数检验
        if (isEmpty(params)) {
            return;
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("queryStep must be positive");
        }

        //按查询步长分批查询
        final int size = params.size();
        final int step = (size + maxSize - 1) / maxSize;
        IntStream
                .range(0, step).parallel().mapToObj(i -> params.stream()
                .skip(i * maxSize).limit(maxSize).collect(Collectors.toList()))
                .forEach(subParams -> {
                    //根据参数查询结果
                    List<R> resultList = resultMapper.apply(subParams);
                    if (isEmpty(resultList)) {
                        return;
                    }

                    //构建结果映射
                    Map<K, List<R>> resultMap = toStream(resultList)
                            .filter(a -> a != null)
                            .collect(Collectors.groupingBy(resultKeyMapper));

                    //参数-结果匹配
                    toStream(subParams).forEach(param -> {
                        List<R> result = resultMap.get(paramKeyMapper.apply(param));
                        if (result != null) {
                            matcher.accept(param, result);
                        }
                    });
                });
    }
}
