package vn.ute.mobile.project.service.id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CustomIdGenerator implements IdentifierGenerator {
  private static final Map<String, AtomicLong> counters = new ConcurrentHashMap<>();
  private static final String PREFIX = "UTE";
  private static final long MAX_SUFFIX = 99999999L;

  @Override
  public Object generate(SharedSessionContractImplementor session, Object entity) {
    String entityName = entity.getClass().getSimpleName();

    // Lấy hoặc tạo counter cho entity này
    AtomicLong counter = counters.computeIfAbsent(entityName, k -> new AtomicLong(0));

    String generatedId;
    boolean idExists;

    do {
      long nextValue = counter.incrementAndGet();

      if (nextValue > MAX_SUFFIX) {
        throw new RuntimeException("Exceeded the maximum ID limit for " + entityName + ": " + PREFIX + MAX_SUFFIX);
      }

      // Tạo ID dạng String: PREFIX + entityName + nextValue
      generatedId = PREFIX + entityName + String.format("%08d", nextValue);

      // Kiểm tra xem ID đã tồn tại trong cơ sở dữ liệu hay chưa
      idExists = checkIdExists(session, entity.getClass(), generatedId);

    } while (idExists); // Lặp lại cho đến khi tìm được ID không trùng

    return generatedId;
  }

  private boolean checkIdExists(SharedSessionContractImplementor session, Class<?> entityClass, String id) {
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<Long> query = cb.createQuery(Long.class);
    Root<?> root = query.from(entityClass);

    query.select(cb.count(root)).where(cb.equal(root.get("id"), id));

    Long count = session.createQuery(query).getSingleResult();
    return count > 0;
  }
}