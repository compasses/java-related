package jet.mq.elastic.document;

/**
 * Created by I311352 on 10/25/2016.
 */
@FunctionalInterface
public interface ExtractUpdateFields<R, T> {
    R getUpdateFields(T t);
}

