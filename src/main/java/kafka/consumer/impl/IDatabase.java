package kafka.consumer.impl;

import java.sql.Connection;


/**
 * Created by xiaotao on 2017/8/17.
 */
public interface IDatabase<T> {
      void insert(T t);

      int delete(T t);

      int update(T t);
}
