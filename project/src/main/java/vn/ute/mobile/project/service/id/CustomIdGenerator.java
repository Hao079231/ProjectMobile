package vn.ute.mobile.project.service.id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {
  private static final Map<String, AtomicLong> counters = new ConcurrentHashMap<>();
  private static final String PREFIX = "UTE";
  private static final long MAX_SUFFIX = 99999999L;

  @Override
  public Object generate(SharedSessionContractImplementor session, Object o) {
    String entityName = o.getClass().getSimpleName();

    // Lấy hoặc tạo counter cho entity này
    AtomicLong counter = counters.computeIfAbsent(entityName, k -> new AtomicLong(0));

    long nextValue = counter.incrementAndGet();

    if (nextValue > MAX_SUFFIX) {
      throw new RuntimeException("Exceeded the maximum ID limit for " + entityName + ": " + PREFIX + MAX_SUFFIX);
    }

    // Tạo ID dạng String: PREFIX + entityName + nextValue
    return PREFIX + entityName + String.format("%08d", nextValue);
  }
}